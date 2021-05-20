import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;

public class GameOfLife {
	public static int SIZE = 30;
	public static int TIMEOUT_TIME_MINUTES = 5;
	public static Color DEADCELL = Color.black;
	public static Color LIVECELL = Color.white;
	public static Game curGame = new Game();
	public static JFrame f = new JFrame("Nimbus Look and Feel");
	static JPanel board = new JPanel(new GridLayout(30, 30));
	static JLabel[][] cells = new JLabel[30][30];
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		
		f.setBackground(new Color(255, 244, 171));
		f.setTitle("The Game of Life");
		ImageIcon logo = new ImageIcon("golLogo.png");
		f.setIconImage(logo.getImage());
		JButton refresh = new JButton("🔁");
		JButton next = new JButton("▶");
		
		refresh.setBounds(300, 600, 80, 80);
		refresh.addActionListener(new RefreshGrid());
		next.setBounds(420, 600, 80, 80);
		next.addActionListener(new NextGen());
		 
		f.add(refresh);
		f.add(next);
		initGrid();
		f.setSize(800, 800);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	public static void initGrid() {
		board.setBounds(125, 40, 550, 550);
		board.setBackground(new Color(255, 244, 171));
		board.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Generation: " + Game.getGen()));
		board.setVisible(true);
		int[][] grid = Game.getGrid();
		System.out.println("Called display");
		for(int i = 0;i < grid.length;i ++) {
			for(int g = 0;g < grid[i].length; g ++) {
				if(grid[i][g] == 0) {
					JLabel live = new JLabel(new ImageIcon("liveCell.png"));
					board.add(live);
				}
				else {
					JLabel dead = new JLabel(new ImageIcon("deadCell.png"));
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
		System.out.println("Called update");
		for(int i = 0;i < grid.length;i ++) {
			for(int g = 0;g < grid[i].length; g ++) {
				System.out.print(grid[i][g]);
				if(grid[i][g] == 0) {
					JLabel live = new JLabel(new ImageIcon("liveCell.png"));
					board.add(live);
				}
				else {
					JLabel dead = new JLabel(new ImageIcon("deadCell.png"));
					board.add(dead);
				}
			}
			System.out.println();
		}
		board.revalidate();
	}
	
}
class RefreshGrid implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		Game.randomizeGrid();
		GameOfLife.updateGrid();
		System.out.println("Refresh pressed");
	}
	
}
class NextGen implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		Game.nextGen();
		GameOfLife.updateGrid();
		System.out.println("Next pressed");
	}
}