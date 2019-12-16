
public class RookPiece extends ChessPiece {

    /**
     * initialize the Rook Piece with type (bR) and position
     */
    public RookPiece(String type, String position) {
        super(type, position);
    }

    /**
     * Check if this Rook move is valid <br>
     * Rook moves on vertical or horizontal line
     * return true if move is valid
     */
    @Override
    public boolean isValidMove(String moveTo) {
        String start = getPosition();
        char colS = start.charAt(0);
        int rowS = start.charAt(1) - '0';
        char colE = moveTo.charAt(0);
        int rowE = moveTo.charAt(1) - '0';
        if (colS - colE != 0 && rowS == rowE) {
            return true;
        }
        if (colS == colE && rowS - rowE != 0) {
            return true;
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
