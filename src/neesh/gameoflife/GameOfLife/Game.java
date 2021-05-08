package neesh.gameoflife.GameOfLife;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Game {
	private static int[][] grid;
	private static int generation;
	private static Message lastMessage;
	public Game() {  //Constructor
		grid = new int[GameOfLife.size][GameOfLife.size];
		generation = 0;
	}
	public static int[][] getGrid() {  //Method that will return the grid for a certain Game
		return grid;
	}
	public static int getGen() {
		return generation;
	}
	public static Message getLast() {
		return lastMessage;
	}
	public static void setLast(Message message) {
		lastMessage = message;
	}
	public static boolean isDead() {  //Checks if a grid is dead with no living cells on it.
		for(int i = 0;i < grid.length;i ++) {
			for(int g = 0;g < grid[i].length; g ++) {
				if(grid[i][g] == 1) {
					return false;
				}
			}
		}
		return true;
	}
	public static String getGridString() {  //Creates a String that represents the grid, using the specified emotes in GameOfLife.java
		String str = "";
		for(int i = 0;i < GameOfLife.size; i ++) {
			for(int g= 0;g < GameOfLife.size;g++) {
				if(grid[i][g] == 0) {
					str += GameOfLife.deadCell;
				}
				else {
					str += GameOfLife.liveCell;
				}
			}
			str += "\n";
		}
		return str;
	}
	public static void nextGen() {   //A method that will edit the grid to set it to the next generation
		int size = GameOfLife.size;
		int[][] future = new int[size][size];
		  
        // Loop through every cell
        for (int l = 1; l < size-1; l++)
        {
            for (int m = 1; m < size-1; m++)
            {
                // finding number of surrounding living cells
                int aliveNeighbours = 0;
                for (int i = -1; i <= 1; i++)
                    for (int j = -1; j <= 1; j++)
                    	aliveNeighbours += grid[l + i][m + j];
  
                // The cell needs to be subtracted from
                // its neighbours as it was counted before
                aliveNeighbours -= grid[l][m];
  
                // Implementing the Rules of Life
  
                // Cell is lonely and dies
                if ((grid[l][m] == 1) && (aliveNeighbours < 2))
                    future[l][m] = 0;
  
                // Cell dies due to over population
                else if ((grid[l][m] == 1) && (aliveNeighbours > 3))
                    future[l][m] = 0;
  
                // A new cell is born
                else if ((grid[l][m] == 0) && (aliveNeighbours == 3))
                    future[l][m] = 1;
  
                // Remains the same
                else
                    future[l][m] = grid[l][m];
            }
        }
        generation++;
        grid = future;	
	}
	public static void randomizeGrid() {  //Randomizes the grid, setting each cell to either 1 or 0, alive or dead
		for(int i = 0;i < grid.length;i ++) {
			for(int g = 0; g< grid[i].length;g++) {
				int ran = (int) (Math.random() * 2);
				grid[i][g] = ran;
			}
		}
	}
}
