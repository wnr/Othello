package kth.game.othello.board;

/**
 * A handler implementing common actions on an othello board
 */
public class OthelloBoardHandler {

	private RectangularBoard board;

	public OthelloBoardHandler(RectangularBoard board) {
		this.board = board;
	}

	public RectangularBoard getBoard() {
		return board;
	}

	/**
	 * Occupies four nodes in the middle of the board. Assumes that the board has a square shape with even side length.
	 * 
	 * @param firstPlayerID The id of the starting player
	 * @param secondPlayerID The id of the player going second
	 */
	public void initializeStartingPositions(String firstPlayerID, String secondPlayerID) {
		int mid = board.getWidth() / 2 - 1;
		board.setNode(mid, mid, board.getNode(mid, mid).occupy(secondPlayerID));
		board.setNode(mid, mid + 1, board.getNode(mid, mid + 1).occupy(firstPlayerID));
		board.setNode(mid + 1, mid, board.getNode(mid + 1, mid).occupy(firstPlayerID));
		board.setNode(mid + 1, mid + 1, board.getNode(mid + 1, mid + 1).occupy(secondPlayerID));
	}

}
