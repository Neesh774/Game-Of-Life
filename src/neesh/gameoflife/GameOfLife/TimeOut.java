package neesh.gameoflife.GameOfLife;

import java.util.TimerTask;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class TimeOut extends TimerTask{
	private static MessageChannel channel;
	private static User author;
	public TimeOut(MessageChannel nchannel, User nauthor) {
		channel = nchannel;
		author = nauthor;
	}
	public void run() {
		if(GameUtil.hasGame(author)) {
			Embeds.sendTimeOutEmbed(channel, author);
			GameUtil.endGame(author);
		}
	}
}
