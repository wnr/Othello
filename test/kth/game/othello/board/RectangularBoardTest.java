package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RectangularBoardTest {
	@Test
	public void gettersTest() {
		RectangularBoard board = new RectangularBoard(4, 8);
		Assert.assertEquals(4, board.getNumRows());
		Assert.assertEquals(8, board.getNumCols());
		Assert.assertEquals(4 * 8, board.getNumNodes());
	}

	@Test
	public void getNodesTest() {
		RectangularBoard board = new RectangularBoard(8, 8);
		List<Node> nodes = board.getNodes();
		Assert.assertEquals(board.getNumNodes(), nodes.size());

		int prev = -1;
		for (Node node : nodes) {
			int xy = node.getXCoordinate() * board.getNumRows() + node.getYCoordinate();
			Assert.assertEquals(prev + 1, xy);
			Assert.assertEquals(null, node.getOccupantPlayerId());
			Assert.assertFalse(node.isMarked());
			prev = xy;
		}
	}

	@Test
	public void getNodeTest() {
		RectangularBoard board = new RectangularBoard(3, 4);
		Assert.assertEquals("1:1", board.getNode(1, 1).getId());
		Assert.assertEquals("0:0", board.getNode(0, 0).getId());
		Assert.assertEquals("2:3", board.getNode(2, 3).getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void outOfRangeTest() {
		RectangularBoard board = new RectangularBoard(2, 2);
		board.getNode(2, 1);
	}
}
