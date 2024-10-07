import java.util.Comparator;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Alphabetical;
import edu.uwm.cs351.HigherScore;
import edu.uwm.cs351.Nondiscrimination;
import edu.uwm.cs351.Player;
import edu.uwm.cs351.Position;
import edu.uwm.cs351.Team;


public class TestTeam extends LockedTestCase {
	protected void assertException(Class<?> excClass, Runnable f) {
		try {
			f.run();
			assertFalse("Should have thrown an exception, not returned",true);
		} catch (RuntimeException ex) {
			if (!excClass.isInstance(ex)) {
				ex.printStackTrace();
				assertFalse("Wrong kind of exception thrown: "+ ex.getClass().getSimpleName(),true);
			}
		}		
	}

	/**
	 * A class defined to ensure that student code uses a comparator
	 * rather than trying to check equality.
	 * @param T
	 */
	private class PlayerComparator implements Comparator<Player> {

		private final Comparator<Player> wrapped;
		
		public PlayerComparator(Comparator<Player> c) {
			wrapped = c;
		}
		@Override
		public int compare(Player o1, Player o2) {
			return wrapped.compare(o1, o2);
		}
		
	}
	private Comparator<Player> wrap(Comparator<Player> c) {
		return new PlayerComparator(c);
	}
	
	private Team team, ateam, bteam;
	private Player p1, p2, p3, p4, p5, p6, p7;

	@Override
	protected void setUp() {
		team = new Team(wrap(Nondiscrimination.getInstance()), "Pat");
		ateam = new Team(wrap(Alphabetical.getInstance()),"Pat");
		bteam = new Team(wrap(HigherScore.getInstance()),"Pat");
		p1 = new Player("p1", 55, Position.Quarterback);
		p2 = new Player("p2", 33, Position.Defensive_Line);
		p3 = new Player("p3", 77, Position.Kicker);
		p4 = new Player("p4", 44, Position.Tight_End);
		p5 = new Player("p5", 66, Position.Runningback);
		p6 = new Player("p6", 88, Position.Wide_Receiver);
		p7 = new Player("p7", 77, Position.Runningback);
	}

	// locked tests
	
	public void test() {
		// In the following, the name of p1 is "p1", for p2 is "p2" etc.
		team.add(p1); // using non-discrimination:
		team.add(p2); // code adds p2 in most efficient place (from head)
		assertEquals(Ts(400647975),team.get(0).getName()); // who's highest priority now?
		ateam.add(p3); // using alphabetical ("A" priority over "B")
		ateam.add(p4);
		assertEquals(Ts(1043331350),ateam.get(0).getName()); // who's highest priority?
		bteam.add(p5); // p5's score is 66 // bteam uses score for priority
		bteam.add(p6); // p6's score is 88
		assertEquals(Ts(660613387),bteam.get(0).getName()); // who's highest priority?
		String result;
		try {
			team.add(p5);
			result = "OK";
		} catch (RuntimeException ex) {
			result = ex.getClass().getSimpleName();
		}
		// Can we add p6 to another team?
		assertEquals(Ts(1032731533),result);
	}

	
	// test0N: test add (using non-discrimination)
	
	public void test01() {
		team.add(p1);
		assertNull(p1.getNext());
	}
	
	public void test02() {
		team.add(p2);
		assert(p2.getPrevious() != null);
	}
	
	public void test03() {
		assertException(RuntimeException.class,() -> team.add(null));
	}
	
	public void test04() {
		team.add(p3);
		assertException(IllegalArgumentException.class, () -> team.add(p3));
	}
	
	public void test05() {
		team.add(p3);
		assertException(RuntimeException.class,() -> team.add(null));		
	}
	
	public void test06() {
		team.add(p4);
		team.add(p5);
		assertSame(p4, p5.getNext());
	}
	
	public void test07() {
		team.add(p1);
		team.add(p6);
		assertSame(p6, p1.getPrevious());
	}
	
	public void test08() {
		team.add(p4);
		team.add(p2);
		team.add(p1);
		assertTrue("dummy <- p1?",p1.getPrevious() != null);
		assertSame("p1 -> p2?",p2,p1.getNext());
		assertSame("p1 <- p2?",p1,p2.getPrevious());
		assertSame("p2 -> p4?",p4,p2.getNext());
		assertSame("p2 <- p4?",p2,p4.getPrevious());
		assertTrue("p4 -> null?",p4.getNext() == null);
	}
	
	public void test09() {
		team.add(p1);
		team.add(p3);
		team.add(p6);
		assertException(IllegalArgumentException.class,() -> team.add(p1));
		assertException(IllegalArgumentException.class,() -> team.add(p3));
		assertException(IllegalArgumentException.class,() -> team.add(p6));
	}
	
	
	// test1N: testing alphabetical comparator and high score
	
