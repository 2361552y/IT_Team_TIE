package commandline;

import java.util.ArrayList;

public class GameStatistics {
	private int numberOfRounds;
	private int winnerID;
	private int numberOfDraws;
	private int numberOfPlayers;
	private int player1Wins = -1;
	private int player2Wins = -1;
	private int player3Wins = -1;
	private int player4Wins = -1;
	private int player5Wins = -1;
	
	public GameStatistics(int numberOfRounds, int numberOfDraws, ArrayList<Player> players) {
		this.numberOfRounds = numberOfRounds;
		this.numberOfDraws = numberOfDraws;
		this.numberOfPlayers = players.size();
		for(Player p : players) {
			switch(p.getPlayerID()) {
			case 1:
				player1Wins = p.getCountWinRounds();
				break;
			case 2:
				player2Wins = p.getCountWinRounds();
				break;
			case 3:
				player3Wins = p.getCountWinRounds();
				break;
			case 4:
				player4Wins = p.getCountWinRounds();
				break;
			case 5:
				player5Wins = p.getCountWinRounds();
				break;
			default:
				
			}
			if(!p.isFailed()) {
				winnerID = p.getPlayerID();
			}
		}
	}
	
	public int getNumberOfRounds() {
		return numberOfRounds;
	}
	public void setNumberOfRounds(int numberOfRounds) {
		this.numberOfRounds = numberOfRounds;
	}
	public int getWinnerID() {
		return winnerID;
	}
	public void setWinnerID(int winnerID) {
		this.winnerID = winnerID;
	}
	public int getNumberOfDraws() {
		return numberOfDraws;
	}
	public void setNumberOfDraws(int numberOfDraws) {
		this.numberOfDraws = numberOfDraws;
	}
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	public int getPlayer1Wins() {
		return player1Wins;
	}
	public void setPlayer1Wins(int player1Wins) {
		this.player1Wins = player1Wins;
	}
	public int getPlayer2Wins() {
		return player2Wins;
	}
	public void setPlayer2Wins(int player2Wins) {
		this.player2Wins = player2Wins;
	}
	public int getPlayer3Wins() {
		return player3Wins;
	}
	public void setPlayer3Wins(int player3Wins) {
		this.player3Wins = player3Wins;
	}
	public int getPlayer4Wins() {
		return player4Wins;
	}
	public void setPlayer4Wins(int player4Wins) {
		this.player4Wins = player4Wins;
	}
	public int getPlayer5Wins() {
		return player5Wins;
	}
	public void setPlayer5Wins(int player5Wins) {
		this.player5Wins = player5Wins;
	}
	public int compAIWins() {
		int aIWins = player2Wins;
		if(player3Wins != -1) {
			aIWins += player3Wins;
		}
		if(player4Wins != -1) {
			aIWins += player4Wins;
		}
		if(player5Wins != -1) {
			aIWins += player5Wins;
		}
		return aIWins;
	}
	
	@Override
	public String toString() {
		String scores = "   You: " + player1Wins + "\r\n" +
				"   AI Player 1: " + player2Wins + "\r\n";
		if(player3Wins != -1){
			scores += "   AI Player 2: " + player3Wins + "\r\n";
		}
		if(player4Wins != -1){
			scores += "   AI Player 3: " + player4Wins + "\r\n";
		}
		if(player5Wins != -1){
			scores += "   AI Player 4: " + player5Wins + "\r\n";
		}
		return "Game Statistics:\r\n" +
				"Number of Players: " + numberOfPlayers + "\r\n" +
				"Scores:\r\n" + scores;
	}
	
}
