package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.*;
import kth.game.othello.rules.Rules;

/**
 * A really move strategy that will make the move by random. It will simply choose a random node among the possible
 * nodes to move to.
 *
 * @author Lucas Wiener
 */
public class RandomStrategy implements MoveStrategy {
	private final BoardHandlerFactory boardHandlerFactory;

	/**
	 * Creates the random move strategy instance.
	 *
	 * @param boardHandlerFactory The factory to create othello board handlers.
	 */
	public RandomStrategy(BoardHandlerFactory boardHandlerFactory) {
		this.boardHandlerFactory = boardHandlerFactory;
	}

	@Override
	public String getName() {
		return "Random";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		// TODO: Use rules here somehow?
		BoardImpl copiedBoard = ((BoardImpl) board).copyWithoutObservers();
		BoardHandler boardHandler = boardHandlerFactory.createOthelloBoardHandler(copiedBoard);

		List<Node> validMoves = boardHandler.getValidMoves(playerId);

		if (validMoves.isEmpty()) {
			return null;
		}

		return validMoves.get(new java.util.Random().nextInt(validMoves.size()));
	}
}