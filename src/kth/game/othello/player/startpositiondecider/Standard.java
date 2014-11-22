package kth.game.othello.player.startpositiondecider;

import java.util.ArrayList;
import java.util.List;

/**
 * This {@link StartPositionDecider} just organizes the starting positions in the same order as the given list of
 * players.
 * 
 * @author Mathias Lindblom
 */
public class Standard implements StartPositionDecider {

	private final List<String> players;

	/**
	 *
	 * @param players The list of players that will play the game.
	 */
	public Standard(List<String> players) {
		this.players = players;
	}

	@Override
	public List<String> getStartingPositions() {
		return new ArrayList<>(players);
	}
}
