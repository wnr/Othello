package kth.game.othello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import kth.game.othello.board.BoardFactory;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.board.OthelloBoardHandlerFactory;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.movestrategy.GreedyStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.player.turndecider.Rotation;
import kth.game.othello.player.turndecider.TurnDecider;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreImpl;
import kth.game.othello.score.ScoreItem;

/**
 * Implementation of the OthelloFactory interface.
 *
 * @author Lucas Wiener
 */
public class OthelloFactoryImpl implements OthelloFactory {
	@Override
	public Othello createComputerGame() {
		MoveStrategy greedyMoveStrategy = new GreedyStrategy(new OthelloBoardHandlerFactory());
		Player computer1 = new ComputerPlayer("computer1", "Bottler", greedyMoveStrategy);
		Player computer2 = new ComputerPlayer("computer2", "Bigbot", greedyMoveStrategy);
		PlayerHandler playerHandler = createPlayerHandler(computer1, computer2);
		OthelloBoardHandler boardHandler = createOthelloBoardHandler(computer1, computer2);
		Score score = createScore(boardHandler.getBoard().getNodes(), computer1, computer2);
		return new OthelloImpl(boardHandler, playerHandler, score);
	}

	@Override
	public Othello createHumanGame() {
		HumanPlayer human1 = new HumanPlayer("human1", "HeatoN");
		HumanPlayer human2 = new HumanPlayer("human2", "Kungen");
		PlayerHandler playerHandler = createPlayerHandler(human1, human2);
		OthelloBoardHandler boardHandler = createOthelloBoardHandler(human1, human2);
		Score score = createScore(boardHandler.getBoard().getNodes(), human1, human2);
		return new OthelloImpl(boardHandler, playerHandler, score);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		MoveStrategy greedyMoveStrategy = new GreedyStrategy(new OthelloBoardHandlerFactory());
		Player human = new HumanPlayer("human", "HeatoN");
		Player computer = new ComputerPlayer("computer", "Bottler", greedyMoveStrategy);
		PlayerHandler playerHandler = createPlayerHandler(human, computer);
		OthelloBoardHandler boardHandler = createOthelloBoardHandler(human, computer);
		Score score = createScore(boardHandler.getBoard().getNodes(), human, computer);
		return new OthelloImpl(boardHandler, playerHandler, score);
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		return null;
	}

	private OthelloBoardHandler createOthelloBoardHandler(Player player1, Player player2) {
		BoardImpl board = new BoardFactory().createOthelloBoard(player1, player2);
		return new OthelloBoardHandler(board);
	}

	private PlayerHandler createPlayerHandler(Player... players) {
		List<Player> playerList = Arrays.asList(players);
		List<String> playerIdList = playerList.stream().map(Player::getId).collect(Collectors.toList());
		TurnDecider turnDecider = new Rotation(playerIdList);
		return new PlayerHandler(playerList, turnDecider);
	}

	private Score createScore(List<Node> nodes, Player... players) {

		// Begin by calculating the initial score for all the players
		Map<String, Integer> initialScores = new HashMap<>();
		for (Player p : players) {
			initialScores.put(p.getId(), 0);
		}
		nodes.stream().filter(Node::isMarked).forEach(node -> {
			String playerId = node.getOccupantPlayerId();
			initialScores.put(playerId, (initialScores.containsKey(playerId)) ? initialScores.get(playerId) + 1 : 1);
		});

		// Create the score items with the initial score
		List<ScoreItem> scores = new ArrayList<>();
		for (Player player : players) {
			scores.add(new ScoreItem(player.getId(), initialScores.get(player.getId())));
		}
		ScoreImpl score = new ScoreImpl(scores);

		// Here we make sure that score listens on each node
		for (Node node : nodes) {
			node.addObserver(score);
		}
		return score;
	}
}
