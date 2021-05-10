package neesh.gameoflife.GameOfLife;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

//This class is a way for me to use reactions to change a game
public class GameReactionListener extends ListenerAdapter{
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {  //JDA throws this event when somebody adds a reaction to any message
		User user = event.getUser();
		if(user.isBot() || !GameUtil.hasGame(user)) {  //Makes sure the user isn't the bot and the person reacting actually has a game
			return;
		}
//		System.out.println("Detected Reaction of " + event.getReactionEmote().toString());
		Guild guild = event.getGuild();
		MessageReaction reaction = event.getReaction();
		TextChannel channel = event.getChannel();   //making my life easier
		
		String emote = event.getReactionEmote().toString();
		if(emote.equals("RE:U+25b6")) {  //This is the play button emote, which the user will use if they want to go forward a generation
			GameUtil.getGame(user);
			Game.nextGen();
			if(Game.isDead()) {  //Checks if the game is dead before going to the next generation
				Embeds.sendDeadGameEmbed(channel, user);
				Embeds.sendEndEmbed(channel, user);
				GameUtil.endGame(user);
			}
			else {
				GameUtil.editLast(user);
			}
//			System.out.println("Detected Next gen");
		}
		else if(emote.equals("RE:U+23f9")) {  //This is the refresh emote, which the player will use if they want to re-randomize their grid
			GameUtil.endGame(user);
			Embeds.sendEndEmbed(channel, user);
//			System.out.println("Detected refresh grid");
		}
		else if(emote.equals("RE:U+1f501")) {  //This is the stop button emote, which the player will use if they want to stop their game
			GameUtil.getGame(user);
			Game.randomizeGrid();
			GameUtil.editLast(user);
		}
		else if(emote.equals("RE:U+23ef")) {
			if(Game.getPlaying()) {
				GameUtil.getGame(user);
				channel.sendMessage("Stopped playing.").queue();
				Game.togglePlay();
			}
			else {
				GameUtil.getGame(user);
				channel.sendMessage("Started playing.").queue();
				Game.togglePlay();
			}
		}
	}
	@Override
	public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
		User user = event.getUser();
		if(user.isBot() || !GameUtil.hasGame(user)) {  //Makes sure the user isn't the bot and the person reacting actually has a game
			return;
		}
//		System.out.println("Detected Reaction of " + event.getReactionEmote().toString());
		Guild guild = event.getGuild();
		MessageReaction reaction = event.getReaction();
		TextChannel channel = event.getChannel();   //making my life easier
		
		String emote = event.getReactionEmote().toString();
		if(emote.equals("RE:U+25b6")) {  //This is the play button emote, which the user will use if they want to go forward a generation
			GameUtil.getGame(user);
			Game.nextGen();
			if(Game.isDead()) {  //Checks if the game is dead before going to the next generation
				Embeds.sendDeadGameEmbed(channel, user);
				Embeds.sendEndEmbed(channel, user);
				GameUtil.endGame(user);
			}
			else {
				GameUtil.editLast(user);
			}
//			System.out.println("Detected Next gen");
		}
		else if(emote.equals("RE:U+23f9")) {  //This is the refresh emote, which the player will use if they want to re-randomize their grid
			GameUtil.endGame(user);
			Embeds.sendEndEmbed(channel, user);
//			System.out.println("Detected refresh grid");
		}
		else if(emote.equals("RE:U+1f501")) {  //This is the stop button emote, which the player will use if they want to stop their game
			GameUtil.getGame(user);
			Game.randomizeGrid();
			GameUtil.editLast(user);
		}
		else if(emote.equals("RE:U+23ef")) {
			GameUtil.getGame(user);
			if(Game.getPlaying()) {
				GameUtil.getGame(user);
				channel.sendMessage("Stopped playing.").queue();
				Game.togglePlay();
			}
			else {
				GameUtil.getGame(user);
				channel.sendMessage("Started playing.").queue();
				Game.togglePlay();
			}
		}
	}
}
