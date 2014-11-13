package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;

public class NodeImplTest {

	private static final String PLAYER_1 = "player1";
	private static final String PLAYER_2 = "player2";

	@Test
	public void constructorTest() {
		NodeImpl node = new NodeImpl(PLAYER_1, 3, 5);
		Assert.assertEquals(node.getOccupantPlayerId(), PLAYER_1);
		Assert.assertEquals(node.getXCoordinate(), 3);
		Assert.assertEquals(node.getYCoordinate(), 5);
	}

	@Test
	public void occupyTest() {
		NodeImpl node1 = new NodeImpl(PLAYER_1, 0, 0);
		NodeImpl node2 = node1.occupy(PLAYER_2);
		Assert.assertNotEquals(node1.getOccupantPlayerId(), node2.getOccupantPlayerId());
		Assert.assertEquals(node2.getOccupantPlayerId(), PLAYER_2);
	}

	@Test
	public void nodeIDTest() {
		NodeImpl node1 = new NodeImpl(null, 2, 7);
		NodeImpl node2 = new NodeImpl(null, 2, 7);
		Assert.assertEquals(node1.getId(), node2.getId());
	}

}
