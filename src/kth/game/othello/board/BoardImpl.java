package kth.game.othello.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * A board implementation storing nodes on a rectangular grid.
 *
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class BoardImpl implements Board {

	private TreeMap<String, NodeImpl> nodes;

	/**
	 * Constructs a new BoardImpl containing the specified nodes
	 * 
	 * @param nodes The nodes that the board should contain
	 */
	public BoardImpl(Collection<NodeImpl> nodes) {
		this.nodes = new TreeMap<>();
		for (NodeImpl node : nodes) {
			this.nodes.put(node.getId(), node);
		}
	}

	/**
	 * The number of nodes in the board.
	 *
	 * @return the number of nodes
	 */
	public int getNumNodes() {
		return nodes.size();
	}

	/**
	 * Get the node on a specified position.
	 *
	 * @param x The x-coordinate of the node
	 * @param y The y-coordinate of the node
	 * @throws java.lang.IllegalArgumentException if the coordinates are outside the board
	 */
	public Node getNode(int x, int y) {
		return getNode(NodeIdUtil.createNodeId(x, y));
	}

	/**
	 * Gets the node given a node id.
	 *
	 * @param nodeId The id of the node to return.
	 * @return The node that has the given node id.
	 * @throws java.lang.IllegalArgumentException if no node with the id exist in the board
	 */
	public Node getNode(String nodeId) {
		if (!nodes.containsKey(nodeId)) {
			throw new IllegalArgumentException("No node found with id: " + nodeId);
		}
		return nodes.get(nodeId);
	}

	/**
	 * Determines if the board contains a node on the specified position
	 *
	 * @param x The x-coordinate to search for
	 * @param y The y-coordinate to search for
	 * @return True if a node exists on this position
	 */
	public boolean containsNodeOnPosition(int x, int y) {
		return nodes.containsKey(NodeIdUtil.createNodeId(x, y));
	}

	/**
	 * Determines if the board contains a node with the specified id.
	 * 
	 * @param nodeId The nodeId to search for
	 * @return True if a node with this id exists
	 */
	public boolean containsNodeWithId(String nodeId) {
		return nodes.containsKey(nodeId);
	}

	/**
	 * Make a node on the board occupied by the specified player.
	 *
	 * @param nodeId The node to occupy
	 * @param playerId The occupying player
	 * @throws java.lang.IllegalArgumentException If the node does not exist on the board
	 */
	public void occupyNode(String nodeId, String playerId) {
		if (!nodes.containsKey(nodeId)) {
			throw new IllegalArgumentException("No node found with id: " + nodeId);
		}
		nodes.get(nodeId).setOccupantPlayerId(playerId);
	}

	@Override
	public List<Node> getNodes() {
		ArrayList<Node> res = new ArrayList<>();
		for (Node node : nodes.values()) {
			res.add(node);
		}
		return res;
	}
}
