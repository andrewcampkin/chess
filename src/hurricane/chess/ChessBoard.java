package hurricane.chess;

/**
 * TODO add in turns, figure out determining check, castleing, en-passat there
 * is a bug with moving pieces more than 1 or 2 squares at a time. the logic
 * that checks the way is clear seems to be wrong. Need to give error messages
 * when the move is wrong, with appropriate feedback to the user. Need to make a
 * GUI as well with mouse moves. Make pictures of pieces to be shown on the
 * board.
 * 
 * @author Andrew
 * 
 */
public class ChessBoard {
	private ChessPiece[][] chessPieces;
	private static final int BOARD_SIZE = 8;

	public ChessBoard() {
		// initialise the board with standard setup for the start of a chess
		// game
		chessPieces = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
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

		// have a validate method for each type of piece, so 12 validate methods
		switch (currentPiece) {
		case empty:
			return false;
		case whiteking:
			if (!checkWhiteKing(startX, startY, endX, endY)) {
				return false;
			}
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
			break;
		case blackking:
			if (!checkBlackKing(startX, startY, endX, endY)) {
				return false;
			}
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
			break;
		default:
			return false;
		}
		chessPieces[endX][endY] = chessPieces[startX][startY];
		chessPieces[startX][startY] = ChessPiece.empty;
		return true;
	}

	private boolean checkBlackPawn(int startX, int startY, int endX, int endY) {
		return (startX - endX == 1 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty)
				|| (startX == 6 && endX == 4 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty && chessPieces[endX + 1][endY] == ChessPiece.empty)
				|| (startX - endX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY]
						.toString().contains("white"));
	}

	private boolean checkBlackRook(int startX, int startY, int endX, int endY) {
		// piece moves on same x or same y, and the path to the end point is
		// clear, and the end point is either empty or has enemy piece
		return ((startX == endX || startY == endY) && (checkPathBlackRook(
				startX, startY, endX, endY)));
	}

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
		for (int i = startX; i < endX - 1; i++) {
			for (int j = startY; j < endY - 1; j++) {
				if (chessPieces[i][j] != ChessPiece.empty) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkBlackKnight(int startX, int startY, int endX, int endY) {
		return (((Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) || (Math
				.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2)) && !chessPieces[endX][endY]
				.toString().contains("black"));
	}

	private boolean checkBlackBishop(int startX, int startY, int endX, int endY) {
		// piece moves on same x and same y, and the path to the end point is
		// clear, and the end point is either empty or has enemy piece
		return ((Math.abs(startX - endX) == Math.abs(startY - endY)) && (checkPathBlackBishop(
				startX, startY, endX, endY)));
	}

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

	private boolean checkBlackQueen(int startX, int startY, int endX, int endY) {
		// either startx-endx = starty-endy or starty=endy or startx=endx, and
		// the way is clear. Call check bishop or check rook method depending
		// on which way it moves
		if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
			return checkPathBlackBishop(startX, startY, endX, endY);
		} else if (startX == endX || startY == endY) {
			return checkPathBlackRook(startX, startY, endX, endY);
		} else {
			return false;
		}
	}

	private boolean checkBlackKing(int startX, int startY, int endX, int endY) {
		//TODO check that we are still at least one square away from the white king
		return (Math.abs(startX - endX) <= 1 && Math.abs(startY - endY) <= 1 && !chessPieces[endX][endY]
				.toString().contains("black"));
	}

	private boolean checkWhitePawn(int startX, int startY, int endX, int endY) {
		return (endX - startX == 1 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty)
				|| (startX == 1 && endX == 3 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty && chessPieces[endX - 1][endY] == ChessPiece.empty)
				|| (endX - startX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY]
						.toString().contains("black"));
	}

	private boolean checkWhiteRook(int startX, int startY, int endX, int endY) {
		// piece moves on same x or same y, and the path to the end point is
		// clear, and the end point is either empty or has enemy piece
		return ((startX == endX || startY == endY) && (checkPathWhiteRook(
				startX, startY, endX, endY)));
	}

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
		for (int i = startX; i < endX - 1; i++) {
			for (int j = startY; j < endY - 1; j++) {
				if (chessPieces[i][j] != ChessPiece.empty) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkWhiteKnight(int startX, int startY, int endX, int endY) {
		return (((Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) || (Math
				.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2)) && !chessPieces[endX][endY]
				.toString().contains("white"));
	}

	private boolean checkWhiteBishop(int startX, int startY, int endX, int endY) {
		// piece moves on same x and same y, and the path to the end point is
		// clear, and the end point is either empty or has enemy piece
		return ((Math.abs(startX - endX) == Math.abs(startY - endY)) && (checkPathWhiteBishop(
				startX, startY, endX, endY)));
	}

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

	private boolean checkWhiteQueen(int startX, int startY, int endX, int endY) {
		// either startx-endx = starty-endy or starty=endy or startx=endx, and
		// the way is clear. Call check bishop or check rook method depending
		// on which way it moves
		if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
			return checkPathWhiteBishop(startX, startY, endX, endY);
		} else if (startX == endX || startY == endY) {
			return checkPathWhiteRook(startX, startY, endX, endY);
		} else {
			return false;
		}
	}

	private boolean checkWhiteKing(int startX, int startY, int endX, int endY) {
		//TODO check that we are still at least one square away from the black king
		return (Math.abs(startX - endX) <= 1 && Math.abs(startY - endY) <= 1 && !chessPieces[endX][endY]
				.toString().contains("white"));
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
				// have a validate method for each type of piece, so 12 validate
				// methods
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

	public ChessPiece getPiece(int xPos, int yPos) {
		return chessPieces[xPos][yPos];
	}

	/**
	 * Helper method to determine if black King is in check
	 * @return
	 */
	public boolean blackInCheck() {
		// go through every white piece on the board to see if it can take the black king in one move
		
		// if the white piece is a pawn, king must be x+1 and y+/-1
		// rook, on the same line and a move to there would be valid
		// knight, x+/-2,y+/-1 x+/-1,y+/-2 checking if the black king is on each square
		// bishop, on the same diagonal and a move to there would be valid
		// queen, check as if rook and bishop
		// king, nothing.  king move method should stop the kings from getting within a square of each other
		
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Helper method to determine if white King is in check
	 * @return
	 */
	public boolean whiteInCheck() {
		// TODO Auto-generated method stub
		return false;
	}
}
