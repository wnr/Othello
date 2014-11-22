package kth.game.othello.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import kth.game.othello.Othello;
import kth.game.othello.player.turnrotator.TurnRotator;

/**
 * A handler with the overall responsibility regarding the players in the game.
 * 
 * @author Mathias Lindblom
 */
public class PlayerHandler {
	private final List<Player> players;
	private final TurnRotator turnRotator;

	/**
	 * Constructor stores the players and the {@link TurnRotator} to be used when deciding which player is the next one
	 * to make a move.
	 *
	 * @param players The list of players to play the game.
	 * @param turnRotator The {@link TurnRotator} to be used by the handler.
	 */
	public PlayerHandler(List<Player> players, TurnRotator turnRotator) {
		this.players = players;
		this.turnRotator = turnRotator;
	}

	/**
	 * Sets the starting player according to the given player ID.
	 * 
	 * @param playerId The player ID that corresponds to the starting player.
	 *
	 * @return The {@link Player} that was selected using the given player ID.
	 */
	public Player setStartingPlayer(String playerId) {
		turnRotator.setFirstPlayerInTurn(playerId);
		return getPlayerInTurn();
	}

	/**
	 * 
	 * @return A copy of the list of players.
	 */
	public List<Player> getPlayers() {
		return new ArrayList<>(players);
	}

	/**
	 * Will return a ordered list of the players starting from the next player in turn and ending with the last player
	 * that will have a turn. Each player will be represented exactly once in the list unless no player is currently the
	 * next player in turn. Does not take in consideration if a player can not move or if a player has several moves
	 * before another player has a turn.
	 *
	 * @return A list of players, in order starting from the next player in turn and ending with the last player in
	 *         turn. Returns a empty list if no player is currently in turn.
	 */
	public List<Player> getAllPlayersInTurnOrder() {
		List<String> playerIds = turnRotator.getAllPlayersInTurnOrder();

		return getPlayersFromIds(playerIds);
	}

	/**
	 * The same as {@link PlayerHandler#getAllPlayersInTurnOrder} but returns ID´s instead of the actual players.
	 *
	 * @return A list of player ID´s, in order starting from the next player in turn and ending with the last player in
	 *         turn. Returns a empty list if no player is currently in turn.
	 */
	public List<String> getPlayerIdsInTurnOrder() {
		return turnRotator.getAllPlayersInTurnOrder();
	}

	/**
	 * 
	 * @return A list of all playerIds
	 */
	public List<String> getPlayerIds() {
		return getPlayerIdsFromPlayers(players);
	}

	/**
	 * Returns the player corresponding the given player ID.
	 * 
	 * @param playerId The ID of the player.
	 * @return The {@link Player} matching the player ID.
	 * @throws java.lang.IllegalArgumentException If playerId does not match a player in the list of players.
	 */
	public Player getPlayerFromId(String playerId) {
		Player player = getPlayerFromIdPrivate(playerId);
		if (player == null) {
			throw new IllegalArgumentException("Unknown Player ID: " + playerId);
		}
		return player;
	}

	/**
	 * Will update the next player in turn that is able to make a move. Will return a list containing the ID´ of the
	 * players that had their turn skipped in order from the the first skipped turn to the last. A turn is skipped when
	 * the player in turn is not able to make a move. If no player is skipped or if no player can make a move. The
	 * returned list will be empty.
	 *
	 * @param othello The othello game to check for valid moves TODO: Will become a OthelloBoardInspector
	 * @return A list of players that were in turn but were skipped since they could not make a move.
	 */
	public List<Player> updatePlayerInTurn(Othello othello) {
		return getPlayersFromIds(turnRotator.updatePlayerInTurn(othello));
	}

	/**
	 * 
	 * @return The player in turn. Null if no player is in turn.
	 */
	public Player getPlayerInTurn() {
		return getPlayerFromIdPrivate(turnRotator.getPlayerInTurn());
	}

	/**
	 * 
	 * @return The number of players.
	 */
	public int getNumPlayers() {
		return getPlayers().size();
	}

	/**
	 *
	 * @return A random player.
	 */
	public Player getRandomPlayer() {
		return players.get(new Random().nextInt(players.size()));
	}

	private List<String> getPlayerIdsFromPlayers(List<Player> players) {
		return players.stream().map(Player::getId).collect(Collectors.toList());
	}

	private List<Player> getPlayersFromIds(List<String> playerIds) {
		List<Player> playersInTurn = new ArrayList<>();
		for (int i = 0; i < playerIds.size(); i++) {
			playersInTurn.add(getPlayerFromId(playerIds.get(i)));
		}
		return playersInTurn;
	}

	/**
	 * Like {@link PlayerHandler#getPlayerFromId} but returns null instead of throwing an exception if the ID does not
	 * match a player.
	 * 
	 * @param playerId The id to convert to a {@link Player}.
	 * @return The player matching the ID. Null if no match was found.
	 */
	private Player getPlayerFromIdPrivate(String playerId) {
		if (playerId == null) {
			return null;
		}
		Optional<Player> oPlayer = getPlayers().stream().filter(t -> t.getId().equals(playerId)).findAny();
		if (oPlayer.isPresent()) {
			return oPlayer.get();
		}
		return null;
	}
}
