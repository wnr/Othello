package kth.game.othello.board;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OthelloBoardHandlerTest {

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
				Assert.assertEquals(true, node.isMarked());
				Assert.assertEquals(player2, node.getOccupantPlayerId());
			} else if (node.getXCoordinate() == 3 && node.getYCoordinate() == 4 || node.getXCoordinate() == 4
					&& node.getYCoordinate() == 3) {
				Assert.assertEquals(true, node.isMarked());
				Assert.assertEquals(player1, node.getOccupantPlayerId());
			}
		}
	}
}
