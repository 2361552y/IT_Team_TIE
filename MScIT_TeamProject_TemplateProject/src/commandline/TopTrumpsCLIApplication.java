package commandline;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
 	 * @param args
	 */
	static boolean writeGameLogsToFile = false; // Should we write game logs to file?
	public static void main(String[] args) {

		try {
			if (args[0].equalsIgnoreCase("true")) {
				writeGameLogsToFile=true; // Command line selection
				View.preparation();
			}
		
			// State
			boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		
			// Loop until the user wants to exit the game
			while (!userWantsToQuit) {
				
				// ----------------------------------------------------
				// Add your game logic here based on the requirements
				// ----------------------------------------------------
				output("Do you wan to see the past statistics or play a new game?");
				output("		input \"1\" 	---- Check the History Statistics.");
				output("		input \"2\" 	---- Play a New Game.");
				output("		press \"Enter\" ---- Exit.");
				String s = getInput();
				if(s.equals("1")) {
					//Connect to the database, use SQL statements to query statistics and display.
					try {
						output(JDBC.queryHistoryStatistics().toString());
					} catch (ClassNotFoundException e) {
						System.err.println("JDBC Driver not found!");
						e.printStackTrace();
					} catch (SQLException e) {
						System.err.println("Database Error!");
						e.printStackTrace();
					}
				}else if(s.equals("2")){
					String[] commandArgs = {String.valueOf(writeGameLogsToFile)};
					Controller.output("----New Game Starts!");
					try {
						Controller.main(commandArgs);
					} catch (Exception e) {
						System.err.println("Sorry! Error!");
						e.printStackTrace();
					}
					
				}else if(s.trim().equals("")) {
					userWantsToQuit=true; // use this when the user wants to exit the game
				}else{
					System.err.println("Type Error. Retry!");
				}
			}
			output("Thanks! Bye!");
		} catch (IOException e) {
			System.err.println("IO Error!");
			e.printStackTrace();
		} finally {
			try {
				JDBC.closeConnection();
				View.close();
			} catch (SQLException e) {
				System.err.println("Failed to close JDBC!");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Failed to close FileWriter!");
				e.printStackTrace();
			}
		}
	}
	
	private static void output(String s) throws IOException {
		System.out.println(s);
		if(writeGameLogsToFile) {
			View.write(s);
		}
	}

	private static String getInput() {
		return new Scanner(System.in).nextLine();
	}

}
