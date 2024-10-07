package edu.uwm.cs351;

import java.util.Comparator;

/**
 * The class Alphabetical.
 * 
 * Returns t1 > t2 if t1 has more 'value' than t2.
 * We define value lexicographically
 * Players should not have the same name, 
 * but if they do the first player has higher precedence
 * 
 */

//NB: Don't change this class!
public class Alphabetical implements Comparator<Player> {

	@Override // required
	public int compare(Player o1, Player o2) {
		return o2.getName().compareTo(o1.getName());
	}

	@Override // implementation
	public String toString() {
		return "Alphabetical";}
	
	private static Comparator<Player> instance = new Alphabetical();
	
	/**
	 * Gets a single instance of the comparator.
	 * @return a single instance of the comparator
	 */
	public static Comparator<Player> getInstance() { return instance; }
}
