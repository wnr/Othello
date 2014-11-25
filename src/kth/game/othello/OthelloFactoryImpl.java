package kth.game.othello;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kth.game.othello.board.BoardFactory;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.turndecider.Rotation;
import kth.game.othello.player.turndecider.TurnDecider;

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
		PlayerHandler playerHandler = createPlayerHandler(computer1, computer2);
		OthelloBoardHandler boardHandler = createOthelloBoardHandler(computer1, computer2);
		return new OthelloImpl(boardHandler, playerHandler, new AI(boardHandler));
	}

	@Override
	public Othello createHumanGame() {
		HumanPlayer human1 = new HumanPlayer("human1", "HeatoN");
		HumanPlayer human2 = new HumanPlayer("human2", "Kungen");
		PlayerHandler playerHandler = createPlayerHandler(human1, human2);
		OthelloBoardHandler boardHandler = createOthelloBoardHandler(human1, human2);
		return new OthelloImpl(boardHandler, playerHandler, null);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		Player human = new HumanPlayer("human", "HeatoN");
		Player computer = new ComputerPlayer("computer", "Bottler");
		PlayerHandler playerHandler = createPlayerHandler(human, computer);
		OthelloBoardHandler boardHandler = createOthelloBoardHandler(human, computer);
		return new OthelloImpl(boardHandler, playerHandler, new AI(boardHandler));
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		return null;
	}

	private OthelloBoardHandler createOthelloBoardHandler(Player player1, Player player2) {
		BoardImpl board = BoardFactory.createOthelloBoard(player1, player2);
		return new OthelloBoardHandler(board);
	}

	private PlayerHandler createPlayerHandler(Player... players) {
		List<Player> playerList = Arrays.asList(players);
		List<String> playerIdList = playerList.stream().map(Player::getId).collect(Collectors.toList());
		TurnDecider turnDecider = new Rotation(playerIdList);
		return new PlayerHandler(playerList, turnDecider);
	}
}
