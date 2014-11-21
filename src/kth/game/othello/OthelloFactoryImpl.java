package kth.game.othello;

import java.util.List;
import java.util.Set;

import kth.game.othello.board.BoardFactory;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.OthelloBoardHandler;
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
		Player computer1 = new ComputerPlayer("computer1", "Bottler");
		Player computer2 = new ComputerPlayer("computer2", "Bigbot");
		OthelloBoardHandler boardHandler = createOthelloBoardHandler(computer1, computer2);
		return new OthelloImpl(boardHandler, computer1, computer2, new AI(boardHandler));
	}

	@Override
	public Othello createHumanGame() {
		HumanPlayer human1 = new HumanPlayer("human1", "HeatoN");
		HumanPlayer human2 = new HumanPlayer("human2", "Kungen");
		OthelloBoardHandler boardHandler = createOthelloBoardHandler(human1, human2);
		return new OthelloImpl(boardHandler, human1, human2);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		Player human = new HumanPlayer("human", "HeatoN");
		Player computer = new ComputerPlayer("computer", "Bottler");
		OthelloBoardHandler boardHandler = createOthelloBoardHandler(human, computer);
		return new OthelloImpl(boardHandler, human, computer, new AI(boardHandler));
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		return null;
	}

	private OthelloBoardHandler createOthelloBoardHandler(Player player1, Player player2) {
		BoardImpl board = BoardFactory.createOthelloBoard(player1, player2);
		return new OthelloBoardHandler(board);
	}
}
