package hurricane.chess;

import java.awt.Dimension;

public class ChessMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChessWindow theWindow = new ChessWindow();
		theWindow.init();
		theWindow.start();
		
		javax.swing.JFrame window = new javax.swing.JFrame("Chess game");
		window.setContentPane(theWindow);
		Dimension size = new Dimension(800, 800);
		window.setMinimumSize(size);
		window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}

}
