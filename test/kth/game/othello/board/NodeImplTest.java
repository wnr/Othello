package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;

public class NodeImplTest {

	private static final String PLAYER_1 = "player1";
	private static final String PLAYER_2 = "player2";

	@Test
	public void constructorTest() {
		{
			NodeImpl node = new NodeImpl(PLAYER_1, 3, 5);
			Assert.assertEquals(PLAYER_1, node.getOccupantPlayerId());
			Assert.assertEquals(3, node.getXCoordinate());
			Assert.assertEquals(5, node.getYCoordinate());
			Assert.assertEquals(true, node.isMarked());
		}
		{
			NodeImpl node = new NodeImpl(5, 3);
			Assert.assertEquals(null, node.getOccupantPlayerId());
			Assert.assertEquals(5, node.getXCoordinate());
			Assert.assertEquals(3, node.getYCoordinate());
			Assert.assertEquals(false, node.isMarked());
		}
	}

	@Test
	public void occupyTest() {
		NodeImpl node = new NodeImpl(PLAYER_1, 0, 0);
		node.setOccupantPlayerId(PLAYER_2);
		Assert.assertEquals(PLAYER_2, node.getOccupantPlayerId());
		Assert.assertEquals(true, node.isMarked());
		node.setOccupantPlayerId(null);
		Assert.assertEquals(null, node.getOccupantPlayerId());
		Assert.assertEquals(false, node.isMarked());
	}

	@Test
	public void nodeIDTest() {
		{
			NodeImpl node = new NodeImpl(2, 7);
			Assert.assertEquals("2:7", node.getId());
		}
	}

}
