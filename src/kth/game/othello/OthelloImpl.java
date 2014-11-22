package kth.game.othello;

import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.score.Score;

/**
 * The implementation of the interface Othello whose purpose is to represent an Othello game.
 *
 * @author Mathias Lindblom
 */
public class OthelloImpl implements Othello {
	private final PlayerHandler playerHandler;
	OthelloBoardHandler othelloBoardHandler;
	AI ai;

	/**
	 * Constructs an Othello game instance.
	 *
	 * @param othelloBoardHandler The handler responsible of both holding the game board and the Othello board logic
	 * @param playerHandler The handler responsible of both player logic and holding the players to play Othello
	 * @param ai The ai to be used by the computer players. Required if any computer players.
	 */
	public OthelloImpl(OthelloBoardHandler othelloBoardHandler, PlayerHandler playerHandler, AI ai) {
		this.othelloBoardHandler = othelloBoardHandler;
		this.playerHandler = playerHandler;
		this.ai = ai;
	}

	@Override
	public Board getBoard() {
		return othelloBoardHandler.getBoard();
	}

	@Override
	public Player getPlayerInTurn() {
		return playerHandler.getPlayerInTurn();
	}

	@Override
	public List<Player> getPlayers() {
		return playerHandler.getPlayers();
	}

	@Override
	public Score getScore() {
		return null;
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
		return othelloBoardHandler.hasAnyAValidMove(playerHandler.getPlayerIds());
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return othelloBoardHandler.isMoveValid(playerId, nodeId);
	}

	@Override
	public List<Node> move() {
		Player playerInTurn = playerHandler.getPlayerInTurn();
		if (playerInTurn.getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Next player in turn is not a computer");
		}

		String nodeId = ai.getMoveWithMostSwaps(playerInTurn.getId());

		return move(playerInTurn.getId(), nodeId);
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		Player playerInTurn = playerHandler.getPlayerInTurn();
		if (playerInTurn == null) {
			throw new IllegalArgumentException("The move is invalid. Might be that the game is over or not started");
		} else if (!playerInTurn.getId().equals(playerId)) {
			throw new IllegalArgumentException("The move is invalid. Not this players turn");
		}
		List<Node> swappedNodes = othelloBoardHandler.move(playerId, nodeId);

		playerHandler.updatePlayerInTurn(this);

		return swappedNodes;
	}

	@Override
	public void start() {
		start(playerHandler.getRandomPlayer().getId());
	}

	@Override
	public void start(String playerId) {
		playerHandler.setStartingPlayer(playerId);

		othelloBoardHandler.initializeStartingPositions(playerHandler.getPlayerIdsInTurnOrder());
	}
}
