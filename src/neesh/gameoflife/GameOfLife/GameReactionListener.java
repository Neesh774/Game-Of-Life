package neesh.gameoflife.GameOfLife;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameReactionListener extends ListenerAdapter{
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
		User user = event.getUser();
		if(user.isBot() || !GameUtil.hasGame(user)) {
			return;
		}
//		System.out.println("Detected Reaction of " + event.getReactionEmote().toString());
		Guild guild = event.getGuild();
		MessageReaction reaction = event.getReaction();
		TextChannel channel = event.getChannel();
		
		String emote = event.getReactionEmote().toString();
		if(emote.equals("RE:U+25b6")) {
			GameUtil.getGame(user);
			Game.nextGen();
			if(Game.isDead()) {
				Embeds.sendDeadGameEmbed(channel, user);
				Embeds.sendEndEmbed(channel, user);
				GameUtil.endGame(user);
			}
			else {
				GameUtil.editLast(user);
			}
//			System.out.println("Detected Next gen");
		}
		else if(emote.equals("RE:U+23f9")) {
			GameUtil.endGame(user);
			Embeds.sendEndEmbed(channel, user);
//			System.out.println("Detected refresh grid");
		}
		else if(emote.equals("RE:U+1f501")) {
			GameUtil.getGame(user);
			Game.randomizeGrid();
			GameUtil.editLast(user);
		}
	}
}
