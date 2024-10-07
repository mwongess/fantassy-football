import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.uwm.cs351.Player;
import edu.uwm.cs351.Position;
import edu.uwm.cs351.Team;
import junit.framework.TestCase;

public class TestInvariant extends TestCase {
	protected Team.Spy spy;
	protected int reports;
	
	protected void assertReporting(boolean expected, Supplier<Boolean> test) {
		reports = 0;
		Consumer<String> savedReporter = spy.getReporter();
		try {
			spy.setReporter((String message) -> {
				++reports;
				if (message == null || message.trim().isEmpty()) {
					assertFalse("Uninformative report is not acceptable", true);
				}
				if (expected) {
					assertFalse("Reported error incorrectly: " + message, true);
				}
			});
			assertEquals(expected, test.get().booleanValue());
			if (!expected) {
				assertEquals("Expected exactly one invariant error to be reported", 1, reports);
			}
			spy.setReporter(null);
		} finally {
			spy.setReporter(savedReporter);
		}
	}
	
	protected void assertWellFormed(boolean expected, Team r) {
		assertReporting(expected, () -> spy.wellFormed(r));
	}

	private static class Puppet extends Player {
		public Player next, prev;
		public Puppet(String n, int r, Position p){super(n,r,p);}
		public Player getNext() {return next;}
		public Player getPrevious() {return prev;}
	}

	protected Puppet p0, p1, p2, p3, p4, p5;
	protected Team self;
	// NB: The following comparator is "perverse", but it should be accepted anyway:
	protected Comparator<Player> c = (p,q) -> p.getScore() - q.getScore();
	
	@Override // implementation
	protected void setUp() {
		spy = new Team.Spy();
		p0 = new Puppet("", Integer.MAX_VALUE, null);
		p1 = new Puppet("Player1",1,Position.Kicker);
		p2 = new Puppet("Player2",2,Position.Quarterback);
		p3 = new Puppet("Player3",3,Position.Runningback);
		p4 = new Puppet("Player4",4,null);
		p5 = new Puppet("", Integer.MAX_VALUE, null);
	}

	public void testA() {
		self = spy.newInstance(null, c, "Packers");
		assertWellFormed(false, self);
	}
	
	public void testB() {
		self = spy.newInstance(p0, null, "Packers");
		assertWellFormed(false, self);
	}
	
	public void testC() {
		self = spy.newInstance(p0, c, null);
		assertWellFormed(false, self);
	}
	
	public void testD() {
		self = spy.newInstance(null, c, null);
		assertWellFormed(false, self);
	}

	public void testE() {
		self = spy.newInstance(p0, c, "");
		assertWellFormed(true, self);
	}
	
	public void testF() {
		p0 = new Puppet("A", Integer.MAX_VALUE, null);
		self = spy.newInstance(p0, c, "");
		assertWellFormed(false, self);
	}
	
	public void testG() {
		p0 = new Puppet("", Integer.MAX_VALUE-1, null);
		self = spy.newInstance(p0, c, "");
		assertWellFormed(false, self);
	}
	
	public void testH() {
		p0 = new Puppet("", Integer.MAX_VALUE, Position.Quarterback);
		self = spy.newInstance(p0, c, "");
		assertWellFormed(false, self);
	}
	
	public void testI() {
		p0 = new Puppet("A", Integer.MAX_VALUE, Position.Runningback);
		self = spy.newInstance(p0, c, "");
		assertWellFormed(false, self);
	}
	
	public void testJ() {
		p0.prev = p1;
		self = spy.newInstance(p0, c, "");
		assertWellFormed(false, self);
		p1.next = p0;
		assertWellFormed(false, self);
	}
	
	public void testK() {
		p0.prev = p0;
		self = spy.newInstance(p0, c, "");
		assertWellFormed(false, self);
		p0.next = p0;
		assertWellFormed(false, self);
	}
	
	public void testL() {
		p0.next = p1;
		self = spy.newInstance(p0, c, "Ps");
		assertWellFormed(false, self);
	}
	
	public void testM() {
		p0.next = p1;
		self = spy.newInstance(p0, c, "Ps");
		p1.prev = p0;
		assertWellFormed(true, self);
	}
	
	public void testN() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p0;
		assertWellFormed(false, self);
	}
	
	public void testO() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p0;
		p0.prev = p1;
		assertWellFormed(false, self);
	}
	
	public void testP() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p1;
		assertWellFormed(false, self);
	}
	
	public void testQ() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		assertWellFormed(false, self);
	}
	
	public void testR() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		p2.prev = p1;
		assertWellFormed(true, self);
	}
	
	public void testS() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		p2.prev = p1;
		p2.next = p1;
		assertWellFormed(false, self);
	}
	
	public void testT() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		p2.prev = p1;
		p2.next = p3;
		p3.prev = p2;
		assertWellFormed(true, self);
	}
	
	public void testU() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		p2.prev = p1;
		p2.next = p3;
		p3.prev = p2;
		p3.next = p1;
		assertWellFormed(false, self);
	}
	
	public void testV() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		p2.prev = p1;
		p2.next = p3;
		p3.prev = p2;
		p3.next = p2;
		assertWellFormed(false, self);
	}
	
	public void testW() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		p2.prev = p1;
		p2.next = p3;
		p3.prev = p2;
		p3.next = p4;
		p4.prev = p3;
		assertWellFormed(true, self);
	}
	
	public void testX() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		p2.prev = p1;
		p2.next = p3;
		p3.prev = p2;
		p3.next = p4;
		p4.prev = p3;
		p4.next = p2;
		assertWellFormed(false, self);
	}
	
	public void testY() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		p2.prev = p1;
		p2.next = p3;
		p3.prev = p2;
		p3.next = p4;
		p4.prev = p3;
		p4.next = p5;
		p5.prev = p4;
		assertWellFormed(true, self);
	}
	
	public void testZ() {
		self = spy.newInstance(p0, c, "Ps");
		p0.next = p1;
		p1.prev = p0;
		p1.next = p2;
		p2.prev = p1;
		p2.next = p3;
		p3.prev = p2;
		p3.next = p4;
		p4.prev = p3;
		p4.next = p5;
		p5.prev = p4;
		p5.next = p2;
		assertWellFormed(false, self);
	}
	
}
