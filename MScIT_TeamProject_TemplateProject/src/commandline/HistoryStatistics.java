package commandline;

public class HistoryStatistics {

	private int numberOfGames;
	private int numberOfHumanWins;
	private int numberOfAIWins;
	private double averageNumberOfDraws;
	private int longestGame;
	//constructor
	public HistoryStatistics(int g, int h, int a,double d, int l)
	{
		this.numberOfGames = g;
		this.numberOfHumanWins = h;
		this.numberOfAIWins = a;
		this.averageNumberOfDraws = d;
		this.longestGame = l;
	}
	// no-parameter constructor
	public HistoryStatistics(){
	}
	//toString
	public String toString()
	{
		return "Game Statistics:\r\n" + 
				"   Number of Games: "+ numberOfGames + "\r\n" + 
				"   Number of Human Wins: " + numberOfHumanWins + "\r\n" + 
				"   Number of AI Wins: "+ numberOfAIWins + "\r\n" + 
				"   Average number of Draws: " + averageNumberOfDraws + "\r\n" + 
				"   Longest Game: "+ numberOfGames;
	}
	
	//getters and setters
	public int getNumberOfGames() {
		return numberOfGames;
	}

	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public int getNumberOfHumanWins() {
		return numberOfHumanWins;
	}

	public void setNumberOfHumanWins(int numberOfHumanWins) {
		this.numberOfHumanWins = numberOfHumanWins;
	}

	public int getNumberOfAIWins() {
		return numberOfAIWins;
	}

	public void setNumberOfAIWins(int numberOfAIWins) {
		this.numberOfAIWins = numberOfAIWins;
	}

	public double getAverageNumberOfDraws() {
		return averageNumberOfDraws;
	}

	public void setAverageNumberOfDraws(String averageNumberOfDraws) {
		this.averageNumberOfDraws = Double.valueOf(averageNumberOfDraws);
	}

	public int getLongestGame() {
		return longestGame;
	}

	public void setLongestGame(int longestGame) {
		this.longestGame = longestGame;
	}
	
}
