package kth.game.othello.board;

/**
 * Responsible of creating OthelloBoardHandler instances.
 */

public class OthelloBoardHandlerFactory {
	/**
	 * Creates an OthelloBoardHandler instance given a Board instance.
	 *
	 * @param board The board instance to handle.
	 * @return An OthelloBoardHandler instance to handle the given board.
	 */
	public OthelloBoardHandler createOthelloBoardHandler(RectangularBoard board) {
		return new OthelloBoardHandler(board);
	}
}
