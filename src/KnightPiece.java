
public class KnightPiece extends ChessPiece {

    /**
     * initialize the Knight Piece with type (bN) and position
     */
    public KnightPiece(String type, String position) {
        super(type, position);
    }

    /**
     * Check if this Knight move is valid <br>
     * Knight moves in L shape (there is 8 different L shapes a Knight can move)
     *
     * return true if move is valid
     */
    @Override
    public boolean isValidMove(String moveTo) {
        String start = getPosition();
        char col = start.charAt(0);
        int row = start.charAt(1) - '0';
        //specify the 8 L correct Knight moves for this pawn
        String validPositions[] = {
            String.valueOf((char) (col + 2) + "" + (row + 1)),
            String.valueOf((char) (col + 2) + "" + (row - 1)),
            String.valueOf((char) (col - 2) + "" + (row + 1)),
            String.valueOf((char) (col - 2) + "" + (row - 1)),
            String.valueOf((char) (col + 1) + "" + (row + 2)),
            String.valueOf((char) (col + 1) + "" + (row - 2)),
            String.valueOf((char) (col - 1) + "" + (row + 2)),
            String.valueOf((char) (col - 1) + "" + (row - 2))};
        //if the end position is one of the 8 correct moves, then valid move
        for (int j = 0; j < validPositions.length; j++) {
            if (validPositions[j].equals(moveTo)) {
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
