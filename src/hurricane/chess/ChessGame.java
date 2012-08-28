package hurricane.chess;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

public class ChessGame {

	public ChessBoard board;
	private Turn turn;
	// Store the list of moves as a stack of board states
	private Stack<ChessBoard> moveList;
	private BufferedImage chessBoardImage;
	private Map<String, BufferedImage> images;

	public Turn getTurn() {
		return turn;
	}

	/**
	 * Initialises a new game of chess and loads the images of the chess pieces.
	 */
	public ChessGame() {
		board = new ChessBoard();
		moveList = new Stack<ChessBoard>();
		moveList.push(board);
		turn = Turn.white;
		loadImages();
	}

	private void loadImages() {
		images = new HashMap<String, BufferedImage>();
		try {
			images.put("blackbishop", ImageIO.read(new File("src/hurricane/chess/res/Bishop_Black-10.png")));
			images.put("whitebishop", ImageIO.read(new File("src/hurricane/chess/res/Bishop_White-10.png")));
			images.put("blackking", ImageIO.read(new File("src/hurricane/chess/res/King_Black-10.png")));
			images.put("whiteking", ImageIO.read(new File("src/hurricane/chess/res/King_White-10.png")));
			images.put("blackknight", ImageIO.read(new File("src/hurricane/chess/res/Knight_Black-10.png")));
			images.put("whiteknight", ImageIO.read(new File("src/hurricane/chess/res/Knight_White-10.png")));
			images.put("blackpawn", ImageIO.read(new File("src/hurricane/chess/res/Pawn_Black-10.png")));
			images.put("whitepawn", ImageIO.read(new File("src/hurricane/chess/res/Pawn_White-10.png")));
			images.put("blackqueen", ImageIO.read(new File("src/hurricane/chess/res/Queen_Black-10.png")));
			images.put("whitequeen", ImageIO.read(new File("src/hurricane/chess/res/Queen_White-10.png")));
			images.put("blackrook", ImageIO.read(new File("src/hurricane/chess/res/Rook_Black-10.png")));
			images.put("whiterook", ImageIO.read(new File("src/hurricane/chess/res/Rook_White-10.png")));
		} catch (IOException e) {
			System.err.print("Image files could not be loaded\n");
		}
	}

	public boolean move(int startX, int startY, int endX, int endY, JTextArea outputArea) {
		outputArea.append(startX + ", " + startY + ", " + endX + ", " + endY + "\n");
		// check the correct colour is being moved, the spot to move contains a
		// piece of the correct colour
		
		String piece = board.getPiece(startX, startY).toString();
		if (piece.contains(turn.toString())&& board.movePiece(startX, startY, endX, endY)) {

			
			// TODO replace this nonsense with an undo method in the chessboard class IMO
			// that way we can say, if (currentmoverincheck) { board.undo return false }
			// then we can get rid of this silly stack of board states that seems to be causing other problems like phantom moves
			
			// TODO attempt to make a move that leaves your king in check.  move is denied.  make a different move that covers the check
			// then both moves happen.  if you attempt a move that leaves your king in check, it locks you into a state where 
			// you cant make another move unless you move another piece to block the check and then both moves happen
			
			// add the current board state to the list of moves
			//moveList.push(new ChessBoard(board));
			//this.board = moveList.peek();
			moveList.push(this.board);

			if (currentMoverInCheck()) {
				// if we get in here the move is illegal as their king is now in
				// check.
				// roll back the turn and return false
				moveList.pop();
				this.board = moveList.peek();
				outputArea.append("King cannot be in check at the end of turn. ");
				outputArea.append("It is " + turn.toString() + "'s turn now.\n");
				return false;
			}
			if (board.whiteInCheck()) {
				outputArea.append("White is in check!\n");
			}
			if (board.blackInCheck()) {
				outputArea.append("Black is in check!\n");
			}
			toggleTurn();
			outputArea.append("Successful move. ");
			outputArea.append("It is " + turn.toString() + "'s turn now.\n");

			return true;
		} else {
			if (board.whiteInCheck()) {
				outputArea.append("White is in check!");
			}
			if (board.blackInCheck()) {
				outputArea.append("Black is in check!");
			}
			outputArea.append("Invalid move!. ");
			outputArea.append("It is " + turn.toString() + "'s turn now." + "\n");
			return false;
		}
	}

