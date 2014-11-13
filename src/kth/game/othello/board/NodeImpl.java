package kth.game.othello.board;

/**
 * Representation of a node, containing information of its position and an id of the occupying player. This node
 * implementation is immutable, making it safe to pass a node-instance around.
 */
public class NodeImpl implements Node {

	private String nodeID;
	private String playerID;
	private int x;
	private int y;

	/**
	 * Constructs a node instance. The node-id is determined by the values of x and y.
	 * 
	 * @param playerID The id of the player occupying this node, or null if it's free
	 * @param x The x-coordinate of the node
	 * @param y The y-coordinate of the node
	 */
	public NodeImpl(String playerID, int x, int y) {
		this.nodeID = createNodeID(x, y);
		this.playerID = playerID;
		this.x = x;
		this.y = y;
	}

	@Override
	public String getId() {
		return nodeID;
	}

	@Override
	public String getOccupantPlayerId() {
		return playerID;
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
		return playerID != null;
	}

	/**
	 * Make this node occupied by a player.
	 * 
	 * @param newOccupantPlayerID The id of the new player
	 * @return A new node object representing the state of the node after it has been occupied by the player
	 */
	public NodeImpl occupy(String newOccupantPlayerID) {
		return new NodeImpl(newOccupantPlayerID, x, y);
	}

	private String createNodeID(int x, int y) {
		return x + "x" + y;
	}

}
