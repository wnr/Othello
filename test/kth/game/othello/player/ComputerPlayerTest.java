package kth.game.othello.player;

import org.junit.Assert;
import org.junit.Test;

public class ComputerPlayerTest {
	@Test
	public void ConstructorTest() {
		{
			ComputerPlayer player = new ComputerPlayer(null, null);
			Assert.assertEquals(Player.Type.COMPUTER, player.getType());
		}
		{
			ComputerPlayer player = new ComputerPlayer("", "");
			Assert.assertEquals(Player.Type.COMPUTER, player.getType());
		}
	}
}
