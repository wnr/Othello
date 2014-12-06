package kth.game.othello.player.turndecider;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.Othello;

/**
 * This {@link TurnDecider} will simply loop (rotate) the player turns according to the order in the player list given
 * in the constructor.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class Rotation implements TurnDecider {
	private static final int NO_PLAYER_IN_TURN = -1;

	private final List<String> players;
	private int playerInTurnIndex;

	/**
	 * The order of the players in the given list is the order that this {@link TurnDecider} will loop through turns.
	 * 
	 * @param players The players that will play the game.
	 */
	public Rotation(List<String> players) {
		playerInTurnIndex = NO_PLAYER_IN_TURN;
		this.players = players;
	}

	@Override
	public List<String> updatePlayerInTurn(Othello othello) {
		List<String> skippedTurns = new ArrayList<>();

		if (!othello.isActive()) {
			// Game might not be started or has ended. In any case. No next player available.
			playerInTurnIndex = NO_PLAYER_IN_TURN;
		} else {
			String nextPlayerInTurn;
			for (int i = Math.floorMod(playerInTurnIndex + 1, players.size()); i != playerInTurnIndex; i = Math
					.floorMod(i + 1, players.size())) {

				nextPlayerInTurn = players.get(i);
				if (othello.hasValidMove(nextPlayerInTurn)) {
					// This player can play. Set him/her as the next player in turn
					playerInTurnIndex = i;
					break;
				} else {
					// This player can not play this turn. Add to skipped turns and continue the search
					skippedTurns.add(nextPlayerInTurn);
				}
			}
		}
		return skippedTurns;
	}

	@Override
	public void setFirstPlayerInTurn(String startingPlayerId) {
		playerInTurnIndex = players.indexOf(startingPlayerId);
	}

	@Override
	public String getPlayerInTurn() {
		if (noPlayerIsInTurn()) {
			return null;
		}
		return players.get(playerInTurnIndex);
	}

	private boolean noPlayerIsInTurn() {
		return playerInTurnIndex == NO_PLAYER_IN_TURN;
	}
}
