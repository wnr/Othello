package kth.game.othello.board;

import java.util.LinkedList;
import java.util.List;

/**
 * A handler implementing common actions on an othello board. The board handler is responsible of mutating the board.
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
	 * @param firstPlayerId The id of the starting player
	 * @param secondPlayerId The id of the player going second
	 */
	public void initializeStartingPositions(String firstPlayerId, String secondPlayerId) {
		int mid = (board.getNumCols() / 2) - 1;
		board.getNode(mid, mid).setOccupantPlayerId(secondPlayerId);
		board.getNode(mid, mid + 1).setOccupantPlayerId(firstPlayerId);
		board.getNode(mid + 1, mid).setOccupantPlayerId(firstPlayerId);
		board.getNode(mid + 1, mid + 1).setOccupantPlayerId(secondPlayerId);
	}

	/**
	 * Get valid nodes that can be moved to by the given player id.
	 *
	 * @param playerId The player that should be checked for valid moves
	 * @return All valid nodes the player move to
	 */
	public List<Node> getValidMoves(String playerId) {
		List<Node> validNodes = new LinkedList<Node>();

		for (int x = 0; x < board.getNumRows(); x++) {
			for (int y = 0; y < board.getNumCols(); y++) {
				NodeImpl node = board.getNode(x, y);
				if (isValidMove(node,playerId)) {
					validNodes.add(node);
				}
			}
		}

		return validNodes;
	}

	/**
	 * Makes a move in the Othello board by occupying the given node id with the player given the player id. Will occupy
	 * the given node and swap all possible nodes. If the move is invalid, an exception is thrown.
	 * 
	 * @param playerId The player id to occupy the given node.
	 * @param nodeId The node id to be occupied by the given player.
	 * @return A list of swapped nodes as a result of the move (including the given placed node).
	 * @throws IllegalArgumentException if the move is not valid.
	 */
	public List<Node> move(String playerId, String nodeId) {
		NodeImpl node = board.getNode(nodeId);
		List<NodeImpl> swaps = getSwaps(node, playerId);

		if (swaps.isEmpty()) {
			throw new IllegalArgumentException("The move is invalid.");
		}

		swaps.add(node);

		for (NodeImpl n : swaps) {
			n.setOccupantPlayerId(playerId);
		}

		return new LinkedList<Node>(swaps);
	}

	/**
	 * Determines if the given player can make a move by occupying the specified node.
	 * @param node The node to occupy
	 * @param playerId The player who tries to move
	 * @return True if the given move is valid
	 */
	private boolean isValidMove(NodeImpl node, String playerId) {
		return !node.isMarked() && !getSwaps(node, playerId).isEmpty();
	}

	/**
	 * Computes the nodes that will be swapped if the player moves to the given node. It will compute all swapped nodes
	 * in all directions from the given node.
	 *
	 * @param node The node that the given player will occupy.
	 * @param playerId The player id that will occupy the given node.
	 * @return A list of all nodes swapped in all directions starting from the given node and that it is playerId that
	 *         will occupy the starting node. The list is empty if it is an invalid move (no swaps possible).
	 */
	private List<NodeImpl> getSwaps(NodeImpl node, String playerId) {
		List<NodeImpl> swaps = new LinkedList<NodeImpl>();

		for (int xStep = -1; xStep <= 1; xStep++) {
			for (int yStep = -1; yStep <= 1; yStep++) {
				swaps.addAll(getSwapsInDirection(node, playerId, xStep, yStep));
			}
		}

		return swaps;
	}

	/**
	 * Computes a list of nodes that will be swapped if the given player will move to the given node. The algorithm will
	 * start from the given node and step in the given x and y direction. If no swaps are possible the method will
	 * return an empty list. Otherwise the list of the swapped nodes (except the start node).
	 *
	 * @param node The node that the player will occupy. This will be the start node.
	 * @param playerId The player id that will occupy the given node.
	 * @param xStep The step the algorithm should take in the x-axis (-1, 0 or 1).
	 * @param yStep The step the algorithm should take in the y-axis (-1, 0, or 1).
	 * @return A list of all nodes swapped in the direction given by xStep and yStep, starting from given node and that
	 *         it is playerId that will occupy the start node.
	 */
	private List<NodeImpl> getSwapsInDirection(NodeImpl node, String playerId, int xStep, int yStep) {
		List<NodeImpl> swaps = new LinkedList<NodeImpl>();

		for (int x = node.getXCoordinate() + xStep, y = node.getYCoordinate() + yStep; board.isInRange(x, y); x += xStep, y += yStep) {
			NodeImpl n = board.getNode(x, y);
			if (!n.isMarked()) {
				return new LinkedList<NodeImpl>();
			}

			if (n.getOccupantPlayerId().equals(playerId)) {
				return swaps;
			}

			swaps.add(n);
		}

		return new LinkedList<NodeImpl>();
	}
}
