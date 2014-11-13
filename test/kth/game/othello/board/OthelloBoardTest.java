package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OthelloBoardTest {

	@Test
	public void setAndGetNodeTest() {
		OthelloBoard board = OthelloBoardFactory.createOthelloBoard();
		board.setNode(2, 2, board.getNode(3, 3));
		Assert.assertEquals(board.getNode(2, 2), board.getNode(3, 3));
		Assert.assertNotEquals(board.getNode(2, 3), board.getNode(3, 3));
	}

	@Test
	public void getNodesTest() {
		OthelloBoard board = OthelloBoardFactory.createOthelloBoard();
		List<Node> nodes = board.getNodes();
		Assert.assertEquals(nodes.size(), board.getWidth() * board.getHeight());
		int prev = -1;
		for (Node node : nodes) {
			int xy = node.getXCoordinate() * board.getWidth() + node.getYCoordinate();
			Assert.assertEquals(xy, prev + 1);
			prev = xy;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void outOfRangeTest() {
		OthelloBoard board = new OthelloBoard(2, 2, null);
		board.getNode(2, 1);
	}

}
