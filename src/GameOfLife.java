import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;

public class GameOfLife {
	public static int SIZE = 30;
	public static int TIMEOUT_TIME_MINUTES = 5;
	public static String DEADCELL = "whiteCell.png";
	public static String LIVECELL = "blackCell.png";
	public static Game curGame = new Game();
	public static JFrame f = new JFrame("Nimbus Look and Feel");
	static JPanel board = new JPanel(new GridLayout(30, 30));
	static JLabel[][] cells = new JLabel[30][30];
	static String[] colors = {"Black", "White", "Blue", "Green","Orange", "Pink", "Purple", "Red", "Yellow"};
	static JComboBox<String> liveColors = new JComboBox<>(colors);
	static JComboBox<String> deadColors = new JComboBox<>(colors);
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

		refresh.setBounds(300, 600, 80, 80);
		refresh.addActionListener(new RefreshGrid());
		next.setBounds(420, 600, 80, 80);
		next.addActionListener(new NextGen());
		rules.setBounds(20, 20, 40, 40);
		rules.addActionListener(new showRules());
		settings.setBounds(20, 60, 40, 40);
		settings.addActionListener(new showSettings());
		liveColors.addActionListener(new liveCellChanged());
		deadColors.addActionListener(new deadCellChanged());

		f.add(refresh);
		f.add(next);
		f.add(rules);
		f.add(settings);
		initGrid();
		f.setSize(800, 800);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public static void initGrid() {
		board.setBounds(125, 40, 550, 550);
		board.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Generation: " + Game.getGen()));
		board.setVisible(true);
		int[][] grid = Game.getGrid();
		for(int i = 0;i < grid.length;i ++) {
			for(int g = 0;g < grid[i].length; g ++) {
				if(grid[i][g] == 1) {
					JLabel live = new JLabel(new ImageIcon(LIVECELL));
					board.add(live);
				}
				else {
					JLabel dead = new JLabel(new ImageIcon(DEADCELL));
					board.add(dead);
				}
			}
		}
		f.add(board);
	}
	public static void updateGrid() {
		board.removeAll();
		board.repaint();
		int[][] grid = Game.getGrid();
		board.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Generation: " + Game.getGen()));
		for(int i = 0;i < grid.length;i ++) {
			for(int g = 0;g < grid[i].length; g ++) {
				if(grid[i][g] == 1) {
					JLabel live = new JLabel(new ImageIcon(LIVECELL));
					board.add(live);
				}
				else {
					JLabel dead = new JLabel(new ImageIcon(DEADCELL));
					board.add(dead);
				}
			}
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
	public static void addToolbar() {
		JOptionPane settings = new JOptionPane();
		JPanel options = new JPanel();
		JLabel liveCells = new JLabel("Living Cell Color: ");
		JLabel deadCells = new JLabel("Dead Cell Color: ");
		options.add(liveCells);
		options.add(liveColors);
		options.add(deadCells);
		options.add(deadColors);
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
		GameOfLife.addToolbar();
	}
}
class liveCellChanged implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> cb = (JComboBox<String>)e.getSource();
		String color = (String)cb.getSelectedItem();
		GameOfLife.LIVECELL = color.toLowerCase() + "Cell.png";
		GameOfLife.updateGrid();
	}
}
class deadCellChanged implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> cb = (JComboBox<String>)e.getSource();
		String color = (String)cb.getSelectedItem();
		GameOfLife.DEADCELL = color.toLowerCase() + "Cell.png";
		GameOfLife.updateGrid();
	}
}