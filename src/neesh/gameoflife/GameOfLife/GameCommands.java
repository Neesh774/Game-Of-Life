package neesh.gameoflife.GameOfLife;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameCommands extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();   //Gets the actual message in words
		User author = event.getAuthor();  //Gets the author of the message
		MessageChannel channel = event.getChannel();  //Gets the channel the message was sent in
		String prefix = GameOfLife.prefix;
		if(author.isBot()) {
			return;  //Returns if the message doesn't start with the prefix or the bot sent the message
		}
		String[] args = new String[5];
		String command = "";
		if(message.length() > prefix.length()) {
			args = message.strip().substring(prefix.length()).split("\\s+");
			command = args[0];
		}
		//======================================Creating Game================================
		if(command.equalsIgnoreCase("start")) {
			if(GameUtil.hasGame(author)) {
				Embeds.sendGameRepeatEmbed(channel, author);
			}
			else {
				GameUtil.createGame(author);
				Embeds.sendGameEmbed(channel, GameUtil.getGame(author), author);
			}
		}
		//======================================Ending game================================
		else if(command.equalsIgnoreCase("stop") || message.equalsIgnoreCase("s")) {
			if(GameUtil.hasGame(author)) {
				GameUtil.endGame(author);
				Embeds.sendEndEmbed(channel, author);
			}
			else {
				Embeds.sendNoGameEmbed(channel, author);
			}
		}
		//======================================Next Generation================================
		else if(message.equalsIgnoreCase("n") && GameUtil.hasGame(author)) {
			GameUtil.getGame(author);
			Game.nextGen();
			if(Game.isDead()) {
				Embeds.sendDeadGameEmbed(channel, author);
				Embeds.sendEndEmbed(channel, author);
				GameUtil.endGame(author);
			}
			else {
				GameUtil.editLast(author);
			}
		}
		//======================================Refresh Grid================================
		else if(message.equalsIgnoreCase("r") && GameUtil.hasGame(author)) {
			GameUtil.getGame(author);
			Game.randomizeGrid();
			Embeds.sendGameEmbed(channel, GameUtil.getGame(author), author);
		}
		//=====================================Changing Grid Size============================
		else if(command.equalsIgnoreCase("size")) {
			if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				Embeds.sendNoPermissionsEmbed(channel, author);
				return;
			}
			try {
				GameOfLife.size = Integer.parseInt(args[0]);
			}
			catch(Error e) {
				e.printStackTrace();
				Embeds.sendErrorEmbed(channel, author);
			}
		}
	}
}
