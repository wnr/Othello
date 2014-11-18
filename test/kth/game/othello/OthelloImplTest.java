package kth.game.othello;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.player.Player;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class OthelloImplTest {


	@Test
	public void isMoveValidTest() {
		OthelloBoardHandler obhMock = Mockito.mock(OthelloBoardHandler.class);
		Node nodeMock1 = Mockito.mock(Node.class);
		Node nodeMock2 = Mockito.mock(Node.class);
		OthelloImpl othello = new OthelloImpl(obhMock, null, null);

		String playerId = "playerId";
		String nodeId = "nodeId";
		String nodeIdWrong = "otherNodeId";

		List<Node> nodeList = new ArrayList<>();

		when(nodeMock1.getId()).thenReturn(nodeId);
		when(nodeMock2.getId()).thenReturn(nodeIdWrong);
		when(obhMock.getValidMoves(playerId)).thenReturn(nodeList);
		Assert.assertFalse(othello.isMoveValid(playerId, nodeId));

		nodeList.add(nodeMock2);
		Assert.assertFalse(othello.isMoveValid(playerId, nodeId));

		nodeList.add(nodeMock1);
		Assert.assertTrue(othello.isMoveValid(playerId, nodeId));
	}

	@Test
	public void moveTest() throws NoSuchFieldException, IllegalAccessException {
		OthelloBoardHandler obhMock = Mockito.mock(OthelloBoardHandler.class);
		PlayerHandler phMock = Mockito.mock(PlayerHandler.class);

		OthelloImpl othello1 = new OthelloImpl(obhMock, phMock, null);

		Player playerMock = Mockito.mock(Player.class);
		String playerId1 = "playerId1";
		String nodeId = "nodeId";
		List<Node> nodeList = new ArrayList<>();
		nodeList.add(Mockito.mock(Node.class));

		when(obhMock.move(playerId1, nodeId)).thenReturn(nodeList);
		when(phMock.getPlayerInTurn()).thenReturn(playerMock);
		when(playerMock.getId()).thenReturn(playerId1);

		List<Node> returnList = othello1.move(playerId1, nodeId);
		Assert.assertEquals(returnList, nodeList);
		verify(phMock).updatePlayerInTurn(othello1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void moveExceptionTest() {
		PlayerHandler phMock = Mockito.mock(PlayerHandler.class);
		OthelloImpl othello = new OthelloImpl(null, phMock, null);
		when(phMock.getPlayerInTurn()).thenReturn(null);
		othello.move("playerId", "nodeId");
	}

	@Test(expected = IllegalArgumentException.class)
	public void moveExceptionTest2() {
		PlayerHandler phMock = Mockito.mock(PlayerHandler.class);
		Player playerMock = Mockito.mock(Player.class);
		OthelloImpl othello = new OthelloImpl(null, phMock, null);

		when(phMock.getPlayerInTurn()).thenReturn(playerMock);
		when(playerMock.getId()).thenReturn("playerId1");
		othello.move("playerId2", "nodeId");
	}

}