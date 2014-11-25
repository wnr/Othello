package kth.game.othello.player.movestrategy;

import kth.game.othello.Othello;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.board.OthelloBoardHandlerFactory;

import java.util.List;

/**
 * A really bad move strategy that will make the move that result in the least node swaps. If there are multiple candidates
 * one Node will just be picked.
 *
 * @author Lucas Wiener
 */
public class Lowest implements MoveStrategy {
    OthelloBoardHandlerFactory boardHandlerFactory;

    /**
     * Creates the lowest move strategy instance.
     *
     * @param boardHandlerFactory The factory to create othello board handlers.
     */
    public Lowest(OthelloBoardHandlerFactory boardHandlerFactory) {
        this.boardHandlerFactory = boardHandlerFactory;
    }

    @Override
    public String getName() {
        return "Lowest";
    }

    @Override
    public Node move(String playerId, Othello othello) {
        // TODO: Make a copy of the Board before creating a board handler with it.
        BoardImpl board = (BoardImpl) othello.getBoard();
        OthelloBoardHandler boardHandler = boardHandlerFactory.createOthelloBoardHandler(board);

        List<Node> validMoves = boardHandler.getValidMoves(playerId);

        Node lowestNode = null;
        int lowestSwaps = Integer.MAX_VALUE;

        for (Node n : validMoves) {
            String nodeId = n.getId();
            int numSwaps = boardHandler.getNumSwaps(playerId, nodeId);
            if (numSwaps < lowestSwaps) {
                lowestSwaps = numSwaps;
                lowestNode = n;
            }
        }

        return lowestNode;
    }
}
