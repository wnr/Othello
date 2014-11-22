package kth.game.othello.player.turnrotator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kth.game.othello.Othello;

/**
 * Given a list of playerIds, the {@link TurnRotator} will loop the player turns according to the order the players had
 * in the list given at initialization.
 * 
 * @author Mathias Lindblom
 */
public class DefaultTurnRotator implements TurnRotator {
	private static final int NO_PLAYER_IN_TURN = -1;

	List<String> players;
	int playerInTurnIndex;

	public DefaultTurnRotator(List<String> players) {
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
	public List<String> getAllPlayersInTurnOrder() {
		if (noPlayerIsInTurn()) {
			return new ArrayList<>();
		}
		List<String> playersInTurn = new ArrayList<>(players);
		Collections.rotate(playersInTurn, players.size() - playerInTurnIndex);
		return playersInTurn;
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
