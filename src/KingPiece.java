
public class KingPiece extends ChessPiece {

    /**
     * initialize the King Piece with type (bK) and position
     */
    public KingPiece(String type, String position) {
        super(type, position);
    }

    /**
     * Check if this King move is valid <br>
     * King moves one cell in all directions (there is 8 valid moves)
     *
     * return true if move is valid
     */
    @Override
    public boolean isValidMove(String moveTo) {
        String start = getPosition();
        char col = start.charAt(0);
        int row = start.charAt(1) - '0';
        //specify the King 8 correct moves for this location
        String pos[] = {
            String.valueOf((char) (col) + "" + (row + 1)),
            String.valueOf((char) (col + 1) + "" + (row + 1)),
            String.valueOf((char) (col - 1) + "" + (row + 1)),
            String.valueOf((char) (col) + "" + (row - 1)),
            String.valueOf((char) (col + 1) + "" + (row - 1)),
            String.valueOf((char) (col - 1) + "" + (row - 1)),
            String.valueOf((char) (col + 1) + "" + (row)),
            String.valueOf((char) (col - 1) + "" + (row))};
        //check if end location matches one of the valid moves for this King
        for (int j = 0; j < pos.length; j++) {
            if (pos[j].equals(moveTo)) {
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
