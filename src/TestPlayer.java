import java.util.Comparator;

import edu.uwm.cs351.Alphabetical;
import edu.uwm.cs351.ByPosition;
import edu.uwm.cs351.HigherScore;
import edu.uwm.cs351.Nondiscrimination;
import edu.uwm.cs351.Player;
import edu.uwm.cs351.Position;
import junit.framework.TestCase;


public class TestPlayer extends TestCase {
	
	private Comparator<Player> c;
	private Player p1,p2,p3,p4,p5,p6,p7;
	
	//testing helper method
	private static void testPlayers(Player... expected) {
		for (int i=0; i < expected.length; ++i) {
			Player t = expected[i];
			Player expectedPrev =                 (i == 0)? null: expected[i-1];
			Player expectedNext = (i == expected.length-1)? null: expected[i+1];
			
			assertSame(t+".prev was incorrect", expectedPrev, t.getPrevious());
			assertSame(t+".next was incorrect", expectedNext, t.getNext());
		}
	}
	
	// we use concealment to make sure your code doesn't
	// try compare comparators!
	protected Comparator<Player> conceal(Comparator<Player> c) {
		return new Comparator<Player>() {
			@Override // required
			public int compare(Player p1, Player p2) {
				return c.compare(p1, p2);
			}
			
			@Override // implementation
			public String toString() {
				return "<concealed>";
			}
		};
	}
	
	// testA0: simple tests of addInPriority
	
