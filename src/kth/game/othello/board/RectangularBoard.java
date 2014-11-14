package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

/**
 * A board implementation storing nodes on a rectangular grid.
 */
public class RectangularBoard implements Board {
	private int numRows;
	private int numCols;
	private ArrayList<NodeImpl> board;

	/**
	 * Constructs a new Othello board with the given dimensions and fills it with un-occupied nodes.
	 * 
	 * @param numRows The width of the board
	 * @param numCols The height of the board
	 */
	public RectangularBoard(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		board = new ArrayList<NodeImpl>(getNumNodes());

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				board.add(new NodeImpl(i, j));
			}
		}
	}

	@Override
	public List<Node> getNodes() {
		return new ArrayList<Node>(board);
	}

	/**
	 * The number of nodes in the board.
	 *
	 * @return the number of nodes
	 */
	public int getNumNodes() {
		return numRows * numCols;
	}

	/**
	 * The number of rows of the board.
	 *
	 * @return the number of rows
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * The number of columns of the board.
	 *
	 * @return the number of columns
	 */
	public int getNumCols() {
		return numCols;
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
		return board.get(getNodeIndex(x, y));
	}

	/**
	 * Checks so that the x and y coordinates are in range of the board.
	 *
	 * @param x the x-coordinate to check
	 * @param y the y-coordinate to check
	 * @throws java.lang.IllegalArgumentException if the coordinates are outside the board
	 */
	private void rangeCheck(int x, int y) {
		int index = getNodeIndex(x, y);

		if (index < 0 || index >= getNumNodes()) {
			throw new IllegalArgumentException("Invalid board position: (" + x + "," + y + ")");
		}
	}

	/**
	 * Gets the list index of the node by x and y coordinates.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @return the index of the list which responds to the given x and y coordinates
	 */
	private int getNodeIndex(int x, int y) {
		return x * numCols + y;
	}
}
