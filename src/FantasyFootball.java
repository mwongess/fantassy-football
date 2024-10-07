import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import edu.uwm.cs351.Alphabetical;
import edu.uwm.cs351.Player;
import edu.uwm.cs351.Position;
import edu.uwm.cs351.Team;

/**
 *  Simulates one week of a fantasy football game with the option 
 *  of using random scores or with the week 17 scores from this past 
 *  season, or random scores. More teams could be played at once if the 
 *  makeRealPlayers method is extended. This program uses the Team
 *  class to hold the Player data and find the winner. The winner 
 *  is chosen based on the combined scores of the players on the team. 
 *  
 *  Programs that predict fantasy football scores are becoming very 
 *  popular as they can take into account any amount of information from past seasons. 
 *  This program is simple, but given that fantasy football data is widely 
 *  available online, one could develop a scoring system that might give some 
 *  very good recommendations on what players to choose.
 *
 *  The players in the program are the top ten picks of a fantasy football columnist Michael Fabiano.
 *  
 *  -LD
 */

public class FantasyFootball {
	String footballIcon = "lib"+File.separator+"football.png";
	List<Player> quarterbacks, runningbacks, widerecievers, tightends, kickers, defensiveline ;
	List<Team> allTeams;
	Team userTeam;
	private int numTeams;
	private JFrame frame;
	private boolean random;

