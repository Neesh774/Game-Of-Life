package neesh.gameoflife.GameOfLife;

import java.util.TimerTask;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
//This class allows me to create a time out for games that aren't updated for a certain amount of time, and the actual timer will call the run function which ends the game.
public class TimeOut extends TimerTask{
	private static MessageChannel channel;
	private static User author;
	public TimeOut(MessageChannel nchannel, User nauthor) {  //constructor
		channel = nchannel;
		author = nauthor;
	}
	public void run() {  //The thing that will run when the timer completes, which is ending the game if the user even still has the game and hasn't stopped it.
		if(GameUtil.hasGame(author)) {
			Embeds.sendTimeOutEmbed(channel, author);
			GameUtil.endGame(author);
		}
	}
}
