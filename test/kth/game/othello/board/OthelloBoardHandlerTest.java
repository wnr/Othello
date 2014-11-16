package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OthelloBoardHandlerTest {

	private OthelloBoardHandler getSpecialEndGameBoard(String player1, String player2) {
		RectangularBoard board = new RectangularBoard(8, 8);

		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumCols(); j++) {
				board.getNode(i, j).setOccupantPlayerId(player1);
			}
		}

		board.getNode(3, 7).setOccupantPlayerId(null);
		board.getNode(4, 6).setOccupantPlayerId(null);
		board.getNode(4, 7).setOccupantPlayerId(null);
		board.getNode(5, 6).setOccupantPlayerId(null);
		board.getNode(5, 7).setOccupantPlayerId(player2);
		board.getNode(6, 7).setOccupantPlayerId(null);

		return new OthelloBoardHandler(board);
	}

	@Test
	public void initializeStartingPositionsTest() {
		String player1 = "player1";
		String player2 = "player2";
		RectangularBoard board = new RectangularBoard(8, 8);
		OthelloBoardHandler boardHandler = new OthelloBoardHandler(board);
		boardHandler.initializeStartingPositions(player1, player2);
		List<Node> nodes = board.getNodes();
		for (Node node : nodes) {
			if (node.getXCoordinate() == 3 && node.getYCoordinate() == 3 || node.getXCoordinate() == 4
					&& node.getYCoordinate() == 4) {
				Assert.assertTrue(node.isMarked());
				Assert.assertEquals(player2, node.getOccupantPlayerId());
			} else if (node.getXCoordinate() == 3 && node.getYCoordinate() == 4 || node.getXCoordinate() == 4
					&& node.getYCoordinate() == 3) {
				Assert.assertTrue(node.isMarked());
				Assert.assertEquals(player1, node.getOccupantPlayerId());
			}
		}
	}

	@Test
	public void getValidMovesTest() {
		String player1 = "player1";
		String player2 = "player2";

		// Test initial game setup
		{
			RectangularBoard board = new RectangularBoard(8, 8);
			OthelloBoardHandler boardHandler = new OthelloBoardHandler(board);
			boardHandler.initializeStartingPositions(player1, player2);

			List<Node> validMoves = boardHandler.getValidMoves(player1);
			Assert.assertEquals(4, validMoves.size());
			Assert.assertTrue(validMoves.contains(new NodeImpl(2, 3)));
			Assert.assertTrue(validMoves.contains(new NodeImpl(3, 2)));
			Assert.assertTrue(validMoves.contains(new NodeImpl(4, 5)));
			Assert.assertTrue(validMoves.contains(new NodeImpl(5, 4)));

			boardHandler.getBoard().getNode(2, 3).setOccupantPlayerId(player1);
			boardHandler.getBoard().getNode(3, 3).setOccupantPlayerId(player1);
			validMoves = boardHandler.getValidMoves(player2);
			Assert.assertEquals(3, validMoves.size());
			Assert.assertTrue(validMoves.contains(new NodeImpl(2, 2)));
			Assert.assertTrue(validMoves.contains(new NodeImpl(4, 2)));
			Assert.assertTrue(validMoves.contains(new NodeImpl(4, 2)));

			boardHandler.getBoard().getNode(4, 2).setOccupantPlayerId(player2);
			boardHandler.getBoard().getNode(4, 3).setOccupantPlayerId(player2);
			validMoves = boardHandler.getValidMoves(player1);
			Assert.assertEquals(5, validMoves.size());
			Assert.assertTrue(validMoves.contains(new NodeImpl(5, 1)));
			Assert.assertTrue(validMoves.contains(new NodeImpl(5, 2)));
			Assert.assertTrue(validMoves.contains(new NodeImpl(5, 3)));
			Assert.assertTrue(validMoves.contains(new NodeImpl(5, 4)));
			Assert.assertTrue(validMoves.contains(new NodeImpl(5, 5)));
		}

		// Test special end game boar
		{
			OthelloBoardHandler boardHandle = getSpecialEndGameBoard(player1, player2);
			Assert.assertEquals(0, boardHandle.getValidMoves(player2).size());
		}
	}

	@Test
	public void moveTest() {
		String player1 = "player1";
		String player2 = "player2";

		// Test initial game setup
		{
			RectangularBoard board = new RectangularBoard(8, 8);
			OthelloBoardHandler boardHandler = new OthelloBoardHandler(board);
			boardHandler.initializeStartingPositions(player1, player2);

			List<Node> swaps = boardHandler.move(player1, "2:3");
			Assert.assertEquals(2, swaps.size());
			Assert.assertTrue(swaps.contains(new NodeImpl(player1, 2, 3)));
			Assert.assertTrue(swaps.contains(new NodeImpl(player1, 3, 3)));

			swaps = boardHandler.move(player2, "4:2");
			boardHandler.getBoard().getNode(4, 2).setOccupantPlayerId(player2);
			boardHandler.getBoard().getNode(4, 3).setOccupantPlayerId(player2);
			Assert.assertEquals(2, swaps.size());
			Assert.assertTrue(swaps.contains(new NodeImpl(player2, 4, 2)));
			Assert.assertTrue(swaps.contains(new NodeImpl(player2, 4, 3)));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidMoveTest() {
		String player1 = "player1";
		String player2 = "player2";
		RectangularBoard board = new RectangularBoard(8, 8);
		OthelloBoardHandler boardHandler = new OthelloBoardHandler(board);
		boardHandler.initializeStartingPositions(player1, player2);
		boardHandler.move(player1, "2:2");
	}
}
