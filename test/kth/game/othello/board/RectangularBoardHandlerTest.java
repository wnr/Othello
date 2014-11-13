package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RectangularBoardHandlerTest {

	@Test
	public void initializeStartingPositionsTest() {
		String player1 = "player1";
		String player2 = "player2";
		RectangularBoard board = BoardFactory.createOthelloBoard();
		OthelloBoardHandler boardHandler = new OthelloBoardHandler(board);
		boardHandler.initializeStartingPositions(player1, player2);
		List<Node> nodes = board.getNodes();
		for (Node node : nodes) {
			if (node.getXCoordinate() == 3 && node.getYCoordinate() == 3 || node.getXCoordinate() == 4
					&& node.getYCoordinate() == 4) {
				Assert.assertTrue(node.isMarked() && node.getOccupantPlayerId().equals(player2));
			} else if (node.getXCoordinate() == 3 && node.getYCoordinate() == 4 || node.getXCoordinate() == 4
					&& node.getYCoordinate() == 3) {
				Assert.assertTrue(node.isMarked() && node.getOccupantPlayerId().equals(player1));
			}
		}
	}

}
