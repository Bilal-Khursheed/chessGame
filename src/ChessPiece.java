
public abstract class ChessPiece {

    /**
     * the type of chess piece (rook,bishop,king...etc)
     */
    private String type;

    /**
     * current position for this piece
     */
    private String position;

    /**
     * used for castling only if piece has moved
     */
    private boolean hasMoved;

    /**
     * initialize the chess piece type and position
     */
    public ChessPiece(String type, String position) {
        this.type = type;
        this.position = position;
    }

    /**
     * return current piece position
     */
    public String getPosition() {
        return position;
    }

    /**
     * return current piece type
     */
    public String getType() {
        return type;
    }

    /**
     * change the piece position
     */
    public void setPosition(String position) {
        hasMoved = true;
        this.position = position;
    }

    /**
     * Used for castling only if piece King/Rook has moved
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * abstract method to check if a move on this piece is valid (implemented in
     * sub-classes)
     *
     * return true if valid move, otherwise false
     */
    public abstract boolean isValidMove(String moveTo);

    /**
     * return String representing this piece using it's type
     */
    @Override
    public String toString() {
        return type;
    }

}
