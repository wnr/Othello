package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.BoardHandler;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.BoardHandlerFactory;

/**
 * A really move strategy that will make the move by random. It will simply choose a random node among the possible
 * nodes to move to.
 *
 * @author Lucas Wiener
 */
public class RandomStrategy implements MoveStrategy {
	BoardHandlerFactory boardHandlerFactory;

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
	public Node move(String playerId, Othello othello) {
		// TODO: This cast will be fixed when Othello is changed to BoardInspector or something equally better.
		BoardImpl board = ((BoardImpl) othello.getBoard()).copyWithoutObservers();
		BoardHandler boardHandler = boardHandlerFactory.createOthelloBoardHandler(board);
		List<Node> validMoves = boardHandler.getValidMoves(playerId);

		if (validMoves.isEmpty()) {
			return null;
		}

		return validMoves.get(new java.util.Random().nextInt(validMoves.size()));
	}
}