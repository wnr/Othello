package kth.game.othello;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.player.Player;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author mathiaslindblom
 */
public class PlayerHandlerTest {

	private static final String[] Ids = { "playerId1", "playerId2", "playerId3" };

	@Test
	public void constructorTest() {
		PlayerHandler ph = createPlayerHandler(null, null);
		Assert.assertEquals(null, ph.getPlayerInTurn());
	}

	@Test
	public void initiateStartingPlayerTest() {
		PlayerHandler ph = createPlayerHandler(Ids[0], Ids[1]);
		ph.initiateStartingPlayer(Ids[1]);
		Assert.assertEquals(Ids[1], ph.getPlayerInTurn().getId());
	}

	@Test
	public void getPlayersInTurnOrderTest() {
		PlayerHandler ph = createPlayerHandler(Ids);
		ph.initiateStartingPlayer(Ids[1]);
		List<Player> players = ph.getPlayersInTurnOrder();
		Assert.assertEquals(Ids[1], players.get(0).getId());
		Assert.assertEquals(Ids[2], players.get(1).getId());
		Assert.assertEquals(Ids[0], players.get(2).getId());
		Assert.assertEquals(3, players.size());
	}

	@Test
	public void getPlayersIdsInTurnOrder() {
		PlayerHandler ph = createPlayerHandler(Ids);
		ph.initiateStartingPlayer(Ids[1]);
		List<String> players = ph.getPlayerIdsInTurnOrder();
		Assert.assertEquals(Ids[1], players.get(0));
		Assert.assertEquals(Ids[2], players.get(1));
		Assert.assertEquals(Ids[0], players.get(2));
		Assert.assertEquals(3, players.size());
	}

	@Test
	public void getPlayerTest() {
		PlayerHandler ph = createPlayerHandler(Ids);
		Assert.assertEquals(Ids[1], ph.getPlayer(Ids[1]).getId());
	}

	@Test
	public void updatePlayerInTurnTest() {
		Othello othello = Mockito.mock(Othello.class);

		PlayerHandler ph = createPlayerHandler(Ids);
		when(othello.isActive()).thenReturn(false);
		ph.updatePlayerInTurn(othello);
		Assert.assertEquals(null, ph.getPlayerInTurn());

		ph = createPlayerHandler(Ids);
		ph.initiateStartingPlayer(Ids[2]);
		when(othello.isActive()).thenReturn(true);
		when(othello.hasValidMove(Ids[0])).thenReturn(false);
		when(othello.hasValidMove(Ids[1])).thenReturn(false);
		when(othello.hasValidMove(Ids[2])).thenReturn(true);
		Assert.assertEquals(Ids[2], ph.getPlayerInTurn().getId());

		ph = createPlayerHandler(Ids);
		ph.initiateStartingPlayer(Ids[2]);
		when(othello.hasValidMove(any(String.class))).thenReturn(true);

		ph.updatePlayerInTurn(othello);
		Assert.assertEquals(Ids[0], ph.getPlayerInTurn().getId());
	}

	private PlayerHandler createPlayerHandler(String... IDs) {
		List<Player> players = new ArrayList<>();
		for (int i = 0; i < IDs.length; i++) {
			Player playerMock = Mockito.mock(Player.class);
			when(playerMock.getId()).thenReturn(IDs[i]);
			players.add(playerMock);
		}
		return new PlayerHandler(players);
	}
}
