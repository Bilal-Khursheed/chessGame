
public class PawnPiece extends ChessPiece {

    /**
     * initialize the Pawn Piece with type (bp) and position
     */
    public PawnPiece(String type, String position) {
        super(type, position);
    }

    /**
     * Check if this Pawn move is valid <br>
     * Pawn moves up/down one cell in 3 directions<br>
     *
     * P.S: this method is not 100% accurate because it check pawn move in 3
     * direction.<br>
     * for example if pawn at c2, this method will consider the moves c3,b3,d3
     * valid moves.<br>
     * how ever the pawn moves is handled in more detailed in Board.java class.
     * so for now we just check if it move 1 cell in correct direction.<br>
     * later in Board.java class a method call checkPawn() will handle this 3
     * moves in more accurate way
     *
     * return true if move is valid
     */
    @Override
    public boolean isValidMove(String moveTo) {
        String start = getPosition();
        char col = start.charAt(0);
        int row = start.charAt(1) - '0';
        //if black pawn (move down)
        if (getType().equals("bp")) {
            String pos[] = {String.valueOf((char) (col) + "" + (row - 2)),
                String.valueOf((char) (col) + "" + (row - 1)),
                String.valueOf((char) (col + 1) + "" + (row - 1)),
                String.valueOf((char) (col - 1) + "" + (row - 1))};
            if (pos[1].equals(moveTo) || pos[2].equals(moveTo) || pos[3].equals(moveTo)) {
                return true;
            }
            if (row == 7 && pos[0].equals(moveTo)) {
                return true;
            }
        }//otherwise, a white pawn (move up)
        else {
            String pos[] = {String.valueOf((char) (col) + "" + (row + 2)),
                String.valueOf((char) (col) + "" + (row + 1)),
                String.valueOf((char) (col + 1) + "" + (row + 1)),
                String.valueOf((char) (col - 1) + "" + (row + 1))};
            if (pos[1].equals(moveTo) || pos[2].equals(moveTo) || pos[3].equals(moveTo)) {
                return true;
            }
            if (row == 2 && pos[0].equals(moveTo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Change the Piece position
     */
    @Override
    public void setPosition(String position) {
        super.setPosition(position);
    }

    /**
     * return String to represent this piece
     */
    @Override
    public String toString() {
        return super.toString();
    }

}
