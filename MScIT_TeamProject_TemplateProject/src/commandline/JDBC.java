package commandline;

import java.sql.*;
import java.text.DecimalFormat;

public class JDBC {
	
	static Connection connection = null;
	static Statement statement = null;
	
	private static void getConnection() throws ClassNotFoundException, SQLException {
		if (connection == null) {
			// load the JDBC driver for Postgresql
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/topTrumps", "postgres", "999999");
		}
	}

	public static HistoryStatistics queryHistoryStatistics() throws SQLException, ClassNotFoundException {
		getConnection();
		statement = connection.createStatement();
		HistoryStatistics history = new HistoryStatistics();
		String sql1 = "select count(*) as numberOfGames from gamedata;";
		String sql2 = "select count(WinnerId) as numberOfHumanWins from gamedata where WinnerId !='1';";
		String sql3 = "select count(WinnerId) as numberOfAIWins from gamedata where WinnerId ='1'; ";
		String sql4 = "select avg(numberofdraws) as averageNumberOfDraws from gamedata;";
		String sql5 = "select max(numberofrounds) as longestGame from gamedata;";
		
		ResultSet rs = statement.executeQuery(sql1);
		rs.next();
		history.setNumberOfGames(rs.getInt("numberOfGames"));
		
		rs = statement.executeQuery(sql2);
		rs.next();
		history.setNumberOfHumanWins(rs.getInt("numberOfHumanWins"));
		
		rs = statement.executeQuery(sql3);
		rs.next();
		history.setNumberOfAIWins(rs.getInt("numberOfAIWins"));
		
		rs = statement.executeQuery(sql4);
		rs.next();
		double d = rs.getDouble("averageNumberOfDraws");
		history.setAverageNumberOfDraws(new DecimalFormat("0.0").format(d));
		
		rs = statement.executeQuery(sql5);
		rs.next();
		history.setLongestGame(rs.getInt("longestGame"));
		
		return history;
	}
	
	public static void closeConnection() throws SQLException {
		if(connection != null) {
			connection.close();
		}
	}
	
	public static int updateDatabase(GameStatistics gs) throws ClassNotFoundException, SQLException {
		getConnection();
		statement = connection.createStatement();
		String sql = "INSERT INTO gamedata ("
				+ "numberofrounds,"
				+ "winnerid,"
				+ "numberofdraws,"
				+ "numberofplayers,"
				+ "player1wins,"
				+ "player2wins,"
				+ "player3wins,"
				+ "player4wins,"
				+ "player5wins"
				+ ")"
				+ " VALUES (" +
													gs.getNumberOfRounds() + ", " +
													gs.getWinnerID() + ", " +
													gs.getNumberOfDraws() + ", " +
													gs.getNumberOfPlayers() + ", " +
													gs.getPlayer1Wins() + ", " +
													gs.getPlayer2Wins() + ", " +
													gs.getPlayer3Wins() + ", " +
													gs.getPlayer4Wins() + ", " +
													gs.getPlayer5Wins() +
													");";
		return statement.executeUpdate(sql);
	}
}
