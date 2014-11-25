package kth.game.othello.player.movestrategy;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.board.OthelloBoardHandlerFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomTest {
	@Test
	public void moveMultipleEquallyGoodTest() {
		// Test initial game board (4 equally good moves)
		{
			List<Node> validMoves = new LinkedList<>();
			validMoves.add(new NodeImpl(2, 3));
			validMoves.add(new NodeImpl(2, 3));
			validMoves.add(new NodeImpl(4, 5));
			validMoves.add(new NodeImpl(5, 4));

			OthelloBoardHandler mockedBoardHandler = mock(OthelloBoardHandler.class);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("2:3"))).thenReturn(1);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("3:2"))).thenReturn(1);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("4:5"))).thenReturn(1);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("5:4"))).thenReturn(1);
			when(mockedBoardHandler.getValidMoves(anyString())).thenReturn(validMoves);

			Othello mockedOthello = getMockedOthello();
			MoveStrategy lowestStrategy = getMockedStrategy(mockedBoardHandler);

			Node move = lowestStrategy.move("player1", mockedOthello);

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
	public void moveNotPossibleTest() {
		OthelloBoardHandler mockedBoardHandler = mock(OthelloBoardHandler.class);
		when(mockedBoardHandler.getValidMoves(anyString())).thenReturn(new LinkedList<>());

		Othello mockedOthello = getMockedOthello();
		MoveStrategy lowestStrategy = getMockedStrategy(mockedBoardHandler);

		Node move = lowestStrategy.move("player1", mockedOthello);
		Assert.assertEquals(null, move);
	}

	private Othello getMockedOthello() {
		Othello mockedOthello = mock(Othello.class);
		when(mockedOthello.getBoard()).thenReturn(null);
		return mockedOthello;
	}

	private MoveStrategy getMockedStrategy(OthelloBoardHandler boardHandler) {
		OthelloBoardHandlerFactory mockedFactory = mock(OthelloBoardHandlerFactory.class);
		when(mockedFactory.createOthelloBoardHandler(anyObject())).thenReturn(boardHandler);

		return new Lowest(mockedFactory);
	}
}
