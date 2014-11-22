package kth.game.othello.player;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.player.turnrotator.DefaultTurnRotator;
import kth.game.othello.player.turnrotator.TurnRotator;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Mathias Lindblom
 */
public class DefaultTurnRotatorTest {

	private static final String[] Ids = { "playerId1", "playerId2", "playerId3" };

	@Test
	public void initiateTest() {
		TurnRotator tr = new DefaultTurnRotator(Arrays.asList(Ids));

		tr.setFirstPlayerInTurn(Ids[2]);
		Assert.assertEquals(Ids[2], tr.getPlayerInTurn());

		// Now test with wrong ID
		tr.setFirstPlayerInTurn("Strange ID");
		Assert.assertEquals(null, tr.getPlayerInTurn());
	}

	@Test
	public void getPlayersIdsInTurnOrderTest() {
		TurnRotator tr = createDefaultTurnRotator(Ids[1], Ids);
		List<String> expectedList = new ArrayList<>();
		expectedList.add(Ids[1]);
		expectedList.add(Ids[2]);
		expectedList.add(Ids[0]);

		List<String> returnedList = tr.getAllPlayersInTurnOrder();

		Assert.assertEquals(expectedList, returnedList);
	}

	@Test
	public void updateNextPlayerInTurnTest() {
		TurnRotator tr = createDefaultTurnRotator(Ids[1], Ids);
		Othello othelloMock = Mockito.mock(Othello.class);

		when(othelloMock.isActive()).thenReturn(false);
		List<String> skippedList = tr.updatePlayerInTurn(othelloMock);
		Assert.assertEquals(null, tr.getPlayerInTurn());
		Assert.assertEquals(new ArrayList<>(), skippedList);

		tr = createDefaultTurnRotator(Ids[1], Ids);
		when(othelloMock.isActive()).thenReturn(true);
		when(othelloMock.hasValidMove(Ids[0])).thenReturn(true);
		when(othelloMock.hasValidMove(Ids[1])).thenReturn(false);
		when(othelloMock.hasValidMove(Ids[2])).thenReturn(false);
		skippedList = tr.updatePlayerInTurn(othelloMock);
		Assert.assertEquals(Ids[0], tr.getPlayerInTurn());
		List<String> expectedList = new ArrayList<>();
		expectedList.add(Ids[2]);
		Assert.assertEquals(expectedList, skippedList);

		tr = createDefaultTurnRotator(Ids[1], Ids);
		when(othelloMock.hasValidMove(Ids[0])).thenReturn(false);
		when(othelloMock.hasValidMove(Ids[1])).thenReturn(true);
		skippedList = tr.updatePlayerInTurn(othelloMock);
		Assert.assertEquals(Ids[1], tr.getPlayerInTurn());
		expectedList.clear();
		expectedList.add(Ids[2]);
		expectedList.add(Ids[0]);
		Assert.assertEquals(expectedList, skippedList);
	}

	/**
	 * Creates a new DefaultTurnRotator. Assumes that the simple method setFirstPlayerInTurn is fully functional.
	 * 
	 * @param startingPlayer
	 * @param playerIds
	 * @return
	 */
	private TurnRotator createDefaultTurnRotator(String startingPlayer, String... playerIds) {
		TurnRotator turnRotator = new DefaultTurnRotator(Arrays.asList(playerIds));
		turnRotator.setFirstPlayerInTurn(startingPlayer);
		return turnRotator;
	}
}
