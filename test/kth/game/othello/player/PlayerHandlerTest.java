package kth.game.othello.player;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kth.game.othello.player.turnrotator.TurnRotator;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author mathiaslindblom
 */
public class PlayerHandlerTest {

	private static final String[] Ids = { "playerId1", "playerId2", "playerId3" };

	@Test
	public void initiateStartingPlayerTest() throws NoSuchFieldException, IllegalAccessException {
		TurnRotator trMock = Mockito.mock(TurnRotator.class);
		PlayerHandler ph = createPlayerHandler(trMock, Ids[0], Ids[1]);
		ph.initiateStartingPlayer(Ids[1]);
		verify(trMock).initialize(ph.getPlayerIds(), Ids[1]);
	}

	@Test
	public void getPlayerFromIdTest() {
		PlayerHandler ph = createPlayerHandler(null, Ids);
		Assert.assertEquals(Ids[1], ph.getPlayerFromId(Ids[1]).getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPlayerFromIdTest2() {
		PlayerHandler ph = createPlayerHandler(null, Ids);
		ph.getPlayerFromId("This is not a ID");
	}

	@Test
	public void getAllPlayersInTurnOrderTest() {
		TurnRotator trMock = Mockito.mock(TurnRotator.class);
		PlayerHandler ph = createPlayerHandler(trMock, Ids);
		when(trMock.getAllPlayersInTurnOrder()).thenReturn(Arrays.asList(Ids));
		List<Player> returnList = ph.getAllPlayersInTurnOrder();

		Assert.assertEquals(ph.getPlayers(), returnList);
	}

	private PlayerHandler createPlayerHandler(TurnRotator turnRotator, String... IDs) {
		List<Player> players = new ArrayList<>();
		for (int i = 0; i < IDs.length; i++) {
			Player playerMock = Mockito.mock(Player.class);
			when(playerMock.getId()).thenReturn(IDs[i]);
			players.add(playerMock);
		}
		return new PlayerHandler(players, turnRotator);
	}
}
