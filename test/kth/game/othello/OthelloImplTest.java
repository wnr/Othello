package kth.game.othello;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.board.BoardHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;

import org.junit.Assert;
import org.junit.Test;

public class OthelloImplTest {

	@Test
	public void moveTest() throws NoSuchFieldException, IllegalAccessException {
		BoardHandler obhMock = mock(BoardHandler.class);
		PlayerHandler phMock = mock(PlayerHandler.class);

		OthelloImpl othello1 = new OthelloImpl(obhMock, phMock, null);

		Player playerMock = mock(Player.class);
		String playerId1 = "playerId1";
		String nodeId = "nodeId";
		List<Node> nodeList = new ArrayList<>();
		nodeList.add(mock(Node.class));

		when(obhMock.move(playerId1, nodeId)).thenReturn(nodeList);
		when(phMock.getPlayerInTurn()).thenReturn(playerMock);
		when(playerMock.getId()).thenReturn(playerId1);

		List<Node> returnList = othello1.move(playerId1, nodeId);
		Assert.assertEquals(returnList, nodeList);
		verify(phMock).updatePlayerInTurn(othello1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void moveWithNoPlayerInTurnExceptionTest() {
		PlayerHandler phMock = mock(PlayerHandler.class);
		OthelloImpl othello = new OthelloImpl(null, phMock, null);
		when(phMock.getPlayerInTurn()).thenReturn(null);
		othello.move("playerId", "nodeId");
	}

	@Test(expected = IllegalArgumentException.class)
	public void moveWrongPlayerExceptionTest() {
		PlayerHandler phMock = mock(PlayerHandler.class);
		Player playerMock = mock(Player.class);
		OthelloImpl othello = new OthelloImpl(null, phMock, null);

		when(phMock.getPlayerInTurn()).thenReturn(playerMock);
		when(playerMock.getId()).thenReturn("playerId1");
		othello.move("playerId2", "nodeId");
	}

}