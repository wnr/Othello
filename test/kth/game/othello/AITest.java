package kth.game.othello;

import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;
import kth.game.othello.board.OthelloBoardHandler;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AITest {

	@Test
	public void getMoveWithMostSwapsTest() {
		// Test initial game board (4 equally good moves)
		{
			List<NodeImpl> validMoves = new LinkedList<NodeImpl>();
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

			AI ai = new AI(mockedBoardHandler);

			String move = ai.getMoveWithMostSwaps("player1");

			boolean found = false;
			for (Node n : validMoves) {
				if (n.getId().equals(move)) {
					found = true;
					break;
				}
			}

			Assert.assertTrue(found);
		}

		// Test with one best move
		{
			List<NodeImpl> validMoves = new LinkedList<NodeImpl>();
			validMoves.add(new NodeImpl(1, 3));
			validMoves.add(new NodeImpl(2, 3));
			validMoves.add(new NodeImpl(4, 5));
			validMoves.add(new NodeImpl(5, 4));

			OthelloBoardHandler mockedBoardHandler = mock(OthelloBoardHandler.class);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("1:3"))).thenReturn(2);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("3:2"))).thenReturn(1);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("4:5"))).thenReturn(1);
			when(mockedBoardHandler.getNumSwaps(anyString(), eq("5:4"))).thenReturn(1);
			when(mockedBoardHandler.getValidMoves(anyString())).thenReturn(validMoves);

			AI ai = new AI(mockedBoardHandler);

			String move = ai.getMoveWithMostSwaps("player1");

			Assert.assertEquals("1:3", move);
		}

		// Test with no possible moves
		{
			OthelloBoardHandler mockedBoardHandler = mock(OthelloBoardHandler.class);
			when(mockedBoardHandler.getValidMoves(anyString())).thenReturn(new LinkedList<NodeImpl>());
			AI ai = new AI(mockedBoardHandler);
			String move = ai.getMoveWithMostSwaps("player1");
			Assert.assertEquals(null, move);
		}
	}
}
