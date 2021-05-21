import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameOfLife {
	public static int SIZE = 30;
	public static int TIMEOUT_TIME_MINUTES = 5;
	public static String DEADCELL = "assets/whiteCell.png";
	public static String LIVECELL = "assets/blackCell.png";
	
	public static Game curGame = new Game();
	public static JFrame f = new JFrame("Nimbus Look and Feel");
	static JPanel board = new JPanel(new GridLayout(SIZE, SIZE));
	static JSpinner size = new JSpinner(new SpinnerNumberModel(30, 5, 40, 1));
	
	static String[] colors = {"Black", "White", "Blue", "Green","Orange", "Pink", "Purple", "Red", "Yellow"};
	static JComboBox<String> liveColors = new JComboBox<>(colors);
	static JComboBox<String> deadColors = new JComboBox<>(colors);
	static boolean isPlaying = false;
	static Timer playingTimer = new Timer();
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

		f.setBackground(new Color(255, 244, 171));
		f.setTitle("The Game of Life");
		ImageIcon logo = new ImageIcon("golLogo.png");
		f.setIconImage(logo.getImage());
		JButton refresh = new JButton("🔁");
		JButton next = new JButton("▶");
		JButton rules = new JButton("📜");
		JButton settings = new JButton("⚙");
		JButton play = new JButton("⏯");
		JButton clear = new JButton("⭕");

		refresh.setBounds(20, 560, 80, 80);
		refresh.addActionListener(new RefreshGrid());
		refresh.setToolTipText("Refresh your grid with a completely random one.");
		next.setBounds(20, 660, 80, 80);
		next.addActionListener(new NextGen());
		next.setToolTipText("Go to the next generation of your grid.");
		rules.setBounds(20, 20, 40, 40);
		rules.addActionListener(new showRules());
		rules.setToolTipText("View the rules for Conway's Game of Life");
		settings.setBounds(20, 60, 40, 40);
		settings.addActionListener(new showSettings());
		settings.setToolTipText("Change the settings for your game.");
		play.setBounds(20, 360, 80, 80);
		play.addActionListener(new playGame());
		clear.setBounds(20, 460, 80, 80);
		clear.addActionListener(new clearGrid());
		clear.setToolTipText("Clear your grid and everything on it.");
		liveColors.addActionListener(new liveCellChanged());
		deadColors.addActionListener(new deadCellChanged());

		f.add(refresh);
		f.add(next);
		f.add(rules);
		f.add(settings);
		f.add(clear);
		f.add(play);
		initGrid();
		f.setSize(800, 800);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static void initGrid() {
		board.setBounds(130, 30, 600, 600);
		board.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Generation: " + Game.getGen()));
		board.setVisible(true);
		int[][] grid = Game.getGrid();
		int x = 130;
		int y = 30;
		for(int i = 0;i < grid.length;i ++) {
			for(int g = 0;g < grid[i].length; g ++) {
				if(grid[i][g] == 1) {
					JButton live = new JButton(new ImageIcon(new ImageIcon(LIVECELL).getImage().getScaledInstance(600/SIZE, 600/SIZE, 1)));
					live.addActionListener(new liveCellClicked(i, g));
					live.setBounds(x, y, 600/SIZE, 600/SIZE);
					board.add(live);
				}
				else {
					JButton dead = new JButton(new ImageIcon(new ImageIcon(DEADCELL).getImage().getScaledInstance(600/SIZE, 600/SIZE, 1)));
					dead.addActionListener(new deadCellClicked(i, g));
					dead.setBounds(x, y, 600/SIZE, 600/SIZE);
					board.add(dead);
				}
				x += 600/SIZE;
			}
			y += 600/SIZE;
		}
		f.add(board);
	}
	public static void updateGrid() {
		board.setLayout(new GridLayout(SIZE, SIZE));
		board.setBounds(130, 30, 600, 600);
		board.removeAll();
		board.repaint();
		int[][] grid = Game.getGrid();
		board.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Generation: " + Game.getGen()));
		int x = 130;
		int y = 30;
		for(int i = 0;i < grid.length;i ++) {
			for(int g = 0;g < grid[i].length; g ++) {
				if(grid[i][g] == 1) {
					JButton live = new JButton(new ImageIcon(new ImageIcon(LIVECELL).getImage().getScaledInstance(600/SIZE, 600/SIZE, 1)));
					live.addActionListener(new liveCellClicked(i, g));
					live.setBounds(x, y, 600/SIZE, 600/SIZE);
					board.add(live);
				}
				else {
					JButton dead = new JButton(new ImageIcon(new ImageIcon(DEADCELL).getImage().getScaledInstance(600/SIZE, 600/SIZE, 1)));
					dead.addActionListener(new deadCellClicked(i, g));
					dead.setBounds(x, y, 600/SIZE, 600/SIZE);
					board.add(dead);
				}
				x += 600/SIZE;
			}
			y += 600/SIZE;
		}
		board.revalidate();
	}
	public static void showRules() {
		JOptionPane rules = new JOptionPane();
		String rulesString = "Conway's Game of Life, created by John Conway, is a zero player game. This means it has no input apart from it's starting position, and is simply a simulation."
				+ "\n The Rules: \n     1. Each living cell with one or no neighbors dies, as if by solitude.\n     2. Each living cell with four or more neighbors dies, as if by overpopulation.\n     3. Each living cell with two or three neighbors survives.\n     4. Each dead cell with three neighbors becomes populated.";
		JOptionPane.showMessageDialog(null, rulesString, "The Game of Life Rules", JOptionPane.INFORMATION_MESSAGE, null);
		rules.setVisible(true);
	}
	public static void showSettings() {
		JOptionPane settings = new JOptionPane();
		JPanel options = new JPanel();
		JLabel liveCells = new JLabel("Living Cell Color: ");
		JLabel deadCells = new JLabel("Dead Cell Color: ");
		JLabel cellSize = new JLabel("Grid Size: ");
		size.addChangeListener(new sizeChanged());
		options.add(liveCells);
		options.add(liveColors);
		options.add(deadCells);
		options.add(deadColors);
		options.add(cellSize);
		options.add(size);
		JOptionPane.showMessageDialog(f, options);
		settings.setVisible(true);
	}
}
class RefreshGrid implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		Game.randomizeGrid();
		GameOfLife.updateGrid();
	}
	
}
class NextGen implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		Game.nextGen();
		GameOfLife.updateGrid();
	}
}
class showRules implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		GameOfLife.showRules();
		
	}
}
class showSettings implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		GameOfLife.showSettings();
	}
}
class liveCellChanged implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> cb = (JComboBox<String>)e.getSource();
		String color = (String)cb.getSelectedItem();
		GameOfLife.LIVECELL = "assets/" + color.toLowerCase() + "Cell.png";
		GameOfLife.updateGrid();
	}
}
class deadCellChanged implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> cb = (JComboBox<String>)e.getSource();
		String color = (String)cb.getSelectedItem();
		GameOfLife.DEADCELL = "assets/" + color.toLowerCase() + "Cell.png";
		GameOfLife.updateGrid();
	}
}
class sizeChanged implements ChangeListener{
	@Override
	public void stateChanged(ChangeEvent e) {
		int value = (int)((JSpinner)e.getSource()).getValue();
		GameOfLife.SIZE = value;
		GameOfLife.curGame = new Game();
		Game.randomizeGrid();
		GameOfLife.updateGrid();
	}	
}
class liveCellClicked implements ActionListener{
	int x;
	int y;
	public liveCellClicked(int nx, int ny) {
		x = nx;
		y = ny;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Game.getGrid()[x][y] = 0;
		GameOfLife.updateGrid();
	}
}
class deadCellClicked implements ActionListener{
	int x;
	int y;
	public deadCellClicked(int nx, int ny) {
		x = nx;
		y = ny;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Game.getGrid()[x][y] = 1;
		GameOfLife.updateGrid();
	}
}
class clearGrid implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		Game.setGrid(new int[GameOfLife.SIZE][GameOfLife.SIZE]);
		GameOfLife.updateGrid();
	}
	
}
class playGame implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(GameOfLife.isPlaying) {
			GameOfLife.isPlaying = false;
			System.out.println("Stopped playing");
			GameOfLife.playingTimer.cancel();
			GameOfLife.playingTimer = new Timer();
		}
		else {
			GameOfLife.isPlaying = true;
			System.out.println("Started playing");
			TimerTask playTimer = new PlayTimerTask();
			GameOfLife.playingTimer.schedule(playTimer, 500, 5000);
		}
	}
}