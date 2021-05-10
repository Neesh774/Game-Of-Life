package neesh.gameoflife.GameOfLife;

import java.util.ArrayList;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

//This class is a collection of all the important commands that are relevant to the game
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
		if(message.length() > prefix.length()) {  //Makes sure that the message length is longer than the prefix length before parsing it into an array
			args = message.strip().substring(prefix.length()).split("\\s+");
			command = args[0];
		}
		//======================================Creating Game================================
		if(command.equalsIgnoreCase("start")) {
			if(GameUtil.hasGame(author)) {  //Makes sure the player doesn't already have a game
				Embeds.sendGameRepeatEmbed(channel, author);
			}
			else {
				GameUtil.createGame(author);  //Creates a game
				Embeds.sendGameEmbed(channel, GameUtil.getGame(author), author);
			}
		}
		//======================================Ending game================================
		else if(command.equalsIgnoreCase("stop") || message.equalsIgnoreCase("s")) {
			if(GameUtil.hasGame(author)) {  //Making sure that the player actually has a game to end
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
			if(Game.isDead()) {  //Checks if the game is already dead before editing it, or else it'll just show a blank grid
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
			Game.randomizeGrid();  //Re-randomizes the grid
			Embeds.sendGameEmbed(channel, GameUtil.getGame(author), author);
		}
		//=====================================Changing Grid Size============================
		else if(command.equalsIgnoreCase("size")) {
			if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {  //Makes sure that the player actually has an administrator permission in the server they're in
				Embeds.sendNoPermissionsEmbed(channel, author);
				return;
			}
			try {
				int newsize = Integer.parseInt(args[1]);
				if(newsize > 14 || newsize < 3) {  //The embed won't properly send if it isn't within these limits.
					Embeds.sendErrorEmbed(channel, author);
				}
				else {
					GameOfLife.size = newsize;
					Embeds.sendSuccessSizeEmbed(channel, author, newsize);
				}
			}
			catch(Error e) {  //Thrown if the argument either isn't there or isn't an integer
				e.printStackTrace();
				Embeds.sendErrorEmbed(channel, author);
			}
		}
		//=====================================Presets============================
		else if(command.equalsIgnoreCase("preset")){
			if(!Presets.isPreset(args[1])) {
				Embeds.sendErrorEmbed(channel, author);
				return;
			}
			if(GameUtil.hasGame(author)) {
				GameUtil.getGame(author);
				Game.setGrid(Presets.getPreset(args[1]));
				GameUtil.editLast(author);
			}
			else {
				GameUtil.createGame(author);
				Game.setGrid(Presets.getPreset(args[1]));
				Embeds.sendGameEmbed(channel, GameUtil.getGame(author), author);
			}
		}
		else if(command.equalsIgnoreCase("presets")) {
			Embeds.sendPresetsEmbed(channel, author);
		}
		//=====================================TimeOut============================
		else if(command.equalsIgnoreCase("timeout")) {
			if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {  //Makes sure that the player actually has an administrator permission in the server they're in
				Embeds.sendNoPermissionsEmbed(channel, author);
				return;
			}
			try {
				int newtime = Integer.parseInt(args[1]);
				if(newtime > 15 || newtime < 1) {  //The embed won't properly send if it isn't within these limits.
					Embeds.sendErrorEmbed(channel, author);
				}
				else {
					GameOfLife.timeOutTimeMinutes = newtime;
					Embeds.sendSuccessTimeOutEmbed(channel, author, newtime);
				}
			}
			catch(Error e) {  //Thrown if the argument either isn't there or isn't an integer
				e.printStackTrace();
				Embeds.sendErrorEmbed(channel, author);
			}
		}
		//=====================================Playing============================
		else if(command.equalsIgnoreCase("play") || message.equalsIgnoreCase("p")) {
			if(!GameUtil.hasGame(author)) {
				Embeds.sendNoGameEmbed(channel, author);
				return;
			}
			if(Game.getPlaying()) {
				GameUtil.getGame(author);
				channel.sendMessage("Stopped playing.").queue();
				Game.togglePlay();
			}
			else {
				GameUtil.getGame(author);
				channel.sendMessage("Started playing.").queue();
				Game.togglePlay();
			}
		}
	}
}
