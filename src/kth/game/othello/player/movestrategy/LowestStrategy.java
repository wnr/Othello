package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.*;
import kth.game.othello.rules.Rules;

/**
 * A really bad move strategy that will make the move that result in the least node swaps. If there are multiple
 * candidates one Node will just be picked.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class LowestStrategy implements MoveStrategy {
	private final BoardHandlerFactory boardHandlerFactory;

	/**
	 * Creates the lowest move strategy instance.
	 *
	 * @param boardHandlerFactory The factory to create othello board handlers.
	 */
	public LowestStrategy(BoardHandlerFactory boardHandlerFactory) {
		this.boardHandlerFactory = boardHandlerFactory;
	}

	@Override
	public String getName() {
		return "Lowest";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		// TODO: Use rules here somehow?
		BoardImpl copiedBoard = ((BoardImpl) board).copyWithoutObservers();
		BoardHandler boardHandler = boardHandlerFactory.createOthelloBoardHandler(copiedBoard);

		List<Node> validMoves = boardHandler.getValidMoves(playerId);

		Node lowestNode = null;
		int lowestSwaps = Integer.MAX_VALUE;

		for (Node n : validMoves) {
			String nodeId = n.getId();
			int numSwaps = boardHandler.getNumSwaps(playerId, nodeId);
			if (numSwaps < lowestSwaps) {
				lowestSwaps = numSwaps;
				lowestNode = n;
			}
		}

		return lowestNode;
	}
}
