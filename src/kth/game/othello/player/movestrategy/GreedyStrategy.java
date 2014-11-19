package kth.game.othello.player.movestrategy;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.board.OthelloBoardHandlerFactory;

import java.util.List;

/**
 * A greedy move strategy that will make the move that result in the most node swaps. If there are multiple candidates
 * one Node will just be picked.
 *
 * @author Lucas Wiener
 */
public class GreedyStrategy implements MoveStrategy {
	OthelloBoardHandlerFactory boardHandlerFactory;

	/**
	 * Creates the greedy move strategy instance.
	 *
	 * @param boardHandlerFactory The factory to create othello board handlers.
	 */
	public GreedyStrategy(OthelloBoardHandlerFactory boardHandlerFactory) {
		this.boardHandlerFactory = boardHandlerFactory;
	}

	@Override
	public String getName() {
		return "Greedy";
	}

	@Override
	public Node move(String playerId, Othello othello) {
		OthelloBoardHandler boardHandler = boardHandlerFactory.createOthelloBoardHandler(othello.getBoard());

		List<Node> validMoves = boardHandler.getValidMoves(playerId);

		Node highestNode = null;
		int highestSwaps = 0;

		for (Node n : validMoves) {
			String nodeId = n.getId();
			int numSwaps = boardHandler.getNumSwaps(playerId, nodeId);
			if (numSwaps > highestSwaps) {
				highestSwaps = numSwaps;
				highestNode = n;
			}
		}

		return highestNode;
	}
}
