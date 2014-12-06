package kth.game.othello.board;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class BoardHistoryTest {
	@Test
	public void pushAndPopTest() {
		BoardImpl mockedBoard = mock(BoardImpl.class);
		BoardHistory boardHistory = new BoardHistory(mockedBoard);

		List<Node> firstMove = new ArrayList<>();
		firstMove.add(new NodeImpl("player1", 0, 0));
		firstMove.add(new NodeImpl("player2", 3, 3));
		firstMove.add(new NodeImpl("player3", 1, 2));

		boardHistory.push(firstMove);

		List<Node> secondMove = new ArrayList<>();
		secondMove.add(new NodeImpl("player1", 1, 1));
		secondMove.add(new NodeImpl("player1", 2, 1));
		secondMove.add(new NodeImpl("player3", 3, 3));

		boardHistory.push(secondMove);

		boardHistory.pop();
		verifyOccupyNode(mockedBoard, secondMove);

		boardHistory.pop();
		verifyOccupyNode(mockedBoard, firstMove);

		boardHistory.pop();
		boardHistory.pop();
	}

	private void verifyOccupyNode(BoardImpl mockedBoard, List<Node> move) {
		Collections.reverse(move);
		for (Node n : move) {
			verify(mockedBoard, times(1)).occupyNode(n.getId(), n.getOccupantPlayerId());
		}
	}
}