	public FantasyFootball() {

		frame = new JFrame("Fantasy Football");		
		frame.setSize(320, 320);
		frame.setResizable(false);
		
		ImageIcon icon = new ImageIcon(footballIcon);
		frame.setIconImage(icon.getImage());

		JLabel enterTeam = new JLabel("Enter number of teams to play against (Max 3)", SwingConstants.CENTER);
		JButton real = new JButton("Play with week 17 scores");
		JButton rand = new JButton("Play with random scores");
		JTextField numTeamsField = new JTextField(8);
		JLabel errorLabel = new JLabel("");
		frame.setLayout(new FlowLayout());
		frame.add(real);
		frame.add(rand);
		frame.add(enterTeam);
		frame.add(numTeamsField);
		frame.add(errorLabel);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


		rand.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//create and shuffle a list of players with random scores
				random = true;
				validateNumTeams(errorLabel, numTeamsField);
			}
		});
		real.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//create and shuffle a list of players with week 17 scores
				random = false;
				validateNumTeams(errorLabel, numTeamsField);	
			}
		});
	}

	public static void main(String[] args) {
		new FantasyFootball();
	}

	private void findWinner() {
		Team winner = new Team(Alphabetical.getInstance(), "");

		for(Team t: allTeams) {
			System.out.println("-----------" + t + "-----------");
			int tscore = t.totalScore();
			if(tscore > winner.totalScore()) winner = t;
			t.printPlayers();
			System.out.println("Score: " + tscore);
			System.out.println();
		}
		System.out.println();
		System.out.println("The Winner is: " + winner + "!");
	}
	
	private void play(int numteams) {
		allTeams = createShuffledTeams(numTeams);
		frame.dispose();
		findWinner();
	}
	
	//create and shuffle teams, including user's team
	private List<Team> createShuffledTeams(int manyTeams) {
		makeRealPlayers();
		Collections.shuffle(quarterbacks);
		Collections.shuffle(runningbacks);
		Collections.shuffle(widerecievers);
		Collections.shuffle(tightends);
		Collections.shuffle(kickers);
		Collections.shuffle(defensiveline);

		List<Team> list = new ArrayList<Team>();
		
		userTeam = new Team(Alphabetical.getInstance(), "Your Team");

		pickFrom(quarterbacks, "quarterback");
		pickFrom(runningbacks, "runningback");
		pickFrom(runningbacks, "second runningback");
		pickFrom(widerecievers, "widereciever");
		pickFrom(widerecievers, "second widereciever");
		pickFrom(tightends, "tightend");
		pickFrom(tightends, "second tightend");
		pickFrom(kickers, "kicker");
		pickFrom(defensiveline, "defensive line");
		
		list.add(userTeam);

		for(int i=1;i<=numTeams;++i) {
			Team t = new Team(Alphabetical.getInstance(), "Team " + i);
			t.add(setScore(0, 30, quarterbacks.remove(0)));
			t.add(setScore(0, 30, runningbacks.remove(0)));
			t.add(setScore(0, 30, runningbacks.remove(0)));
			t.add(setScore(0, 30, widerecievers.remove(0)));
			t.add(setScore(0, 30, widerecievers.remove(0)));
			t.add(setScore(0, 30, tightends.remove(0)));
			t.add(setScore(0, 30, tightends.remove(0)));
			t.add(setScore(0, 30, kickers.remove(0)));
			t.add(setScore(0, 30, defensiveline.remove(0)));
			list.add(t);
		}
		return list;
	}
	
	//takes user choices and fills userTeam
	private void pickFrom(List<Player> l, String listName) {
		JDialog frame1 = new JDialog(frame,"Fantasy Football",true);		
		frame1.setSize(320, 320);
		frame1.setResizable(false);

		ImageIcon icon = new ImageIcon("football.png");
		frame1.setIconImage(icon.getImage());

		JLabel choosePlayersField = new JLabel("Choose your " + listName, SwingConstants.CENTER);
		JLabel whiteSpace = new JLabel        ("------------------------------------------------------", SwingConstants.CENTER);
		frame1.setLayout(new FlowLayout());
		frame1.add(choosePlayersField);
		frame1.add(whiteSpace);
		int i = 0;
		for(Player p: l){
			JButton tempButton = new JButton(i + ". " + p.getName());
			tempButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					userTeam.add(p);
					l.remove(p);
					frame1.dispose();				
				}
			});
			frame1.add(tempButton);
			++i;
		}
		frame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}

	//Generate a score between bound1 and bound2 and gives it to Player p
	private Player setScore(int bound1, int bound2, Player p) {
		if(!random) {return p;}
		int min = Math.min(bound1,bound2);
		int max = Math.max(bound1,bound2);
		Random r = new Random();
		p.setScore(r.nextInt((max - min) + 1) + min);
		return p;
	}
	
	//validates if valid input, play the game with given input
	private void validateNumTeams(JLabel errorLabel, JTextField numTeamsField) {
		String in = numTeamsField.getText();
		try {
			numTeams = Integer.parseInt( String.valueOf( in ) );
			if(numTeams < 1 || numTeams > 4) {
				errorLabel.setText("Enter an int 1-4");
			}
			else {play(Integer.parseInt(in));}
		} catch ( NumberFormatException exc ) {
			errorLabel.setText("Not an int");
		}
	}

	private void makeRealPlayers() {
		//Quarterbacks
		quarterbacks = new ArrayList<Player>();
		quarterbacks.add(new Player("Patrick Mahones", 11 , Position.Quarterback));
		quarterbacks.add(new Player("Deshaun Watson", 0 , Position.Quarterback));
		quarterbacks.add(new Player("Aaron Rodgers", 19 , Position.Quarterback));
		quarterbacks.add(new Player("Baker Mayfield", 20 , Position.Quarterback));
		quarterbacks.add(new Player("Matt Ryan", 14 , Position.Quarterback));
		quarterbacks.add(new Player("Carson Wentz", 17 , Position.Quarterback));
		quarterbacks.add(new Player("Cam Newton", 0 , Position.Quarterback));
		quarterbacks.add(new Player("Kyler Murray", 13 , Position.Quarterback));
		quarterbacks.add(new Player("Jared Goff", 25 , Position.Quarterback));
		quarterbacks.add(new Player("Russel Wilson", 20 , Position.Quarterback));
		//Runningbacks
		runningbacks = new ArrayList<Player>();
		runningbacks.add(new Player("Saquon Barkley", 20 , Position.Runningback));
		runningbacks.add(new Player("Christian McCaffrey", 22 , Position.Runningback));
		runningbacks.add(new Player("Alvin Kamara", 19 , Position.Runningback));
		runningbacks.add(new Player("Ezekiel Elliot", 27 , Position.Runningback));
		runningbacks.add(new Player("Le'Veon Bell", 12 , Position.Runningback));
		runningbacks.add(new Player("David Johnson", 0 , Position.Runningback));
		runningbacks.add(new Player("James Conner", 0 , Position.Runningback));
		runningbacks.add(new Player("Nick Chubb", 5 , Position.Runningback));
		runningbacks.add(new Player("Joe Mixon", 30 , Position.Runningback));
		runningbacks.add(new Player("Todd Gurley", 10 , Position.Runningback));
		//Wide Recievers
		widerecievers = new ArrayList<Player>();
		widerecievers.add(new Player("DeAndre Hopkins", 0 , Position.Wide_Receiver));
		widerecievers.add(new Player("Davante Adams", 22 , Position.Wide_Receiver));
		widerecievers.add(new Player("Julio Jones", 14 , Position.Wide_Receiver));
		widerecievers.add(new Player("Michael Thomas", 7 , Position.Wide_Receiver));
		widerecievers.add(new Player("Odell Beckham", 17 , Position.Wide_Receiver));
		widerecievers.add(new Player("Tyreek Hill", 10 , Position.Wide_Receiver));
		widerecievers.add(new Player("JuJu Smith-Schuster", 2 , Position.Wide_Receiver));
		widerecievers.add(new Player("Mike Evans", 0 , Position.Wide_Receiver));
		widerecievers.add(new Player("Keenan Allen", 23 , Position.Wide_Receiver));
		widerecievers.add(new Player("Antonio Brown", 0 , Position.Wide_Receiver));
		//Tight Ends
		tightends = new ArrayList<Player>();
		tightends.add(new Player("Travis Kelce", 5 , Position.Tight_End));
		tightends.add(new Player("Zach Ertz", 0 , Position.Tight_End));
		tightends.add(new Player("George Kittle", 16 , Position.Tight_End));
		tightends.add(new Player("Hunter Henry", 15 , Position.Tight_End));
		tightends.add(new Player("Evan Engram", 0 , Position.Tight_End));
		tightends.add(new Player("O.J. Howard", 0 , Position.Tight_End));
		tightends.add(new Player("Jared Cook", 12 , Position.Tight_End));
		tightends.add(new Player("Vance McDonald", 3 , Position.Tight_End));
		tightends.add(new Player("David Njoku", 0 , Position.Tight_End));
		tightends.add(new Player("Jimmy Graham", 9 , Position.Tight_End));
		//Kickers
		kickers = new ArrayList<Player>();
		kickers.add(new Player("Greg Zuerlein", 7 , Position.Kicker));
		kickers.add(new Player("Justin Tucker", 14 , Position.Kicker));
		kickers.add(new Player("Wil Lutz", 6 , Position.Kicker));
		kickers.add(new Player("Harrison Butker", 7 , Position.Kicker));
		kickers.add(new Player("Stephen Gostkowski", 0 , Position.Kicker));
		kickers.add(new Player("Robbie Gould", 8 , Position.Kicker));
		kickers.add(new Player("Ka'imi Fairbairn", 2 , Position.Kicker));
		kickers.add(new Player("Jake Elliot", 11 , Position.Kicker));
		kickers.add(new Player("Mason Crosby", 10 , Position.Kicker));
		kickers.add(new Player("Mike Badgley", 3 , Position.Kicker));
		//Defensive Linemen
		defensiveline = new ArrayList<Player>();
		defensiveline.add(new Player("Darius Leonard", 7 , Position.Defensive_Line));
		defensiveline.add(new Player("Luke Kuechly", 6 , Position.Defensive_Line));
		defensiveline.add(new Player("Bobby Wagner", 5 , Position.Defensive_Line));
		defensiveline.add(new Player("Deion Jones", 13 , Position.Defensive_Line));
		defensiveline.add(new Player("Leighton Vander Esch", 0 , Position.Defensive_Line));
		defensiveline.add(new Player("Blake Martinez", 11 , Position.Defensive_Line));
		defensiveline.add(new Player("Roquan Smith", 0 , Position.Defensive_Line));
		defensiveline.add(new Player("Tremaine Edmunds", 1 , Position.Defensive_Line));
		defensiveline.add(new Player("Cory Littleton", 7 , Position.Defensive_Line));
		defensiveline.add(new Player("Lavonte David", 6 , Position.Defensive_Line));
	}

}
