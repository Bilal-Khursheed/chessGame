
public class QueenPiece extends ChessPiece {

    /**
     * initialize the Queen Piece with type (bQ) and position
     */
    public QueenPiece(String type, String position) {
        super(type, position);
    }

    /**
     * Check if this Queen move is valid <br>
     * Queen moves on vertical, horizontal or diagonal line
     *
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
        if (colS - colE == rowS - rowE) {
            return true;
        }
        if (colS - colE == rowE - rowS) {
            return true;
        }
        if (colE - colS == rowS - rowE) {
            return true;
        }
        if (colE - colS == rowE - rowS) {
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
