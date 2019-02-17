package commandline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Model {

    String[] header; // The headers in .txt document.

    int numberOfPlayers; // Number of Players.

    /*
     * ArrayList for storing Cards.
     */
    ArrayList<Card> cards = new ArrayList<Card>();
    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<Player> gamedPlayers = new ArrayList<Player>();
    ArrayList<Card> communalPile = new ArrayList<Card>();
    ArrayList<Card> roundPile = new ArrayList<Card>();

    /*
     * round counters.
     */
    int basicRound = 0;
    int comboRound = 0;
    int totalCombos = 0;
    int drawCount = 0;
    int totalRound;

    /*
     * indexes recorder.
     */
    int winnerIndex;
    int initialPlayerIndex;
    int currentPlayerIndex;

    /*
     * flags.
     */
    boolean ended = false;
    boolean equaled = false;
    boolean combo = false;

    //Test log or not.
    boolean writeGameLogsToFile = false;

    public Model(boolean logFlag, int numberOfPlayers) throws Exception {
        writeGameLogsToFile = logFlag;
        this.numberOfPlayers = numberOfPlayers;
    }

    public Model(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void end() throws IOException, SQLException, ClassNotFoundException {
        /*
         *  Game ending.
         */
        Controller.output("----------Game Over!----------");
        Controller.output("----Player " + players.get(0).getPlayerID() + " won the game! " + players.get(0).getPlayerCards().size() + " cards in total!");
        GameStatistics gs = new GameStatistics(totalRound, drawCount, gamedPlayers);
        Controller.output(gs.toString());

        //update database
        if (JDBC.updateDatabase(gs) != 0) {
            Controller.output("----------Database Updated!----------");
        }
    }

    public void prepare() throws Exception {
        /*
         * Data initializing.
         */
        initialPlayerIndex = new Random().nextInt(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player());
        }
        Controller.output("number of players: " + players.size());
        /*
         * Game preparing.
         */
        getCards();
        Controller.output("----------Cards loaded!----------");

        allocateCards();
        Controller.output("----------Cards allocated!----------");
    }

    private void check() throws IOException {
        boolean hasFailure = false;
        for (Player p : players) {
            p.check();
            if (p.isFailed()) {
                hasFailure = true;
                gamedPlayers.add(p);
                Controller.output("----Sorry, Player " + p.getPlayerID() + " without any card lost the game!");
            }
        }
        for (Player p : gamedPlayers) {
            players.remove(p);
        }
        if (hasFailure) {
            numberOfPlayers = players.size();
            Controller.output("----There are/is " + numberOfPlayers + " player(s) survived.");
        }
        if (numberOfPlayers == 1) {
            gamedPlayers.add(players.get(0));
            ended = true;
        }
    }

    public void round() throws IOException {
        compRoundInfo();
        process();
    }

    public void compRoundInfo() throws IOException {
        /*
         * If not combo or not equaled, reset the currentPlayerIndex.
         * Else if combo, show combo congratulations.
         * Else if equaled, inform with draw.
         */
        if (!equaled && !combo) {
            currentPlayerIndex = ((basicRound++ % numberOfPlayers) + initialPlayerIndex) % numberOfPlayers;
            comboRound = 0;
        } else if (!equaled && combo) {
            comboRound++;
            totalCombos++;
            Controller.output("----Player " + (currentPlayerIndex + 1) + " has the win combo for " + comboRound + " times!");
        } else if (equaled) {
            drawCount++;
        }
        totalRound = basicRound + drawCount + totalCombos;
        Controller.output("-------------------------------------------------------------------");
        Controller.output("---------- Round " + totalRound + " ! ----------");
        if (combo || equaled) {
            Controller.output("----Player " + (currentPlayerIndex + 1) + " keeps determining the character.");
        }
        equaled = false;

        Controller.output("----Player " + (currentPlayerIndex + 1) + " is to determine a character.");
    }
    private void process() throws IOException {
        provideCards();
        int character = determineCharacter();
        compare(character);
        check();
    }

    public void provideCards() throws IOException {
        for (Player p : players) {
            Card providedCard = p.provideCard();
            roundPile.add(providedCard);
        }
        Controller.output("----------Round cards provided.----------");
    }

    public ArrayList<Card> getRoundPile(){
        return roundPile;
    }

    private int determineCharacter() throws IOException {
        int character;
        if (players.get(currentPlayerIndex).getPlayerID() == 1) {
            character = decideCharacter();
        } else {
            character = new Random().nextInt(5) + 1;
        }
        Controller.output("----Character " + character + " is chosen!");
        return character;
    }

    private void compare(int character) throws IOException {
        int initialValue = getCharacterValue(roundPile.get(currentPlayerIndex), character);
        int max = initialValue;
        winnerIndex = currentPlayerIndex;
        ArrayList<Integer> winnersIndexes = new ArrayList<Integer>();
        for (int i = 0; i < roundPile.size(); i++) {
            int value = getCharacterValue(roundPile.get(i), character);
            if (max < value) {
                max = value;
            }
        }
        for (int i = 0; i < roundPile.size(); i++) {
            int value = getCharacterValue(roundPile.get(i), character);
            if (max == value) {
                winnersIndexes.add(i);
            }
        }
        if (winnersIndexes.size() > 1) {
            equaled = true;
            for (Card c : roundPile) {
                communalPile.add(c);
            }
            roundPile.clear();
            Controller.output("----There are " + winnersIndexes.size() + " cards are both the maximum on the character!");
            Controller.output("----All cards on the table are going to the cummunal pile!");
            Controller.output("----Now, the cummunal pile has " + communalPile.size() + " cards.");
            Controller.output("----It was a draw!");
            return;
        } else {
            winnerIndex = winnersIndexes.get(0);
            if (winnerIndex == currentPlayerIndex) {
                combo = true;
            } else {
                combo = false;
            }
            Player winner = players.get(winnerIndex);
            winner.recordWin();
            for (Card c : roundPile) {
                winner.addCard(c);
            }
            roundPile.clear();
            Controller.output("----Player " + (winnerIndex + 1) + " won the round!");
            if (communalPile.size() != 0) {
                for (Card c : communalPile) {
                    winner.addCard(c);
                }
                Controller.output("----Those " + communalPile.size() + " cards in commual pile belong to him!");
                communalPile.clear();
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

    private int decideCharacter() throws IOException {
        Controller.output("----It is your turn to choose a character to compare. \r\n"
                + "Please enter a number for:");
        Controller.output("	1 -- size");
        Controller.output("	2 -- speed");
        Controller.output("	3 -- range");
        Controller.output("	4 -- firepower");
        Controller.output("	5 -- cargo");
        Scanner s = new Scanner(System.in);
        int character = s.nextInt();
        Controller.output("You chose " + character + "!");
        return character;
    }

    private void allocateCards() throws IOException {
        int count = 0;
        int cardAmount = cards.size();
        for (int i = 0; i < cardAmount; i++) {
            int cardIndex = new Random().nextInt(cards.size());
            players.get(count++ % numberOfPlayers).addCard(cards.remove(cardIndex));
        }
        for (Player p : players) {
            p.reverseCards();
        }
        Controller.output("----------Cards shuffled!----------");
    }

    /**
     * Read the .txt document, get all cards and store them into ArrayList.
     *
     * @throws Exception
     */
    private void getCards() throws Exception {
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

    public boolean getEnded() {
        return ended;
    }

    public int getInitialPlayer() {
        return initialPlayerIndex + 1;
    }

    public ArrayList<Integer> getRoundNumbers() {
        ArrayList<Integer> roundNumbers = new ArrayList<Integer>();
        roundNumbers.add(totalRound);
        roundNumbers.add(players.get(currentPlayerIndex).getPlayerID());
        for(Player p : players){
            roundNumbers.add(p.getPlayerID());
        }
        return roundNumbers;
    }

    public void chooseCharacter(int character) throws IOException {
        compare(character);
        check();
    }


    public String showResult() throws IOException {
        if(equaled){
            return "Result: Draw!";
        }else if(winnerIndex == 0){
            return "Result: You won!";
        }else {
            return "Result: AI Player " + winnerIndex + " won!";
        }

    }

    public ArrayList<String> showWinner() throws IOException {
        ArrayList<String> s = new ArrayList<String>();
        if(!ended){
            for(Player p : players){
                s.add(p.cardLeft());
            }
            compRoundInfo();
            provideCards();
        }else{
            compRoundInfo();
            switch (players.get(0).getPlayerID()){
                case 1:
                    s.add("Yeah! You won the game!");
                    break;
                default:
                    s.add("Oh NO! AI Player " + (players.get(0).getPlayerID()-1) + " won the game!.");
            }
        }
        return  s;
    }
}
