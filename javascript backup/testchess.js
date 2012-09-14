//test for chess.js


function ChessBoard(boardState){
    //boardState is a string representing the current board state to load
    //see function setBoardState for description of what the string looks like
    this.boardState = boardState;
    var ChessPiece = {
        "whiteking":1, "whitequeen":2, "whitebishop":3, "whiteknight":4, "whiterook":5, "whitepawn":6, "blackking":7, "blackqueen":8, "blackbishop":9, "blackknight":10, "blackrook":11, "blackpawn":12, "empty":13
    }
    var blackKingX;
    var blackKingY;
    var whiteKingX;
    var whiteKingY;
    var pawnEPPosition;
    var EPLastMove;
    var whiteKingHasntMoved;
    var leftWhiteRookHasntMoved;
    var rightWhiteRookHasntMoved;
    var blackKingHasntMoved;
    var leftBlackRookHasntMoved;
    var rightBlackRookHasntMoved;
    var prev_chessPieces;
    var prev_blackKingX;
    var prev_blackKingY;
    var prev_whiteKingX;
    var prev_whiteKingY;
    var prev_EPLastMove;
    var prev_whiteKingHasntMoved;
    var prev_leftWhiteRookHasntMoved;
    var prev_rightWhiteRookHasntMoved;
    var prev_blackKingHasntMoved;
    var prev_leftBlackRookHasntMoved;
    var prev_rightBlackRookHasntMoved;
    
    pawnEPPosition = -1;
    EPLastMove = false;
    whiteKingHasntMoved = true;
    leftWhiteRookHasntMoved = true;
    rightWhiteRookHasntMoved = true;
    blackKingHasntMoved = true;
    leftBlackRookHasntMoved = true;
    rightBlackRookHasntMoved = true;
    
    //initalise the board
    var chessPieces = new Array(8);
    for(var i = 0; i < 8; i++){
        chessPieces[i] = new Array(8);
        for (var j = 0; j < 8; j++) {
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
    if(this.boardState != null && this.boardState.length == 64) {
        this.setBoardState(this.boardState, chessPieces, ChessPiece);
    } else {
        this.boardState = this.initialiseBoardState();
    }
//end of code that runs as part of constructor?
//rest is function declarations
this.move = function(startX, startY, endX, endY){
    if (startX < 0 || startX > 7 || startY < 0 || startY > 7 || endX < 0 || endX > 7 || endY < 0 || endY > 7) {
        return false;
    }
    if (startX == endX && startY == endY) {
        return false;
    }
    var currentPiece = chessPieces[startX][startY];
    if ((currentPiece.toString().indexOf("white") != -1 && chessPieces[endX][endY].toString().indexOf("white") != -1) || (currentPiece.toString().indexOf("black") != -1 && chessPieces[endX][endY].toString().indexOf("black") != -1)) {
        return false;
    }
    if (chessPieces[endX][endY].toString().indexOf("king") != -1) {
        return false;
    }
    this.copyVariables();
    switch (currentPiece) {
    case ChessPiece.empty:
        return false;
    case ChessPiece.whiteking:
        if (!checkWhiteKing(startX, startY, endX, endY)) {
            return false;
        }
        whiteKingHasntMoved = false;
        whiteKingX = endX;
        whiteKingY = endY;
        //castle left side
        if(startX == 0 && startY == 4 && endX == 0 && endY == 2) {
            chessPieces[0][0] = ChessPiece.empty;
            chessPieces[0][3] = ChessPiece.whiterook;
        }
        //castle right side
        if(startX == 0 && startY == 4 && endX == 0 && endY == 6) {
            chessPieces[0][7] = ChessPiece.empty;
            chessPieces[0][5] = ChessPiece.whiterook;
        }
        break;
    case ChessPiece.whitequeen:
        if (!checkWhiteQueen(startX, startY, endX, endY)) {
            return false;
        }
        break;
    case ChessPiece.whitebishop:
        if (!checkWhiteBishop(startX, startY, endX, endY)) {
            return false;
        }
        break;
    case ChessPiece.whiteknight:
        if (!checkWhiteKnight(startX, startY, endX, endY)) {
            return false;
        }
        break;
    case ChessPiece.whiterook:
        if (!checkWhiteRook(startX, startY, endX, endY)) {
            return false;
        }
        if (startX == 0 && startY == 0) {
            leftWhiteRookHasntMoved = false;
        }
        if (startX == 0 && startY == 7) {
            rightWhiteRookHasntMoved = false;
        }
        break;
    case ChessPiece.whitepawn:
        if (!this.checkWhitePawn(startX, startY, endX, endY)) {
            return false;
        }
        if (endX - startX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY] == ChessPiece.empty && EPLastMove && endY == pawnEPPosition && endX == 5) {
            chessPieces[endX - 1][endY] = ChessPiece.empty;
        }
        pawnEPPosition = -1;
        EPLastMove = false;
        if (startX == 1 && endX == 3) {
            pawnEPPosition = startY;
            EPLastMove = true;
        }
        break;
    case ChessPiece.blackking:
        if (!this.checkBlackKing(startX, startY, endX, endY)) {
            return false;
        }
        blackKingHasntMoved = false;
        blackKingX = endX;
        blackKingY = endY;
        //castle left side
        if(startX == 7 && startY == 4 && endX == 7 && endY == 2) {
            chessPieces[7][0] = ChessPiece.empty;
            chessPieces[7][3] = ChessPiece.blackrook;
        }
        //castle right side
        if(startX == 7 && startY == 4 && endX == 7 && endY == 6) {
            chessPieces[7][7] = ChessPiece.empty;
            chessPieces[7][5] = ChessPiece.blackrook;
        }
        break;
    case ChessPiece.blackqueen:
        if (!this.checkBlackQueen(startX, startY, endX, endY)) {
            return false;
        }
        break;
    case ChessPiece.blackbishop:
        if (!this.checkBlackBishop(startX, startY, endX, endY)) {
                return false;
        }
        break;
    case ChessPiece.blackknight:
        if (!this.checkBlackKnight(startX, startY, endX, endY)) {
            return false;
        }
        break;
    case ChessPiece.blackrook:
        if (!this.checkBlackRook(startX, startY, endX, endY)) {
            return false;
        }
        if (startX == 7 && startY == 0) {
            leftBlackRookHasntMoved = false;
        }
        if (startX == 7 && startY == 7) {
            rightBlackRookHasntMoved = false;
        }
        break;
    case ChessPiece.blackpawn:
        if (!this.checkBlackPawn(startX, startY, endX, endY)) {
            return false;
        }
        if (startX - endX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY] == ChessPiece.empty && EPLastMove && endY == pawnEPPosition && endX == 2) {
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
    this.boardState = this.saveBoardState(chessPieces, ChessPiece);
    return true;
}

ChessBoard.prototype.copyVariables = function(){
    prev_chessPieces = new Array(8);
    for (var i = 0; i < 8; i++) {
        prev_chessPieces[i] = new Array(8);
        for (var j = 0; j < 8; j++) {
            prev_chessPieces[i][j] = chessPieces[i][j];
        }
    }
    prev_blackKingX = blackKingX;
    prev_blackKingY = blackKingY;
    prev_whiteKingX = whiteKingX;
    prev_whiteKingY = whiteKingY;
    prev_pawnEPPosition = pawnEPPosition;
    prev_EPLastMove = EPLastMove;
    prev_whiteKingHasntMoved = whiteKingHasntMoved;
    prev_leftWhiteRookHasntMoved = leftWhiteRookHasntMoved;
    prev_rightWhiteRookHasntMoved = rightWhiteRookHasntMoved;
    prev_blackKingHasntMoved = blackKingHasntMoved;
    prev_leftBlackRookHasntMoved = leftBlackRookHasntMoved;
    prev_rightBlackRookHasntMoved = rightBlackRookHasntMoved;
}

ChessBoard.prototype.checkBlackPawn = function(startX, startY, endX, endY) {
    return (startX - endX == 1 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty || (startX == 6 && endX == 4 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty && chessPieces[endX + 1][endY] == ChessPiece.empty) || (startX - endX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY] == "white")) || (startX - endX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY] == ChessPiece.empty && EPLastMove == true && endY == pawnEPPosition && endX == 2);
}

ChessBoard.prototype.checkBlackRook = function(startX, startY, endX, endY) {
    return ((startX == endX || startY == endY) && (this.checkPathBlackRook(startX, startY, endX, endY)));
}

ChessBoard.prototype.checkPathBlackRook = function(startX, startY, endX, endY) {
    var endPiece = chessPieces[endX][endY].toString();
    if (endPiece.contains("black")) {
        return false;
    }
    if (startX > endX) {
        var temp = startX;
        startX = endX;
        endX = temp;
    }
    if (startY > endY) {
        var temp = startY;
        startY = endY;
        endY = temp;
    }
    if (startX == endX) {
        for (var i = startY + 1; i < endY - 1; i++) {
            if (chessPieces[startX][i] != ChessPiece.empty) {
                return false;
            }
        }
    } else {
        for (var i = startX + 1; i < endX - 1; i++) {
            if (chessPieces[i][startY] != ChessPiece.empty) {
                return false;
            }
        }
    }
    return true;
}

ChessBoard.prototype.checkBlackKnight = function(startX, startY, endX, endY) {
    return (((Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) || (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2)) && !chessPiece[endX][endY] == "black");
}

ChessBoard.prototype.checkBlackBishop = function(startX, startY, endX, endY) {
    return ((Math.abs(startX - endX) == Math.abs(startY - endY)) && (this.checkPathBlackBishop(startX, startY, endX, endY)));
}

ChessBoard.prototype.checkPathBlackBishop = function(startX, startY, endX, endY) {
    var endPiece = chessPieces[endX][endY].toString();
    if (endPiece.contains("black")) {
	return false;
    }
    if ((startX < endX && startY < endY) || (startX > endX && startY > endY)) {
	if (startX > endX) {
	    var j = endY + 1;
	    for (var i = endX + 1; i < startX; i++) {
		if (chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
	    }
	} else {
	    var j = startY + 1;
	    for (var i = startX + 1; i < endX; i++) {
		if (chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
	    }
	}
    } else {
	if (startX < endX) {
            var j = startY - 1;
	    for (var i = startX + 1; i < endX; i++) {
		if (chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j--;
	    }
	} else {
	    var j = startY + 1;
	    for (var i = startX - 1; i > endX; i--) {
		if (chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
	    }
	}
    }
    return true;
}

ChessBoard.prototype.checkBlackQueen = function(startX, startY, endX, endY) {
    if (startX == endX || startY == endY) {
	return this.checkPathBlackRook(startX, startY, endX, endY);
    } else if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
	return this.checkPathBlackBishop(startX, startY, endX, endY);
    } else {
	return false;
    }
}

ChessBoard.prototype.checkBlackKing = function(startX, startY, endX, endY) {
    if (startX == 7 && startY == 4 && endX == 7 && endY == 2 && blackKingHasntMoved && leftBlackRookHasntMoved) {
	return this.castleLeftBlack();
    }
    if (startX == 7 && startY == 4 && endX == 7 && endY == 6 && blackKingHasntMoved && rightBlackRookHasntMoved) {
	return this.castleRightBlack();
    }
    return Math.abs(startX - endX) <= 1 && Math.abs(startY - endY) <= 1 && !chessPieces[endX][endY] == "black" && (Math.abs(blackKingX - whiteKingX) > 1 || Math.abs(blackKingY - whiteKingY) > 1);
}

ChessBoard.prototype.castleRightBlack = function() {
    return chessPieces[7][5] == ChessPiece.empty && chessPieces[7][6] == ChessPiece.empty && !this.blackInCheck(7, 5) && !this.blackInCheck(7, 6);
}

ChessBoard.prototype.castleLeftBlack = function() {
    return chessPieces[7][3] == ChessPiece.empty && chessPieces[7][2] == ChessPiece.empty && chessPieces[7][1] == ChessPiece.empty && !this.blackInCheck(7, 3) && !this.blackInCheck(7, 2);
}

ChessBoard.prototype.checkWhitePawn = function(startX, startY, endX, endY) {
    return (endX - startX == 1 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty) || (startX == 1 && endX == 3 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty && chessPieces[endX - 1][endY] == ChessPiece.empty) || (endX - startX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY] == "black") || (endX - startX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY] == ChessPiece.empty && EPLastMove && endY == pawnEPPosition && endX == 5);
}

ChessBoard.prototype.checkWhiteRook = function(startX, startY, endX, endY) {
    return ((startX == endX || startY == endY) && (this.checkPathWhiteRook(startX, startY, endX, endY)));
}

ChessBoard.prototype.checkPathWhiteRook = function(startX, startY, endX, endY) {
    var endPiece = chessPieces[endX][endY].toString();
    if (endPiece.contains("white")) {
	return false;
    }
    if (startX > endX) {
	var temp = startX;
	startX = endX;
	endX = temp;
    }
    if (startY > endY) {
	var temp = startY;
	startY = endY;
	endY = temp;
    }
    if (startX == endX) {
	for (var i = startY + 1; i < endY - 1; i++) {
	    if (chessPieces[startX][i] != ChessPiece.empty) {
		return false;
	    }
        }
    } else {
	for (var i = startX + 1; i < endX - 1; i++) {
	    if (chessPieces[i][startY] != ChessPiece.empty) {
		return false;
	    }
	}
    }
    return true;
}

ChessBoard.prototype.checkWhiteKnight = function(startX, startY, endX, endY) {
    return (((Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) || (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2)) && !chessPieces[endX][endY] == "white");
}

ChessBoard.prototype.checkWhiteBishop = function(startX, startY, endX, endY) {
    return ((Math.abs(startX - endX) == Math.abs(startY - endY)) && (this.checkPathWhiteBishop(startX, startY, endX, endY)));
}

ChessBoard.prototype.checkPathWhiteBishop = function(startX, startY, endX, endY) {
    var endPiece = chessPieces[endX][endY].toString();
    if (endPiece.contains("white")) {
	return false;
    }
    if ((startX < endX && startY < endY) || (startX > endX && startY > endY)) {
	if (startX > endX) {
	    var j = endY + 1;
	    for (var i = endX + 1; i < startX; i++) {
		if (chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
            }
	} else {
	    var j = startY + 1;
	    for (var i = startX + 1; i < endX; i++) {
		if (chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
	    }
	}
    } else {
	if (startX < endX) {
	    var j = startY - 1;
	    for (var i = startX + 1; i < endX; i++) {
		if (chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j--;
	    }
	} else {
	    var j = startY + 1;
	    for (var i = startX - 1; i > endX; i--) {
                if (chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
	    }
	}
    }
    return true;
}

ChessBoard.prototype.checkWhiteQueen = function(startX, startY, endX, endY) {
    if (startX == endX || startY == endY) {
	return this.checkPathWhiteRook(startX, startY, endX, endY);
    } else if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
	return this.checkPathWhiteBishop(startX, startY, endX, endY);
    } else {
	return false;
    }
}

ChessBoard.prototype.checkWhiteKing = function(startX, startY, endX, endY) {
    if (startX == 0 && startY == 4 && endX == 0 && endY == 2 && whiteKingHasntMoved && leftWhiteRookHasntMoved) {
	return this.castleLeftWhite();
    }
    if (startX == 0 && startY == 4 && endX == 0 && endY == 6 && whiteKingHasntMoved && rightWhiteRookHasntMoved) {
	return this.castleRightWhite();
    }
    return Math.abs(startX - endX) <= 1 && Math.abs(startY - endY) <= 1 && !chessPieces[endX][endY] == "white" && (Math.abs(blackKingX - whiteKingX) > 1 || Math.abs(blackKingY - whiteKingY) > 1);
}

ChessBoard.prototype.castleRightWhite = function() {
    return chessPieces[0][5] == ChessPiece.empty && chessPieces[0][6] == ChessPiece.empty && !this.whiteInCheck(0, 5) && !this.whiteInCheck(0, 6);
}

ChessBoard.prototype.castleLeftWhite = function() {
    return chessPieces[0][3] == ChessPiece.empty && chessPieces[0][2] == ChessPiece.empty && chessPieces[0][1] == ChessPiece.empty && !this.whiteInCheck(0, 3) && !this.whiteInCheck(0, 2);
}

ChessBoard.prototype.getPiece = function(xPos, yPos) {
    return chessPieces[xPos][yPos];
}

ChessBoard.prototype.blackInCheck = function(xPos, yPos) {
    for (var i = 0; i < 8; i++) {
	for (var j = 0; j < 8; j++) {
	    var currentPiece = chessPieces[i][j];
	    switch (currentPiece) {
	    case ChessPiece.whitequeen:
		if (this.checkWhiteQueen(i, j, xPos, yPos)) {
		    return true;
		}
		break;
	    case ChessPiece.whitebishop:
		if (this.checkWhiteBishop(i, j, xPos, yPos)) {
		    return true;
		}
		break;
            case ChessPiece.whiteknight:
                if (this.checkWhiteKnight(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case ChessPiece.whiterook:
                if (this.checkWhiteRook(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case ChessPiece.whitepawn:
                if (j != 0 && j != 7) {
                    if ((i + 1 == xPos && j + 1 == yPos) || (i + 1 == xPos && j - 1 == yPos)) {
                        return true;
                    }
                } else if (j == 0) {
                    if (i + 1 == xPos && j + 1 == yPos) {
                        return true;
                    }
                } else {
                    if (i + 1 == xPos && j - 1 == yPos) {
                        return true;
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

ChessBoard.prototype.whiteInCheck = function(xPos, yPos) {
    for (var i = 0; i < 8; i++) {
	for (var j = 0; j < 8; j++) {
	    var currentPiece = chessPieces[i][j];
            switch (currentPiece) {
            case ChessPiece.blackqueen:
                if (this.checkBlackQueen(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case ChessPiece.blackbishop:
                if (this.checkBlackBishop(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case ChessPiece.blackknight:
                if (this.checkBlackKnight(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case ChessPiece.blackrook:
                if (this.checkBlackRook(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case ChessPiece.blackpawn:
                if (j != 0 && j != 7) {
                    if ((i - 1 == xPos && j + 1 == yPos) || (i - 1 == xPos && j - 1 == yPos)) {
                        return true;
                    }
                } else if (j == 0) {
                    if (i - 1 == xPos && j + 1 == yPos) {
                        return true;
                    }
                } else {
                    if (i - 1 == xPos && j - 1 == yPos) {
                        return true;
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
    
ChessBoard.prototype.revertMove = function() {
    chessPieces = new Array(8);
    for (var i = 0; i < 8; i++) {
        chessPieces[i] = new Array(8);
        for (var j = 0; j < 8; j++) {
            chessPieces[i][j] = prev_chessPieces[i][j];
        }
    }
    blackKingX = prev_blackKingX;
    blackKingY = prev_blackKingY;
    whiteKingX = prev_whiteKingX;
    whiteKingY = prev_whiteKingY;
    pawnEPPosition = prev_pawnEPPosition;
    EPLastMove = prev_EPLastMove;
    whiteKingHasntMoved = prev_whiteKingHasntMoved;
    leftWhiteRookHasntMoved = prev_leftWhiteRookHasntMoved;
    rightWhiteRookHasntMoved = prev_rightWhiteRookHasntMoved;
    blackKingHasntMoved = prev_blackKingHasntMoved;
    leftBlackRookHasntMoved = prev_leftBlackRookHasntMoved;
    rightBlackRookHasntMoved = prev_rightBlackRookHasntMoved;
}

ChessBoard.prototype.printBoard = function() {
    var r = "";
    r += ("  " + 0 + "  " + 1 + "  " + 2 + "  " + 3 + "  " + 4 + "  " + 5 + "  " + 6 + "  " + 7);
    for (var i = 8 - 1; i >= 0; i--) {
        r = "";
	r += (i + " ");
	for (var j = 0; j < 8; j++) {
	    var currentPiece = chessPieces[i][j];
	    switch (currentPiece)
            {
	    case ChessPiece.whiteking:
                    r+=("WK ");
                    break;
            case ChessPiece.whitequeen:
                    r+=("WQ ");
                    break;
            case ChessPiece.whitebishop:
                    r+=("WB ");
                    break;
            case ChessPiece.whiteknight:
                    r+=("Wk ");
                    break;
            case ChessPiece.whiterook:
                    r+=("WR ");
                    break;
            case ChessPiece.whitepawn:
                    r+=("WP ");
                    break;
            case ChessPiece.blackking:
                    r+=("BK ");
                    break;
            case ChessPiece.blackqueen:
                    r+=("BQ ");
                    break;
            case ChessPiece.blackbishop:
                    r+=("BB ");
                    break;
            case ChessPiece.blackknight:
                    r+=("Bk ");
                    break;
            case ChessPiece.blackrook:
                    r+=("BR ");
                    break;
            case ChessPiece.blackpawn:
                    r+=("BP ");
                    break;
            case ChessPiece.empty:
                    r+=("E  ");
                    break;
            }
        }
	console.log(r);
    }
    r = "";
    r+=("  " + 0 + "  " + 1 + "  " + 2 + "  " + 3 + "  " + 4 + "  " + 5 + "  " + 6 + "  " + 7);
    console.log(r);
}
}//end of chess "class"

//String will have characters with no seperation. upper case for black lower for white
//K king, Q queen, B bishop, N knight, R rook, P pawn
//use method charAt() on the string
ChessBoard.prototype.setBoardState = function(boardState, chessPieces, ChessPiece){
    for(var i = 0; i < 8; i++){
        for (var j = 0; j < 8; j++) {
            var nextPos = i * 8 + j;
            var nextChar = boardState.charAt(nextPos);
            switch(nextChar)
            {
                case 'K':
                    chessPieces[i][j] = ChessPiece.blackking;
                    break;
                case 'Q':
                    chessPieces[i][j] = ChessPiece.blackqueen;
                    break;
                case 'B':
                    chessPieces[i][j] = ChessPiece.blackbishop;
                    break;
                case 'N':
                    chessPieces[i][j] = ChessPiece.blackknight;
                    break;
                case 'R':
                    chessPieces[i][j] = ChessPiece.blackrook;
                    break;
                case 'P':
                    chessPieces[i][j] = ChessPiece.blackpawn;
                    break;
                case 'k':
                    chessPieces[i][j] = ChessPiece.whiteking;
                    break;
                case 'q':
                    chessPieces[i][j] = ChessPiece.whitequeen;
                    break;
                case 'b':
                    chessPieces[i][j] = ChessPiece.whitebishop;
                    break;
                case 'n':
                    chessPieces[i][j] = ChessPiece.whiteknight;
                    break;
                case 'r':
                    chessPieces[i][j] = ChessPiece.whiterook;
                    break;
                case 'p':
                    chessPieces[i][j] = ChessPiece.whitepawn;
                    break;
                case 'e':
                    chessPieces[i][j] = ChessPiece.empty;
                    break;
                default:
                    console.log("something is wrong" + i + ", " + j + ", " + nextChar);
                    break;
            }
        }
    }
    //console.log(boardState);
}

ChessBoard.prototype.saveBoardState = function(chessPieces, ChessPiece){
    var boardState = "";
    for(var i = 0; i < 8; i++){
        for (var j = 0; j < 8; j++) {
            var nextPiece = chessPieces[i][j];
            switch(nextPiece)
            {
                case ChessPiece.blackking:
                    boardState = boardState + 'K';
                    break;
                case ChessPiece.blackqueen:
                    boardState = boardState + 'Q';
                    break;
                case ChessPiece.blackbishop:
                    boardState = boardState + 'B';
                    break;
                case ChessPiece.blackknight:
                    boardState = boardState + 'N';
                    break;
                case ChessPiece.blackrook:
                    boardState = boardState + 'R';
                    break;
                case ChessPiece.blackpawn:
                    boardState = boardState + 'P';
                    break;
                case ChessPiece.whiteking:
                    boardState = boardState + 'k';
                    break;
                case ChessPiece.whitequeen:
                    boardState = boardState + 'q';
                    break;
                case ChessPiece.whitebishop:
                    boardState = boardState + 'b';
                    break;
                case ChessPiece.whiteknight:
                    boardState = boardState + 'n';
                    break;
                case ChessPiece.whiterook:
                    boardState = boardState + 'r';
                    break;
                case ChessPiece.whitepawn:
                    boardState = boardState + 'p';
                    break;
                case ChessPiece.empty:
                    boardState = boardState + 'e';
                    break;
                default:
                    console.log("something is wrong!" + i + ", " + j + ", " + nextPiece.toString());
                    break;
            }
        }
    }
    //console.log(boardState);
    return boardState;
}

ChessBoard.prototype.initialiseBoardState = function(){
    var newBoardState;
    newBoardState = "rnbqkbkrppppppppeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeePPPPPPPPRNBQKBNR";
    return newBoardState;
}

