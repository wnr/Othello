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
		List<NodeImpl> markedBorder = getMarkedBorder();

		for (NodeImpl borderNode : markedBorder) {
			for (NodeImpl adjacentNode : getNodeBorder(borderNode)) {
				if (!adjacentNode.isMarked() && !validNodes.contains(adjacentNode)
						&& !getSwaps(adjacentNode, playerId).isEmpty()) {
					validNodes.add(adjacentNode);
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
	 * Computes the number of swapped nodes if a move is made by given player to given
	 * node. This will not perform the actual node.
	 * 
	 * @param playerId The player that will make the move.
	 * @param nodeId The node that the player will move to.
	 * @return The number of nodes that will be swapped (not including the one moved to).
	 * @throws IllegalArgumentException if the potential move is not valid or the given node does not exist.
	 */
	public int getNumSwaps(String playerId, String nodeId) {
		NodeImpl node = board.getNode(nodeId);
		int numSwaps = getSwaps(node, playerId).size();

		if(numSwaps == 0) {
			throw new IllegalArgumentException("Invalid move");
		}

		return numSwaps;
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

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				swaps.addAll(getSwapsDirection(node, playerId, i, j));
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
	 * @param iStep The step the algorithm should take in the x-axis (-1, 0 or 1).
	 * @param jStep The step the algorithm should take in the y-axis (-1, 0, or 1).
	 * @return A list of all nodes swapped in the direction given by iStep and jStep, starting from given node and that
	 *         it is playerId that will occupy the start node.
	 */
	private List<NodeImpl> getSwapsDirection(NodeImpl node, String playerId, int iStep, int jStep) {
		List<NodeImpl> swaps = new LinkedList<NodeImpl>();

		for (int i = node.getXCoordinate() + iStep, j = node.getYCoordinate() + jStep; board.isInRange(i, j); i += iStep, j += jStep) {
			NodeImpl n = board.getNode(i, j);
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

	/**
	 * Gets all marked nodes that are adjacent to at least one unmarked node.
	 * 
	 * @return A list of all marked nodes that are adjacent to at least one unmarked node.
	 */
	private List<NodeImpl> getMarkedBorder() {
		List<NodeImpl> markedBorder = new LinkedList<NodeImpl>();

		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumCols(); j++) {
				NodeImpl node = board.getNode(i, j);
				if (isMarkedNodeAdjacentToUnmarkedNodes(node)) {
					markedBorder.add(node);
				}
			}
		}

		return markedBorder;
	}

	/**
	 * Tells if the given node is marked and if there is at least one unmarked node adjacent to it.
	 *
	 * @param node The given node to test.
	 * @return true if the given node is marked and has at least one unmarked nodes adjacent to it. Otherwise false.
	 */
	private boolean isMarkedNodeAdjacentToUnmarkedNodes(NodeImpl node) {
		if (!node.isMarked()) {
			return false;
		}

		List<NodeImpl> border = getNodeBorder(node);

		for (Node adjacentNode : border) {
			if (!adjacentNode.isMarked()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets all nodes adjacent to the given node.
	 *
	 * @param node The node to get the border of.
	 * @return A list of all nodes adjacent to the given node.
	 */
	private List<NodeImpl> getNodeBorder(NodeImpl node) {
		List<NodeImpl> border = new LinkedList<NodeImpl>();

		for (int i = node.getXCoordinate() - 1; i <= node.getXCoordinate() + 1; i += 1) {
			for (int j = node.getYCoordinate() - 1; j <= node.getYCoordinate() + 1; j += 1) {
				if (node.getXCoordinate() == i && node.getYCoordinate() == j) {
					continue;
				}

				if (board.isInRange(i, j)) {
					border.add(board.getNode(i, j));
				}
			}
		}

		return border;
	}
}
