package kth.game.othello;

import kth.game.othello.board.NodeImpl;
import kth.game.othello.board.OthelloBoardHandler;

import java.util.List;

/**
 * Artificial intelligence for the Othello game. Responsible for thinking out moves for a player.
 *
 * @author Lucas Wiener
 */
public class AI {
	OthelloBoardHandler boardHandler;

	/**
	 * Constructs the AI instance that will be used to compute moves of the game for players.
	 * 
	 * @param boardHandler The board handle to be used by this AI
	 */
	AI(OthelloBoardHandler boardHandler) {
		this.boardHandler = boardHandler;
	}

	/**
	 * Computes the best move (the one that will result in most swapped nodes). If there are no moves the result will be
	 * a null string. If there are several best moves, one will simply be picked.
	 *
	 * @param playerId The player that the best move should be computed for.
	 * @return The node id of the best node to move to for the given player. A null value will be returned if there are
	 *         no possible moves for the player.
	 */
	public String getMoveWithMostSwaps(String playerId) {
		List<NodeImpl> validMoves = boardHandler.getValidMoves(playerId);

		String highestId = null;
		int highestSwaps = 0;

		for (NodeImpl n : validMoves) {
			String nodeId = n.getId();
			int numSwaps = boardHandler.getNumSwaps(playerId, nodeId);
			if (numSwaps > highestSwaps) {
				highestSwaps = numSwaps;
				highestId = n.getId();
			}
		}

		return highestId;
	}
}
