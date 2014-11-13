package kth.game.othello.board;

public class OthelloBoardFactory {

	public static OthelloBoard createOthelloBoard() {
		int width = 8;
		int height = 8;
		NodeImpl[][] board = new NodeImpl[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				board[x][y] = new NodeImpl(null, x, y);
			}
		}
		return new OthelloBoard(width, height, board);
	}

}