	public void test10() {
		ateam.add(p1);
		ateam.add(p2);
		assertSame(p2,p1.getNext());
		assertSame(p1,p2.getPrevious());
	}
	
	public void test11() {
		ateam.add(p2);
		ateam.add(p1);
		assertSame(p2,p1.getNext());
		assertSame(p1,p2.getPrevious());
	}
	
	public void test12() {
		ateam.add(p1);
		ateam.add(p3);
		ateam.add(p2);
		assertSame(p1,p2.getPrevious());
		assertSame(p3,p2.getNext());
	}
	
	public void test13() {
		ateam.add(p1);
		assertException(IllegalArgumentException.class, () -> team.add(p1));
	}
	
	public void test14() {
		ateam.add(p3);
		ateam.add(p1);
		ateam.add(p5);
		ateam.add(p2);
		ateam.add(p6);
		ateam.add(p4);
		assertSame(p3,p4.getPrevious());
		assertSame(p5,p4.getNext());
		assertSame(p5,p6.getPrevious());
		assertNull(p6.getNext());
	}
	
	public void test15() {
		bteam.add(p1);
		bteam.add(p2);
		assertSame(p2,p1.getNext());
		assertSame(p1,p2.getPrevious());		
	}
	
	public void test16() {
		bteam.add(p3);
		bteam.add(p4);
		bteam.add(p5);
		assertSame(p3,p5.getPrevious());
		assertSame(p4,p5.getNext());
	}
	
	public void test17() {
		bteam.add(p3);
		bteam.add(p6);
		bteam.add(p4);
		bteam.add(p7);
		assertSame(p3,p7.getPrevious());
		assertSame(p4,p7.getNext());
		assertTrue(p6.getPrevious() != null); // dummy!
	}
	
	public void test18() {
		ateam.add(p1);
		bteam.add(p2);
		team.add(p3);
		assertException(IllegalArgumentException.class,() -> bteam.add(p1));
	}
	
	public void test19() {
		ateam.add(p1);
		p1.remove();
		bteam.add(p1);
	}
	
	
	// test4N: testing size
	
	public void test20() {
		assertEquals(0, team.size());
	}
	
	public void test21() {
		team.add(p3);
		assertEquals(1,team.size());
	}
	
	public void test22() {
		team.add(p1);
		team.add(p2);
		assertEquals(2,team.size());
	}
	
	public void test23() {
		team.add(p3);
		team.add(p4);
		team.add(p5);
		assertEquals(3,team.size());
	}
	
	public void test27() {
		team.add(p1);
		ateam.add(p2);
		team.add(p3);
		ateam.add(p4);
		team.add(p5);
		ateam.add(p6);
		team.add(p7);
		assertEquals(4,team.size());
	}
	
	public void test29() {
		bteam.add(p1);
		bteam.add(p2);
		bteam.add(p3);
		bteam.add(p4);
		bteam.add(p5);
		p2.remove();
		assertEquals(4,bteam.size());
	}
	

	// test3N: tests of totalScore
	
	public void test30() {
		assertEquals(0,team.totalScore());
	}

	public void test31() {
		team.add(p1);
		// tList: [p1]
		assertEquals(55,team.totalScore());
	}
	
	public void test32() {
		team.add(p1);
		team.add(p2);
		// tList: [p2,p1]
		assertEquals(88,team.totalScore());
	}
	
	public void test33() {
		team.add(p1);
		team.add(p2);
		team.add(p3);
		// tList: [p2,p2,p1]
		assertEquals(165,team.totalScore());
	}
	
	public void test34() {
		team.add(p1);
		team.add(p2);
		team.add(p3);
		team.add(p4);
		// tList: [p4,p3,p2,p1]
		assertEquals(209,team.totalScore());
	}

	public void test35() {
		team.add(p4);
		team.add(p5);
		team.add(p6);

		assertEquals(198,team.totalScore());
	}

	public void test36(){
		team.add(p6);
		team.add(p5);
		team.add(p4);
		team.add(p3);
		team.add(p2);
		team.add(p1);
		assertEquals(363,team.totalScore());
	}

	
	// test4N: tests of get
	
	public void test40() {
		assertException(IndexOutOfBoundsException.class, () -> team.get(0));
	}
	
	public void test41() {
		team.add(p2);
		assertSame(p2,team.get(0));
		assertException(IndexOutOfBoundsException.class, () -> team.get(1));
	}
	
	public void test42() {
		team.add(p4);
		team.add(p2);
		assertSame(p2,team.get(0));
		assertSame(p4,team.get(1));
		assertException(IndexOutOfBoundsException.class, () -> team.get(2));
		assertException(IndexOutOfBoundsException.class, () -> team.get(3));
		assertException(IndexOutOfBoundsException.class, () -> team.get(-1));
	}
	