	/**
	 * 
	 */
	private void toggleTurn() {
		if (turn == Turn.black) {
			turn = Turn.white;
		} else {
			turn = Turn.black;
		}
	}

	/**
	 * Helper method to determine if the current turn is in check. If this
	 * method returns true the turn needs to be rolled back and the turn method
	 * needs to return false.
	 * 
	 * @return
	 */
	private boolean currentMoverInCheck() {
		// get the current mover and call black check or white check method
		if (turn == Turn.black) {
			return board.blackInCheck();
		} else {
			return board.whiteInCheck();
		}
	}

	public BufferedImage makeImage() {
		try {
			chessBoardImage = ImageIO.read(new File("src/hurricane/chess/res/chessboard.png"));
		} catch (IOException e) {
			System.err.print("Image files could not be loaded\n");
		}
		Graphics2D g = chessBoardImage.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		int startValue = 20;
		int increment = 75;
		int pieceX = 7;
		int pieceY = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (pieceX < 0) {
					pieceX = 7;
				}
				if (pieceY > 7) {
					pieceY = 0;
				}
				String nextPiece = board.getPiece(pieceX, pieceY).toString();
				if (nextPiece.equals("empty")) {
					pieceX--;
					continue;
				}
				g.drawImage(images.get(nextPiece), startValue + i * increment, startValue + j * increment, null);
				pieceX--;
			}
			pieceY++;
		}
		return chessBoardImage;
	}

	public boolean move(Point firstPoint, Point secondPoint,
			JTextArea outputArea) {
		// translate the points here
		int startX = 0, startY = 7, endX = 0, endY = 7;
		int firstX = 0, firstY = 0, incr = 75;
		if (firstPoint.x < firstX + incr) {
			startY = 0;
		} else if (firstPoint.x < firstX + 2 * incr) {
			startY = 1;
		} else if (firstPoint.x < firstX + 3 * incr) {
			startY = 2;
		} else if (firstPoint.x < firstX + 4 * incr) {
			startY = 3;
		} else if (firstPoint.x < firstX + 5 * incr) {
			startY = 4;
		} else if (firstPoint.x < firstX + 6 * incr) {
			startY = 5;
		} else if (firstPoint.x < firstX + 7 * incr) {
			startY = 6;
		}

		if (secondPoint.x < firstX + incr) {
			endY = 0;
		} else if (secondPoint.x < firstX + 2 * incr) {
			endY = 1;
		} else if (secondPoint.x < firstX + 3 * incr) {
			endY = 2;
		} else if (secondPoint.x < firstX + 4 * incr) {
			endY = 3;
		} else if (secondPoint.x < firstX + 5 * incr) {
			endY = 4;
		} else if (secondPoint.x < firstX + 6 * incr) {
			endY = 5;
		} else if (secondPoint.x < firstX + 7 * incr) {
			endY = 6;
		}

		if (firstPoint.y < firstY + incr) {
			startX = 7;
		} else if (firstPoint.y < firstY + 2 * incr) {
			startX = 6;
		} else if (firstPoint.y < firstY + 3 * incr) {
			startX = 5;
		} else if (firstPoint.y < firstY + 4 * incr) {
			startX = 4;
		} else if (firstPoint.y < firstY + 5 * incr) {
			startX = 3;
		} else if (firstPoint.y < firstY + 6 * incr) {
			startX = 2;
		} else if (firstPoint.y < firstY + 7 * incr) {
			startX = 1;
		}

		if (secondPoint.y < firstY + incr) {
			endX = 7;
		} else if (secondPoint.y < firstY + 2 * incr) {
			endX = 6;
		} else if (secondPoint.y < firstY + 3 * incr) {
			endX = 5;
		} else if (secondPoint.y < firstY + 4 * incr) {
			endX = 4;
		} else if (secondPoint.y < firstY + 5 * incr) {
			endX = 3;
		} else if (secondPoint.y < firstY + 6 * incr) {
			endX = 2;
		} else if (secondPoint.y < firstY + 7 * incr) {
			endX = 1;
		}

		return this.move(startX, startY, endX, endY, outputArea);
	}
}

enum Turn {
	white, black
}
