package neesh.gameoflife.GameOfLife;

import java.util.Scanner;

public class GameOfLife {
	public static int size = 10;
	public static String deadCell = "⬜";
	public static String liveCell = "⬛";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game current = new Game();
		Game.randomizeGrid();
		Scanner in = new Scanner(System.in);
		boolean isActive = true;
		System.out.println(Game.getGridString());
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
			}
			else if(command.equalsIgnoreCase("next") || command.equalsIgnoreCase("n")) {
				Game.nextGen();
				if(Game.isDead()) {
					System.out.println("Your game died :( Ended it for you.");
					isActive = false;
					return;
				}
				System.out.println(Game.getGridString());
			}
			else if(command.equalsIgnoreCase("setdc")) {
				try {
					if(messages[1].length() > 1) {
						System.out.println("Please only enter 1 character.");
						return;
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
						return;
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
					if(newsize > 30 || newsize < 3) {
						System.out.println("Please enter a number between 3 and 30.");
						return;
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
		}
	}

}
