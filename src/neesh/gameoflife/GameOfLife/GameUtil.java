package neesh.gameoflife.GameOfLife;

import java.awt.Color;
import java.util.HashMap;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class GameUtil {
	public static HashMap<User, Game> games = new HashMap<>();
	public static boolean hasGame(User author) {
		return games.containsKey(author);
	}
	public static void createGame(User author) {
		Game game = new Game();
		games.put(author, game);
	}
	public static Game getGame(User author) {
		return games.get(author);
	}
	public static void editLast(User author) {
		EmbedBuilder display = new EmbedBuilder();
		display.setColor(Color.orange);
		display.setTitle("Game Of Life");
		getGame(author);
		display.setDescription(Game.getGridString());
		display.addField("Requested by ", author.getAsMention(), true);
		games.get(author);
		Game.getLast().editMessage(display.build()).queue();
	}
	public static void display(MessageChannel channel, User author) {
		EmbedBuilder display = new EmbedBuilder();
		display.setColor(Color.orange);
		display.setTitle("Game Of Life");
		getGame(author);
		display.setDescription(Game.getGridString());
		display.addField("Requested by ", author.getAsMention(), true);
		channel.sendMessage(display.build()).queue(message -> {
			getGame(author);
			Game.setLast(message);
		});
	}
}
