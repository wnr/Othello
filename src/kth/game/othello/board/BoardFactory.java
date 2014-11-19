package kth.game.othello.board;

import java.util.ArrayList;

import kth.game.othello.player.Player;

/**
 * A factory for producing Othello Boards.
 *
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class BoardFactory {
	/**
	 * Creates an quadratic Othello board of size 8.
	 *
	 * @return an Othello Board of size 8
	 */
	public static BoardImpl createOthelloBoard(Player player1, Player player2) {
		ArrayList<NodeImpl> nodes = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				NodeImpl node = new NodeImpl(i, j);
				if (i == 3 && j == 3 || i == 4 && j == 4) {
					node.setOccupantPlayerId(player2.getId());
				} else if (i == 3 && j == 4 || i == 4 && j == 3) {
					node.setOccupantPlayerId(player1.getId());
				}
				nodes.add(node);
			}
		}
		return new BoardImpl(nodes);
	}
}
