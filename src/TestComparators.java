import java.util.Comparator;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Nondiscrimination;
import edu.uwm.cs351.Player;
import edu.uwm.cs351.Position;
import edu.uwm.cs351.Alphabetical;
import edu.uwm.cs351.ByPosition;
import edu.uwm.cs351.HigherScore;

public class TestComparators extends LockedTestCase {

	private Comparator<Player> c;
	private Player[] players;
	private Player p1, p2, p3,p4;

	@Override
	protected void setUp() {
		players = new Player[] {
				new Player("",  10000, Position.Quarterback),
				new Player("C", 1, Position.Defensive_Line),
			    new Player("B", 2, Position.Kicker),
			    new Player("C", 3, Position.Tight_End),
			    new Player("B", 4, Position.Runningback),
			    new Player("A", 2, Position.Wide_Receiver),
			    new Player("AA", 0, null),
				new Player("", Integer.MAX_VALUE, null)
		};
	}

	protected void testComparator(Comparator<Player> c, Player[]... playerClasses) {
		for (int i=0; i < playerClasses.length; ++i) {
			for (Player t1 : playerClasses[i]) {
				for (int j=0; j < playerClasses.length; ++j) {
					for (Player t2 : playerClasses[j]) {
						try {
							int expected = i-j;
							int result = c.compare(t1, t2);
							testComparison(c + "(" + t1 + "," + t2 + ")", expected, result);
						} catch (RuntimeException ex) {
							assertFalse(c + "(" + t1 + "," + t2 + ") threw exception " +ex, true);
						}
					}
				}
			}
		}
	}

	private void testComparison(String explain, int expected, int result) {
		if (result < 0 && expected < 0 || result > 0 && expected > 0 || result == 0 && expected == 0) {
			assertTrue(true);
		} else {
			assertFalse(explain + "=" + result + ", not " + asString(expected) + " 0",true);
		}
	}

	private String asString(int c) {
		if (c == 0) return "=";
		else if (c < 0) return "<";
		else return ">";
	}

	public void test00(){
		// For unlocking Comparator tests...
		// If c.compare(a, b) > 0 then enter: >
		// If c.compare(a, b) = 0 then enter: =
		// If c.compare(a, b) < 0 then enter: <

		//Comparator Used: HigherScore: higher score has higher priority
		c = HigherScore.getInstance();

		//Constructor args are:
		//       Player( name				Score	Position)
		p1 = new Player("Aaron Rogers",		12, 	Position.Quarterback);
		p2 = new Player("Cam Newton", 		5, 		Position.Defensive_Line);
		p3 = new Player("Baker Mayfield", 	2, 		Position.Kicker);
		p4 = new Player("Deshaun Watson", 	2, 		Position.Tight_End);

		assertEquals("p2 _ p3", Ts(1873034446),    asString(c.compare(p2, p3)));
		assertEquals("p3 _ p4", Ts(990943628),    asString(c.compare(p3, p4)));
		assertEquals("p2 _ p1", Ts(1364225945),    asString(c.compare(p2, p1)));
	}

	public void test01(){
		// For unlocking Comparator tests...
		// If c.compare(a, b) > 0 then enter: >
		// If c.compare(a, b) = 0 then enter: =
		// If c.compare(a, b) < 0 then enter: <

		//Comparator Used: ALphabetical: earlier alphabetical mean HIGHER priority
		c = Alphabetical.getInstance();
		// NB: This is counter-intuitive: "A" > "B"

		//Constructor args are:
		//       Player( name				Score	Position)
		p1 = new Player("Aaron Rogers",		12, 	Position.Quarterback);
		p2 = new Player("Cam Newton", 		5, 		Position.Defensive_Line);
		p3 = new Player("Baker Mayfield", 	2, 		Position.Kicker);
		p4 = new Player("Deshaun Watson", 	2, 		Position.Tight_End);

		assertEquals("p2 _ p3", Ts(399231720),    asString(c.compare(p2, p3)));
		assertEquals("p1 _ p4", Ts(1048086119),    asString(c.compare(p1, p4)));
		assertEquals("p3 _ p4", ">",    asString(c.compare(p3, p4)));
		assertEquals("p4 _ p2", "<",    asString(c.compare(p4, p2)));
	}

	public void test02(){
		// For unlocking Comparator tests...
		// If c.compare(a, b) > 0 then enter: >
		// If c.compare(a, b) = 0 then enter: =
		// If c.compare(a, b) < 0 then enter: <

		//Comparator Used: Non-discrimination: no-one has priority over anyone else
		c = Nondiscrimination.getInstance();

		//Constructor args are:
		//       Player( name				Score	Position)
		p4 = new Player("Aaron Rogers",		12, 	Position.Quarterback);
		p2 = new Player("Cam Newton", 		5, 		Position.Defensive_Line);
		p3 = new Player("Baker Mayfield", 	2, 		Position.Kicker);
		p1 = new Player("Deshaun Watson", 	2, 		Position.Tight_End);

		assertEquals("p2 _ p3", Ts(1111354586),    asString(c.compare(p2, p3)));
		assertEquals("p3 _ p4", "=",    asString(c.compare(p3, p4)));
		assertEquals("p4 _ p1", "=",    asString(c.compare(p4, p1)));
	}

	public void test03(){
		// For unlocking Comparator tests...
		// If c.compare(a, b) > 0 then enter: >
		// If c.compare(a, b) = 0 then enter: =
		// If c.compare(a, b) < 0 then enter: <

		//Comparator Used: We use position, where null is better than any
		// Positions are: 
		// Quarterback > Runningback > Wide_Receiver > 
		// Tight_End > Kicker > Defensive_Line
		c = ByPosition.getInstance();

		//Constructor args are:
		//       Player( name				Score	Position)
		p4 = new Player("Aaron Rogers",		12, 	Position.Quarterback);
		p2 = new Player("Cam Newton", 		5, 		Position.Defensive_Line);
		p3 = new Player("Baker Mayfield", 	2, 		Position.Kicker);
		p1 = new Player("Famous Amos", 	    1, 		null);

		assertEquals("p2 _ p3", "<",    asString(c.compare(p2, p3)));
		assertEquals("p3 _ p4", Ts(1469548867),    asString(c.compare(p3, p4)));
		assertEquals("p4 _ p1", Ts(1248166370),    asString(c.compare(p4, p1)));
	}

	public void testNondiscrimination() {
		testComparator(Nondiscrimination.getInstance(),players);
	}

	public void testAlphabetical() {
		testComparator(Alphabetical.getInstance(), 
				new Player[]{players[1],players[3]},
				new Player[]{players[2],players[4]},
				new Player[]{players[6]},
				new Player[]{players[5]},
				new Player[]{players[0],players[7]});
	}

	public void testHighestScore() {
		testComparator(HigherScore.getInstance(),
				new Player[]{players[6]},
				new Player[]{players[1]},
				new Player[]{players[2], players[5]},
				new Player[]{players[3]},
				new Player[]{players[4]},
				new Player[]{players[0]},
				new Player[]{players[7]});
	}
	
	public void testByPosition() {
		testComparator(ByPosition.getInstance(),
				new Player[] {players[1]},
				new Player[] {players[2]},
				new Player[] {players[3]},
				new Player[] {players[5]},
				new Player[] {players[4]},
				new Player[] {players[0]},
				new Player[] {players[6], players[7]});
	}
}
