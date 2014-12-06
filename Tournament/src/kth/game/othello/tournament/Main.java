package kth.game.othello.tournament;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

/**
 * Start point for the Tournament Othello game module.
 */
public class Main {
    public static void main(String[] args) {
        Othello othello = new OthelloFactoryImpl().createComputerGame();
        OthelloView othelloView = OthelloViewFactory.create(othello);
        othelloView.start();
    }
}
