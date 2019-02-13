package commandline;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
	int playerID;
	ArrayList<Card> playerCards = new ArrayList<Card>();
	private static int InitialID = 1;
	boolean failed = false;

	public Player(ArrayList<Card> playerCards) {
		playerID = InitialID++;
		this.playerCards = playerCards;
	}
	
	public Player() {
		playerID = InitialID++;
	}

	public void addCard(Card card) {
		playerCards.add(card);
	}
	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(ArrayList<Card> playerCards) {
		this.playerCards = playerCards;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public static void resetID() {
		InitialID = 1;
	}

	public Card drawCard() {
		Card c = playerCards.remove(0);
//		if(playerID == 1) {
			System.out.println("Player " + playerID + ", Your card drawn is " + c.toString());
			System.out.println("There are " + playerCards.size() + " cards in your deck.");
//		}
		return c;
	}
	
	/**
	 * Check whether the player is failed.
	 */
	public void check() {
		if(playerCards.size() == 0) {
			failed = true;
		}
	}

	/**
	 * Reverse the sequence of cards.
	 */
	public void reverseCards() {
		Collections.reverse(playerCards);
	}
}
