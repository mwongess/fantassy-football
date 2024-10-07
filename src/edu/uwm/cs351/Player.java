package edu.uwm.cs351;

import java.util.Comparator;

/**
 * The Class Player.
 */
public class Player {

	/** The name of this player. */
	final private String name;

	/** How many points this player has scored */
	private int score;

	/** This player's position */
	private final Position position;

	/** The links to the previous and next players. */
	private Player prev, next;

	/**
	 * Instantiates a new Player.
	 * 
	 * @param name     the name of the player, must not be null
	 * @param score    how many points this player has scored
	 * @param position the position of the player, where null means can play ANY
	 *                 position
	 */
	public Player(String name, int score, Position position) {
		if (name == null)
			throw new NullPointerException("name cannot be null");
		this.name = name;
		this.position = position;
		checkParameter("score", score);
		this.score = score;
	}

	private static void checkParameter(String name, int p) {
		if (p < 0)
			throw new IllegalArgumentException(name + " must be positive value");
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets this player's score.
	 * 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score.
	 * 
	 * @param the score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Gets this player's position.
	 * 
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Gets the previous player.
	 * 
	 * @return the previous player
	 */
	public Player getPrevious() {
		return prev;
	}

	/**
	 * Gets the next player.
	 * 
	 * @return the next player
	 */
	public Player getNext() {
		return next;
	}

	/**
	 * Remove this item from its list.
	 * This player will be completely disconnected from any other players.
	 */
	public void remove() {
		// Handle connections for previous player
		if (prev != null) {
			prev.next = next;
		}

		// Handle connections for next player
		if (next != null) {
			next.prev = prev;
		}

		// Disconnect this player
		prev = null;
		next = null;
	}

	/**
	 * Add another player into this player's team by priority order.
	 * 
	 * @param p        the player to add to our list, must not be null
	 * @param priority comparator of players in the list, must not be null
	 */
	public void addInPriority(Player p, Comparator<Player> priority) {
		if (p == null)
			throw new NullPointerException("player cannot be null");
		if (priority == null)
			throw new NullPointerException("priority cannot be null");

		int comparison = priority.compare(p, this);

		if (comparison < 0) {
			// p should go before this player
			if (prev != null) {
				// Recursively try to add before prev
				prev.addInPriority(p, priority);
			} else {
				// Insert at beginning
				p.next = this;
				p.prev = null;
				this.prev = p;
			}
		} else {
			// p should go after this player
			if (next != null) {
				int nextComparison = priority.compare(p, next);
				if (nextComparison < 0) {
					// Insert between this and next
					p.next = next;
					p.prev = this;
					next.prev = p;
					this.next = p;
				} else {
					// Recursively try to add after next
					next.addInPriority(p, priority);
				}
			} else {
				// Insert at end
				p.prev = this;
				p.next = null;
				this.next = p;
			}
		}
	}

	/**
	 * Sort players after this one according to the priority.
	 * 
	 * @param c comparator to use, must not be null
	 */
	public void sortByPriority(Comparator<Player> c) {
		if (c == null)
			throw new NullPointerException("comparator cannot be null");

		// While we have a next player
		while (next != null) {
			int comparison = c.compare(this, next);

			if (comparison > 0) {
				// Next player should come before this one
				// Remove next from list
				Player toMove = next;
				next = toMove.next;
				if (next != null) {
					next.prev = this;
				}
				toMove.next = null;
				toMove.prev = null;

				// Recursively add back in correct position
				if (prev != null) {
					prev.addInPriority(toMove, c);
				} else {
					// We're at the start, so just make it the new start
					toMove.next = this;
					this.prev = toMove;
				}
			} else {
				// Everything up to next is sorted
				// Recursively sort from next onward
				next.sortByPriority(c);
				break;
			}
		}
	}
}