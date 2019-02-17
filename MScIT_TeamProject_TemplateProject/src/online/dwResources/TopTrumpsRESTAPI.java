package online.dwResources;

import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import commandline.Card;
import commandline.Model;
import online.configuration.TopTrumpsJSONConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import commandline.JDBC;

@Path("/toptrumps") // Resources specified here should be hosted at http://localhost:7777/toptrumps
@Produces(MediaType.APPLICATION_JSON) // This resource returns JSON content
@Consumes(MediaType.APPLICATION_JSON) // This resource can take JSON content as input
/**
 * This is a Dropwizard Resource that specifies what to provide when a user
 * requests a particular URL. In this case, the URLs are associated to the
 * different REST API methods that you will need to expose the game commands
 * to the Web page.
 * 
 * Below are provided some sample methods that illustrate how to create
 * REST API methods in Dropwizard. You will need to replace these with
 * methods that allow a TopTrumps game to be controled from a Web page.
 */
public class TopTrumpsRESTAPI {

	/** A Jackson Object writer. It allows us to turn Java objects
	 * into JSON strings easily. */
	ObjectWriter oWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

	Model model;
	
	/**
	 * Contructor method for the REST API. This is called first. It provides
	 * a TopTrumpsJSONConfiguration from which you can get the location of
	 * the deck file and the number of AI players.
	 * @param conf
	 */
	public TopTrumpsRESTAPI(TopTrumpsJSONConfiguration conf) throws Exception {
		// ----------------------------------------------------
		// Add relevant initalization here
		// ----------------------------------------------------

		model = new Model(conf.getNumAIPlayers() + 1);
		//Game preparing.
		model.prepare();
		//Game processing.
		model.compRoundInfo();
		model.provideCards();
	}
	
	// ----------------------------------------------------
	// Add relevant API methods here
	// ----------------------------------------------------
	@GET
	@Path("/getRoundCards")
	public String getRoundCards() throws IOException, ClassNotFoundException, SQLException {
		ArrayList<Card> cards = model.getRoundPile();
		ArrayList<String[]> roundCards = new ArrayList<String[]>();
		for(Card c : cards){
			roundCards.add(c.cardToString());
		}
		String json = oWriter.writeValueAsString(roundCards);
		return json;
	}
	@GET
	@Path("/getRoundNumbers")
	public String getRoundNumbers() throws IOException, ClassNotFoundException, SQLException {
		String json = oWriter.writeValueAsString(model.getRoundNumbers());
		return json;
	}
	@GET
	@Path("/getStats")
	public String getStats() throws IOException, ClassNotFoundException, SQLException {
		String stats = JDBC.queryHistoryStatistics().toString();
		String[] statsList = stats.split("\\r\\n");
		String json = oWriter.writeValueAsString(statsList);
		return json;
	}
	@GET
	@Path("/selectCharacter")
	public String selectCharacter(@QueryParam("Character") int character) throws IOException {
		model.chooseCharacter(character);
		return null;
	}
	@GET
	@Path("/showResult")
	public String showResult() throws IOException, ClassNotFoundException, SQLException {
		return model.showResult();
	}
	@GET
	@Path("/showWinner")
	public String showWinner() throws IOException, ClassNotFoundException, SQLException {
		ArrayList<String> s = model.showWinner();
		String json = oWriter.writeValueAsString(s);
		return json;
	}
//	@GET
//	@Path("/helloJSONList")
//	/**
//	 * Here is an example of a simple REST get request that returns a String.
//	 * We also illustrate here how we can convert Java objects to JSON strings.
//	 * @return - List of words as JSON
//	 * @throws IOException
//	 */
//	public String helloJSONList() throws IOException {
//
//		List<String> listOfWords = new ArrayList<String>();
//		listOfWords.add("Hello");
//		listOfWords.add("World!");
//
//		// We can turn arbatory Java objects directly into JSON strings using
//		// Jackson seralization, assuming that the Java objects are not too complex.
//		String listAsJSONString = oWriter.writeValueAsString(listOfWords);
//
//		return listAsJSONString;
//	}
//
//	@GET
//	@Path("/helloWord")
//	/**
//	 * Here is an example of how to read parameters provided in an HTML Get request.
//	 * @param Word - A word
//	 * @return - A String
//	 * @throws IOException
//	 */
//	public String helloWord(@QueryParam("Word") String Word) throws IOException {
//		return "Hello "+Word;
//	}
//
}
