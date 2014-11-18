package kth.game.othello.board;

/**
 * Responsible of creating OthelloBoardHandler instances.
 */

public class OthelloBoardHandlerFactory {
	/**
	 * Creates an OthelloBoardHandler instance given a Board instance.
	 *
	 * @param board The board instance to handle.
	 * @return An OthelloBoardhandler instance to handle the given board.
	 */
	public OthelloBoardHandler createOthelloBoardHandler(Board board) {
		// TODO: Change this to something more suitable when the new BoardImpl class exists. Then OthelloBoardHandler
		// TODO: should only need an BoardImpl as input.
		return new OthelloBoardHandler((RectangularBoard) board);
	}
}
