package neesh.gameoflife.GameOfLife;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class GameOfLife {
	public static JDA jda;
	public static String prefix = "gol";  //default prefix for the bot
	public static int size = 14;  //default size for the grid(14x14)
	public static String deadCell = "⬛";  //default emote to use for dead cells
	public static String liveCell = "⬜";  //default emote to use for living cells
	public static void main(String[] args) throws FileNotFoundException, LoginException {
		Scanner tk = new Scanner(new File("token.txt"));
		String token = tk.nextLine();    //Tells the program to get the token from the token.txt file, because it's supposed to be secret.
		jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).build();  //Simply saying start the bot using this token
		jda.getPresence().setStatus(OnlineStatus.IDLE);   //Unimportant
		jda.getPresence().setActivity(Activity.watching("golhelp"));   //Unimportant
		jda.addEventListener(new UtilCommands());  //Tells the bot to tell UtilCommands.java when a message is sent
		jda.addEventListener(new GameCommands());  //Tells the bot to tell GameCommands.java when a message is sent
		jda.addEventListener(new GameReactionListener());  //Tells the bot to tell GameReactionListener.java when a message is sent
	}

}
