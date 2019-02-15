package commandline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TopTrumpsCommandLineVersion {

	static String[] header; // The headers in .txt document.

	static int numberOfPlayers; // Number of Players.

	/*
	 * ArrayList for storing Cards.
	 */
	static ArrayList<Card> cards = new ArrayList<Card>();
	static ArrayList<Player> players = new ArrayList<Player>();
	static ArrayList<Player> gamedPlayers = new ArrayList<Player>(); 
	static ArrayList<Card> communalPile = new ArrayList<Card>();
	static ArrayList<Card> roundPile = new ArrayList<Card>();

	/*
	 * round counters.
	 */
	static int basicRound = 0;
	static int comboRound = 0;
	static int totalCombos = 0;
	static int drawCount = 0;
	static int totalRound;

	/*
	 * indexes recorder.
	 */
	static int winnerIndex;
	static int initialPlayerIndex;
	static int currentPlayerIndex;
	
	/*
	 * flags.
	 */
	static boolean ended = false;
	static boolean equaled = false;
	static boolean combo = false;
	
	//Test log or not.
	static boolean writeGameLogsToFile = false;
	
	public static void main(String[] args) throws Exception {
		
		// Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true")) {
			writeGameLogsToFile=true;
		}
		/*
		 * Data initializing.
		 */
		output("--Please enter the number of players (no more than 5 including the human player):");
		Scanner scanner = new Scanner(System.in);
		numberOfPlayers = scanner.nextInt();
		initialPlayerIndex =  new Random().nextInt(numberOfPlayers);
		for (int i = 0; i < numberOfPlayers; i++) {
			players.add(new Player());
		}
		output("number of players: " + players.size());
		
		/*
		 * Game preparing.
		 */
		getCards();
		output("----------Cards loaded!----------");
		
		allocateCards();
		output("----------Cards allocated!----------");

		/*
		 * Game processing.
		 */
		output("----------Game Start!----------");
		output("----Player " + (initialPlayerIndex+1) + " is selected to be the first one for determining character.");
		while (!ended) {
			round();
		}

		/*
		 *  Game ending.
		 */
		output("----------Game Over!----------");
		output("----Player " + players.get(0).getPlayerID() + " won the game! " + players.get(0).getPlayerCards().size() + " cards in total!" );
		GameStatistics gs = new GameStatistics(totalRound, drawCount, gamedPlayers);
		output(gs.toString());
		//update database
		if(JDBC.updateDatabase(gs) != 0) {
			output("----------Database Updated!----------");
		}
	}
	
	private static void output(String s) throws IOException {
		System.out.println(s);
		if(writeGameLogsToFile) {
			TestLog.write(s);
		}
	}

	private static void check() throws IOException {
		boolean hasFailure = false;
		for (Player p : players) {
			p.check();
			if (p.isFailed()) {
				hasFailure = true;
				gamedPlayers.add(p);
				output("----Sorry, Player " + p.getPlayerID() + " without any card lost the game!");
			}
		}
		for(Player p : gamedPlayers) {
			players.remove(p);
		}
		if(hasFailure) {
			numberOfPlayers = players.size();
			output("----There are/is " + numberOfPlayers + " player(s) survived.");
		}
		if(numberOfPlayers == 1) {
			gamedPlayers.add(players.get(0));
			ended = true;
		}
	}

	private static void round() throws IOException {
		/*
		 * If not combo or not equaled, reset the currentPlayerIndex.
		 * Else if combo, show combo congratulations.
		 * Else if equaled, inform with draw.
		 */
		if(!equaled && !combo) {
			currentPlayerIndex = ((basicRound++ % numberOfPlayers) + initialPlayerIndex) % numberOfPlayers;
			comboRound = 0;
		}else if(!equaled && combo) {
			comboRound++;
			totalCombos++;
			output("----Player " + (currentPlayerIndex + 1) + " has the win combo for " + comboRound + " times!");
		}else if(equaled) {
			drawCount++;
		}
		totalRound = basicRound + drawCount + totalCombos;
		output("-------------------------------------------------------------------");
		output("---------- Round " + totalRound + " ! ----------");
		if(combo || equaled) {
			output("----Player " + (currentPlayerIndex+1) + " keeps determining the character.");
		}
		equaled = false;
		
		output("----Player " + (currentPlayerIndex+1) + " is to determine a character.");
		process();
	}

	private static void process() throws IOException {	
		provideCards();
		int character = determineCharacter();
		compare(character);
		check();
	}

	private static void provideCards() throws IOException {
		for(Player p : players) {
			Card providedCard = p.provideCard();
			roundPile.add(providedCard);
		}
		output("----------Round cards provided.----------");
	}

	private static int determineCharacter() throws IOException {
		int character;
		if (currentPlayerIndex == 0) {
			character = decideCharacter();
		}else {
			character = new Random().nextInt(5) + 1;
		}
		output("----Character " + character+ " is chosen!");
		return character;
	}

	private static void compare(int character) throws IOException {
		int initialValue = getCharacterValue(roundPile.get(currentPlayerIndex), character);
		int max = initialValue;
		winnerIndex = currentPlayerIndex;
		ArrayList<Integer> winnersIndexes = new ArrayList<Integer>();
		for(int i = 0; i < roundPile.size(); i++) {
			int value = getCharacterValue(roundPile.get(i), character);
			if (max < value) { 
				max = value; 
			}
		}
		for(int i = 0; i < roundPile.size(); i++) {
			int value = getCharacterValue(roundPile.get(i), character);
			if (max == value) { 
				winnersIndexes.add(i);
			}
		}
		if(winnersIndexes.size() > 1) {
			equaled = true;
			for(Card c : roundPile) {
				communalPile.add(c);
			}
			roundPile.clear();
			output("----There are " + winnersIndexes.size() + " cards are both the maximum on the character!");
			output("----All cards on the table are going to the cummunal pile!");
			output("----Now, the cummunal pile has " + communalPile.size() + " cards.");
			output("----It was a draw!");
			return;
		}else {
			winnerIndex = winnersIndexes.get(0);
			if(winnerIndex == currentPlayerIndex) {
				combo = true;
			}else {
				combo = false;
			}
			Player winner = players.get(winnerIndex);
			winner.recordWin();
			for (Card c : roundPile) {
				winner.addCard(c);
			}
			roundPile.clear();
			output("----Player " + (winnerIndex+1) +" won the round!");
			if (communalPile.size() != 0) {
				for (Card c : communalPile) {
					winner.addCard(c);
				}
				output("----Those " + communalPile.size() + " cards in commual pile belong to him!");
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

	private static int decideCharacter() throws IOException {
		output("----It is your turn to choose a character to compare. \r\n"
				+ "Please enter a number for:");
		output("	1 -- size");
		output("	2 -- speed");
		output("	3 -- range");
		output("	4 -- firepower");
		output("	5 -- cargo");
		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	private static void allocateCards() throws IOException {
		int count = 0;
		int cardAmount = cards.size();
		for (int i = 0; i < cardAmount; i++) {
			int cardIndex = new Random().nextInt(cards.size());
			players.get(count++ % numberOfPlayers).addCard(cards.remove(cardIndex));
		}
		for(Player p : players) {
			p.reverseCards();
		}
		output("----------Cards shuffled!----------");
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

		// Read to jump over the header line.
		s = br.readLine();
		header = s.split(" ");

		// Read the data, generate the cards and store into ArrayList.
		while ((s = br.readLine()) != null) {
			cards.add(new Card(s));
		}
	}
}
