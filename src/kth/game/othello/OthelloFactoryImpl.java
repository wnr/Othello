package kth.game.othello;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kth.game.othello.board.BoardFactory;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.board.RectangularBoard;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.startpositiondecider.Standard;
import kth.game.othello.player.startpositiondecider.StartPositionDecider;
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
		OthelloBoardHandler boardHandler = createOthelloBoardHandler();
		PlayerHandler playerHandler = createPlayerHandler(new ComputerPlayer("computer1", "Bottler"),
				new ComputerPlayer("computer2", "Bigbot"));

		return new OthelloImpl(boardHandler, playerHandler, new AI(boardHandler));
	}

	@Override
	public Othello createHumanGame() {
		OthelloBoardHandler boardHandler = createOthelloBoardHandler();
		PlayerHandler playerHandler = createPlayerHandler(new HumanPlayer("human1", "HeatoN"), new HumanPlayer(
				"human2", "Kungen"));
		return new OthelloImpl(boardHandler, playerHandler, null);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		OthelloBoardHandler boardHandler = createOthelloBoardHandler();
		PlayerHandler playerHandler = createPlayerHandler(new HumanPlayer("human", "HeatoN"), new ComputerPlayer(
				"computer", "Bottler"));
		return new OthelloImpl(boardHandler, playerHandler, new AI(boardHandler));
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		return null;
	}

	private OthelloBoardHandler createOthelloBoardHandler() {
		RectangularBoard board = BoardFactory.createOthelloBoard();
		return new OthelloBoardHandler(board);
	}

	private PlayerHandler createPlayerHandler(Player... players) {
		List<Player> playerList = Arrays.asList(players);
		List<String> playerIdList = playerList.stream().map(Player::getId).collect(Collectors.toList());
		TurnDecider turnDecider = new Rotation(playerIdList);
		StartPositionDecider startPositionDecider = new Standard(playerIdList);
		return new PlayerHandler(playerList, turnDecider, startPositionDecider);
	}
}