	public void testA00() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p1.addInPriority(p2, c);
		testPlayers(p2, p1);
	}
	
	public void testA01() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p2.addInPriority(p1, c);
		testPlayers(p2, p1);
	}
	
	public void testA02() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 10, Position.Defensive_Line);
		p2.addInPriority(p1, c);
		testPlayers(p2, p1);
	}
	
	public void testA03() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 10, Position.Defensive_Line);
		p1.addInPriority(p2, c);
		testPlayers(p1, p2);
	}
	
	public void testA04() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p1.addInPriority(p2, c);
		testPlayers(p1, p2);
	}
	
	public void testA05() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p2.addInPriority(p1, c);
		testPlayers(p1, p2);
	}
	
	
	/// testA1: tests with a third player
	
	public void testA10() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p1.addInPriority(p3, c);
		
		testPlayers(p3, p2, p1);
	}
	
	public void testA11() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		
		testPlayers(p3, p2, p1);
	}
	
	public void testA12() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p3, c);
		p1.addInPriority(p2, c);
		
		testPlayers(p3, p2, p1);
	}
	
	public void testA13() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		
		testPlayers(p3, p2, p1);
	}
	
	public void testA14() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p2.addInPriority(p3, c);
		p2.addInPriority(p1, c);
		
		testPlayers(p3, p2, p1);
	}
	
	public void testA15() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p2.addInPriority(p3, c);
		p3.addInPriority(p1, c);
		
		testPlayers(p3, p2, p1);
	}
	
	public void testA16() {
		c = conceal(Nondiscrimination.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p1.addInPriority(p3, c);
		
		testPlayers(p1, p3, p2);
	}
	
	public void testA17() {
		c = conceal(Nondiscrimination.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		
		testPlayers(p1, p2, p3);
	}
	
	public void testA18() {
		c = conceal(Alphabetical.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p1.addInPriority(p3, c);
		
		testPlayers(p3, p1, p2);
	}
	
	public void testA19() {
		c = conceal(Alphabetical.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		
		testPlayers(p3, p1, p2);
	}

	
	/// testA2x: Trickier tests adding a third
	
	public void testA20() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 10, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p1.addInPriority(p3, c);
		
		testPlayers(p3, p1, p2);
	}
	
	public void testA21() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 10, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		
		testPlayers(p3, p1, p2);
	}
	
	public void testA22() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 10, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.setScore(20); // now out of order
		p1.addInPriority(p3, c);
		
		testPlayers(p3, p1, p2);
	}
	
	public void testA23() {
		c = conceal(HigherScore.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 10, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.setScore(20); // now out of order
		p2.addInPriority(p3, c);
		
		testPlayers(p3, p1, p2);
	}
	
	public void testA24() {
		c = conceal(Nondiscrimination.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p2.addInPriority(p1, c);
		c = conceal(Alphabetical.getInstance()); // now out of order
		p2.addInPriority(p3, c);
		
		testPlayers(p3, p2, p1);
	}
	
	public void testA25() {
		c = conceal(Nondiscrimination.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p2.addInPriority(p1, c);
		c = conceal(Alphabetical.getInstance()); // now out of order
		p1.addInPriority(p3, c);
		
		testPlayers(p3, p2, p1);
	}
	
	public void testA26() {
		c = conceal(Nondiscrimination.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p2.addInPriority(p3, c);
		c = conceal(HigherScore.getInstance()); // now out of order
		p2.addInPriority(p1, c);
		
		testPlayers(p2, p3, p1);
	}
	
	public void testA27() {
		c = conceal(Nondiscrimination.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 20, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p2.addInPriority(p3, c);
		c = conceal(HigherScore.getInstance()); // now out of order
		p3.addInPriority(p1, c);
		
		testPlayers(p2, p3, p1);
	}
	
	public void testA28() {
		c = conceal(Nondiscrimination.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 10, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p2.addInPriority(p3, c);
		c = conceal(HigherScore.getInstance()); // now out of order
		p2.addInPriority(p1, c);
		
		testPlayers(p2, p1, p3);
	}
	
	public void testA29() {
		c = conceal(Nondiscrimination.getInstance());		
		p1 = new Player("joe1", 10, Position.Quarterback);
		p2 = new Player("joe2", 10, Position.Defensive_Line);
		p3 = new Player("joe", 30, Position.Kicker);

		p2.addInPriority(p3, c);
		c = conceal(HigherScore.getInstance()); // now out of order
		p3.addInPriority(p1, c);
		
		testPlayers(p2, p3, p1);
	}
	
	
	/// testA3/4: testing adding a fourth player
	
	public void testA30() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA31() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p2.addInPriority(p4, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA32() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p1.addInPriority(p4, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA33() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p4, c);
		p1.addInPriority(p3, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA34() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p4, c);
		p2.addInPriority(p3, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA35() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA36() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		p1.addInPriority(p2, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA37() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		p3.addInPriority(p2, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA38() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		p4.addInPriority(p2, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA39() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		p2.addInPriority(p1, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA40() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		p3.addInPriority(p1, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA41() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		p4.addInPriority(p1, c);
		
		testPlayers(p1, p2, p3, p4);	
	}
	
	public void testA42() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 20, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p1.addInPriority(p4, c);
		
		testPlayers(p1, p3, p4, p2);	
	}
	
	public void testA43() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 20, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p4, c);
		
		testPlayers(p1, p3, p4, p2);	
	}
	
	public void testA44() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 20, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p3.addInPriority(p4, c);
		
		testPlayers(p1, p3, p4, p2);	
	}
	
	public void testA45() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 10, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p1.addInPriority(p4, c);
		
		testPlayers(p1, p3, p2, p4);	
	}
	
	public void testA46() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 10, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p4, c);
		
		testPlayers(p1, p3, p2, p4);	
	}
	
	public void testA47() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 10, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p3.addInPriority(p4, c);
		
		testPlayers(p1, p3, p2, p4);	
	}
	
	public void testA48() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p3.addInPriority(p4, c);
		
		testPlayers(p1, p4, p3, p2);	
	}
	
	public void testA49() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);
		
		p2.addInPriority(p1, c);
		p1.addInPriority(p3, c);
		c = conceal(HigherScore.getInstance()); // now out of order
		p3.addInPriority(p4, c);
		
		testPlayers(p2, p1, p4, p3);	
	}
	
	
	/// testA5x: testing five players
	
	public void testA50() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p1.addInPriority(p5, c);
		
		testPlayers(p1, p2, p3, p4, p5);
	}
	
	public void testA51() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p5, c);
		
		p1.addInPriority(p4, c);
		
		testPlayers(p1, p2, p3, p4, p5);
	}
	
	public void testA52() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p4, c);
		p4.addInPriority(p5, c);
		
		p1.addInPriority(p3, c);
		
		testPlayers(p1, p2, p3, p4, p5);
	}
	
	public void testA53() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p4, c);
		p4.addInPriority(p5, c);
		
		p5.addInPriority(p3, c);
		
		testPlayers(p1, p2, p3, p4, p5);
	}
	
	public void testA54() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		p4.addInPriority(p5, c);
		
		p5.addInPriority(p2, c);
		
		testPlayers(p1, p2, p3, p4, p5);
	}
	
	public void testA55() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		p4.addInPriority(p5, c);
		
		p5.addInPriority(p1, c);
		
		testPlayers(p1, p2, p3, p4, p5);
	}
	
	public void testA56() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 20, Position.Runningback);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p4.addInPriority(p5, c);
		
		testPlayers(p4, p1, p3, p5, p2);
	}
	
	public void testA57() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 30, Position.Runningback);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p2.addInPriority(p5, c);
		
		testPlayers(p4, p1, p5, p3, p2);
	}
	
	public void testA58() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		
		p2.addInPriority(p4, c);
		p4.addInPriority(p1, c);
		p1.addInPriority(p3, c);
		c = conceal(HigherScore.getInstance()); // out of order
		
		p4.addInPriority(p5, c);
		
		testPlayers(p2, p4, p1, p5, p3);
	}
	
	public void testA59() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		
		p1.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p4, c);
		c = conceal(HigherScore.getInstance()); // out of order
		
		p2.addInPriority(p5, c);
		
		testPlayers(p1, p5, p3, p2, p4);
	}
	
	
	/// testA6x: tests with six or players
	
	public void testA60() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		p6 = new Player("P6", 15, null);
		
		p3.addInPriority(p5, c);
		p5.addInPriority(p1, c);
		p1.addInPriority(p4, c);
		p4.addInPriority(p2, c);
		
		p2.addInPriority(p6, c);
		testPlayers(p6, p3, p5, p1, p4, p2);
	}
	
	public void testA61() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		p6 = new Player("P6", 15, null);
		
		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		p4.addInPriority(p5, c);
		
		p1.addInPriority(p6, c);
		testPlayers(p1, p2, p3, p4, p5, p6);
	}
	
	public void testA62() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);
		p6 = new Player("P6", 15, null);
		p7 = new Player("P7", 35, Position.Wide_Receiver);
		
		p3.addInPriority(p5, c);
		p5.addInPriority(p1, c);
		p1.addInPriority(p4, c);
		p4.addInPriority(p2, c);
		p4.addInPriority(p6, c);
		
		p6.addInPriority(p7, c);
		testPlayers(p4, p7, p1, p5, p3, p6, p2);
	}

	

	public void testR10() {
		p1 = new Player("J Q Smoth",10, Position.Kicker);
		p1.remove();
		testPlayers(p1);
	}
	
	public void testR20() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 10, Position.Defensive_Line);
		p2 = new Player("P2", 20, Position.Quarterback);
		p1.addInPriority(p2, c);
		
		p1.remove();
		testPlayers(p1);
	}
	
	public void testR21() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 10, Position.Defensive_Line);
		p2 = new Player("P2", 20, Position.Quarterback);
		p1.addInPriority(p2, c);
		
		p1.remove();
		testPlayers(p2);
	}
	
	public void testR22() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 10, Position.Defensive_Line);
		p2 = new Player("P2", 20, Position.Quarterback);
		p1.addInPriority(p2, c);
		
		p2.remove();
		testPlayers(p1);
	}
	
	public void testR23() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 10, Position.Defensive_Line);
		p2 = new Player("P2", 20, Position.Quarterback);
		p1.addInPriority(p2, c);
		
		p2.remove();
		testPlayers(p2);
	}
	
	public void testR30() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p2.remove();
		testPlayers(p3,p1);
	}
	
	public void testR31() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p2.remove();
		testPlayers(p2);
	}

	public void testR32() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p3.remove();
		testPlayers(p2,p1);
	}
	
	public void testR33() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p3.remove();
		testPlayers(p3);
	}

	public void testR34() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p1.remove();
		testPlayers(p3,p2);
	}
	
	public void testR35() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p1.remove();
		testPlayers(p1);
	}
	
	public void testR40() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p1.remove();
		testPlayers(p1);
	}
	
	public void testR41() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p1.remove();
		testPlayers(p2, p3, p4);
	}

	public void testR42() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p2.remove();
		testPlayers(p2);
	}
	
	public void testR43() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p2.remove();
		testPlayers(p1, p3, p4);
	}

	
	public void testR44() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p3.remove();
		testPlayers(p3);
	}
	
	public void testR45() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p3.remove();
		testPlayers(p1, p2, p4);
	}

	public void testR46() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p4.remove();
		testPlayers(p4);
	}
	
	public void testR47() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, c);
		p2.addInPriority(p3, c);
		p3.addInPriority(p4, c);
		
		p4.remove();
		testPlayers(p1, p2, p3);
	}

	public void testR50() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p1.remove();
		testPlayers(p1);
	}

	public void testR51() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p1.remove();
		testPlayers(p5, p4, p3, p2);
	}

	public void testR52() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p2.remove();
		testPlayers(p2);
	}

	public void testR53() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p2.remove();
		testPlayers(p5, p4, p3, p1);
	}

	public void testR54() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p3.remove();
		testPlayers(p3);
	}

	public void testR55() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p3.remove();
		testPlayers(p5, p4, p2, p1);
	}

	public void testR56() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p4.remove();
		testPlayers(p4);
	}

	public void testR57() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p4.remove();
		testPlayers(p5, p3, p2, p1);
	}

	public void testR58() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p5.remove();
		testPlayers(p5);
	}

	public void testR59() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, null);
		p4 = new Player("P4", 40, Position.Kicker);
		p5 = new Player("P5", 25, Position.Runningback);

		p5.addInPriority(p4, c);
		p4.addInPriority(p3, c);
		p3.addInPriority(p2, c);
		p2.addInPriority(p1, c);
		
		p5.remove();
		testPlayers(p4, p3, p2, p1);
	}
	
	
	public void testS00() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 30, Position.Quarterback);
		
		p1.sortByPriority(c);
		testPlayers(p1);
	}
	
	public void testS10() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 30, Position.Quarterback);
		p2 = new Player("Lizzie", 20, Position.Tight_End);
		p1.addInPriority(p2, Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p1, p2);
	}
	
	public void testS11() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 30, Position.Quarterback);
		p2 = new Player("Lizzie", 20, Position.Tight_End);
		p1.addInPriority(p2, Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p1, p2);
	}

	public void testS12() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("Nancy", 30, Position.Quarterback);
		p2 = new Player("Lizzie", 20, Position.Tight_End);
		p1.addInPriority(p2, Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p2, p1);
	}

	public void testS13() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("Nancy", 30, Position.Quarterback);
		p2 = new Player("Lizzie", 20, Position.Tight_End);
		p1.addInPriority(p2, Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p1, p2);
	}
	
	public void testS14() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("Nancy", 30, Position.Quarterback);
		p2 = new Player("Lizzie", 20, Position.Quarterback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p1, p2);		
	}
	
	public void testS15() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("Nancy", 30, Position.Quarterback);
		p2 = new Player("Lizzie", 20, Position.Quarterback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p1, p2);		
	}

	public void testS20() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 20, Position.Quarterback);
		p3 = new Player("Beth", 10, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}

	public void testS21() {
		c = conceal(Nondiscrimination.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 20, Position.Quarterback);
		p3 = new Player("Beth", 10, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS22() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 30, Position.Quarterback);
		p3 = new Player("Beth", 10, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS23() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 30, Position.Quarterback);
		p3 = new Player("Beth", 40, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p3, p1, p2);
	}
	
	public void testS24() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 30, Position.Quarterback);
		p3 = new Player("Beth", 40, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p3.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS25() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 20, Position.Quarterback);
		p3 = new Player("Beth", 20, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS26() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 10, Position.Kicker);
		p2 = new Player("Lizzie", 20, Position.Quarterback);
		p3 = new Player("Beth", 20, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p2, p3, p1);
	}
	
	public void testS27() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 10, Position.Kicker);
		p2 = new Player("Lizzie", 20, Position.Quarterback);
		p3 = new Player("Beth", 20, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS28() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 20, Position.Kicker);
		p2 = new Player("Lizzie", 40, Position.Quarterback);
		p3 = new Player("Beth", 20, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS29() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 20, Position.Kicker);
		p2 = new Player("Lizzie", 20, Position.Quarterback);
		p3 = new Player("Beth", 30, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p3, p1, p2);
	}
	
	public void testS30() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 20, Position.Quarterback);
		p3 = new Player("Beth", 10, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS31() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 10, Position.Quarterback);
		p3 = new Player("Beth", 20, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p1, p3, p2);
	}
	
	public void testS32() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 20, Position.Kicker);
		p2 = new Player("Lizzie", 30, Position.Quarterback);
		p3 = new Player("Beth", 10, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p2, p1, p3);
	}
	
	public void testS33() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("Nancy", 20, Position.Kicker);
		p2 = new Player("Lizzie", 30, Position.Quarterback);
		p3 = new Player("Beth", 10, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS34() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("Nancy", 20, Position.Kicker);
		p2 = new Player("Lizzie", 30, Position.Defensive_Line);
		p3 = new Player("Beth", 10, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p3, p1, p2);
	}
	
	public void testS35() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("Nancy", 20, Position.Kicker);
		p2 = new Player("Lizzie", 30, Position.Defensive_Line);
		p3 = new Player("Beth", 10, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p3.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS36() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 10, Position.Quarterback);
		p3 = new Player("Beth", 20, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p2, p3, p1);
	}
	
	public void testS37() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 10, Position.Quarterback);
		p3 = new Player("Beth", 20, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p1, p2, p3);
	}
	
	public void testS38() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 10, Position.Quarterback);
		p3 = new Player("Beth", 20, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p1.sortByPriority(c);
		testPlayers(p3, p2, p1);
	}

	public void testS39() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("Nancy", 30, Position.Kicker);
		p2 = new Player("Lizzie", 10, Position.Quarterback);
		p3 = new Player("Beth", 20, Position.Runningback);
		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3,  Nondiscrimination.getInstance());

		p2.sortByPriority(c);
		testPlayers(p3, p1, p2);
	}

	public void testS40() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p4, Nondiscrimination.getInstance());
		
		p2.sortByPriority(c);
		testPlayers(p1, p2, p3, p4);
	}

	public void testS41() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p4.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p1, Nondiscrimination.getInstance());
		
		p3.sortByPriority(c);
		testPlayers(p1, p2, p4, p3);
	}

	public void testS42() {
		c = conceal(Alphabetical.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p4.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p1, Nondiscrimination.getInstance());
		
		p2.sortByPriority(c);
		testPlayers(p1, p4, p3, p2);
	}

	public void testS43() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p4, Nondiscrimination.getInstance());
		
		p3.sortByPriority(c);
		testPlayers(p1, p4, p2, p3);
	}

	public void testS44() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p4, Nondiscrimination.getInstance());
		
		p2.sortByPriority(c);
		testPlayers(p1, p4, p3, p2);
	}

	public void testS45() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p4, Nondiscrimination.getInstance());
		
		p2.sortByPriority(c);
		testPlayers(p3, p1, p4, p2);
	}

	public void testS46() {
		c = conceal(ByPosition.getInstance());
		p1 = new Player("P1", 30, Position.Tight_End);
		p2 = new Player("P2", 10, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p4, Nondiscrimination.getInstance());
		
		p3.sortByPriority(c);
		testPlayers(p1, p2, p3, p4);
	}

	public void testS47() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 10, Position.Tight_End);
		p2 = new Player("P2", 40, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p4, Nondiscrimination.getInstance());
		
		p2.sortByPriority(c);
		testPlayers(p1, p2, p4, p3);
	}

	public void testS48() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 10, Position.Tight_End);
		p2 = new Player("P2", 40, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 30, Position.Kicker);

		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p4, Nondiscrimination.getInstance());
		
		p3.sortByPriority(c);
		testPlayers(p1, p2, p4, p3);
	}

	public void testS49() {
		c = conceal(HigherScore.getInstance());
		p1 = new Player("P1", 10, Position.Tight_End);
		p2 = new Player("P2", 30, Position.Defensive_Line);
		p3 = new Player("P3", 20, Position.Quarterback);
		p4 = new Player("P4", 40, Position.Kicker);

		p1.addInPriority(p2, Nondiscrimination.getInstance());
		p2.addInPriority(p3, Nondiscrimination.getInstance());
		p3.addInPriority(p4, Nondiscrimination.getInstance());
		
		p3.sortByPriority(c);
		testPlayers(p4, p1, p2, p3);
	}

}
