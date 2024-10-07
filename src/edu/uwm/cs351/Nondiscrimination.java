package edu.uwm.cs351;

import java.util.Comparator;

/**
 * The class Nondiscrimination.
 * A player priority mechanism that does not discriminate on the basis
 * of score or name or position. All players are equal
 */
public class Nondiscrimination implements Comparator<Player> {

	@Override // required
	public int compare(Player o1, Player o2) {
		// All players are considered equal in this comparator
		return 0;
	}

	@Override // implementation
	public String toString() {
		return "Nondiscrimination";
	}

	private static Comparator<Player> instance = new Nondiscrimination();

	/**
	 * Gets a single instance of Nondiscrimination comparator.
	 * 
	 * @return a single instance of Nondiscrimination comparator
	 */
	public static Comparator<Player> getInstance() {
		return instance;
	}
}
