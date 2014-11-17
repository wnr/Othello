package kth.game.othello;

import java.util.List;
import java.util.Set;

import kth.game.othello.board.BoardFactory;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.board.RectangularBoard;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.Player;

/**
 * Implementation of the OthelloFactory interface.
 *
 * @author Lucas Wiener
 */
public class OthelloFactoryImpl implements OthelloFactory {
	@Override
	public Othello createComputerGame() {
		OthelloBoardHandler boardHandler = createOthelloBoardHandler();
		Player computer1 = new ComputerPlayer("computer1", "Bottler");
		Player computer2 = new ComputerPlayer("computer2", "Bigbot");
		return new OthelloImpl(boardHandler, computer1, computer2, new AI(boardHandler));
	}

	@Override
	public Othello createHumanGame() {
		OthelloBoardHandler boardHandler = createOthelloBoardHandler();
		HumanPlayer human1 = new HumanPlayer("human1", "HeatoN");
		HumanPlayer human2 = new HumanPlayer("human2", "Kungen");
		return new OthelloImpl(boardHandler, human1, human2);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		OthelloBoardHandler boardHandler = createOthelloBoardHandler();
		Player human = new HumanPlayer("human", "HeatoN");
		Player computer = new ComputerPlayer("computer", "Bottler");
		return new OthelloImpl(boardHandler, human, computer, new AI(boardHandler));
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		return null;
	}

	private OthelloBoardHandler createOthelloBoardHandler() {
		RectangularBoard board = BoardFactory.createOthelloBoard();
		return new OthelloBoardHandler(board);
	}
}
