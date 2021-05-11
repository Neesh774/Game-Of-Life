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
	public void run() {  //Makes sure the player has a game, and then does the protocol for the next generation, which is making sure it isn't dead and then editing the message.
		if(GameUtil.hasGame(player)) {
			GameUtil.getGame(player);
			Game.nextGen();
			if(Game.isDead()) {
				GameUtil.endGame(player);
				Embeds.sendDeadGameEmbed(channel, player);
				Game.purgePlayTimer();
				return;
			}
			GameUtil.editLast(player);
		}
		else {
			Game.purgePlayTimer();	
		}
	}
}
