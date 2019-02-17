package commandline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Controller {

    static boolean writeGameLogsToFile;

    public static void main(String[] args) throws Exception {

        // Should we write game logs to file?
        if (args[0].equalsIgnoreCase("true")) {
            writeGameLogsToFile = true;
        } else {
            writeGameLogsToFile = false;
        }

        // How many players?
        output("--Please enter the number of players (no more than 5 including yourself):");
        Scanner scanner = new Scanner(System.in);
        int numberOfPlayers = scanner.nextInt();

        Model model = new Model(writeGameLogsToFile, numberOfPlayers);

        //Game preparing.
        model.prepare();

		/*
		 * Game processing.
		 */
		//Game start.
		output("----------Game Start!----------");
		output("----Player " + model.getInitialPlayer() + " is selected to be the first one for determining character.");
		//Game rounds.
		while(!model.getEnded()){
			model.round();
		}
        //Game ended.
		model.end();
	}

	public static void output(String s) throws IOException {
		System.out.println(s);
		if(writeGameLogsToFile) {
			View.write(s);
		}
	}
}