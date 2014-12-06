package kth.game.othello.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Responsible for storing and handling board history moves. Can push (add) and pop (undo) moves.
 *
 * @author Mathias Lindblom
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class BoardHistory {
    private Stack<List<Node>> moves;
    private final BoardImpl board;

    /**
     * Creates an instance of the board history class.
     *
     * @param board The board that the instance should handle the history for.
     */
    public BoardHistory(BoardImpl board) {
        this.board = board;
        moves = new Stack<>();
    }

    /**
     * Saves a move history. Now the board history will remember this as the last move, and will be able to undo the
     * move. This method should be called before the player id has actually been changed in the board by the move.
     *
     * @param move The list of nodes that will be swapped by this move.
     */
    public void push(List<Node> move) {
        moves.push(new ArrayList<>(move));
    }

    /**
     * Will undo the previously added move. Will set the occupant player id's of the nodes to the player id's before the
     * move was performed. The observers of the nodes swapped will be notified as usual. If there is no history to pop,
     * nothing will happen.
     */
    public void pop() {
        if (moves.size() == 0) {
            return;
        }

        List<Node> move = moves.pop();
        Collections.reverse(move);

        for (Node node : move) {
            board.occupyNode(node.getId(), node.getOccupantPlayerId());
        }
    }
}
