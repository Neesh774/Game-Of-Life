import java.util.TimerTask;

public class PlayTimerTask extends TimerTask{

	@Override
	public void run() {
		Game.nextGen();
		GameOfLife.updateGrid();
	}

}
