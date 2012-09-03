package hurricane.chess;

/**
 * Class to represent the current state of the chess board and provide methods to manipulate the game.
 * 
 * TODO figure out castleing
 * 
 * @author Andrew
 * 
 */
public class ChessBoard {
	private ChessPiece[][] chessPieces;
	private static final int BOARD_SIZE = 8;
	private int blackKingX;
	private int blackKingY;
	private int whiteKingX;
	private int whiteKingY;
	private int pawnEPPosition;
	private boolean EPLastMove;

	/* Holder variables to enable moves to be rolled back */
	private ChessPiece[][] prev_chessPieces;
	private int prev_blackKingX;
	private int prev_blackKingY;
	private int prev_whiteKingX;
	private int prev_whiteKingY;
	private int prev_pawnEPPosition;
	private boolean prev_EPLastMove;
	
	/**
	 * Default constructor
	 */
	public ChessBoard() {
		// initialise the board with standard setup for the start of a chess
		// game
		chessPieces = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
		pawnEPPosition = -1;
		EPLastMove = false;
		initialisePieces();
	}

	/**
	 * Method to move pieces on the chess board. Checks if move is valid
	 * according to the rules of chess and carries out the move.
	 * 
	 * @param startX
	 *            x-axis is across the bottom of the board starting from left,
	 *            range 0-7
	 * @param startY
	 *            y-axis is up the board starting from white side, range 0-7
	 * @param endX
	 *            x-axis is across the bottom of the board starting from left,
	 *            range 0-7
	 * @param endY
	 *            y-axis is up the board starting from white side, range 0-7
	 * @return false if the move is invalid
	 */
	public boolean movePiece(int startX, int startY, int endX, int endY) {

		// out of range co-ordinates, must convert co-ordinates to 0-7 range
		// first
		if (startX < 0 || startX > 7 || startY < 0 || startY > 7 || endX < 0
				|| endX > 7 || endY < 0 || endY > 7) {
			return false;
		}

		// start co-ord is the same as end co-ord
		if (startX == endX && startY == endY) {
			return false;
		}

		ChessPiece currentPiece = chessPieces[startX][startY];

		// end piece is the king or a piece of the same colour as moving piece
		if ((currentPiece.toString().contains("white") && chessPieces[endX][endY]
				.toString().contains("white"))
				|| (currentPiece.toString().contains("black") && chessPieces[endX][endY]
						.toString().contains("black"))) {
			return false;
		}
		if (chessPieces[endX][endY].toString().contains("king")) {
			return false;
		}

		copyVariables();
		
		// have a validate method for each type of piece, so 12 validate methods
		switch (currentPiece) {
		case empty:
			return false;
		case whiteking:
			if (!checkWhiteKing(startX, startY, endX, endY)) {
				return false;
			}
			whiteKingX = endX;
			whiteKingY = endY;
			break;
		case whitequeen:
			if (!checkWhiteQueen(startX, startY, endX, endY)) {
				return false;
			}
			break;
		case whitebishop:
			if (!checkWhiteBishop(startX, startY, endX, endY)) {
				return false;
			}
			break;
		case whiteknight:
			if (!checkWhiteKnight(startX, startY, endX, endY)) {
				return false;
			}
			break;
		case whiterook:
			if (!checkWhiteRook(startX, startY, endX, endY)) {
				return false;
			}
			break;
		case whitepawn:
			if (!checkWhitePawn(startX, startY, endX, endY)) {
				return false;
			}
			if (endX - startX == 1 && Math.abs(startY - endY) == 1
					&& chessPieces[endX][endY] == ChessPiece.empty
					&& EPLastMove && endY == pawnEPPosition && endX == 5){
				chessPieces[endX - 1][endY] = ChessPiece.empty;
			}
			pawnEPPosition = -1;
			EPLastMove = false;
			if (startX == 1 && endX == 3) {
				pawnEPPosition = startY;
				EPLastMove = true;
			}
			break;
		case blackking:
			if (!checkBlackKing(startX, startY, endX, endY)) {
				return false;
			}
			blackKingX = endX;
			blackKingY = endY;
			break;
		case blackqueen:
			if (!checkBlackQueen(startX, startY, endX, endY)) {
				return false;
			}
			break;
		case blackbishop:
			if (!checkBlackBishop(startX, startY, endX, endY)) {
				return false;
			}
			break;
		case blackknight:
			if (!checkBlackKnight(startX, startY, endX, endY)) {
				return false;
			}
			break;
		case blackrook:
			if (!checkBlackRook(startX, startY, endX, endY)) {
				return false;
			}
			break;
		case blackpawn:
			if (!checkBlackPawn(startX, startY, endX, endY)) {
				return false;
			}
			if (startX - endX == 1 && Math.abs(startY - endY) == 1
					&& chessPieces[endX][endY] == ChessPiece.empty
					&& EPLastMove && endY == pawnEPPosition && endX == 2) {
				chessPieces[endX + 1][endY] = ChessPiece.empty;
			}
			pawnEPPosition = -1;
			EPLastMove = false;
			if (startX == 6 && endX == 4) {
				pawnEPPosition = startY;
				EPLastMove = true;
			}
			break;
		default:
			return false;
		}
		chessPieces[endX][endY] = chessPieces[startX][startY];
		chessPieces[startX][startY] = ChessPiece.empty;
		return true;
	}

