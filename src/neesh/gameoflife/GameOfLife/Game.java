package neesh.gameoflife.GameOfLife;

import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Game {
	private static int[][] grid;
	private static int generation;
	private static Message lastMessage;
	private static Timer timer;
	private static Timer playTimer;
	private static User player;
	private static boolean isPlaying;
	public Game(User nplayer) {  //Constructor
		grid = new int[GameOfLife.size][GameOfLife.size];
		generation = 0;
		player = nplayer;
		timer = new Timer();
		playTimer = new Timer();
	}
	public static void setTimer() {
		TimerTask timeout = new TimeOut(lastMessage.getChannel(), player);
		timer.schedule(timeout, 1000 * 60 * GameOfLife.timeOutTimeMinutes);
	}
	public static int[][] getGrid() {  //Method that will return the grid for a certain Game
		return grid;
	}
	public static void setGrid(int[][] preset){
		grid = preset;
	}
	public static int getGen() {
		return generation;
	}
	public static void togglePlay() {  //runs the play timer
		isPlaying = !isPlaying;
		if(!isPlaying) {
			playTimer.cancel();
			playTimer = new Timer();
		}
		else {
			play();
		}
	}
	public static Message getLast() {
		return lastMessage;
	}
	public static void purgeTimer() {  //cancels the time out timer
		timer.cancel();
		timer = new Timer();
	}
	public static void purgePlayTimer() {  //cancels the play timer
		playTimer.cancel();
		playTimer = new Timer();
	}
	public static boolean getPlaying() {
		return isPlaying;
	}
	public static void setLast(Message message) {
		lastMessage = message;
	}
	public static void play() {  //runs the play timer task every 1 second.
		TimerTask play = new PlayTimerTask(lastMessage.getChannel(), player);
		playTimer.schedule(play, 500, 1000);
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
        for (int l = 0; l < size; l++)
        {
            for (int m = 0; m < size; m++)
            {
                // finding number of surrounding living cells
                int aliveNeighbours = getSurrounding(l, m);
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
	public static int getSurrounding(int x, int y) {  //gets the surrounding cells
		int result = 0;
		for(int i = -1;i < 2; i ++) {
			for(int g = -1; g< 2; g++) {
				try {
					result += grid[x+i][y+g];
				}
				catch(IndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		return result-1;
	}
	public static void randomizeGrid() {  //Randomizes the grid, setting each cell to either 1 or 0, alive or dead
		for(int i = 0;i < grid.length;i ++) {
			for(int g = 0; g< grid[i].length;g++) {
				int ran = (int) (Math.random() * 2);
				grid[i][g] = ran;
			}
		}
		generation = 0;
	}
}
