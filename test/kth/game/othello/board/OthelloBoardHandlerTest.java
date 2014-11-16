package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.List;

public class OthelloBoardHandlerTest {

	private OthelloBoardHandler getSpecialEndGameBoardHandler(String player1, String player2) {
		NodeImpl[][] nodes = getNodeMatrix();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				nodes[i][j].setOccupantPlayerId(player1);
			}
		}

		nodes[3][7].setOccupantPlayerId(null);
		nodes[4][6].setOccupantPlayerId(null);
		nodes[4][7].setOccupantPlayerId(null);
		nodes[5][6].setOccupantPlayerId(null);
		nodes[5][7].setOccupantPlayerId(player2);
		nodes[6][7].setOccupantPlayerId(null);

		return new OthelloBoardHandler(getMockedBoard(nodes));
	}

	private OthelloBoardHandler getInitialGameBoardHandler(String player1, String player2) {
		NodeImpl[][] nodes = getNodeMatrix();
		nodes[3][3].setOccupantPlayerId(player2);
		nodes[4][4].setOccupantPlayerId(player2);
		nodes[3][4].setOccupantPlayerId(player1);
		nodes[4][3].setOccupantPlayerId(player1);
		RectangularBoard board = getMockedBoard(nodes);
		return new OthelloBoardHandler(board);
	}

	private NodeImpl[][] getNodeMatrix() {
		NodeImpl[][] nodes = new NodeImpl[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				nodes[i][j] = new NodeImpl(i, j);
			}
		}

		return nodes;
	}

	private RectangularBoard getMockedBoard(NodeImpl[][] nodes) {
		RectangularBoard mockedBoard = mock(RectangularBoard.class);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				when(mockedBoard.getNode(i, j)).thenReturn(nodes[i][j]);
				when(mockedBoard.getNode(i + ":" + j)).thenReturn(nodes[i][j]);
				when(mockedBoard.isInRange(i, j)).thenReturn(true);
			}
		}

		when(mockedBoard.getNumRows()).thenReturn(8);
		when(mockedBoard.getNumCols()).thenReturn(8);

		return mockedBoard;
	}

	@Test
	public void initializeStartingPositionsTest() {
		String player1 = "player1";
		String player2 = "player2";

		NodeImpl[][] nodes = getNodeMatrix();
		RectangularBoard mockedBoard = getMockedBoard(nodes);

		OthelloBoardHandler boardHandler = new OthelloBoardHandler(mockedBoard);
		boardHandler.initializeStartingPositions(player1, player2);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i == 3 && j == 3 || i == 4 && j == 4) {
					Assert.assertEquals(player2, nodes[i][j].getOccupantPlayerId());
				} else if (i == 3 && j == 4 || i == 4 && j == 3) {
					Assert.assertEquals(player1, nodes[i][j].getOccupantPlayerId());
				} else {
					Assert.assertFalse(nodes[i][j].isMarked());
				}
			}
		}
	}

	@Test
	public void getValidMovesTest() {
		String player1 = "player1";
		String player2 = "player2";

		// Test initial game setup
		{
			OthelloBoardHandler boardHandler = getInitialGameBoardHandler(player1, player2);

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
			OthelloBoardHandler boardHandle = getSpecialEndGameBoardHandler(player1, player2);
			Assert.assertEquals(0, boardHandle.getValidMoves(player2).size());
		}
	}

	@Test
	public void moveTest() {
		String player1 = "player1";
		String player2 = "player2";

		// Test initial game setup
		{
			OthelloBoardHandler boardHandler = getInitialGameBoardHandler(player1, player2);

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
		OthelloBoardHandler boardHandler = getInitialGameBoardHandler(player1, player2);
		boardHandler.initializeStartingPositions(player1, player2);
		boardHandler.move(player1, "2:2");
	}
}
