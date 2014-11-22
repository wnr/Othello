package kth.game.othello.player.turnrotator;

import java.util.List;

import kth.game.othello.Othello;

/**
 * Interface used for keeping track of the next player in turn that can make a valid move.
 *
 * @author Mathias Lindblom
 */
public interface TurnRotator {

	/**
	 * Will update the next player in turn that is able to make a move. Will return a list containing the ID´ of the
	 * players that had their turn skipped in order from the the first skipped turn to the last. A turn is skipped when
	 * the player in turn is not able to make a move. If no player is skipped or if no player can make a move, the
	 * returned list will be empty.
	 * 
	 * @param othello The othello game to check for valid moves TODO: Will become a OthelloBoardInspector
	 * @return A list of ID´s of the players that were in turn but were skipped since they could not make a move.
	 */
	List<String> updatePlayerInTurn(Othello othello);

	/**
	 * Sets the first player to make a turn.
	 *
	 * @param playerId The ID of the player that shall be the starting player.
	 */
	void setFirstPlayerInTurn(String playerId);

	/**
	 * Will return a ordered list of the players starting from the next player in turn and ending with the last player
	 * that will have a turn. Each player will be represented exactly once in the list unless no player is currently the
	 * next player in turn. Does not take in consideration if a player can not move or if a player has several moves
	 * before another player has a turn.
	 * 
	 * @return A list of players, in order starting from the next player in turn and ending with the last player in
	 *         turn. Returns a empty list if no player is currently in turn.
	 */
	List<String> getAllPlayersInTurnOrder();

	/**
	 *
	 * @return The next player in turn that has at least one valid move.
	 */
	String getPlayerInTurn();
}
