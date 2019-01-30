package commandline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that
	 * they want to run in command line mode. The contents of args[0] is whether we
	 * should write game logs to a file.
	 * 
	 * @param args
	 */
	static String[] header; // The headers in .txt document.

	static int numberOfPlayers; // Number of Players.

	/*
	 * ArrayList for storing Cards.
	 */
	static ArrayList<Card> cards = new ArrayList<Card>();
	static ArrayList<Player> players = new ArrayList<Player>();
	static ArrayList<Card> communalPile = new ArrayList<Card>();
	static ArrayList<Card> roundPile = new ArrayList<Card>();

	/*
	 * round counters.
	 */
	static int basicRound = 0;
	static int comboRound = 0;
	static int totalRound;

	/*
	 * indexes recorder.
	 */
	static int winnerIndex;
	static int initialPlayerIndex;
	static int currentPlayerIndex = initialPlayerIndex;
	
	static boolean ended = false;

	public static void main(String[] args) {

//		boolean writeGameLogsToFile = false; // Should we write game logs to file?
//		if (args[0].equalsIgnoreCase("true")) {
//			writeGameLogsToFile = true; // Command line selection
//		}

		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {
			// ----------------------------------------------------
			// Add your game logic here based on the requirements
			// ----------------------------------------------------
			
			/*
			 * Data initializing.
			 */
			System.out.println("--Please enter the number of players (no more than 5 including the human player):");
			Scanner s = new Scanner(System.in);
			numberOfPlayers = s.nextInt();
			initialPlayerIndex =  new Random().nextInt(numberOfPlayers - 1);
			for (int i = 0; i < numberOfPlayers; i++) {
				players.add(new Player());
			}
			System.err.println(players.size());
			/*
			 * Game preparing.
			 */
			try {
				getCards();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			distributeCards();

			/*
			 * Game processing.
			 */
			System.out.println("--Game Start!");
			System.out.println("--Player " + (initialPlayerIndex+1) + " is selected to be the first one for determining character.");
			while (!ended) {
				round();
			}

			/*
			 *  Game ending.
			 */
			System.out.println("--Game Over!");
			System.out.println("Player " + players.get(0).getPlayerID() + " won! " + players.get(0).getPlayerCards().size() + " cards in total!" );
			userWantsToQuit = true; // use this when the user wants to exit the game

		}

	}

	private static void check() {
		boolean hasFailure = false;
		for (Player p : players) {
			p.check();
			if (p.isFailed()) {
				hasFailure = true;
				players.remove(p);
				System.out.println("Sorry, Player " + p.getPlayerID() + " without any card lost the game!");
			}
		}
		if(hasFailure) {
			numberOfPlayers = players.size();
			System.out.println("There are " + numberOfPlayers + " players survived.");
		}
		if(numberOfPlayers == 1) {
			ended = true;
		}
	}

	private static void round() {
		currentPlayerIndex = ((basicRound++ % numberOfPlayers) + initialPlayerIndex) % numberOfPlayers;
		System.err.println(currentPlayerIndex);
		totalRound = basicRound + comboRound;
		System.err.println(totalRound + " t " + basicRound + " b " +comboRound + " c");
		
		System.out.println("-------------------------------------------------------------------");
		System.out.println("Round " + totalRound + " !");
		
		process();
		
		while ( !ended && winnerIndex == currentPlayerIndex) {
			comboRound++;
			totalRound = basicRound + comboRound;
			
			System.out.println("Player " + (currentPlayerIndex + 1) + " has the combo win for " + comboRound + " times!");
			
			System.out.println("-------------------------------------------------------------------");
			System.out.println("Round " + totalRound + " !");
			
			process();
		}
	}

	private static void process() {
		drawCards();
		int character = determineCharacter(currentPlayerIndex);
		compare(character);
		check();
	}

	private static void drawCards() {
		for(Player p : players) {
			Card drawnCard = p.drawCard();
			roundPile.add(drawnCard);
		}
	}

	private static int determineCharacter(int currentPlayerIndex2) {
		int character;
		switch (currentPlayerIndex) {
		case 0:
			character = decideCharacter();
			break;
		default:
			character = new Random().nextInt(4) + 1;
		}
		System.out.println("Character " + character+ " is chosen!");
		return character;
	}

	private static void compare(int character) {
		int initialValue = getCharacterValue(roundPile.get(0), character);
		int max = initialValue;
		winnerIndex = 0;
		ArrayList<Integer> winners = new ArrayList<Integer>();
		for(int i = 1; i < roundPile.size(); i++) {
			int value = getCharacterValue(roundPile.get(i), character);
			if (max <= value) { 
				max = value; 
				winnerIndex = i; 
				winners.add(i); 
			}
		}
		if(max == initialValue) {
			winners.add(0);
		}
		if(winners.size() > 1) {
			for(Card c : roundPile) {
				communalPile.add(c);
			}
			roundPile.clear();
			System.out.println("There are " + winners.size() + " cards are equal on the character!");
			System.out.println("They are going to the cummunal pile!");
			return;
		}else {
			Player winner = players.get(winnerIndex);
			for (Card c : roundPile) {
				winner.addCard(c);
			}
			roundPile.clear();
			System.out.println("Player " + (winnerIndex+1) +" won the round!");
			if (communalPile.size() != 0) {
				for (Card c : communalPile) {
					winner.addCard(c);
				}
				System.out.println("Those " + communalPile.size() + " cards in commual pile belong to him!");
				communalPile.clear();
			}
		}

	}

	private static int getCharacterValue(Card drawnCard, int character) {
		switch (character) {
		case 1:
			return drawnCard.getSize();
		case 2:
			return drawnCard.getSpeed();
		case 3:
			return drawnCard.getRange();
		case 4:
			return drawnCard.getFirepower();
		default:
			return drawnCard.getCargo();
		}
	}

	private static int decideCharacter() {
		System.out.println("It is your turn to choose a character to compare. \r\n"
				+ "Please enter a number for:");
		System.out.println("	1 -- size");
		System.out.println("	2 -- speed");
		System.out.println("	3 -- range");
		System.out.println("	4 -- firepower");
		System.out.println("	5 -- cargo");
		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	private static void distributeCards() {
		int count = 0;
		int cardAmount = cards.size();
		for (int i = 0; i < cardAmount; i++) {
			int cardIndex = new Random().nextInt(cards.size());
			players.get(count % numberOfPlayers).addCard(cards.remove(cardIndex));
			count++;
		}
		for(Player p : players) {
			p.reverseCards();
		}
	}

	/**
	 * Read the .txt document, get all cards and store them into ArrayList.
	 * 
	 * @throws Exception
	 */
	private static void getCards() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("./StarCitizenDeck.txt")));

		// Store the readings.
		String s = "";

		// Jump over the Header Line.
		s = br.readLine();

		header = s.split(" ");

		// Read the data, generate the Bond and store into ArrayList.
		while ((s = br.readLine()) != null) {
			cards.add(new Card(s));
		}
	}

	/**
	 * Get the header in the .csv document.
	 * 
	 * @return header.
	 */
	public String[] getHeader() {
		return header;
	}

	/**
	 * Get the bonds ArrayList.
	 * 
	 * @return bonds.
	 */
	public ArrayList<Card> getBondsArray() {
		return cards;
	}
}
