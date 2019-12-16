
public class Cell {

    /**
     * current piece on this cell, could be null to represent empty cell
     */
    private ChessPiece piece;

    /**
     * initialize empty cell
     */
    public Cell() {
        piece = null;
    }

    /**
     * initialize cell with Piece
     */
    public Cell(ChessPiece p) {
        this.piece = p;
    }

    /**
     * return current Piece on this cell (if null returned this means that cell
     * is empty)
     */
    public ChessPiece getChessPiece() {
        return piece;
    }

    /**
     * Change the Piece on this cell (if piece is null means that cell will be
     * empty)
     */
    public void setChessPiece(ChessPiece piece) {
        this.piece = piece;
    }

    /**
     * Cell is empty if no piece on it
     *
     * return true if no piece on cell, otherwise false
     */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * return String to represent this Piece
     */
    @Override
    public String toString() {
        return piece.toString();
    }

}
