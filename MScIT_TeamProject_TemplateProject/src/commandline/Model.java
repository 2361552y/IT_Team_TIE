package commandline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * BondModel is the modelling of storing data into ArrayList of a class after
 * reading the File provided.
 * 
 * @author 33101
 *
 */
public class Model {

	String[] header; // The headers in .txt document.
	int numberOfPlayers; // Number of Players.
	// ArrayList for storing Cards.
	ArrayList<Card> cards = new ArrayList<Card>();
	// ArrayList for storing Players.
	ArrayList<Player> players = new ArrayList<Player>();
	// ArrayList for storing communal Cards.
	ArrayList<Card> communalPile = new ArrayList<Card>();
	// ArrayList for storing round Cards.
	ArrayList<Card> roundPile = new ArrayList<Card>();
	int round = 0;
	int comboRound = 0;
	int totalRound;

	int winnerIndex = -1;
	int initialPlayerIndex = new Random().nextInt(numberOfPlayers - 1);
	int currentPlayerIndex = initialPlayerIndex;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public Model(int numberOfPlayers) throws Exception {
		// preparing
		this.numberOfPlayers = numberOfPlayers;
		for (int i = 0; i < numberOfPlayers; i++) {
			players.add(new Player());
		}
		getCards();
		distributeCards();

		// processing
		while (this.numberOfPlayers > 1) {
			round();
			check();
		}

		// ending
		totalRound = round + comboRound;

	}

	private void check() {
		for (Player p : players) {
			p.check();
			if (p.isFailed()) {
				players.remove(p);
			}
		}
		numberOfPlayers = players.size();
	}

	private void round() {
		currentPlayerIndex = ((round++ % numberOfPlayers) + initialPlayerIndex) % numberOfPlayers;
		int character;
		do {
			comboRound++;
			switch (currentPlayerIndex) {
			case 0:
				character = decideCharacter();
				break;
			default:
				character = new Random().nextInt(4) + 1;
			}
			compare(character);
		} while (winnerIndex == currentPlayerIndex);
		comboRound--;
	}

	private void compare(int character) {
		int max = -1;
		for (Player p : players) {
			Card drawnCard = p.drawCard();
			roundPile.add(drawnCard);
			int value = getCharacterValue(drawnCard, character);
			if (max < value) {
				max = value;
				winnerIndex = p.getPlayerID() - 1;
			} else if (max == value) {
				communalPile.add(drawnCard);
			}
			if(p.getPlayerID() == numberOfPlayers) {
				return;
			}
		}
		Player winner = players.get(winnerIndex);
		for(Card c : roundPile) {
			winner.addCard(c);
		}
		if(communalPile.size() != 0) {
			for(Card c : communalPile) {
				winner.addCard(c);
			}
		}
			

	}

	private int getCharacterValue(Card drawnCard, int character) {
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

	private int decideCharacter() {
		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	private void distributeCards() {
		int count = 0;
		for (int i = 0; i < cards.size(); i++) {
			int cardIndex = new Random().nextInt(cards.size() - count);
			players.get(count % numberOfPlayers).addCard(cards.remove(cardIndex));
			count++;
		}
	}

	/**
	 * Read the .txt document, get all cards and store them into ArrayList.
	 * 
	 * @throws Exception
	 */
	private void getCards() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("/TopTrumpsOfTIE/StarCitizenDeck.txt")));

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
	 * Get the Bond trade from ArrayList by search its ID.
	 * 
	 * @param ID, bondID
	 * @return the certain Bond trade details.
	 */
	public Card getCardsByID(int ID) {
		return cards.get(ID - 1);
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