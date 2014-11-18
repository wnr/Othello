package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import kth.game.othello.player.Player;

/**
 * Handler responsible of holding the list of players and keeping track of who's turn it is.
 * 
 * @author mathiaslindblom
 */
public class PlayerHandler {
	private static final int NO_PLAYER_IN_TURN_INDEX = -1;

	private final List<Player> players;
	private int playerInTurnIndex;

	/**
	 * Constructor only stores the players and sets the player indirectly to null to indicate that the game has not
	 * started yet.
	 * 
	 * @param players The list of players to play the game
	 */
	PlayerHandler(List<Player> players) {
		this.players = players;
		playerInTurnIndex = NO_PLAYER_IN_TURN_INDEX;
	}

	/**
	 * Randomly sets a player as the playerInTurn and returns that player.
	 * 
	 * @return The starting player
	 */
	public Player initiateStartingPlayer() {
		Random random = new Random();
		return initiateStartingPlayer(getPlayerIds().get(random.nextInt(getNumPlayers())));
	}

	/**
	 * Sets the playerInTurn according to the given playerId.
	 * 
	 * @param playerId The playerId that corresponds to the starting player
	 * @return The starting player
	 */
	public Player initiateStartingPlayer(String playerId) {
		Player player = getPlayer(playerId);
		setPlayerInTurn(player);
		return player;
	}

	/**
	 * 
	 * @return A copy of the list of players
	 */
	public List<Player> getPlayers() {
		return new ArrayList<>(players);
	}

	/**
	 * 
	 * @return A list of players, in order starting from the next playerInTurn. If no player is in turn, then returns
	 *         the same as getPlayers()
	 */
	public List<Player> getPlayersInTurnOrder() {
		List<Player> returnList = new ArrayList<>();
		if (getPlayerInTurn() == null) {
			return getPlayers();
		}
		for (int i = playerInTurnIndex; i < playerInTurnIndex + getNumPlayers(); i++) {
			returnList.add(getPlayers().get(Math.floorMod(i, getNumPlayers())));
		}
		return returnList;
	}

	/**
	 * 
	 * @return A list of all playerIds
	 */
	public List<String> getPlayerIds() {
		return getPlayerIds(getPlayers());
	}

	/**
	 *
	 * @return A list of players Ids, in order starting from the next playerInTurn. If no player is in turn, then
	 *         returns the same as getPlayersIds()
	 */
	public List<String> getPlayerIdsInTurnOrder() {
		List<Player> playersInTurnOrder = getPlayersInTurnOrder();
		return getPlayerIds(playersInTurnOrder);
	}

	/**
	 * Returns the player corresponding the the given playerId.
	 * 
	 * @param playerId The ID of the player
	 * @return The {@link Player} matching the playerId
	 * @throws java.lang.IllegalArgumentException If playerId does not match a player in the list
	 */
	public Player getPlayer(String playerId) {
		Player player = getPlayers().stream().filter(t -> t.getId().equals(playerId)).findAny().get();
		if (player == null) {
			throw new IllegalArgumentException("Unknown Player ID: " + playerId);
		}
		return player;
	}

	/**
	 * Updates the next player in turn. Goes through the list of players until someone can make a move and updates that
	 * player to the next playerInTurn. If No player can play, then nextPlayerInTurn is indirectly set to null.
	 * 
	 * @param othello The game, needed for checking which player is the next to play
	 */
	public void updatePlayerInTurn(Othello othello) {
		if (!othello.isActive()) {
			setPlayerInTurn(null);
		} else {
			List<Player> playersInTurnOrder = getPlayersInTurnOrder();
			for (int i = 1; i < getNumPlayers(); i++) {
				Player player = playersInTurnOrder.get(i);
				if (othello.hasValidMove(player.getId())) {
					setPlayerInTurn(player);
					return;
				}
			}
			// Getting here means that the current player is the only player with moves left. No need to update
		}
	}

	/**
	 * 
	 * @return The player in turn. Null if no player is in turn.
	 */
	public Player getPlayerInTurn() {
		if (!isPlayerInTurn()) {
			return null;
		}
		return players.get(playerInTurnIndex);
	}

	public int getNumPlayers() {
		return getPlayers().size();
	}

	/**
	 * 
	 * @return True if a player is in turn. False otherwise.
	 */
	public boolean isPlayerInTurn() {
		return playerInTurnIndex != NO_PLAYER_IN_TURN_INDEX;
	}

	private List<String> getPlayerIds(List<Player> players) {
		return players.stream().map(Player::getId).collect(Collectors.toList());
	}

	private void setPlayerInTurn(Player player) {
		setPlayerInTurnIndex(getListIndexFromPlayer(player));
	}

	private void setPlayerInTurnIndex(int index) {
		playerInTurnIndex = index;
	}

	private int getListIndexFromPlayer(Player player) {
		for (int i = 0; i < getNumPlayers(); i++) {
			if (player == getPlayers().get(i)) {
				return i;
			}
		}
		return -1;
	}

	private int getPlayerInTurnIndex() {
		return playerInTurnIndex;
	}
}
