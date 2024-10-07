package edu.uwm.cs351;

import java.util.Comparator;

/**
 * The class HigherScore.
 * 
 * Returns t1 > t2 if t1 has more 'value' than t2.
 * We define value as the score a player has.
 * 
 */

//NB: Don't change this class!
public class HigherScore implements Comparator<Player> {

	@Override // required
	public int compare(Player o1, Player o2) {
		return o1.getScore() - o2.getScore();
	}

	@Override // implementation
	public String toString() {
		return "HigherScore";}
	
	private static Comparator<Player> instance = new HigherScore();
	
	/**
	 * Gets a single instance of HigherScore comparator.
	 * @return a single instance of HigherScore comparator
	 */
	public static Comparator<Player> getInstance() { return instance; }
}
