package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.player.Player;

/**
 * The implementation of the interface Othello whose purpose is to represent an Othello game.
 * 
 * @author mathiaslindblom
 */
public class OthelloImpl implements Othello {
	OthelloBoardHandler othelloBoardHandler;

	List<Player> players;
	Player playerInTurn;

	/**
	 * Constructs an Othello game instance.
	 *
	 * @param othelloBoardHandler The handler responsible of both holding the game board and the Othello board logic
	 * @param player A player needed to play the game
	 * @param anotherPlayer The other needed player to play the game
	 */
	public OthelloImpl(OthelloBoardHandler othelloBoardHandler, Player player, Player anotherPlayer) {
		this.othelloBoardHandler = othelloBoardHandler;
		players = new ArrayList<Player>();
		players.add(player);
		players.add(anotherPlayer);
		playerInTurn = null;
	}

	@Override
	public Board getBoard() {
		return othelloBoardHandler.getBoard();
	}

	@Override
	public Player getPlayerInTurn() {
		return playerInTurn;
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return othelloBoardHandler.getNodesToSwap(playerId, nodeId);
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return !othelloBoardHandler.getValidMoves(playerId).isEmpty();
	}

	@Override
	public boolean isActive() {
		return !othelloBoardHandler.getValidMoves(players.get(0).getId()).isEmpty()
				|| !othelloBoardHandler.getValidMoves(players.get(1).getId()).isEmpty();
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		List<Node> validMovesList = othelloBoardHandler.getValidMoves(playerId);
		for (Node n : validMovesList) {
			if (n.getId().equals(nodeId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Node> move() {
		// TODO: Implement
		return null;
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		if (!playerInTurn.getId().equals(playerId)) {
			throw new IllegalArgumentException("The move is invalid. Not this players turn");
		}
		List<Node> swappedNodes = othelloBoardHandler.move(playerId, nodeId);

		if (!isActive()) {
			// The game is over
			setPlayerInTurn(null);
		} else {
			setPlayerInTurn(getOtherPlayer(getPlayerFromId(playerId)));
		}

		return swappedNodes;
	}

	@Override
	public void start() {
		Random random = new Random();
		Player startingPlayer = players.get(random.nextInt(players.size()));
		start(startingPlayer.getId());
	}

	@Override
	public void start(String playerId) {
		String secondPlayerId = getOtherPlayer(getPlayerFromId(playerId)).getId();

		setPlayerInTurn(getPlayerFromId(playerId));
		othelloBoardHandler.initializeStartingPositions(playerId, secondPlayerId);
	}

	/**
	 * Takes a playerId and return the corresponding {@link Player} object
	 * 
	 * @param playerId The id of the player
	 * @return The {@link Player} object
	 * @throws java.lang.IllegalArgumentException if the playerId does not corresponding to a player
	 */
	private Player getPlayerFromId(String playerId) {
		for (Player p : players) {
			if (p.getId().equals(playerId)) {
				return p;
			}
		}
		throw new IllegalArgumentException("PlayerId not found in player list");
	}

	/**
	 * Gets the other player that do not equal the given player
	 * 
	 * @param player The player that we DO NOT want to get
	 * @return The other player in the game
	 */
	private Player getOtherPlayer(Player player) {

		if (players.get(0).equals(player)) {
			return players.get(1);
		} else {
			return players.get(0);
		}
	}

	private void setPlayerInTurn(Player player) {
		playerInTurn = player;
	}
}
