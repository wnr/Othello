package kth.game.othello.player.startpositiondecider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This {@link StartPositionDecider} organizes the starting positions at random.
 *
 * @author Mathias Lindblom
 */
public class Random implements StartPositionDecider {

	private final List<String> players;

	/**
	 *
	 * @param players The list of players that will play the game.
	 */
	public Random(List<String> players) {
		this.players = players;
	}

	@Override
	public List<String> getStartingPositions() {
		List<String> startingPositions = new ArrayList<>(players);
		Collections.shuffle(startingPositions);
		return startingPositions;
	}
}
