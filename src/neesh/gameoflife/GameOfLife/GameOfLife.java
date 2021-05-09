package neesh.gameoflife.GameOfLife;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameOfLife {
	public static int size = 10;
	public static String deadCell = "⬜";
	public static String liveCell = "⬛";
	public static boolean isActive = true;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Game current = new Game();
		Game.randomizeGrid();
		Scanner in = new Scanner(System.in);
		System.out.println(Game.getGridString());
		System.out.println("Please enter 'n' to go to the next generation, 'r' to re-randomize your grid, or 's' to end your game.");
		while(isActive) {
			String userInput = in.nextLine();
			String[] messages = userInput.strip().split("\\s+");
			String command = messages[0];
			if(command.equalsIgnoreCase("stop") || command.equalsIgnoreCase("s")) {
				System.out.println("Ended game.");
				isActive = false;
			}
			else if(command.equalsIgnoreCase("refresh") || command.equalsIgnoreCase("r") || command.equalsIgnoreCase("restart")) {
				Game.randomizeGrid();
				System.out.println(Game.getGridString());
				System.out.println("Please enter 'n' to go to the next generation, 'r' to re-randomize your grid, or 's' to end your game.");
			}
			else if(command.equalsIgnoreCase("next") || command.equalsIgnoreCase("n")) {
				Game.nextGen();
				if(Game.isDead()) {
					System.out.println("Your game died :( Ended it for you.");
					isActive = false;
					return;
				}
				System.out.println(Game.getGridString());
				System.out.println("Please enter 'n' to go to the next generation, 'r' to re-randomize your grid, or 's' to end your game.");
			}
			else if(command.equalsIgnoreCase("setdc")) {
				try {
					if(messages[1].length() > 1) {
						System.out.println("Please only enter 1 character.");
						continue;
					}
					deadCell = messages[1];
					System.out.println("Set the new dead cell to " + messages[1]);
				}
				catch(Exception e) {
					System.out.println("Please enter a new dead cell symbol.");
				}
			}
			else if(command.equalsIgnoreCase("setlc")) {
				try {
					if(messages[1].length() > 1) {
						System.out.println("Please only enter 1 character.");
						continue;
					}
					liveCell = messages[1];
					System.out.println("Set the new live cell to " + messages[1]);
				}
				catch(Exception e) {
					System.out.println("Please enter a new live cell symbol.");
				}
			}
			else if(command.equalsIgnoreCase("size")) {
				try {
					int newsize = Integer.parseInt(messages[1]);
					if(newsize > 35 || newsize < 3) {
						System.out.println("Please enter a number between 3 and 35.");
						continue;
					}
					size = newsize;
					Game newgrid = new Game();
					current = newgrid;
					Game.randomizeGrid();
					System.out.println("Set the new size to " + newsize + " and refreshed your grid.");
				}
				catch(Exception e) {
					System.out.println("Please enter a new size.");
				}
			}
			else if(command.equalsIgnoreCase("preset")) {
				try {
					String preset = messages[1];
					if(Presets.isPreset(preset)) {
						Game.setGrid(Presets.getPreset(preset));
						System.out.println("Set your grid to preset " + preset);
						System.out.println(Game.getGridString());
						System.out.println("Please enter 'n' to go to the next generation, 'r' to re-randomize your grid, or 's' to end your game.");
					}
					else {
						System.out.println("Please enter a valid preset ID.");
						continue;
					}
				}
				catch(Exception e) {
					System.out.println("Please enter a valid preset ID.");
				}
			}
		}
	}

}
