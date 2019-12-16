import javafx.scene.*;

public class Board {

    public Cell[][] getBoard() {
        return board;
    }

    /**
     * The game board
     */
    private Cell[][] board;
    private final int SIZE = 8;

    /**
     * which player is playing 1 or 2
     */
    private int currentPlayer;

    /**
     * initialize the game board for playing
     */
    public Board() {
        board = new Cell[SIZE][SIZE];
        //place black pieces
        board[0][0] = new Cell(new RookPiece("bR", "a8")); // place rook
        board[0][1] = new Cell(new KnightPiece("bN", "b8")); // place knight
        board[0][2] = new Cell(new BishopPiece("bB", "c8")); // place bishop
        board[0][3] = new Cell(new QueenPiece("bQ", "d8")); // place queen
        board[0][4] = new Cell(new KingPiece("bK", "e8")); // place king
        board[0][5] = new Cell(new BishopPiece("bB", "f8")); // place bishop
        board[0][6] = new Cell(new KnightPiece("bN", "g8")); // place knight
        board[0][7] = new Cell(new RookPiece("bR", "h8")); // place rook
        for (int i = 0; i < SIZE; i++) {
            board[1][i] = new Cell(new PawnPiece("bp", (char) ('a' + i) + "7"));
        }
        //place white pieces
        board[7][0] = new Cell(new RookPiece("wR", "a1"));
        board[7][1] = new Cell(new KnightPiece("wN", "b1"));
        board[7][2] = new Cell(new BishopPiece("wB", "c1"));
        board[7][3] = new Cell(new QueenPiece("wQ", "d1"));
        board[7][4] = new Cell(new KingPiece("wK", "e1"));
        board[7][5] = new Cell(new BishopPiece("wB", "f1"));
        board[7][6] = new Cell(new KnightPiece("wN", "g1"));
        board[7][7] = new Cell(new RookPiece("wR", "h1"));
        for (int i = 0; i < SIZE; i++) {
            board[6][i] = new Cell(new PawnPiece("wp", (char) ('a' + i) + "2"));
        }

        currentPlayer = 1;
    }

    /**
     * change player playing
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * return player playing
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * This method is called to move a piece from start point to end point
     *
     * return true if the move is valid, otherwise false
     */
    public boolean move(String start, String end) {
        if (start == null || end == null) {
            return false;
        }
        if (start.length() != 2 || end.length() != 2) {
            return false;
        }
        if (!isValidPos(start) || !isValidPos(end)) {
            return false;
        }
        int colS = start.charAt(0) - 'a';
        int rowS = '8' - start.charAt(1);
        int colE = end.charAt(0) - 'a';
        int rowE = '8' - end.charAt(1);
        if (board[rowS][colS] == null) {
            return false;
        }
        if (currentPlayer == 1 && !board[rowS][colS].getChessPiece().getType().startsWith("w")) {
            return false;
        }
        if (currentPlayer == 2 && !board[rowS][colS].getChessPiece().getType().startsWith("b")) {
            return false;
        }
        boolean validMove = board[rowS][colS].getChessPiece().isValidMove(end);
        if (!validMove) {
            return false;
        }
        if (!checkCollisionSameType(start, end)) {
            return false;
        }
        if (!checkBetween(start, end)) {
            return false;
        }
        if (!checkPawn(start, end)) {
            return false;
        }
        board[rowE][colE] = board[rowS][colS];
        board[rowE][colE].getChessPiece().setPosition(end);
        board[rowS][colS] = null;
        isCheck();
        return true;
    }

    /**
     * this method check if on move there is collision of same color <br>
     * for example if we move black Rook from a7 to a6, and a6 has another black
     * piece then this is not valid move
     *
     * return true if no collision of same color, otherwise return false
     */
    private boolean checkCollisionSameType(String start, String end) {
        int colS = start.charAt(0) - 'a';
        int rowS = '8' - start.charAt(1);
        int colE = end.charAt(0) - 'a';
        int rowE = '8' - end.charAt(1);
        //if the end location is empty (then no collision)
        if (board[rowE][colE] == null) {
            return true;
        }
        //otherwise the end location is not empty
        //if start piece is black and end piece is black, then collision (return false)
        if (board[rowE][colE].getChessPiece().getType().startsWith("b")
                && board[rowS][colS].getChessPiece().getType().startsWith("b")) {
            return false;
        }
        //if start piece is white and end piece is white, then collision (return false)
        if (board[rowE][colE].getChessPiece().getType().startsWith("w")
                && board[rowS][colS].getChessPiece().getType().startsWith("w")) {
            return false;
        }
        return true;
    }

