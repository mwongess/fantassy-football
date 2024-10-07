package edu.uwm.cs351;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * The Class Team.
 */
public class Team {

	private Comparator<Player> priority;
	private Player dummy;
	private String teamName;

	private static Consumer<String> reporter = (s) -> System.out.println("Invariant error: " + s);

	private boolean report(String error) {
		reporter.accept(error);
		return false;
	}

	private boolean wellFormed() {
		// 1. The team name is not null
		if (teamName == null)
			return report("team name is null");

		// 2. dummy cannot be null
		if (dummy == null)
			return report("dummy is null");

		// 3. The dummy should have no position (null),
		// the asciibetically first name (the empty string),
		// and the highest score possible.
		if (dummy.getPosition() != null)
			return report("dummy has a position");
		if (!dummy.getName().equals(""))
			return report("dummy doesn't have empty name");
		if (dummy.getScore() != Integer.MAX_VALUE)
			return report("dummy doesn't have max score");

		// 4. comparator is not null
		if (priority == null)
			return report("priority comparator is null");

		// 5. Check if dummy is actually first in priority
		Player current = dummy.getNext();
		while (current != null) {
			if (priority.compare(current, dummy) >= 0) {
				return report("found a player that should come before dummy: " + current);
			}
			// Every node with a next is the previous of its next
			if (current.getNext() != null && current.getNext().getPrevious() != current) {
				return report("broken link between " + current + " and " + current.getNext());
			}
			current = current.getNext();
		}

		return true;
	}

	/**
	 * Instantiates a new Team with the given priority comparator.
	 * 
	 * @param c the priority comparator this Team will use
	 * @param n the name of the team.
	 */
	public Team(Comparator<Player> c, String n) {
		if (c == null)
			throw new NullPointerException("comparator cannot be null");
		if (n == null)
			throw new NullPointerException("team name cannot be null");

		priority = c;
		teamName = n;

		// Create dummy player
		dummy = new Player("", Integer.MAX_VALUE, null);

		assert wellFormed() : "invariant fails at end of constructor";
	}

	private Team(boolean ignored) {
	} // DO NOT CHANGE THIS

	/**
	 * Adds a Player to this Team.
	 * It should be added according to the priority of the team.
	 * 
	 * @param t the Player to add, must not be null and must not be in another team.
	 * @throws IllegalArgumentException if the player is already in a team or if the
	 *                                  added player would come before the
	 *                                  dummy player (a player with an empty name,
	 *                                  highest score possible and null position).
	 */
	public void add(Player t) {
		assert wellFormed() : "invariant fails at beginning of add";

		if (t == null)
			throw new NullPointerException("player cannot be null");

		if (t.getPrevious() != null || t.getNext() != null)
			throw new IllegalArgumentException("player is already in a team");

		if (priority.compare(t, dummy) >= 0)
			throw new IllegalArgumentException("player would come before dummy");

		dummy.addInPriority(t, priority);

		assert wellFormed() : "invariant fails at end of add";
	}

	/**
	 * Change the priority for this list of players,
	 * and then make sure the order reflects the new priority.
	 * 
	 * @param p priority to use, must not be null.
	 */
	public void setPriority(Comparator<Player> p) {
		assert wellFormed() : "invariant broken before set priority";
		if (p == null)
			throw new NullPointerException("need a priority!");
		priority = p;
		refreshPriority();
		assert wellFormed() : "invariant failed during set priority";
	}

	/**
	 * Some elements of the players may have changed the
	 * way the players are ordered. This method
	 * reorders the list to fix things up.
	 * This will use insertion sort for efficiency.
	 * In particular if only a constant number of players
	 * are in the wrong position, this method will take linear time.
	 * If the priority ends up as "perverse" (something shows up
	 * before the dummy) we discard all team members.
	 * (This is not actually ideal for an ADT.)
	 */
	public void refreshPriority() {
		assert wellFormed() : "invariant broken before refresh priority";
		dummy.sortByPriority(priority);
		if (dummy.getPrevious() != null) {
			// Suddenly the priority shows up as perverse.
			dummy.remove(); // actually removes everyone else.
		}
		assert wellFormed() : "invariant failed during refresh priority";
	}

	@Override // implementation
	public String toString() {
		return teamName;
	}

	/**
	 * Compute the number of players.
	 * 
	 * @return number of players
	 */
	public int size() {
		int count = 0;
		Player current = dummy.getNext();
		while (current != null) {
			count++;
			current = current.getNext();
		}
		return count;
	}

	/**
	 * Return the n'th player (0-based).
	 * 
	 * @param index index of the element to return
	 * @return the player at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	public Player get(int index) throws IndexOutOfBoundsException {
		if (index < 0)
			throw new IndexOutOfBoundsException("index cannot be negative");

		Player current = dummy.getNext();
		for (int i = 0; i < index && current != null; i++) {
			current = current.getNext();
		}

		if (current == null)
			throw new IndexOutOfBoundsException("index " + index + " is out of bounds");
		return current;
	}

	/**
	 * Returns team's total score
	 * 
	 * @return the sum of all player's scores on this team
	 */
	public int totalScore() {
		assert wellFormed() : "invariant fails at beginning of totalScore";
		int score = 0;
		Player current = dummy.getNext();
		while (current != null) {
			score += current.getScore();
			current = current.getNext();
		}
		return score;
	}

	/**
	 * prints this team's players
	 */
	public void printPlayers() {
		for (Player i = dummy.getNext(); i != null; i = i.getNext()) {
			System.out.println(i);
		}
	}

	/**
	 * Used for testing the invariant. Do not change this code.
	 */
	public static class Spy {
		/**
		 * Return the sink for invariant error messages
		 * 
		 * @return current reporter
		 */
		public Consumer<String> getReporter() {
			return reporter;
		}

		/**
		 * Change the sink for invariant error messages.
		 * 
		 * @param r where to send invariant error messages.
		 */
		public void setReporter(Consumer<String> r) {
			reporter = r;
		}

		/**
		 * Create a debugging instance of the main class
		 * with a particular data structure.
		 * 
		 * @param d the dummy
		 * @param c the comparator
		 * @param n the name
		 * @return a new instance with the given data structure
		 */
		public Team newInstance(Player d, Comparator<Player> c, String n) {
			Team result = new Team(false);
			result.dummy = d;
			result.priority = c;
			result.teamName = n;
			return result;
		}

		/**
		 * Return whether debugging instance meets the
		 * requirements on the invariant.
		 * 
		 * @param t instance of to use, must not be null
		 * @return whether it passes the check
		 */
		public boolean wellFormed(Team t) {
			return t.wellFormed();
		}
	}
}
