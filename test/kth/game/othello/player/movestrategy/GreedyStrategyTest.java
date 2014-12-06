package kth.game.othello.player.movestrategy;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.BoardHandler;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;

import org.junit.Assert;
import org.junit.Test;

public class GreedyStrategyTest {
	@Test
	public void moveMultipleEquallyGoodTest() {
		// Test initial game board (4 equally good moves)
		{
			List<Node> validMoves = new LinkedList<>();
			validMoves.add(new NodeImpl(2, 3));
			validMoves.add(new NodeImpl(3, 2));
			validMoves.add(new NodeImpl(4, 5));
			validMoves.add(new NodeImpl(5, 4));

			BoardHandler mockedBoardHandler = mock(BoardHandler.class);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("2:3"))).thenReturn(1);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("3:2"))).thenReturn(1);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("4:5"))).thenReturn(1);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("5:4"))).thenReturn(1);
			when(mockedBoardHandler.getValidMoves(anyString())).thenReturn(validMoves);

			Othello mockedOthello = getMockedOthello();
			GreedyStrategy greedyStrategy = getMockedStrategy(mockedBoardHandler);

			// TODO: Change this.
			Node move = greedyStrategy.move("player1", null, mockedOthello.getBoard());

			boolean found = false;
			for (Node n : validMoves) {
				if (n.equals(move)) {
					found = true;
					break;
				}
			}

			Assert.assertTrue(found);
		}
	}

	@Test
	public void moveOneBestTest() {
		List<Node> validMoves = new LinkedList<>();
		validMoves.add(new NodeImpl(1, 3));
		validMoves.add(new NodeImpl(2, 3));
		validMoves.add(new NodeImpl(4, 5));
		validMoves.add(new NodeImpl(5, 4));

		BoardHandler mockedBoardHandler = mock(BoardHandler.class);
		when(mockedBoardHandler.getNumSwaps(anyString(), eq("1:3"))).thenReturn(2);
		when(mockedBoardHandler.getNumSwaps(anyString(), eq("3:2"))).thenReturn(1);
		when(mockedBoardHandler.getNumSwaps(anyString(), eq("4:5"))).thenReturn(1);
		when(mockedBoardHandler.getNumSwaps(anyString(), eq("5:4"))).thenReturn(1);
		when(mockedBoardHandler.getValidMoves(anyString())).thenReturn(validMoves);

		Othello mockedOthello = getMockedOthello();
		GreedyStrategy greedyStrategy = getMockedStrategy(mockedBoardHandler);

		// TODO: Change this
		Node move = greedyStrategy.move("player1", null, mockedOthello.getBoard());

		Assert.assertEquals("1:3", move.getId());
	}

	@Test
	public void moveNotPossibleTest() {
		BoardHandler mockedBoardHandler = mock(BoardHandler.class);
		when(mockedBoardHandler.getValidMoves(anyString())).thenReturn(new LinkedList<>());

		Othello mockedOthello = getMockedOthello();
		MoveStrategy greedyStrategy = getMockedStrategy(mockedBoardHandler);

		// TODO: Change this
		Node move = greedyStrategy.move("player1", null, mockedOthello.getBoard());
		Assert.assertEquals(null, move);
	}

	private Othello getMockedOthello() {
		return MoveStrategyTestHelper.getMockedOthello();
	}

	private GreedyStrategy getMockedStrategy(BoardHandler boardHandler) {
		return new GreedyStrategy(MoveStrategyTestHelper.getMockedBoardHandlerFactory(boardHandler));
	}
}