    /**
     * This method check if there is any pieces between the start and end point
     * for example if we move bishop from c1 to g4, check if any pieces in this
     * path. <br>
     * it won't be valid move if any pieces exist in move path
     *
     * return true if no pieces between the start and end location move,
     * otherwise false
     */
    private boolean checkBetween(String start, String end) {
        int colS = start.charAt(0) - 'a';
        int rowS = '8' - start.charAt(1);
        int colE = end.charAt(0) - 'a';
        int rowE = '8' - end.charAt(1);
        ChessPiece piece = board[rowS][colS].getChessPiece();
        String type = piece.getType().substring(1);
        //check the Bishop and Queen Diagonal move cells
        if (type.equals("B") || (type.equals("Q") && colS != colE)) {
            if (colS < colE && rowS < rowE) {
                for (int i = rowS + 1, j = colS + 1; i < rowE; i++, j++) { // if any cells not empty in path return false
                    if (board[i][j] != null) {
                        return false;
                    }
                }
            }
            if (colS < colE && rowS > rowE) {
                for (int i = rowS - 1, j = colS + 1; i > rowE; i--, j++) {
                    if (board[i][j] != null) {
                        return false;
                    }
                }
            }
            if (colS > colE && rowS < rowE) {
                for (int i = rowS + 1, j = colS - 1; i < rowE; i++, j--) {
                    if (board[i][j] != null) {
                        return false;
                    }
                }
            }
            if (colS > colE && rowS > rowE) {
                for (int i = rowS - 1, j = colS - 1; i > rowE; i--, j--) {
                    if (board[i][j] != null) {
                        return false;
                    }
                }
            }
        }
        //if Rook or Queen piece, check the vertical and horizontal moves
        if (type.equals("R") || (type.equals("Q") && colS == colE)) {
            if (colS == colE) {
                if (rowS > rowE) {
                    for (int i = rowS - 1; i > rowE; i--) {
                        if (board[i][colE] != null) {
                            return false;
                        }
                    }
                } else {
                    for (int i = rowS + 1; i < rowE; i++) {
                        if (board[i][colE] != null) {
                            return false;
                        }
                    }
                }
            } else if (rowS > rowE) {
                for (int j = colS - 1; j > colE; j--) {
                    if (board[rowS][j] != null) {
                        return false;
                    }
                }
            } else {
                for (int j = colS + 1; j < colE; j++) {
                    if (board[rowS][j] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method checks the Pawn move, vertical or diagonal<br>
     * vertical if no pieces on front of it, diagonal if enemy pieces on
     * diagonalF
     *
     * return true if move vertical or diagonal correct, otherwise false
     */
    private boolean checkPawn(String start, String end) {
        int colS = start.charAt(0) - 'a';
        int rowS = '8' - start.charAt(1);
        int colE = end.charAt(0) - 'a';
        int rowE = '8' - end.charAt(1);
        if (!(board[rowS][colS].getChessPiece().getType().substring(1)).equals("p")) {
            return true;
        }
        if (colS == colE) {
            if (board[rowE][colE] != null) {
                return false;
            }
        } else if (board[rowE][colE] == null) {
            return false;
        }
        return true;
    }

    /**
     * This method go over board to see if the white/black king is under attack
     */
    private void isCheck() {
        String whiteKingPos = "";
        String blackKingPos = "";
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] != null) {
                    if (board[row][col].getChessPiece() instanceof KingPiece) {
                        if (board[row][col].getChessPiece().getType().equals("bK")) {
                            blackKingPos = board[row][col].getChessPiece().getPosition();
                        } else {
                            whiteKingPos = board[row][col].getChessPiece().getPosition();
                        }
                    }
                }
            }
        }
        //check if one king is not found, if a king not found, this mean it's killed. end game
        if (whiteKingPos.isEmpty()) {
            System.out.println("Black wins");
            System.exit(0);
        }
        //check if black king is killed
        if (blackKingPos.isEmpty()) {
            System.out.println("White wins");
            System.exit(0);
        }
        //
        //go over the board and check for pieces that attack either kings
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                //if cell not empty
                if (board[row][col] != null) {
                    ChessPiece piece = board[row][col].getChessPiece();
                    String piecePos = piece.getPosition();
                    //if white piece
                    if (piece.getType().startsWith("w")) {
                        if (piece.isValidMove(blackKingPos)) {
                            if (checkBetween(piecePos, blackKingPos) && checkPawn(piecePos, blackKingPos)) {
                                if (isCheckmate(whiteKingPos, blackKingPos)) {
                                    System.out.println("Checkmate");
                                    System.out.println(toString());
                                    System.exit(0);
                                } else {
                                    System.out.println("Check");
                                }
                                return;
                            }
                        }
                    }//if black piece, check if it can attack king
                    else if (piece.isValidMove(whiteKingPos)) {
                        if (checkBetween(piecePos, whiteKingPos) && checkPawn(piecePos, whiteKingPos)) {
                            if (isCheckmate(whiteKingPos, blackKingPos)) {
                                System.out.println("Checkmate");
                                System.out.println(toString());
                                System.exit(0);
                            } else {
                                System.out.println("Check");
                            }
                            return;
                        }

                    }
                }
            }
        }
    }

    /**
     * check if check mate for both whiteKing position and blackKing position
     */
    private boolean isCheckmate(String whiteKingPos, String blackKingPos) {
        boolean isCheckmate = false;
        if (!whiteKingPos.isEmpty()) {
            //check the white king valid moves
            //get King location
            char colW = whiteKingPos.charAt(0);
            int rowW = whiteKingPos.charAt(1) - '0';
            //specify the King 8 correct moves for this location
            String pos[] = {
                String.valueOf((char) (colW) + "" + (rowW + 1)),
                String.valueOf((char) (colW + 1) + "" + (rowW + 1)),
                String.valueOf((char) (colW - 1) + "" + (rowW + 1)),
                String.valueOf((char) (colW) + "" + (rowW - 1)),
                String.valueOf((char) (colW + 1) + "" + (rowW - 1)),
                String.valueOf((char) (colW - 1) + "" + (rowW - 1)),
                String.valueOf((char) (colW + 1) + "" + (rowW)),
                String.valueOf((char) (colW - 1) + "" + (rowW))};
            for (int j = 0; j < pos.length; j++) {
                boolean isCheck = false;
                int colS = whiteKingPos.charAt(0) - 'a';
                int rowS = '8' - whiteKingPos.charAt(1);
                if (isValidPos(pos[j]) && isEmptyLocation(pos[j]) && board[rowS][colS].getChessPiece().isValidMove(pos[j])) {
                    for (int row = 0; row < SIZE; row++) {
                        for (int col = 0; col < SIZE; col++) {
                            if (board[row][col] != null) {
                                ChessPiece piece = board[row][col].getChessPiece();
                                String piecePos = piece.getPosition();
                                if (piece.getType().startsWith("b")) {
                                    if (piece.isValidMove(pos[j]) && checkBetween(piecePos, pos[j]) && checkPawn(piecePos, pos[j])) {
                                        isCheck = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (isCheck) {
                            isCheckmate = true;
                            break;
                        }
                    }
                    if (!isCheck) {
                        isCheckmate = false;
                        break;
                    }
                }
            }
            if (isCheckmate) {
                return true;
            }
        }
        //check the white king valid moves
        //get King location
        if (!blackKingPos.isEmpty()) {
            char colB = blackKingPos.charAt(0);
            int rowB = blackKingPos.charAt(1) - '0';
            //specify the King 8 correct moves for this location
            String posB[] = {
                String.valueOf((char) (colB) + "" + (rowB + 1)),
                String.valueOf((char) (colB + 1) + "" + (rowB + 1)),
                String.valueOf((char) (colB - 1) + "" + (rowB + 1)),
                String.valueOf((char) (colB) + "" + (rowB - 1)),
                String.valueOf((char) (colB + 1) + "" + (rowB - 1)),
                String.valueOf((char) (colB - 1) + "" + (rowB - 1)),
                String.valueOf((char) (colB + 1) + "" + (rowB)),
                String.valueOf((char) (colB - 1) + "" + (rowB))};
            isCheckmate = false;
            for (int j = 0; j < posB.length; j++) {
                boolean isCheck = false;
                //get king position
                int colS = blackKingPos.charAt(0) - 'a';
                //for possible positions, check if king can move to any to be safe
                int rowS = '8' - blackKingPos.charAt(1);
                if (isValidPos(posB[j]) && isEmptyLocation(posB[j]) && board[rowS][colS].getChessPiece().isValidMove(posB[j])) {
                    for (int row = 0; row < SIZE; row++) {
                        for (int col = 0; col < SIZE; col++) {
                            if (board[row][col] != null) {
                                ChessPiece piece = board[row][col].getChessPiece();
                                String piecePos = piece.getPosition();
                                if (piece.getType().startsWith("w")) {
                                    if (piece.isValidMove(posB[j]) && checkBetween(piecePos, posB[j]) && checkPawn(piecePos, posB[j])) {
                                        isCheck = true;
                                        break;
                                    }
                                }
                            }
                        }
                        if (isCheck) {
                            isCheckmate = true;
                            break;
                        }
                    }
                    if (!isCheck) {
                        return false;
                    }
                }
            }
        }
        return isCheckmate;
    }

    /**
     * check if this position inside the board
     *
     * return true is pos inside the board, otherwise false
     */
    private boolean isValidPos(String pos) {
        if (pos.charAt(0) < 'a' || pos.charAt(0) > 'h') {
            return false;
        }
        if (pos.charAt(1) < '1' || pos.charAt(1) > '8') {
            return false;
        }
        return true;
    }

    /**
     * check if board is empty at location pos
     *
     * return true if board empty at pos, otherwise false
     */
    private boolean isEmptyLocation(String pos) {
        int col = pos.charAt(0) - 'a';
        int row = '8' - pos.charAt(1);
        return board[row][col] == null;
    }

    /**
     * Castle King/Rook and return true if valid castle
     *
     * return boolean
     */
    public boolean castle(String pos) {
        if (currentPlayer == 1) {
            if (board[7][4] == null) // if king not in place
            {
                return false;
            }
            if (isCheckmate("e1", "")) // if check mate can't castle
            {
                return false;
            }
            if (pos.equals("g")) {
                if (board[7][7] == null) // right rook not in place
                {
                    return false;
                }
                if (board[7][6] != null || board[7][5] != null) {
                   return false;
               }
                return castleHelper(7, 4, 7, 7);
            } else {
                if (board[7][0] == null) // left rook not in place
                {
                    return false;
                }
                if (board[7][1] != null || board[7][2] != null || board[7][3] != null) {
                    return false;
                }
                return castleHelper(7, 4, 7, 0);
            }
        } else {
            if (board[0][4] == null) // king not in place
            {
                return false;
            }
            if (isCheckmate("", "e8")) // if check mate can't castle
            {
                return false;
            }
            if (pos.equals("g")) {
                if (board[0][7] == null) // right rook not in place
                {
                    return false;
                }
                if (board[0][6] != null || board[0][5] != null) {
                    return false;
                }
                return castleHelper(0, 4, 0, 7);
            } else {
                if (board[0][0] == null) // left rook not in place
                {
                    return false;
                }
                if (board[0][1] != null || board[0][2] != null || board[0][3] != null) {
                    return false;
                }
                return castleHelper(0, 4, 0, 0);
            }
        }
    }

    /**
     * Castle move helper method
     */
    private boolean castleHelper(int kingR, int kingC, int rookR, int rookC) {
        KingPiece king = (KingPiece) (board[kingR][kingC].getChessPiece());
        RookPiece rook = (RookPiece) (board[rookR][rookC].getChessPiece());
        //if either piece moved
        if (king.hasMoved() || rook.hasMoved()) {
            return false;
        }
        if (kingC > rookC) {
            if (kingR == 0) {
                board[kingR][2] = board[kingR][kingC];
                board[kingR][2].getChessPiece().setPosition("c8");
                board[rookR][3] = board[rookR][rookC];
                board[rookR][3].getChessPiece().setPosition("d8");
            } else {
                board[kingR][2] = board[kingR][kingC];
                board[kingR][2].getChessPiece().setPosition("c1");
                board[rookR][3] = board[rookR][rookC];
                board[rookR][3].getChessPiece().setPosition("d1");
            }
        } else {
            if (kingR == 0) {
                board[kingR][6] = board[kingR][kingC];
                board[kingR][6].getChessPiece().setPosition("g8");
                board[rookR][5] = board[rookR][rookC];
                board[rookR][5].getChessPiece().setPosition("f8");
            } else {
                board[kingR][6] = board[kingR][kingC];
                board[kingR][6].getChessPiece().setPosition("g1");
                board[rookR][5] = board[rookR][rookC];
                board[rookR][5].getChessPiece().setPosition("f1");

            }
        }
        board[kingR][kingC] = null;
        board[rookR][rookC] = null;
        return true;
    }

    /**
     * return String to represent the Game board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=============================================\n");
        sb.append("  |  a |  b |  c |  d |  e |  f |  g |  h |\n");
        sb.append("-- ---- ---- ---- ---- ---- ---- ---- ----\n");
        for (int row = 0; row < SIZE; row++) {
            sb.append(8 - row);
            sb.append(" | ");
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == null) {
                    sb.append("   | ");
                } else {
                    sb.append(board[row][col] + " | ");
                }
            }
            sb.append(8 - row + "\n");
            sb.append("-- ---- ---- ---- ---- ---- ---- ---- ----\n");
        }
        sb.append("  |  a |  b |  c |  d |  e |  f |  g |  h |\n");
        return sb.toString();
    }

}
