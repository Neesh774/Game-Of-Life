import java.util.TimerTask;
//This class allows me to create a time out for games that aren't updated for a certain amount of time, and the actual timer will call the run function which ends the game.
public class TimeOut extends TimerTask{
	public void run() {  //The thing that will run when the timer completes, which is ending the game if the user even still has the game and hasn't stopped it.
		System.out.println("Game timed out.");
	}
}