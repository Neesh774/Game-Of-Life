package neesh.gameoflife.GameOfLife;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Embeds {
    public static void sendGameEmbed(MessageChannel channel, Game game, User user) {
    	String grid = Game.getGridString();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Game of Life");	
        embed.setDescription(grid);
        embed.addField("Type r to refresh the grid, or n to go to the next generation.	", "", false);
        embed.addField("Player", user.getAsMention(), false);
        embed.setColor(Color.magenta);
        channel.sendMessage(embed.build()).queue(message ->{
        	message.addReaction("U+25B6").queue();
        	message.addReaction("U+23F9").queue();
        	message.addReaction("U+1F501").queue();
        	Game.setLast(message);
        });
    }
    public static void sendInfoEmbed(MessageChannel channel, User user) {
    	EmbedBuilder embed = new EmbedBuilder();
    	embed.setTitle("Game of Life");
    	embed.setDescription("Start a new Game of Life with `" + GameOfLife.prefix + "start`. You can go to the next generation with `n`, refresh the map with `r`, and end the game with `s`.");
    	embed.addField("My prefix: ", GameOfLife.prefix, false);
    	embed.addField("Player", user.getAsMention(), false);
    	embed.setColor(Color.magenta);
    	channel.sendMessage(embed.build()).queue();
    }
    public static void sendEndEmbed(MessageChannel channel, User user) {
		EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Game of Life");
        embed.setDescription("The game has ended.");
        embed.addField("Player", user.getAsMention(), false);
        embed.setColor(Color.red);
        channel.sendMessage(embed.build()).queue();
    }
    public static void sendGameRepeatEmbed(MessageChannel channel, User user) {
		EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Game of Life");
        embed.setDescription("You already have a game!");
        embed.addField("Player", user.getAsMention(), false);
        embed.setColor(Color.magenta);
        channel.sendMessage(embed.build()).queue();
    }
    public static void sendNoGameEmbed(MessageChannel channel, User user) {
		EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Game of Life");
        embed.setDescription("You don't have a game!");
        embed.addField("Player", user.getAsMention(), false);
        embed.setColor(Color.magenta);
        channel.sendMessage(embed.build()).queue();
    }
    public static void sendDeadGameEmbed(MessageChannel channel, User user) {
    	EmbedBuilder embed = new EmbedBuilder();
    	embed.setTitle("Your game died!");
    	embed.setDescription("All of the cells in your grid died :(");
    	embed.addField("Player", user.getAsMention(), false);
    	embed.setColor(Color.magenta);
    	channel.sendMessage(embed.build()).queue();
    }
    public static void sendErrorEmbed(MessageChannel channel, User user) {
    	EmbedBuilder embed = new EmbedBuilder();
    	embed.setTitle("There was an error");
    	embed.setDescription("Please make sure you're using the right arguments");
    	embed.addField("Player", user.getAsMention(), false);
    	embed.setColor(Color.magenta);
    	channel.sendMessage(embed.build()).queue();
    }
    public static void sendNoPermissionsEmbed(MessageChannel channel, User user) {
    	EmbedBuilder embed = new EmbedBuilder();
    	embed.setTitle("There was an error");
    	embed.setDescription("You don't have the permissions for that.");
    	embed.addField("Player", user.getAsMention(), false);
    	embed.setColor(Color.magenta);
    	channel.sendMessage(embed.build()).queue();
    }
}
