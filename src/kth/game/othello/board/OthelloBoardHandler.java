package kth.game.othello.board;

/**
 * A handler implementing common actions on an othello board.
 *
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class OthelloBoardHandler {
	private RectangularBoard board;

	/**
	 * Constructs a Othello board handler. The board should be a square shape with even side length.
	 *
	 * @param board The board to handle.
	 */
	public OthelloBoardHandler(RectangularBoard board) {
		this.board = board;
	}

	/**
	 * Gets the board that the handler is operating on.
	 *
	 * @return The board that the handler is operating on.
	 */
	public RectangularBoard getBoard() {
		return board;
	}

	/**
	 * Occupies four nodes in the middle of the board (two each for the two players).
	 * 
	 * @param firstPlayerID The id of the starting player
	 * @param secondPlayerID The id of the player going second
	 */
	public void initializeStartingPositions(String firstPlayerID, String secondPlayerID) {
		int mid = (board.getNumCols() / 2) - 1;
		board.getNode(mid, mid).setOccupantPlayerId(secondPlayerID);
		board.getNode(mid, mid + 1).setOccupantPlayerId(firstPlayerID);
		board.getNode(mid + 1, mid).setOccupantPlayerId(firstPlayerID);
		board.getNode(mid + 1, mid + 1).setOccupantPlayerId(secondPlayerID);
	}
}
