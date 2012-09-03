package hurricane.chess;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyJPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ChessGame myGame;
	BufferedImage img;
	
	public MyJPanel() {
		super();
	}
	
	public MyJPanel(ChessGame currentGame) {
		super();
		myGame = currentGame;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		BufferedImage img = myGame.makeImage();
//		img = null;
//		try {
//			img = ImageIO.read(new File("src/hurricane/chess/res/Bishop_Black-10.png"));
//		} catch (IOException e) {
//			
//		}
		g.drawImage(img, 0, 0, this);
		repaint();
	}
}
