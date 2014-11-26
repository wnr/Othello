package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoardHandlerFactory;
import kth.game.othello.board.factory.Diamond;
import kth.game.othello.player.ComputerPlayer;
import kth.game.othello.player.HumanPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;
import kth.game.othello.player.movestrategy.GreedyStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;

import org.junit.Assert;
import org.junit.Test;

public class OthelloLab2IT {

	// TODO: generate other strategies, maybe random
	private MoveStrategy getNewMoveStrategy() {
		return new GreedyStrategy(new OthelloBoardHandlerFactory());
	}

	private OthelloFactory getOthelloFactory() {
		return new OthelloFactoryImpl();
	}

	private Player createComputer(String name) {
		return new ComputerPlayer(UUID.randomUUID().toString(), name, getNewMoveStrategy());
	}

	// TODO: We might want to use this when getOthelloFactory().createGame is implemented
	private Player createHuman(String name) {
		return new HumanPlayer(UUID.randomUUID().toString(), name);
	}

	private void makeNumberOfComputerMoves(int numberOfMoves, Othello othello) {
		for (int i = 0; i < numberOfMoves; i++) {
			Assert.assertEquals(Type.COMPUTER, othello.getPlayerInTurn().getType());
			othello.move();
		}
	}

	private void makeAHumanMove(Othello othello, Player human) {
		for (Node node : othello.getBoard().getNodes()) {
			if (othello.isMoveValid(human.getId(), node.getId())) {
				othello.move(human.getId(), node.getId());
				return;
			}
		}
		throw new IllegalStateException();
	}

	@Test
	public void studyTheInitialScoreTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(0).getId();
		othello.start();
		Assert.assertEquals(2, othello.getScore().getPoints(playerId));
	}

	@Test
	public void studyTheScoreAfterAMoveTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(1).getId();
		othello.start(playerId);
		othello.move(playerId, othello.getBoard().getNode(5, 3).getId());
		Assert.assertEquals(4, othello.getScore().getPoints(playerId));
	}

	@Test
	public void twoHumanOnAClassicalBoardTest() {

		Othello othello = getOthelloFactory().createHumanGame();
		String playerId1 = othello.getPlayers().get(0).getId();
		String playerId2 = othello.getPlayers().get(1).getId();
		othello.start(othello.getPlayers().get(0).getId());

		// Make 8 human moves
		for (int i = 0; i < 8; i++) {
			makeAHumanMove(othello, othello.getPlayerInTurn());
		}

		// Check the score
		Assert.assertEquals(6, othello.getScore().getPoints(playerId1));
		Assert.assertEquals(6, othello.getScore().getPoints(playerId2));

		Assert.assertTrue(othello.isActive());
	}

	@Test
	public void threeComputersOnADiamondBoardTest() {
		List<Player> players = new ArrayList<Player>();
		players.add(createComputer("black"));
		players.add(createComputer("white"));
		players.add(createComputer("orange"));
		Diamond diamond = new Diamond();
		Othello othello = getOthelloFactory().createGame(diamond.getNodes(11, players), players);
		othello.start();
		while (othello.isActive()) {
			othello.move();
		}

		// Check the score
		// TODO: When getOthelloFactory().createGame is implemented, check what score should be the correct ones
		Assert.assertEquals(6, othello.getScore().getPoints("black"));
		Assert.assertEquals(6, othello.getScore().getPoints("white"));
		Assert.assertEquals(6, othello.getScore().getPoints("orange"));

		Assert.assertFalse(othello.isActive());
	}

	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		othello.start(othello.getPlayers().get(0).getId());

		while (othello.isActive()) {
			// Make some moves
			for (int i = 0; othello.isActive() && i < 20; i++) {
				makeNumberOfComputerMoves(1, othello);
			}

			// Change one of the computers strategy
			othello.getPlayers().get(0).setMoveStrategy(getNewMoveStrategy());

		}
		Assert.assertFalse(othello.isActive());
	}
}
