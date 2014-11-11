package kth.game.othello.player;

import org.junit.Assert;
import org.junit.Test;

public class HumanPlayerTest {
	@Test
	public void ConstructorTest() {
		{
			HumanPlayer player = new HumanPlayer(null, null);
			Assert.assertEquals(Player.Type.HUMAN, player.getType());
		}
		{
			HumanPlayer player = new HumanPlayer("", "");
			Assert.assertEquals(Player.Type.HUMAN, player.getType());
		}
	}
}
