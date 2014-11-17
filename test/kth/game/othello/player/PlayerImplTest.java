package kth.game.othello.player;

import org.junit.Assert;
import org.junit.Test;

import kth.game.othello.player.movestrategy.MoveStrategy;

public class PlayerImplTest {
	class PlayerImplTester extends PlayerImpl {
		public PlayerImplTester(String id, String name, Type type) {
			super(id, name, type);
		}

		@Override
		public MoveStrategy getMoveStrategy() {
			return null;
		}

		@Override
		public void setMoveStrategy(MoveStrategy moveStrategy) {

		}
	}

	@Test
	public void ConstructorTest() {
		{
			PlayerImpl player = new PlayerImplTester("", "", Player.Type.COMPUTER);
		}
		{
			PlayerImpl player = new PlayerImplTester("", "", Player.Type.COMPUTER);
		}
	}

	@Test
	public void getIdTest() {
		PlayerImpl player = new PlayerImplTester("id", null, null);
		Assert.assertEquals("id", player.getId());
	}

	@Test
	public void getNameTest() {
		PlayerImpl player = new PlayerImplTester(null, "john doe", null);
		Assert.assertEquals("john doe", player.getName());
	}

	@Test
	public void getType() {
		{
			PlayerImpl player = new PlayerImplTester(null, null, Player.Type.COMPUTER);
			Assert.assertEquals(Player.Type.COMPUTER, player.getType());
		}
		{
			PlayerImpl player = new PlayerImplTester(null, null, Player.Type.HUMAN);
			Assert.assertEquals(Player.Type.HUMAN, player.getType());
		}
	}
}
