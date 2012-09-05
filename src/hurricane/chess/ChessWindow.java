package hurricane.chess;

import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JApplet;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class ChessWindow extends JApplet{
	
	private ChessGame myGame;
	private boolean firstClick;
	private Point firstPoint, secondPoint;
	JLabel lblNewLabel;
	JTextArea outputArea;

	/**
	 * Launch the application.
	 */
	public void init() {
		try {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//ChessWindow window = new ChessWindow();
					myGame = new ChessGame();
					initialize();
				}
			});
		}catch (Exception e) {
			e.printStackTrace();	
		}
	}

	/**
	 * Create the application.
	 */
//	public ChessWindow() {
//		
//
//	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
//		firstClick = false;
//		frmChess = new JFrame();
//		frmChess.setTitle("Chess Player");
//		frmChess.setBounds(100, 100, 800, 700);
//		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		getContentPane().add(frmChess);

		JPanel menu_panel = new JPanel();
		menu_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		getContentPane().add(menu_panel, BorderLayout.WEST);
		menu_panel.setLayout(new BoxLayout(menu_panel, BoxLayout.Y_AXIS));

//		JMenuBar menuBar = new JMenuBar();
//		menu_panel.add(menuBar);
//
//		JMenu options_menu = new JMenu("Options");
//		menuBar.add(options_menu);
//		options_menu.setHorizontalAlignment(SwingConstants.LEFT);
//
//		//resets everything and starts a new game
//		JMenuItem newgame_menu = new JMenuItem("New game");
//		newgame_menu.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				myGame = new ChessGame();
//				firstClick = false;
//				Image img = myGame.makeImage();
//				if (img != null) {
//					lblNewLabel.setIcon(new ImageIcon(img));
//				}
//			}
//		});
//		newgame_menu.setIcon(new ImageIcon(ChessWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/TreeLeaf.gif")));
//		options_menu.add(newgame_menu);
//
//		JMenuItem exit_menu = new JMenuItem("Exit");
//		exit_menu
//				.setIcon(new ImageIcon(
//						ChessWindow.class
//								.getResource("/javax/swing/plaf/metal/icons/ocean/collapsed-rtl.gif")));
//		exit_menu.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				System.exit(0);
//			}
//		});
//
//		JMenuItem savegame_menu = new JMenuItem("Save game");
//		savegame_menu
//				.setIcon(new ImageIcon(
//						ChessWindow.class
//								.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
//		options_menu.add(savegame_menu);
//
//		JMenuItem loadgame_menu = new JMenuItem("Load saved game");
//		loadgame_menu
//				.setIcon(new ImageIcon(
//						ChessWindow.class
//								.getResource("/com/sun/java/swing/plaf/windows/icons/TreeOpen.gif")));
//		options_menu.add(loadgame_menu);
//		options_menu.add(exit_menu);
//
//		JMenu help_menu = new JMenu("Help");
//		help_menu.setHorizontalAlignment(SwingConstants.LEFT);
//		menuBar.add(help_menu);
//
//		JMenuItem about_menu = new JMenuItem("About chess player");
//		about_menu.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				JDialog jd = new JDialog();
//				jd.setTitle("About chess player");
//				jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				jd.setBounds(300, 300, 300, 100);
//				JTextArea jt = new JTextArea();
//				jt.setFont(new Font("Serif", Font.ITALIC, 14));
//				jt.setEditable(false);
//				jt.setText("Chess player by Hurricane.\nNot to be used for commercial purposes.\nhurricane666@gmail.com");
//				jd.getContentPane().add(jt);
//				jd.setVisible(true);
//			}
//		});
//		about_menu
//				.setIcon(new ImageIcon(
//						ChessWindow.class
//								.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
//		help_menu.add(about_menu);
//
//		JMenuItem howtoplay_menu = new JMenuItem("How to play chess");
//		howtoplay_menu.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				JDialog jd = new JDialog();
//				jd.setTitle("How to play chess");
//				jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				jd.setBounds(300, 300, 500, 150);
//				JTextArea jt = new JTextArea();
//				jt.setFont(new Font("Serif", Font.ITALIC, 14));
//				jt.setEditable(false);
//				jt.setText("Move pieces in turn starting with white.\nGame ends when the king is in check and cannot escape or in a draw.\nFor more information on the rules and strategy of chess\nsee http://en.wikipedia.org/wiki/Chess");
//				jd.getContentPane().add(jt);
//				jd.setVisible(true);
//			}
//		});
//		howtoplay_menu
//				.setIcon(new ImageIcon(
//						ChessWindow.class
//								.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
//		help_menu.add(howtoplay_menu);

		JScrollPane scrollPane = new JScrollPane();
		menu_panel.add(scrollPane);

		outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setFont(new Font("Serif", Font.ITALIC, 14));
		outputArea.setLineWrap(true);
		outputArea.setWrapStyleWord(true);
		scrollPane.setViewportView(outputArea);

		JPanel chess_board = new JPanel();

		chess_board.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		getContentPane().add(chess_board, BorderLayout.CENTER);

		lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Point p = arg0.getPoint();
				outputArea.append(p.x + ", " + p.y + "\n");
				if (firstClick) {
					secondPoint = p;

					// System.out.println(firstPoint.x + ", " + firstPoint.y +
					// ", " + secondPoint.x + ", " + secondPoint.y);

					if (myGame.move(firstPoint, secondPoint, outputArea)) {
						Image img = myGame.makeImage();
						if (img != null) {
							lblNewLabel.setIcon(new ImageIcon(img));
						}
					}
					firstClick = false;
				} else {
					firstPoint = p;
					firstClick = true;
				}
			}
		});
		Image img = myGame.makeImage();
		if (img != null) {
			lblNewLabel.setIcon(new ImageIcon(img));
		}
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		chess_board.add(lblNewLabel);
	}

}
