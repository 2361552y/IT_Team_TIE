package commandline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
	int playerID;
	ArrayList<Card> playerCards = new ArrayList<Card>();
	private static int InitialID = 1;
	int countWinRounds = 0;
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

	public int getCountWinRounds() {
		return countWinRounds;
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

	public Card provideCard() throws IOException {
		Card c = playerCards.remove(0);
//		if(playerID == 1) {
			Controller.output("Player " + playerID + ", Your card drawn is " + c.toString());
			Controller.output("There are " + playerCards.size() + " cards in your deck.");
//		}
		return c;
	}
	
	/**
	 * Check whether the player is failed.
	 */
	public void check() throws IOException {
		Controller.output("----------Player " + playerID + " has " + getPlayerCards().size() + " cards left.");
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

	public void recordWin() {
		countWinRounds++;
	}

	public String cardLeft() {
		String s = "";
		switch (getPlayerID()){
			case 1:
				if(!isFailed()){
					s = "Your have " + getPlayerCards().size() + " cards left.";
				}else {
					s = "You lost!";
				}
				return  s;
			default:
				if(!isFailed()){
					s = "AI Player " + (playerID-1) + " has " +getPlayerCards().size()+ " cards left.";
				}else {
					s = "AI Player " + (playerID-1) + " lost!";
				}
				return  s;
		}
	}
}
