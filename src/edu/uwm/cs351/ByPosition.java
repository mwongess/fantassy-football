package edu.uwm.cs351;

import java.util.Comparator;

/**
 * Sort by position, according to the order in the enumeration:
 * earlier is higher priority.
 * Players that can play any position are ranked highest priority (first).
 */
public class ByPosition implements Comparator<Player> {

    @Override // required
    public int compare(Player o1, Player o2) {
        // Handle null cases first
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Cannot compare null players");
        }

        Position p1 = o1.getPosition();
        Position p2 = o2.getPosition();

        // Players with null position (can play any position) have highest priority
        if (p1 == null && p2 == null)
            return 0;
        if (p1 == null)
            return -1; // o1 has higher priority
        if (p2 == null)
            return 1; // o2 has higher priority

        // Use Integer.compare for safe comparison
        return Integer.compare(p1.ordinal(), p2.ordinal());
    }

    @Override // implementation
    public String toString() {
        return "ByPosition";
    }

    private static Comparator<Player> instance = new ByPosition();

    /**
     * Gets a single instance of this comparator.
     * 
     * @return a single instance of this comparator
     */
    public static Comparator<Player> getInstance() {
        return instance;
    }
}
