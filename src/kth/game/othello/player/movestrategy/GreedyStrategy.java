package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.*;
import kth.game.othello.rules.Rules;

/**
 * A greedy move strategy that will make the move that result in the most node swaps. If there are multiple candidates
 * one Node will just be picked.
 *
 * @author Lucas Wiener
 */
public class GreedyStrategy implements MoveStrategy {
	private final BoardHandlerFactory boardHandlerFactory;

	/**
	 * Creates the greedy move strategy instance.
	 *
	 * @param boardHandlerFactory The factory to create othello board handlers.
	 */
	public GreedyStrategy(BoardHandlerFactory boardHandlerFactory) {
		this.boardHandlerFactory = boardHandlerFactory;
	}

	@Override
	public String getName() {
		return "Greedy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		// TODO: Use rules here somehow?
		BoardImpl copiedBoard = ((BoardImpl) board).copyWithoutObservers();
		BoardHandler boardHandler = boardHandlerFactory.createOthelloBoardHandler(copiedBoard);

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
