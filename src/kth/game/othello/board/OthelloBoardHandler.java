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
	 * TODO: Should in the future work in such a way that it works for any amount of players given a "working board".
	 * TODO: The order of the list should determine the position of the players. Now wrongly assumes it is only 2
	 */
	public void initializeStartingPositions(List<String> playersIds) {
		int mid = (board.getNumCols() / 2) - 1;
		board.getNode(mid, mid).setOccupantPlayerId(playersIds.get(1));
		board.getNode(mid, mid + 1).setOccupantPlayerId(playersIds.get(0));
		board.getNode(mid + 1, mid).setOccupantPlayerId(playersIds.get(0));
		board.getNode(mid + 1, mid + 1).setOccupantPlayerId(playersIds.get(1));
	}

	/**
	 * Get valid nodes that can be moved to by the given player id.
	 *
	 * @param playerId The player that should be checked for valid moves
	 * @return All valid nodes the player move to
	 */
	public List<Node> getValidMoves(String playerId) {
		List<Node> validNodes = new LinkedList<>();

		for (int x = 0; x < board.getNumRows(); x++) {
			for (int y = 0; y < board.getNumCols(); y++) {
				NodeImpl node = board.getNode(x, y);
				if (isValidMove(playerId, node)) {
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
		List<Node> swaps = getNodesToSwap(playerId, nodeId);

		if (swaps.isEmpty()) {
			throw new IllegalArgumentException("The move is invalid.");
		}

		swaps.add(node);

		for (Node n : swaps) {
			NodeImpl ni = (NodeImpl) n;
			ni.setOccupantPlayerId(playerId);
		}

		return swaps;
	}

	/**
	 * Computes the number of swapped nodes if a move is made by given player to given node. This will not perform the
	 * actual node.
	 * 
	 * @param playerId The player that will make the move.
	 * @param nodeId The node that the player will move to.
	 * @return The number of nodes that will be swapped (not including the one moved to).
	 * @throws IllegalArgumentException if the potential move is not valid or the given node does not exist.
	 */
	public int getNumSwaps(String playerId, String nodeId) {
		int numSwaps = getNodesToSwap(playerId, nodeId).size();

		if (numSwaps == 0) {
			throw new IllegalArgumentException("Invalid move");
		}

		return numSwaps;
	}

	/**
	 * Determines if the given player can make a move by occupying the specified node.
	 * 
	 * @param node The node to occupy
	 * @param playerId The player who tries to move
	 * @return True if the given move is valid
	 */
	private boolean isValidMove(String playerId, NodeImpl node) {
		return !node.isMarked() && !getNodesToSwap(playerId, node.getId()).isEmpty();
	}

	/**
	 * Check to see if there exist a valid move for a given playerId.
	 *
	 * @param playerId The player to check if any valid moves exist
	 * @return True if there exists at least one move that is valid
	 */
	public boolean hasAValidMove(String playerId) {
		return !getValidMoves(playerId).isEmpty();
	}

	/**
	 * Check to see if there exist at least one valid move for at least one of the given playerIds
	 *
	 * @param playerIds The ID´s of the players to check if a valid move exists.
	 * @return True if at least one valid move was found for some player
	 */
	public boolean hasAValidMove(List<String> playerIds) {
		for (String playerId : playerIds) {
			if (hasAValidMove(playerId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 *
	 * @param playerId The player id that will occupy the given node.
	 * @param nodeId The id of the node that the given player will occupy.
	 * @return A list of all nodes swapped in all directions starting from the given node and that it is playerId that
	 *         will occupy the starting node. The list is empty if it is an invalid move (no swaps possible).
	 */
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		List<NodeImpl> swaps = new LinkedList<>();

		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				swaps.addAll(getSwapsDirection(playerId, board.getNode(nodeId), i, j));
			}
		}
		return new LinkedList<>(swaps);
	}

	/**
	 * Computes a list of nodes that will be swapped if the given player will move to the given node. The algorithm will
	 * start from the given node and step in the given x and y direction. If no swaps are possible the method will
	 * return an empty list. Otherwise the list of the swapped nodes (except the start node).
	 *
	 * @param playerId The player id that will occupy the given node.
	 * @param node The node that the player will occupy. This will be the start node.
	 * @param iStep The step the algorithm should take in the x-axis (-1, 0 or 1).
	 * @param jStep The step the algorithm should take in the y-axis (-1, 0, or 1).
	 * @return A list of all nodes swapped in the direction given by iStep and jStep, starting from given node and that
	 *         it is playerId that will occupy the start node.
	 */
	private List<NodeImpl> getSwapsDirection(String playerId, NodeImpl node, int iStep, int jStep) {
		List<NodeImpl> swaps = new LinkedList<>();

		for (int x = node.getXCoordinate() + iStep, y = node.getYCoordinate() + jStep; board.isInRange(x, y); x += iStep, y += jStep) {
			NodeImpl n = board.getNode(x, y);
			if (!n.isMarked()) {
				return new LinkedList<>();
			}

			if (n.getOccupantPlayerId().equals(playerId)) {
				return swaps;
			}

			swaps.add(n);
		}

		return new LinkedList<>();
	}
}
