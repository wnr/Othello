package kth.game.othello;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardHandler;
import kth.game.othello.board.Node;
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
	private final BoardHandler boardHandler;
	private final Score score;

	/**
	 * Constructs an Othello game instance.
	 *
	 * @param boardHandler The handler responsible of both holding the game board and the Othello board logic
	 * @param playerHandler The handler responsible of both player logic and holding the players to play Othello
	 * @param score The score object responsible of storing the game score for the players
	 */
	public OthelloImpl(BoardHandler boardHandler, PlayerHandler playerHandler, Score score) {
		this.boardHandler = boardHandler;
		this.playerHandler = playerHandler;
		this.score = score;
	}

	@Override
	public Board getBoard() {
		return boardHandler.getBoard();
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
		return score;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return boardHandler.getNodesToSwap(playerId, nodeId);
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return !boardHandler.getValidMoves(playerId).isEmpty();
	}

	@Override
	public boolean isActive() {
		return boardHandler.hasAnyAValidMove(playerHandler.getPlayerIds());
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return boardHandler.isMoveValid(playerId, nodeId);
	}

	@Override
	public List<Node> move() {
		Player playerInTurn = playerHandler.getPlayerInTurn();
		if (playerInTurn.getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Next player in turn is not a computer");
		}

		String nodeId = playerInTurn.getMoveStrategy().move(playerInTurn.getId(), this).getId();

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
		List<Node> swappedNodes = boardHandler.move(playerId, nodeId);

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
	}
}
