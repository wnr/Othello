package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.Observer;

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
			Assert.assertTrue(node.isMarked());
		}
		{
			NodeImpl node = new NodeImpl(5, 3);
			Assert.assertEquals(null, node.getOccupantPlayerId());
			Assert.assertEquals(5, node.getXCoordinate());
			Assert.assertEquals(3, node.getYCoordinate());
			Assert.assertFalse(node.isMarked());
		}
	}

	@Test
	public void occupyTest() {
		NodeImpl node = new NodeImpl(PLAYER_1, 0, 0);
		node.setOccupantPlayerId(PLAYER_2);
		Assert.assertEquals(PLAYER_2, node.getOccupantPlayerId());
		Assert.assertTrue(node.isMarked());
		node.setOccupantPlayerId(null);
		Assert.assertEquals(null, node.getOccupantPlayerId());
		Assert.assertFalse(node.isMarked());
	}

	@Test
	public void nodeIDTest() {
		{
			NodeImpl node = new NodeImpl(2, 7);
			Assert.assertEquals("2:7", node.getId());
		}
	}

	@Test
	public void observableTest() {
		Observer observer = mock(Observer.class);

		NodeImpl node = new NodeImpl(2, 7);
		node.addObserver(observer);
		node.notifyObservers(null);
		node.setOccupantPlayerId("player1");

		verify(observer).update(node, null);

		node.setOccupantPlayerId("player2");
		verify(observer).update(node, "player1");

		node.setOccupantPlayerId(null);
		verify(observer).update(node, "player2");

		node.setOccupantPlayerId(null);
		verify(observer, times(1)).update(node, null); // This method should not have been called (thats why the times
														// is 1 for the previous call)
	}

}
