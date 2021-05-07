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
	public static String prefix = "gol";
	public static String deadCell = ":white_large_square:";
	public static String liveCell = ":black_large_square:";
	public static void main(String[] args) throws FileNotFoundException, LoginException {
		// TODO Auto-generated method stub
		Scanner tk = new Scanner(new File("token.txt"));
		String token = tk.nextLine();
		jda = JDABuilder.createDefault(token).enableIntents(GatewayIntent.GUILD_MEMBERS).build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		jda.getPresence().setActivity(Activity.watching("golhelp"));
		jda.addEventListener(new UtilCommands());
	}

}