	public void test43() {
		ateam.add(p3);
		ateam.add(p4);
		ateam.add(p7);
		assertSame(p3,ateam.get(0));
		assertSame(p4,ateam.get(1));
		assertException(IndexOutOfBoundsException.class, () -> team.get(1));
		assertException(IndexOutOfBoundsException.class, () -> ateam.get(4));
		assertException(IndexOutOfBoundsException.class, () -> bteam.get(-1));
	}
	
	public void test47() {
		bteam.add(p1);
		bteam.add(p2);
		bteam.add(p3);
		bteam.add(p4);
		bteam.add(p5);
		bteam.add(p6);
		bteam.add(p7);
		assertSame(p2,bteam.get(6));
		assertSame(p4,bteam.get(5));
		assertSame(p1,bteam.get(4));
		assertSame(p5,bteam.get(3));
		assertSame(p7,bteam.get(2));
		assertSame(p3,bteam.get(1));
		assertSame(p6,bteam.get(0));
	}
	
	
	/// test5N: testing priority changes
	
	public void test50() {
		team.setPriority(wrap(Alphabetical.getInstance()));
		assertEquals(0,team.size());
	}
	
	public void test51() {
		team.add(p1);
		team.setPriority(wrap(Alphabetical.getInstance()));
		assertEquals(1,team.size());
	}
	
	public void test52() {
		team.add(p1);
		team.add(p2);
		team.setPriority(wrap(Alphabetical.getInstance()));
		assertSame(p2,p1.getNext());
		assertNull(p2.getNext());
	}
	
	public void test53() {
		team.add(p1);
		team.add(p2);
		team.setPriority(wrap(Nondiscrimination.getInstance()));
		assertSame(p1,p2.getNext());
		assertNull(p1.getNext());
	}
	
	public void test54() {
		team.add(p1);
		team.add(p2);
		team.add(p3);
		team.add(p4);
		team.setPriority(wrap(HigherScore.getInstance()));
		assertSame(p3,team.get(0));
		assertSame(p1,p3.getNext());
		assertSame(p4,p1.getNext());
		assertSame(p2,p4.getNext());
		assertNull(p2.getNext());
	}
	
	public void test55() {
		team.add(p1);
		team.add(p2);
		team.add(p3);
		team.add(p4);
		team.add(p5);
		team.add(p6);
		team.setPriority(wrap(Nondiscrimination.getInstance()));
		assertSame(p6,team.get(0));
		assertSame(p5,p6.getNext());
		assertSame(p4,p5.getNext());
		assertSame(p3,p4.getNext());
		assertSame(p2,p3.getNext());
		assertSame(p1,p2.getNext());
		assertNull(p1.getNext());
	}
	
	public void test56() {
		team.add(p1);
		team.add(p2);
		team.add(p3);
		team.add(p4);
		team.add(p5);
		team.add(p6);
		team.setPriority(wrap(Nondiscrimination.getInstance()));
		team.setPriority(wrap(Alphabetical.getInstance()));
		assertSame(p1,team.get(0));
		assertSame(p2,p1.getNext());
		assertSame(p3,p2.getNext());
		assertSame(p4,p3.getNext());
		assertSame(p5,p4.getNext());
		assertSame(p6,p5.getNext());
		assertNull(p6.getNext());		
	}
	
	public void test57() {
		team.add(p1);
		team.add(p2);
		team.add(p3);
		team.add(p4);
		team.add(p5);
		team.add(p6);
		team.add(p7);
		team.setPriority(wrap(HigherScore.getInstance()));
		assertSame(p6,team.get(0));
		assertSame(p7,p6.getNext());
		assertSame(p3,p7.getNext());
		assertSame(p5,p3.getNext());
		assertSame(p1,p5.getNext());
		assertSame(p4,p1.getNext());
		assertSame(p2,p4.getNext());
		assertNull(p2.getNext());
	}
	
	public void test58() {
		bteam.add(p1);
		bteam.add(p2);
		bteam.add(p3);
		bteam.add(p4);
		bteam.add(p5);
		bteam.add(p6);
		bteam.add(p7);
		p6.setScore(66);
		p3.setScore(33);
		p1.setScore(11);
		assertSame(p6,bteam.get(0));
		bteam.refreshPriority();
		assertSame(p7,bteam.get(0));
		assertSame(p6,p7.getNext());
		assertSame(p5,p6.getNext());
		assertSame(p4,p5.getNext());
		assertSame(p3,p4.getNext());
		assertSame(p2,p3.getNext());
		assertSame(p1,p2.getNext());
	}
}
