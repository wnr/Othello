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
		ArrayList<NodeImpl> inputNodes = new ArrayList<>();
		inputNodes.add(new NodeImpl(2, 3));
		inputNodes.add(new NodeImpl(2, 2));
		inputNodes.add(new NodeImpl(3, 3));
		inputNodes.add(new NodeImpl(4, 0));
		inputNodes.add(new NodeImpl(0, 4));
		BoardImpl board = new BoardImpl(inputNodes);
		List<Node> nodes = board.getNodes();
		Assert.assertEquals(nodes.size(), inputNodes.size());
		for (int i = 0; i < nodes.size() - 1; i++) {
			Node n1 = nodes.get(i);
			Node n2 = nodes.get(i + 1);
			Assert.assertTrue(n1.getXCoordinate() < n2.getXCoordinate() || n1.getXCoordinate() == n2.getXCoordinate()
					&& n1.getYCoordinate() < n2.getYCoordinate());
		}
	}

	@Test
	public void occupyTest() {
		String player = "Playaah";
		BoardImpl board = create8x8Board();
		Assert.assertEquals(board.getNode(0, 2).getOccupantPlayerId(), null);
		board.occupyNode(NodeIdUtil.createNodeId(0, 2), player);
		Assert.assertEquals(board.getNode(0, 2).getOccupantPlayerId(), player);
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