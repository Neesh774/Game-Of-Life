package neesh.gameoflife.GameOfLife;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
/*
This class is a collection of utility commands such as getting the prefix for the commands, setting a new prefix for the bot, or getting the ping of the bot.
 */
public class UtilCommands extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {   //JDA will throw this event when a message is sent in any channel
		/*
		 * These declarations are simply making my life easier and the code easier to read
		 */
		String message = event.getMessage().getContentRaw();   //Gets the actual message in words
		User author = event.getAuthor();  //Gets the author of the message
		MessageChannel channel = event.getChannel();  //Gets the channel the message was sent in
		String prefix = GameOfLife.prefix;
		if(author.isBot()) {
			return;  //Returns if the message doesn't start with the prefix or the bot sent the message
		}
//		System.out.println("Message: " + message);
		String[] args = message.strip().substring(prefix.length()).split("\\s+");
//		for(String arg : args) {
//			System.out.println(arg);
//		}
		String command = args[0];
//		System.out.println("Command: |" + command + "|");
		if(event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser())) {
			channel.sendMessage("Hello! My prefix is `" + prefix + "`.").queue();
		}
		if(command.equalsIgnoreCase("help")) {
			channel.sendMessage("Hello! My prefix is `" + prefix + "`.").queue();
		}
		else if(command.equalsIgnoreCase("setprefix")) {
			
			try { //making sure that the command actually has a prefix to be set
				GameOfLife.prefix = args[1];
				prefix = args[1];
				channel.sendMessage("Successfully set the new prefix!").queue();
			}
			catch(ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				channel.sendMessage("Please specify the new prefix.").queue();
			}	
		}
		else if(command.equalsIgnoreCase("ping")) {
	        long time = System.currentTimeMillis();   //Gets the current time
	        Message msg = event.getChannel().sendMessage(":signal_strength: Ping").complete(); //Sends a message to see how long it takes
	        long latency = System.currentTimeMillis() - time;  //Gets the time after sending a message
	        channel.sendMessage("Pong! Your latency is " + latency).queue();  //Sends the message
		}
		else if(command.equalsIgnoreCase("setdeadcell")) {
			try {
				if(args[1].startsWith(":") && args[1].endsWith(":")) {
					GameOfLife.deadCell = args[1];
				}
				else {
					channel.sendMessage("Please send an emote.").queue();
				}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				channel.sendMessage("Please specify the new emote.").queue();
			}
		}
	}
}

