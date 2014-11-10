package kth.game.othello.player;

import org.junit.Assert;
import org.junit.Test;

public class PlayerImplTest {
    @Test
    public void ConstructorTest() {
        {
            PlayerImpl player = new PlayerImpl(null, null, null);
        }
        {
            PlayerImpl player = new PlayerImpl("", "", Player.Type.COMPUTER);
        }
    }

    @Test
    public void getIdTest() {
        PlayerImpl player = new PlayerImpl("id", null, null);
        Assert.assertEquals("id", player.getId());
    }

    @Test
    public void getNameTest() {
        PlayerImpl player = new PlayerImpl(null, "john doe", null);
        Assert.assertEquals("john doe", player.getName());
    }

    @Test
    public void getType() {
        {
            PlayerImpl player = new PlayerImpl(null, null, Player.Type.COMPUTER);
            Assert.assertEquals(Player.Type.COMPUTER, player.getType());
        }
        {
            PlayerImpl player = new PlayerImpl(null, null, Player.Type.HUMAN);
            Assert.assertEquals(Player.Type.HUMAN, player.getType());
        }
    }
}
