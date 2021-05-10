package neesh.gameoflife.GameOfLife;

import java.util.TimerTask;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class PlayTimerTask extends TimerTask{
	private User player;
	private MessageChannel channel;
	public PlayTimerTask(MessageChannel nchannel, User nplayer) {
		player = nplayer;
		channel = nchannel;
	}
	public void run() {
		GameUtil.getGame(player);
		Game.nextGen();
		if(Game.isDead()) {
			GameUtil.endGame(player);
			Embeds.sendDeadGameEmbed(channel, player);
			return;
		}
		GameUtil.editLast(player);
	}
}
