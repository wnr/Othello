package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class BoardImplTest {

	private BoardImpl create8x8Board() {
		ArrayList<NodeImpl> nodes = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				nodes.add(new NodeImpl(i, j));
			}
		}
		return new BoardImpl(nodes);
	}

	@Test
	public void getNodesTest() {
		BoardImpl board = create8x8Board();
		List<Node> nodes = board.getNodes();
		Assert.assertEquals(nodes.size(), 64);
	}

	@Test
	public void containsNodeTest() {
		BoardImpl board = create8x8Board();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Assert.assertTrue(board.containsNodeOnPosition(x, y));
				Assert.assertTrue(board.containsNodeWithId(NodeIdUtil.createNodeId(x, y)));
			}
		}
		Assert.assertFalse(board.containsNodeOnPosition(8, 8));
		Assert.assertFalse(board.containsNodeWithId(NodeIdUtil.createNodeId(8, 8)));
	}

	@Test
	public void getNodeTest() {
		BoardImpl board = create8x8Board();

		// getNode(x, y)
		{
			Assert.assertEquals(NodeIdUtil.createNodeId(1, 1), board.getNode(1, 1).getId());
			Assert.assertEquals(NodeIdUtil.createNodeId(0, 0), board.getNode(0, 0).getId());
			Assert.assertEquals(NodeIdUtil.createNodeId(2, 3), board.getNode(2, 3).getId());
		}

		// getNode(nodeId)
		{
			Assert.assertEquals(NodeIdUtil.createNodeId(1, 1), board.getNode(NodeIdUtil.createNodeId(1, 1)).getId());
			Assert.assertEquals(NodeIdUtil.createNodeId(0, 0), board.getNode(NodeIdUtil.createNodeId(0, 0)).getId());
			Assert.assertEquals(NodeIdUtil.createNodeId(6, 7), board.getNode(NodeIdUtil.createNodeId(6, 7)).getId());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void outOfRangeTest() {
		BoardImpl board = create8x8Board();
		board.getNode(7, 8);
	}
}
