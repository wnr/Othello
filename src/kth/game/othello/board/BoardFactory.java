package kth.game.othello.board;

/**
 * A factory for producing Othello Boards.
 *
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class BoardFactory {
	/**
	 * Creates an quadratic Othello board of size 8.
	 *
	 * @return an Othello Board of size 8
	 */
	public static RectangularBoard createOthelloBoard() {
		return new RectangularBoard(8, 8);
	}

}
