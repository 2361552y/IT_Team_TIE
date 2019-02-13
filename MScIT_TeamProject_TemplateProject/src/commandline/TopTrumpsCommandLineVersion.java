package commandline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	static ArrayList<Card> communalPile = new ArrayList<Card>();
	static ArrayList<Card> roundPile = new ArrayList<Card>();

	/*
	 * round counters.
	 */
	static int basicRound = 0;
	static int comboRound = 0;
	static int totalCombos = 0;
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
	
	public static void main(String[] args) {
		/*
		 * Data initializing.
		 */
		System.out.println("--Please enter the number of players (no more than 5 including the human player):");
		Scanner s = new Scanner(System.in);
		numberOfPlayers = s.nextInt();
		initialPlayerIndex =  new Random().nextInt(numberOfPlayers);
		for (int i = 0; i < numberOfPlayers; i++) {
			players.add(new Player());
		}
		System.err.println("number of players: " + players.size());
		
		/*
		 * Game preparing.
		 */
		try {
			getCards();
		} catch (Exception e) {
			System.err.println("Error! Failed to get cards!");
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
		/*
		 * If not combo, reset the currentPlayerIndex.
		 */
		if(!combo) {
			currentPlayerIndex = ((basicRound++ % numberOfPlayers) + initialPlayerIndex) % numberOfPlayers;
			comboRound = 0;
		}
		equaled = false;
		combo = false;
		totalRound = basicRound + totalCombos;
		
		System.err.println("The current player: " + (currentPlayerIndex + 1));
		System.err.println(" totalround:  " + totalRound + " basicround:  " + basicRound + "  comboround:   " +comboRound + "  totalcombos:   " + totalCombos);
		
		System.out.println("-------------------------------------------------------------------");
		System.out.println("Round " + totalRound + " !");
		
		process();
	}

	private static void process() {	
		drawCards();
		int character = determineCharacter();
		compare(character);
		check();
		if ( !ended && (winnerIndex == currentPlayerIndex || equaled == true)) {
			combo = true;
			comboRound++;
			totalCombos++;
			totalRound = basicRound + comboRound;
			System.out.println("Player " + (currentPlayerIndex + 1) + " has the combo win for " + comboRound + " times!");
		}
	}

	private static void drawCards() {
		for(Player p : players) {
			Card drawnCard = p.drawCard();
			roundPile.add(drawnCard);
		}
	}

	private static int determineCharacter() {
		int character;
		if (currentPlayerIndex == 0) {
			character = decideCharacter();
		}else {
			character = new Random().nextInt(4) + 1;
		}
		System.out.println("Character " + character+ " is chosen!");
		return character;
	}

	private static void compare(int character) {
		int initialValue = getCharacterValue(roundPile.get(0), character);
		int max = initialValue;
		winnerIndex = 0;
		ArrayList<Integer> winnersIndexes = new ArrayList<Integer>();
		for(int i = 1; i < roundPile.size(); i++) {
			int value = getCharacterValue(roundPile.get(i), character);
			if (max <= value) { 
				max = value; 
				winnerIndex = i; 
				winnersIndexes.add(i); 
			}
		}
		if(max == initialValue) {
			winnerIndex = 0;
			winnersIndexes.add(0);
		}
		if(winnersIndexes.size() > 1) {
			equaled = true;
			for(Card c : roundPile) {
				communalPile.add(c);
			}
			roundPile.clear();
			System.out.println("There are " + winnersIndexes.size() + " cards are both the maximum on the character!");
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
			players.get(count++ % numberOfPlayers).addCard(cards.remove(cardIndex));
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

		// Read the header line.
		s = br.readLine();

		header = s.split(" ");

		// Read the data, generate the cards and store into ArrayList.
		while ((s = br.readLine()) != null) {
			cards.add(new Card(s));
		}
	}

	/**
	 * Get the header in the .txt document.
	 * 
	 * @return header.
	 */
	public String[] getHeader() {
		return header;
	}

	/**
	 * Get the cards ArrayList.
	 * 
	 * @return cards.
	 */
	public ArrayList<Card> getCardsArray() {
		return cards;
	}

}
