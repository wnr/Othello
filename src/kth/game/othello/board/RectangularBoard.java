package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

/**
 * A board implementation storing nodes on a rectangular grid.
 *
 * @author Erik Odenman
 * @author Lucas Wiener
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
	 * Gets the node given a node id.
	 *
	 * @param nodeId The id of the node to return.
	 * @return The node that has the given node id.
	 * @throws java.lang.IllegalArgumentException if no node with the id exist in the board
	 */
	public NodeImpl getNode(String nodeId) {
		for (NodeImpl node : board) {
			if (node.getId().equals(nodeId)) {
				return node;
			}
		}

		throw new IllegalArgumentException("The node with id " + nodeId + " does not exist in the board.");
	}

	/**
	 * Checks if the given x and y coordinates are in range of the board.
	 *
	 * @param x The x-coordinate to check.
	 * @param y The y-coordinate to check.
	 * @return true if the x and y coordinates are in range. Otherwise false.
	 */
	public boolean isInRange(int x, int y) {
		return x >= 0 && x < numRows && y >= 0 && y < numCols;
	}

	@Override
	public List<Node> getNodes() {
		return new ArrayList<Node>(board);
	}

	@Override
	public String toString() {
		String s = "";

		String first = null;

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Node n = getNode(i, j);

				if (n.getOccupantPlayerId() == null) {
					s += "#";
				} else {
					if (first == null) {
						first = n.getOccupantPlayerId();
					}

					if (first.equals(n.getOccupantPlayerId())) {
						s += "x";
					} else {
						s += "o";
					}
				}
			}
			s += "\n";
		}

		return s;
	}

	/**
	 * Checks so that the x and y coordinates are in range of the board.
	 *
	 * @param x the x-coordinate to check
	 * @param y the y-coordinate to check
	 * @throws java.lang.IllegalArgumentException if the coordinates are outside the board
	 */
	private void rangeCheck(int x, int y) {
		if (!isInRange(x, y)) {
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