	/**
	 * Helper method to copy the class variables before each move to enable moves to be reverted.
	 */
	private void copyVariables() {
		prev_chessPieces = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				prev_chessPieces[i][j] =  chessPieces[i][j];
			}
		}
		prev_blackKingX = blackKingX;
		prev_blackKingY = blackKingY;
		prev_whiteKingX = whiteKingX;
		prev_whiteKingY = whiteKingY;
		prev_pawnEPPosition = pawnEPPosition;
		prev_EPLastMove = EPLastMove;
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkBlackPawn(int startX, int startY, int endX, int endY) {
		return (startX - endX == 1 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty)
				|| (startX == 6 && endX == 4 && startY == endY
						&& chessPieces[endX][endY] == ChessPiece.empty && chessPieces[endX + 1][endY] == ChessPiece.empty)
				|| (startX - endX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY]
						.toString().contains("white"))
				|| (startX - endX == 1 && Math.abs(startY - endY) == 1
						&& chessPieces[endX][endY] == ChessPiece.empty
						&& EPLastMove == true && endY == pawnEPPosition && endX == 2);
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkBlackRook(int startX, int startY, int endX, int endY) {
		// piece moves on same x or same y, and the path to the end point is
		// clear, and the end point is either empty or has enemy piece
		return ((startX == endX || startY == endY) && (checkPathBlackRook(
				startX, startY, endX, endY)));
	}

	/**
	 * Helper method to check if the path to the end position is clear.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the path is clear
	 */
	private boolean checkPathBlackRook(int startX, int startY, int endX,
			int endY) {
		
		String endPiece = chessPieces[endX][endY].toString();
		
		if (endPiece.contains("black")) {
			return false;
		}
		
		if (startX > endX) {
			int temp = startX;
			startX = endX;
			endX = temp;
		}
		if (startY > endY) {
			int temp = startY;
			startY = endY;
			endY = temp;
		}
		
		if(startX == endX) {
			for (int i = startY + 1; i < endY - 1; i++) {
				if (chessPieces[startX][i] != ChessPiece.empty) {
					return false;
				}
			}
		} else {
			for (int i = startX + 1; i < endX - 1; i++) {
				if (chessPieces[i][startY] != ChessPiece.empty) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkBlackKnight(int startX, int startY, int endX, int endY) {
		return (((Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) || (Math
				.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2)) && !chessPieces[endX][endY]
				.toString().contains("black"));
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkBlackBishop(int startX, int startY, int endX, int endY) {
		// piece moves on same x and same y, and the path to the end point is
		// clear, and the end point is either empty or has enemy piece
		return ((Math.abs(startX - endX) == Math.abs(startY - endY)) && (checkPathBlackBishop(
				startX, startY, endX, endY)));
	}

	/**
	 * Helper method to check if the path to the end position is clear.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the path is clear
	 */
	private boolean checkPathBlackBishop(int startX, int startY, int endX,
			int endY) {
		String endPiece = chessPieces[endX][endY].toString();
		if (endPiece.contains("black")) {
			return false;
		}
		// up to the right and down to the left
		if ((startX < endX && startY < endY)
				|| (startX > endX && startY > endY)) {
			if (startX > endX) {
				// down to the left
				int j = endY + 1;
				for (int i = endX + 1; i < startX; i++) {
					if (chessPieces[i][j] != ChessPiece.empty) {
						return false;
					}
					j++;
				}
			} else {
				// up to the right
				int j = startY + 1;
				for (int i = startX + 1; i < endX; i++) {
					if (chessPieces[i][j] != ChessPiece.empty) {
						return false;
					}
					j++;
				}
			}
		} else {
			// up to the left and down to the right
			if (startX < endX) {
				// down to the right, x goes up y goes down
				int j = startY - 1;
				for (int i = startX + 1; i < endX; i++) {
					if (chessPieces[i][j] != ChessPiece.empty) {
						return false;
					}
					j--;
				}
			} else {
				// up to the left, x goes down y goes up
				int j = startY + 1;
				for (int i = startX - 1; i > endX; i--) {
					if (chessPieces[i][j] != ChessPiece.empty) {
						return false;
					}
					j++;
				}
			}
		}
		return true;
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkBlackQueen(int startX, int startY, int endX, int endY) {
		// either startx-endx = starty-endy or starty=endy or startx=endx, and
		// the way is clear. Call check bishop or check rook method depending
		// on which way it moves
		if (startX == endX || startY == endY) {
			return checkPathBlackRook(startX, startY, endX, endY);
		} else if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
			return checkPathBlackBishop(startX, startY, endX, endY);
		}  else {
			return false;
		}
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkBlackKing(int startX, int startY, int endX, int endY) {
		return Math.abs(startX - endX) <= 1 && Math.abs(startY - endY) <= 1 && !chessPieces[endX][endY]
				.toString().contains("black") && (Math.abs(blackKingX - whiteKingX) > 1 || Math.abs(blackKingY - whiteKingY) > 1);
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkWhitePawn(int startX, int startY, int endX, int endY) {
		return (endX - startX == 1 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty)
				|| (startX == 1 && endX == 3 && startY == endY
						&& chessPieces[endX][endY] == ChessPiece.empty && chessPieces[endX - 1][endY] == ChessPiece.empty)
				|| (endX - startX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY].toString().contains("black"))
				|| (endX - startX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY] == ChessPiece.empty
						&& EPLastMove && endY == pawnEPPosition && endX == 5);
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkWhiteRook(int startX, int startY, int endX, int endY) {
		// piece moves on same x or same y, and the path to the end point is
		// clear, and the end point is either empty or has enemy piece
		return ((startX == endX || startY == endY) && (checkPathWhiteRook(startX, startY, endX, endY)));
	}

	/**
	 * Helper method to check if the path to the end position is clear.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the path is clear
	 */
	private boolean checkPathWhiteRook(int startX, int startY, int endX,
			int endY) {
		
		String endPiece = chessPieces[endX][endY].toString();
		
		if (endPiece.contains("white")) {
			return false;
		}
		
		if (startX > endX) {
			int temp = startX;
			startX = endX;
			endX = temp;
		}
		if (startY > endY) {
			int temp = startY;
			startY = endY;
			endY = temp;
		}
		
		if(startX == endX) {
			for (int i = startY + 1; i < endY - 1; i++) {
				if (chessPieces[startX][i] != ChessPiece.empty) {
					return false;
				}
			}
		} else {
			for (int i = startX + 1; i < endX - 1; i++) {
				if (chessPieces[i][startY] != ChessPiece.empty) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkWhiteKnight(int startX, int startY, int endX, int endY) {
		return (((Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) || (Math
				.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2)) && !chessPieces[endX][endY]
				.toString().contains("white"));
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkWhiteBishop(int startX, int startY, int endX, int endY) {
		// piece moves on same x and same y, and the path to the end point is
		// clear, and the end point is either empty or has enemy piece
		return ((Math.abs(startX - endX) == Math.abs(startY - endY)) && (checkPathWhiteBishop(
				startX, startY, endX, endY)));
	}

	/**
	 * Helper method to check if the path to the end position is clear.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the path is clear
	 */
	private boolean checkPathWhiteBishop(int startX, int startY, int endX,
			int endY) {
		String endPiece = chessPieces[endX][endY].toString();
		if (endPiece.contains("white")) {
			return false;
		}
		// up to the right and down to the left
		if ((startX < endX && startY < endY)
				|| (startX > endX && startY > endY)) {
			if (startX > endX) {
				// down to the left
				int j = endY + 1;
				for (int i = endX + 1; i < startX; i++) {
					if (chessPieces[i][j] != ChessPiece.empty) {
						return false;
					}
					j++;
				}
			} else {
				// up to the right
				int j = startY + 1;
				for (int i = startX + 1; i < endX; i++) {
					if (chessPieces[i][j] != ChessPiece.empty) {
						return false;
					}
					j++;
				}
			}
		} else {
			// up to the left and down to the right
			if (startX < endX) {
				// down to the right, x goes up y goes down
				int j = startY - 1;
				for (int i = startX + 1; i < endX; i++) {
					if (chessPieces[i][j] != ChessPiece.empty) {
						return false;
					}
					j--;
				}
			} else {
				// up to the left, x goes down y goes up
				int j = startY + 1;
				for (int i = startX - 1; i > endX; i--) {
					if (chessPieces[i][j] != ChessPiece.empty) {
						return false;
					}
					j++;
				}
			}
		}
		return true;
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkWhiteQueen(int startX, int startY, int endX, int endY) {
		// either startx-endx = starty-endy or starty=endy or startx=endx, and
		// the way is clear. Call check bishop or check rook method depending
		// on which way it moves
		if (startX == endX || startY == endY) {
			return checkPathWhiteRook(startX, startY, endX, endY);
		} else if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
			return checkPathWhiteBishop(startX, startY, endX, endY);
		} else {
			return false;
		}
	}

	/**
	 * Methods to check the given move is valid.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return true if the move is valid
	 */
	private boolean checkWhiteKing(int startX, int startY, int endX, int endY) {
		return Math.abs(startX - endX) <= 1 && Math.abs(startY - endY) <= 1 && !chessPieces[endX][endY]
				.toString().contains("white") && (Math.abs(blackKingX - whiteKingX) > 1 || Math.abs(blackKingY - whiteKingY) > 1);
	}

	/**
	 * Sets up the board with pieces in the right place for the chess game.
	 * chessPieces array must be initialised first
	 */
	private void initialisePieces() {
		if (chessPieces == null) {
			chessPieces = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
		}
		// hard coded starting positions
		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				chessPieces[i][j] = ChessPiece.empty;
			}
		}
		chessPieces[0][0] = ChessPiece.whiterook;
		chessPieces[0][1] = ChessPiece.whiteknight;
		chessPieces[0][2] = ChessPiece.whitebishop;
		chessPieces[0][3] = ChessPiece.whitequeen;
		chessPieces[0][4] = ChessPiece.whiteking;
		chessPieces[0][5] = ChessPiece.whitebishop;
		chessPieces[0][6] = ChessPiece.whiteknight;
		chessPieces[0][7] = ChessPiece.whiterook;

		chessPieces[1][0] = ChessPiece.whitepawn;
		chessPieces[1][1] = ChessPiece.whitepawn;
		chessPieces[1][2] = ChessPiece.whitepawn;
		chessPieces[1][3] = ChessPiece.whitepawn;
		chessPieces[1][4] = ChessPiece.whitepawn;
		chessPieces[1][5] = ChessPiece.whitepawn;
		chessPieces[1][6] = ChessPiece.whitepawn;
		chessPieces[1][7] = ChessPiece.whitepawn;

		chessPieces[7][0] = ChessPiece.blackrook;
		chessPieces[7][1] = ChessPiece.blackknight;
		chessPieces[7][2] = ChessPiece.blackbishop;
		chessPieces[7][3] = ChessPiece.blackqueen;
		chessPieces[7][4] = ChessPiece.blackking;
		chessPieces[7][5] = ChessPiece.blackbishop;
		chessPieces[7][6] = ChessPiece.blackknight;
		chessPieces[7][7] = ChessPiece.blackrook;

		chessPieces[6][0] = ChessPiece.blackpawn;
		chessPieces[6][1] = ChessPiece.blackpawn;
		chessPieces[6][2] = ChessPiece.blackpawn;
		chessPieces[6][3] = ChessPiece.blackpawn;
		chessPieces[6][4] = ChessPiece.blackpawn;
		chessPieces[6][5] = ChessPiece.blackpawn;
		chessPieces[6][6] = ChessPiece.blackpawn;
		chessPieces[6][7] = ChessPiece.blackpawn;
		blackKingX = 7;
		blackKingY = 4;
		whiteKingX = 0;
		whiteKingY = 4;
	}

	/**
	 * Helper method for testing, prints board to standard out.
	 */
	public void printBoard() {
		System.out.println("  " + 0 + "  " + 1 + "  " + 2 + "  " + 3 + "  " + 4
				+ "  " + 5 + "  " + 6 + "  " + 7);
		for (int i = BOARD_SIZE - 1; i >= 0; i--) {
			System.out.print(i + " ");
			for (int j = 0; j < BOARD_SIZE; j++) {
				ChessPiece currentPiece = chessPieces[i][j];

				switch (currentPiece) {
				case whiteking:
					System.out.print("WK ");
					break;
				case whitequeen:
					System.out.print("WQ ");
					break;
				case whitebishop:
					System.out.print("WB ");
					break;
				case whiteknight:
					System.out.print("Wk ");
					break;
				case whiterook:
					System.out.print("WR ");
					break;
				case whitepawn:
					System.out.print("WP ");
					break;
				case blackking:
					System.out.print("BK ");
					break;
				case blackqueen:
					System.out.print("BQ ");
					break;
				case blackbishop:
					System.out.print("BB ");
					break;
				case blackknight:
					System.out.print("Bk ");
					break;
				case blackrook:
					System.out.print("BR ");
					break;
				case blackpawn:
					System.out.print("BP ");
					break;
				case empty:
					System.out.print("E  ");
					break;
				}
			}
			System.out.println("");
		}
		System.out.println("  " + 0 + "  " + 1 + "  " + 2 + "  " + 3 + "  " + 4
				+ "  " + 5 + "  " + 6 + "  " + 7 + "\n");
	}

	/**
	 * Helper method to find piece at given position on the chess board.
	 * @param xPos
	 * @param yPos
	 * @return The chess piece at position xPos, yPos
	 */
	public ChessPiece getPiece(int xPos, int yPos) {
		return chessPieces[xPos][yPos];
	}

	/**
	 * Helper method to determine if black King is in check
	 * 
	 * @return
	 */
	public boolean blackInCheck() {
		// need to store the position of the kings seperately so they can be checked against.
		// this means updating the move method to keep the kings position
		// go through every white piece on the board to see if it can take the black king next move
		// dont forget the check piece methods dont actually move the pieces, so use them.

		// if the white piece is a pawn, king must be x+1 and y+/-1
		// rook, on the same line and a move to there would be valid
		// knight, x+/-2,y+/-1 x+/-1,y+/-2 checking if the black king is on each
		// square
		// bishop, on the same diagonal and a move to there would be valid
		// queen, check as if rook and bishop
		// king, nothing. king move method should stop the kings from getting
		// within a square of each other
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				ChessPiece currentPiece = chessPieces[i][j];
				switch (currentPiece) {
				case whitequeen:
					if (checkWhiteQueen(i, j, blackKingX, blackKingY)) {
						return true;
					}
					break;
				case whitebishop:
					if (checkWhiteBishop(i, j, blackKingX, blackKingY)) {
						return true;
					}
					break;
				case whiteknight:
					if (checkWhiteKnight(i, j, blackKingX, blackKingY)) {
						return true;
					}
					break;
				case whiterook:
					if (checkWhiteRook(i, j, blackKingX, blackKingY)) {
						return true;
					}
					break;
				case whitepawn:
					if (j != 0 && j != 7) {
						if (chessPieces[i + 1][j + 1]
								.equals(ChessPiece.blackking)
								|| chessPieces[i + 1][j - 1]
										.equals(ChessPiece.blackking)) {
							return true;
						}
					} else {
						if (j == 0) {
							if (chessPieces[i + 1][j + 1]
									.equals(ChessPiece.blackking)) {
								return true;
							}
						} else {
							if (chessPieces[i + 1][j - 1]
									.equals(ChessPiece.blackking)) {
								return true;
							}
						}
					}
					break;
				default:
					continue;
				}
			}
		}
		return false;
	}

	/**
	 * Helper method to determine if white King is in check
	 * 
	 * @return
	 */
	public boolean whiteInCheck() {
		// go through every black piece on the board to see if it can take the
		// white king next move

		// if the black piece is a pawn, king must be x+1 and y+/-1
		// rook, on the same line and a move to there would be valid
		// knight, x+/-2,y+/-1 x+/-1,y+/-2 checking if the black king is on each
		// square
		// bishop, on the same diagonal and a move to there would be valid
		// queen, check as if rook and bishop
		// king, nothing. king move method should stop the kings from getting
		// within a sq
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				ChessPiece currentPiece = chessPieces[i][j];
				switch (currentPiece) {
				case blackqueen:
					if (checkBlackQueen(i, j, whiteKingX, whiteKingY)) {
						return true;
					}
					break;
				case blackbishop:
					if (checkBlackBishop(i, j, whiteKingX, whiteKingY)) {
						return true;
					}
					break;
				case blackknight:
					if (checkBlackKnight(i, j, whiteKingX, whiteKingY)) {
						return true;
					}
					break;
				case blackrook:
					if (checkBlackRook(i, j, whiteKingX, whiteKingY)) {
						return true;
					}
					break;
				case blackpawn:
					if (j != 0 && j != 7) {
						if (chessPieces[i - 1][j + 1]
								.equals(ChessPiece.whiteking)
								|| chessPieces[i - 1][j - 1]
										.equals(ChessPiece.whiteking)) {
							return true;
						}
					} else {
						if (j == 0) {
							if (chessPieces[i - 1][j + 1]
									.equals(ChessPiece.whiteking)) {
								return true;
							}
						} else {
							if (chessPieces[i - 1][j - 1]
									.equals(ChessPiece.whiteking)) {
								return true;
							}
						}
					}
					break;
				default:
					continue;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method to roll back a move.  Will return the chessboard class to its previous state.
	 */
	public void revertMove() {
		// Make copies of each class variable at the start of each move.  Use these copy variables to revert move here.
		chessPieces = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				chessPieces[i][j] =  prev_chessPieces[i][j];
			}
		}
		blackKingX = prev_blackKingX;
		blackKingY = prev_blackKingY;
		whiteKingX = prev_whiteKingX;
		whiteKingY = prev_whiteKingY;
		pawnEPPosition = prev_pawnEPPosition;
		EPLastMove = prev_EPLastMove;
	}
}

enum ChessPiece { whiteking, whitequeen, whitebishop, whiteknight, whiterook, whitepawn, blackking, blackqueen, blackbishop, blackknight, blackrook, blackpawn, empty}