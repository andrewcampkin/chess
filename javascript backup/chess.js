//not being used now, also out of date.
function ChessBoard(){
    var chessPiece = {
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
        this.chessPieces[i] = new Array(8);
        for (var j = 0; j < 8; j++) {
	    this.chessPieces[i][j] = ChessPiece.empty;
	}
    }

    this.chessPieces[0][0] = ChessPiece.whiterook;
    this.chessPieces[0][1] = ChessPiece.whiteknight;
    this.chessPieces[0][2] = ChessPiece.whitebishop;
    this.chessPieces[0][3] = ChessPiece.whitequeen;
    this.chessPieces[0][4] = ChessPiece.whiteking;
    this.chessPieces[0][5] = ChessPiece.whitebishop;
    this.chessPieces[0][6] = ChessPiece.whiteknight;
    this.chessPieces[0][7] = ChessPiece.whiterook;

    this.chessPieces[1][0] = ChessPiece.whitepawn;
    this.chessPieces[1][1] = ChessPiece.whitepawn;
    this.chessPieces[1][2] = ChessPiece.whitepawn;
    this.chessPieces[1][3] = ChessPiece.whitepawn;
    this.chessPieces[1][4] = ChessPiece.whitepawn;
    this.chessPieces[1][5] = ChessPiece.whitepawn;
    this.chessPieces[1][6] = ChessPiece.whitepawn;
    this.chessPieces[1][7] = ChessPiece.whitepawn;

    this.chessPieces[7][0] = ChessPiece.blackrook;
    this.chessPieces[7][1] = ChessPiece.blackknight;
    this.chessPieces[7][2] = ChessPiece.blackbishop;
    this.chessPieces[7][3] = ChessPiece.blackqueen;
    this.chessPieces[7][4] = ChessPiece.blackking;
    this.chessPieces[7][5] = ChessPiece.blackbishop;
    this.chessPieces[7][6] = ChessPiece.blackknight;
    this.chessPieces[7][7] = ChessPiece.blackrook;

    this.chessPieces[6][0] = ChessPiece.blackpawn;
    this.chessPieces[6][1] = ChessPiece.blackpawn;
    this.chessPieces[6][2] = ChessPiece.blackpawn;
    this.chessPieces[6][3] = ChessPiece.blackpawn;
    this.chessPieces[6][4] = ChessPiece.blackpawn;
    this.chessPieces[6][5] = ChessPiece.blackpawn;
    this.chessPieces[6][6] = ChessPiece.blackpawn;
    this.chessPieces[6][7] = ChessPiece.blackpawn;
    this.blackKingX = 7;
    this.blackKingY = 4;
    this.whiteKingX = 0;
    this.whiteKingY = 4;
}
ChessBoard.prototype.move = function(startX, startY, endX, endY){
    if (startX < 0 || startX > 7 || startY < 0 || startY > 7 || endX < 0
                    || endX > 7 || endY < 0 || endY > 7) {
            return false;
    }
    if (startX == endX && startY == endY) {
            return false;
    }
    var currentPiece = this.chessPieces[startX][startY];
    if ((currentPiece.toString().contains("white") && this.chessPieces[endX][endY].toString().contains("white")) || (currentPiece.toString().contains("black") && this.chessPieces[endX][endY]                                    .toString().contains("black"))) {
            return false;
    }
    if (this.chessPieces[endX][endY].toString().contains("king")) {
            return false;
    }
    copyVariables();
    switch (currentPiece) {
    case empty:
        return false;
    case whiteking:
        if (!checkWhiteKing(startX, startY, endX, endY)) {
            return false;
        }
        this.whiteKingHasntMoved = false;
        this.whiteKingX = endX;
        this.whiteKingY = endY;
        //castle left side
        if(startX == 0 && startY == 4 && endX == 0 && endY == 2) {
            this.chessPieces[0][0] = ChessPiece.empty;
            this.chessPieces[0][3] = ChessPiece.whiterook;
        }
        //castle right side
        if(startX == 0 && startY == 4 && endX == 0 && endY == 6) {
            this.chessPieces[0][7] = ChessPiece.empty;
            this.chessPieces[0][5] = ChessPiece.whiterook;
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
        if (startX == 0 && startY == 0) {
            this.leftWhiteRookHasntMoved = false;
        }
        if (startX == 0 && startY == 7) {
            this.rightWhiteRookHasntMoved = false;
        }
        break;
    case whitepawn:
        if (!checkWhitePawn(startX, startY, endX, endY)) {
            return false;
        }
        if (endX - startX == 1 && Math.abs(startY - endY) == 1 && this.chessPieces[endX][endY] == ChessPiece.empty && this.EPLastMove && endY == this.pawnEPPosition && endX == 5) {
            this.chessPieces[endX - 1][endY] = ChessPiece.empty;
        }
        this.pawnEPPosition = -1;
        EPLastMove = false;
        if (startX == 1 && endX == 3) {
            this.pawnEPPosition = startY;
            this.EPLastMove = true;
        }
        break;
    case blackking:
        if (!checkBlackKing(startX, startY, endX, endY)) {
            return false;
        }
        this.blackKingHasntMoved = false;
        this.blackKingX = endX;
        this.blackKingY = endY;
        //castle left side
        if(startX == 7 && startY == 4 && endX == 7 && endY == 2) {
            this.chessPieces[7][0] = ChessPiece.empty;
            this.chessPieces[7][3] = ChessPiece.blackrook;
        }
        //castle right side
        if(startX == 7 && startY == 4 && endX == 7 && endY == 6) {
            this.chessPieces[7][7] = ChessPiece.empty;
            this.chessPieces[7][5] = ChessPiece.blackrook;
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
        if (startX == 7 && startY == 0) {
            this.leftBlackRookHasntMoved = false;
        }
        if (startX == 7 && startY == 7) {
            this.rightBlackRookHasntMoved = false;
        }
        break;
    case blackpawn:
        if (!checkBlackPawn(startX, startY, endX, endY)) {
            return false;
        }
        if (startX - endX == 1 && Math.abs(startY - endY) == 1 && this.chessPieces[endX][endY] == ChessPiece.empty && this.EPLastMove && endY == this.pawnEPPosition && endX == 2) {
            this.chessPieces[endX + 1][endY] = ChessPiece.empty;
        }
        this.pawnEPPosition = -1;
        this.EPLastMove = false;
        if (startX == 6 && endX == 4) {
            this.pawnEPPosition = startY;
            this.EPLastMove = true;
        }
        break;
    default:
        return false;
    }
    this.chessPieces[endX][endY] = this.chessPieces[startX][startY];
    this.chessPieces[startX][startY] = this.ChessPiece.empty;
    return true;
}

ChessBoard.prototype.copyVariables = function(){
    this.prev_chessPieces = new Array(8);
    for (var i = 0; i < 8; i++) {
        this.prev_chessPieces[i] = new Array(8);
        for (var j = 0; j < 8; j++) {
            this.prev_chessPieces[i][j] = chessPieces[i][j];
        }
    }
    this.prev_blackKingX = blackKingX;
    this.prev_blackKingY = blackKingY;
    this.prev_whiteKingX = whiteKingX;
    this.prev_whiteKingY = whiteKingY;
    this.prev_pawnEPPosition = pawnEPPosition;
    this.prev_EPLastMove = EPLastMove;
    this.prev_whiteKingHasntMoved = whiteKingHasntMoved;
    this.prev_leftWhiteRookHasntMoved = leftWhiteRookHasntMoved;
    this.prev_rightWhiteRookHasntMoved = rightWhiteRookHasntMoved;
    this.prev_blackKingHasntMoved = blackKingHasntMoved;
    this.prev_leftBlackRookHasntMoved = leftBlackRookHasntMoved;
    this.prev_rightBlackRookHasntMoved = rightBlackRookHasntMoved;
}

ChessBoard.prototype.checkBlackPawn = function(startX, startY, endX, endY) {
    return (startX - endX == 1 && startY == endY && this.chessPieces[endX][endY] == ChessPiece.empty) || (startX == 6 && endX == 4 && startY == endY && this.chessPieces[endX][endY] == ChessPiece.empty && this.chessPieces[endX + 1][endY] == ChessPiece.empty) || (startX - endX == 1 && Math.abs(startY - endY) == 1 && this.chessPieces[endX][endY].toString().contains("white")) || (startX - endX == 1 && Math.abs(startY - endY) == 1 && this.chessPieces[endX][endY] == ChessPiece.empty && this.EPLastMove == true && endY == this.pawnEPPosition && endX == 2);
}

ChessBoard.prototype.checkBlackRook = function(startX, startY, endX, endY) {
    return ((startX == endX || startY == endY) && (checkPathBlackRook(startX, startY, endX, endY)));
}

ChessBoard.prototype.checkPathBlackRook = function(startX, startY, endX, endY) {
    var endPiece = this.chessPieces[endX][endY].toString();
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
            if (this.chessPieces[startX][i] != ChessPiece.empty) {
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
    return (((Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) || (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2)) && !this.chessPiece[endX][endY].toString().contains("black"));
}

ChessBoard.prototype.checkBlackBishop = function(startX, startY, endX, endY) {
    return ((Math.abs(startX - endX) == Math.abs(startY - endY)) && (checkPathBlackBishop(startX, startY, endX, endY)));
}

ChessBoard.prototype.checkPathBlackBishop = function(startX, startY, endX, endY) {
    var endPiece = this.chessPieces[endX][endY].toString();
    if (endPiece.contains("black")) {
	return false;
    }
    if ((startX < endX && startY < endY) || (startX > endX && startY > endY)) {
	if (startX > endX) {
	    var j = endY + 1;
	    for (var i = endX + 1; i < startX; i++) {
		if (this.chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
	    }
	} else {
	    var j = startY + 1;
	    for (var i = startX + 1; i < endX; i++) {
		if (this.chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
	    }
	}
    } else {
	if (startX < endX) {
            var j = startY - 1;
	    for (var i = startX + 1; i < endX; i++) {
		if (this.chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j--;
	    }
	} else {
	    var j = startY + 1;
	    for (var i = startX - 1; i > endX; i--) {
		if (this.chessPieces[i][j] != ChessPiece.empty) {
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
	return checkPathBlackRook(startX, startY, endX, endY);
    } else if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
	return checkPathBlackBishop(startX, startY, endX, endY);
    } else {
	return false;
    }
}

ChessBoard.prototype.checkBlackKing = function(startX, startY, endX, endY) {
    if (startX == 7 && startY == 4 && endX == 7 && endY == 2 && this.blackKingHasntMoved && this.leftBlackRookHasntMoved) {
	return castleLeftBlack();
    }
    if (startX == 7 && startY == 4 && endX == 7 && endY == 6 && this.blackKingHasntMoved && this.rightBlackRookHasntMoved) {
	return castleRightBlack();
    }
    return Math.abs(startX - endX) <= 1 && Math.abs(startY - endY) <= 1 && !chessPieces[endX][endY].toString().contains("black") && (Math.abs(blackKingX - whiteKingX) > 1 || Math.abs(blackKingY - whiteKingY) > 1);
}

ChessBoard.prototype.castleRightBlack = function() {
    return this.chessPieces[7][5] == ChessPiece.empty && chessPieces[7][6] == ChessPiece.empty && !this.blackInCheck(7, 5) && !this.blackInCheck(7, 6);
}

ChessBoard.prototype.castleLeftBlack = function() {
    return this.chessPieces[7][3] == ChessPiece.empty && this.chessPieces[7][2] == ChessPiece.empty && this.chessPieces[7][1] == ChessPiece.empty && !this.blackInCheck(7, 3) && !this.blackInCheck(7, 2);
}

ChessBoard.prototype.checkWhitePawn = function(startX, startY, endX, endY) {
    return (endX - startX == 1 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty) || (startX == 1 && endX == 3 && startY == endY && chessPieces[endX][endY] == ChessPiece.empty && chessPieces[endX - 1][endY] == ChessPiece.empty) || (endX - startX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY].toString().contains("black")) || (endX - startX == 1 && Math.abs(startY - endY) == 1 && chessPieces[endX][endY] == ChessPiece.empty && EPLastMove && endY == pawnEPPosition && endX == 5);
}

ChessBoard.prototype.checkWhiteRook = function(startX, startY, endX, endY) {
    return ((startX == endX || startY == endY) && (checkPathWhiteRook(startX, startY, endX, endY)));
}

ChessBoard.prototype.checkPathWhiteRook = function(startX, startY, endX, endY) {
    var endPiece = this.chessPieces[endX][endY].toString();
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
	    if (this.chessPieces[startX][i] != ChessPiece.empty) {
		return false;
	    }
        }
    } else {
	for (var i = startX + 1; i < endX - 1; i++) {
	    if (this.chessPieces[i][startY] != ChessPiece.empty) {
		return false;
	    }
	}
    }
    return true;
}

ChessBoard.prototype.checkWhiteKnight = function(startX, startY, endX, endY) {
    return (((Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) || (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2)) && !this.chessPieces[endX][endY].toString().contains("white"));
}

ChessBoard.prototype.checkWhiteBishop = function(startX, startY, endX, endY) {
    return ((Math.abs(startX - endX) == Math.abs(startY - endY)) && (checkPathWhiteBishop(startX, startY, endX, endY)));
}

ChessBoard.prototype.checkPathWhiteBishop = function(startX, startY, endX, endY) {
    var endPiece = this.chessPieces[endX][endY].toString();
    if (endPiece.contains("white")) {
	return false;
    }
    if ((startX < endX && startY < endY) || (startX > endX && startY > endY)) {
	if (startX > endX) {
	    var j = endY + 1;
	    for (var i = endX + 1; i < startX; i++) {
		if (this.chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
            }
	} else {
	    var j = startY + 1;
	    for (var i = startX + 1; i < endX; i++) {
		if (this.chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j++;
	    }
	}
    } else {
	if (startX < endX) {
	    var j = startY - 1;
	    for (var i = startX + 1; i < endX; i++) {
		if (this.chessPieces[i][j] != ChessPiece.empty) {
		    return false;
		}
		j--;
	    }
	} else {
	    var j = startY + 1;
	    for (var i = startX - 1; i > endX; i--) {
                if (this.chessPieces[i][j] != ChessPiece.empty) {
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
	return checkPathWhiteRook(startX, startY, endX, endY);
    } else if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
	return checkPathWhiteBishop(startX, startY, endX, endY);
    } else {
	return false;
    }
}

ChessBoard.prototype.checkWhiteKing = function(startX, startY, endX, endY) {
    if (startX == 0 && startY == 4 && endX == 0 && endY == 2 && this.whiteKingHasntMoved && this.leftWhiteRookHasntMoved) {
	return castleLeftWhite();
    }
    if (startX == 0 && startY == 4 && endX == 0 && endY == 6 && this.whiteKingHasntMoved && this.rightWhiteRookHasntMoved) {
	return castleRightWhite();
    }
    return Math.abs(startX - endX) <= 1 && Math.abs(startY - endY) <= 1 && !this.chessPieces[endX][endY].toString().contains("white") && (Math.abs(this.blackKingX - this.whiteKingX) > 1 || Math.abs(this.blackKingY - this.whiteKingY) > 1);
}

ChessBoard.prototype.castleRightWhite = function() {
    return this.chessPieces[0][5] == ChessPiece.empty && this.chessPieces[0][6] == ChessPiece.empty && !whiteInCheck(0, 5) && !whiteInCheck(0, 6);
}

ChessBoard.prototype.castleLeftWhite = function() {
    return this.chessPieces[0][3] == ChessPiece.empty && this.chessPieces[0][2] == ChessPiece.empty && this.chessPieces[0][1] == ChessPiece.empty && !whiteInCheck(0, 3) && !whiteInCheck(0, 2);
}

ChessBoard.prototype.getPiece = function(xPos, yPos) {
    return this.chessPieces[xPos][yPos];
}

ChessBoard.prototype.blackInCheck = function(xPos, yPos) {
    for (var i = 0; i < 8; i++) {
	for (var j = 0; j < 8; j++) {
	    var currentPiece = this.chessPieces[i][j];
	    switch (currentPiece) {
	    case whitequeen:
		if (checkWhiteQueen(i, j, xPos, yPos)) {
		    return true;
		}
		break;
	    case whitebishop:
		if (checkWhiteBishop(i, j, xPos, yPos)) {
		    return true;
		}
		break;
            case whiteknight:
                if (checkWhiteKnight(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case whiterook:
                if (checkWhiteRook(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case whitepawn:
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
	    var currentPiece = this.chessPieces[i][j];
            switch (currentPiece) {
            case blackqueen:
                if (checkBlackQueen(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case blackbishop:
                if (checkBlackBishop(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case blackknight:
                if (checkBlackKnight(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case blackrook:
                if (checkBlackRook(i, j, xPos, yPos)) {
                    return true;
                }
                break;
            case blackpawn:
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
    this.chessPieces = new Array(8);
    for (var i = 0; i < 8; i++) {
        this.chessPieces[i] = new Array(8);
        for (var j = 0; j < 8; j++) {
            this.chessPieces[i][j] = this.prev_chessPieces[i][j];
        }
    }
    this.blackKingX = this.prev_blackKingX;
    this.blackKingY = this.prev_blackKingY;
    this.whiteKingX = this.prev_whiteKingX;
    this.whiteKingY = this.prev_whiteKingY;
    this.pawnEPPosition = this.prev_pawnEPPosition;
    this.EPLastMove = this.prev_EPLastMove;
    this.whiteKingHasntMoved = this.prev_whiteKingHasntMoved;
    this.leftWhiteRookHasntMoved = this.prev_leftWhiteRookHasntMoved;
    this.rightWhiteRookHasntMoved = this.prev_rightWhiteRookHasntMoved;
    this.blackKingHasntMoved = this.prev_blackKingHasntMoved;
    this.leftBlackRookHasntMoved = this.prev_leftBlackRookHasntMoved;
    this.rightBlackRookHasntMoved = this.prev_rightBlackRookHasntMoved;
}

ChessBoard.prototype.printBoard = function() {
    console.log("  " + 0 + "  " + 1 + "  " + 2 + "  " + 3 + "  " + 4 + "  " + 5 + "  " + 6 + "  " + 7);
    for (var i = 8 - 1; i >= 0; i--) {
	console.log(i + " ");
	for (var j = 0; j < 8; j++) {
	    var currentPiece = this.chessPieces[i][j];
	    switch (currentPiece)
            {
	    case whiteking:
                    console.log("WK ");
                    break;
            case whitequeen:
                    console.log("WQ ");
                    break;
            case whitebishop:
                    console.log("WB ");
                    break;
            case whiteknight:
                    console.log("Wk ");
                    break;
            case whiterook:
                    console.log("WR ");
                    break;
            case whitepawn:
                    console.log("WP ");
                    break;
            case blackking:
                    console.log("BK ");
                    break;
            case blackqueen:
                    console.log("BQ ");
                    break;
            case blackbishop:
                    console.log("BB ");
                    break;
            case blackknight:
                    console.log("Bk ");
                    break;
            case blackrook:
                    console.log("BR ");
                    break;
            case blackpawn:
                    console.log("BP ");
                    break;
            case empty:
                    console.log("E  ");
                    break;
            }
        }
	console.log("");
    }
    console.log("  " + 0 + "  " + 1 + "  " + 2 + "  " + 3 + "  " + 4 + "  " + 5 + "  " + 6 + "  " + 7 + "\n");
}