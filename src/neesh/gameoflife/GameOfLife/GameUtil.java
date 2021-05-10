package neesh.gameoflife.GameOfLife;

import java.awt.Color;
import java.util.HashMap;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class GameUtil {
	public static HashMap<User, Game> games = new HashMap<>();  //A HashMap that links games to users.
	public static boolean hasGame(User author) {
		return games.containsKey(author);
	}
	public static void createGame(User author) {  //A method used to create a Game object, randomize it, and add it to the global games HashMap
		Game game = new Game(author);
		Game.randomizeGrid();
		games.put(author, game);
	}
	public static Game getGame(User author) {  //A method used to get a game from the global games HashMap
		return games.get(author);
	}
	public static void endGame(User author) {  //A method used to end a game and remove it from the HashMap
		getGame(author).getLast().delete().queue();  //Deletes the message of the game
		
		games.remove(author);
	}
	public static void editLast(User author) {  //A method that will edit the last message of a game instead of sending a new message
		EmbedBuilder display = new EmbedBuilder();  //See Embeds.java for an explanation of this code.
		display.setColor(Color.orange);
		display.setTitle("Game Of Life");
		getGame(author);
		display.setDescription(Game.getGridString());
		display.addField("Type r to refresh the grid, or n to go to the next generation.", "", false);
        display.setColor(Color.magenta);
        getGame(author);
		display.addField("Generation", String.valueOf(Game.getGen()), false);
		display.addField("Requested by ", author.getAsMention(), true);
		games.get(author);
		Game.getLast().editMessage(display.build()).queue();
		Game.purgeTimer();
		Game.setTimer();
	}
}
