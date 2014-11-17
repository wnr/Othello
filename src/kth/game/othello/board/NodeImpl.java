package kth.game.othello.board;

import java.util.Observer;

/**
 * Representation of a node, containing information of its position in the board and an id of the occupying player (if
 * any).
 *
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class NodeImpl implements Node {
	private String nodeId;
	private String playerId;
	private int x;
	private int y;

	/**
	 * Constructs a node instance. The node-id is determined by the values of x and y. A node should be identified
	 * uniquely given the x- and y-coordinate
	 * 
	 * @param playerId The id of the player occupying this node, or null if it's free
	 * @param x The x-coordinate of the node
	 * @param y The y-coordinate of the node
	 */
	public NodeImpl(String playerId, int x, int y) {
		this.nodeId = createNodeID(x, y);
		this.playerId = playerId;
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructs a node instance. The node-id is determined by the values of x and y. A node should be identified
	 * uniquely given the x- and y-coordinate
	 *
	 * @param x The x-coordinate of the node
	 * @param y The y-coordinate of the node
	 */
	public NodeImpl(int x, int y) {
		this(null, x, y);
	}

	/**
	 * Sets the occupying player of the node. If playerId is null then no player is occupying the node.
	 *
	 * @param playerId The player id of the player that should be occupying the node. Null if none.
	 */
	public void setOccupantPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@Override
	public void addObserver(Observer observer) {

	}

	@Override
	public String getId() {
		return nodeId;
	}

	@Override
	public String getOccupantPlayerId() {
		return playerId;
	}

	@Override
	public int getXCoordinate() {
		return x;
	}

	@Override
	public int getYCoordinate() {
		return y;
	}

	@Override
	public boolean isMarked() {
		return playerId != null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		NodeImpl node = (NodeImpl) o;

		if (x != node.x)
			return false;
		if (y != node.y)
			return false;
		if (!nodeId.equals(node.nodeId))
			return false;
		if (playerId != null ? !playerId.equals(node.playerId) : node.playerId != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = nodeId.hashCode();
		result = 31 * result + (playerId != null ? playerId.hashCode() : 0);
		result = 31 * result + x;
		result = 31 * result + y;
		return result;
	}

	/**
	 * Constructs a node id by the x and y coordinates.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @return the id of the node given the x and y coordinates
	 */
	private String createNodeID(int x, int y) {
		return x + ":" + y;
	}
}
