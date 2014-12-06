package kth.game.othello.player.turndecider;

import java.util.List;

import kth.game.othello.Othello;

/**
 * Interface used for keeping track of the next player in turn that can make a valid move.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public interface TurnDecider {

	/**
	 * Will update the next player in turn that is able to make a move. Will return a list containing the ID´ of the
	 * players that had their turn skipped in order from the the first skipped turn to the last. A turn is skipped when
	 * the player in turn is not able to make a move. If no player is skipped, the returned list will be empty.
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
	 *
	 * @return The next player in turn that has at least one valid move.
	 */
	String getPlayerInTurn();
}
