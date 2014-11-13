package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

/**
 * A board implementation storing nodes on a rectangular grid.
 */
public class RectangularBoard implements Board {

	private int width;
	private int height;
	private NodeImpl[][] board;

	/**
	 * Constructs a new Othello board with the given dimensions and nodes.
	 * 
	 * @param width The width of the board
	 * @param height The height of the board
	 * @param board The nodes on the board
	 */
	public RectangularBoard(int width, int height, NodeImpl[][] board) {
		this.width = width;
		this.height = height;
		this.board = board;
	}

	/**
	 * Place a node on a specified position.
	 * 
	 * @param x The x-coordinate of the node
	 * @param y The y-coordinate of the node
	 * @param node The node to place
	 * @throws java.lang.IllegalArgumentException if the coordinates are outside the board
	 */
	public void setNode(int x, int y, NodeImpl node) {
		rangeCheck(x, y);
		board[x][y] = node;
	}

	/**
	 * Get the node on a specified position.
	 * 
	 * @param x The x-coordinate of the node
	 * @param y The y-coordinate of the node
	 * @throws java.lang.IllegalArgumentException if the coordinates are outside the board
	 */
	public NodeImpl getNode(int x, int y) {
		rangeCheck(x, y);
		return board[x][y];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * Get all the nodes on the board, ordered by x-value and then y-value.
	 * 
	 * @return A list containing all the nodes on the board
	 */
	public List<Node> getNodes() {
		List<Node> res = new ArrayList<Node>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				res.add(board[x][y]);
			}
		}
		return res;
	}

	private void rangeCheck(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			throw new IllegalArgumentException("Invalid board position: (" + x + "," + y + ")");
		}
	}

}
