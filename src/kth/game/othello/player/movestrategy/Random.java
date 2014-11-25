package kth.game.othello.player.movestrategy;

import kth.game.othello.Othello;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.board.OthelloBoardHandlerFactory;

import java.util.List;

/**
 * A really move strategy that will make the move by random. It will simply choose a random node among the possible
 * nodes to move to.
 *
 * @author Lucas Wiener
 */
public class Random implements MoveStrategy {
    OthelloBoardHandlerFactory boardHandlerFactory;

    /**
     * Creates the random move strategy instance.
     *
     * @param boardHandlerFactory The factory to create othello board handlers.
     */
    public Random(OthelloBoardHandlerFactory boardHandlerFactory) {
        this.boardHandlerFactory = boardHandlerFactory;
    }

    @Override
    public String getName() {
        return "Random";
    }

    @Override
    public Node move(String playerId, Othello othello) {
        // TODO: Make a copy of the Board before creating a board handler with it.
        BoardImpl board = (BoardImpl) othello.getBoard();
        OthelloBoardHandler boardHandler = boardHandlerFactory.createOthelloBoardHandler(board);
        List<Node> validMoves = boardHandler.getValidMoves(playerId);
        return validMoves.get(new java.util.Random().nextInt(validMoves.size()));
    }
}