package kth.game.othello.player.startpositiondecider;

import java.util.List;

/**
 * Interface used for getting the initial starting positions for the players.
 *
 * @author Mathias Lindblom
 */
public interface StartPositionDecider {

	/**
	 * The list returned is the initial positioning order for the players on the game board.
	 *
	 * @return A list containing player ID´s.
	 */
	public List<String> getStartingPositions();
}
