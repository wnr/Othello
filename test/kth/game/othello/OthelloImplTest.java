package kth.game.othello;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.player.Player;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OthelloImplTest {

    @Test
    public void constructorTest() throws NoSuchFieldException, IllegalAccessException {
        OthelloBoardHandler obhMock = Mockito.mock(OthelloBoardHandler.class);
        Player p1Mock = Mockito.mock(Player.class);
        Player p2Mock = Mockito.mock(Player.class);

        OthelloImpl othello = new OthelloImpl(obhMock, p1Mock, p2Mock);

        // Test all fields
        Field obhField = othello.getClass().getDeclaredField("othelloBoardHandler");
        Assert.assertEquals(obhField.get(othello), obhMock);

        List<Player> playerList = (List<Player>) othello.getClass().getDeclaredField("players").get(othello);
        Assert.assertEquals(playerList.size(), 2);
        Assert.assertTrue(playerList.get(0).equals(p1Mock) && playerList.get(1).equals(p2Mock)
                          || playerList.get(0).equals(p2Mock) && playerList.get(1).equals(p1Mock));

        Field playerInTurnField = othello.getClass().getDeclaredField("playerInTurn");
        Assert.assertEquals(playerInTurnField.get(othello), null);
    }

    @Test
    public void hasValidMoveTest() {
        OthelloBoardHandler obhMock = Mockito.mock(OthelloBoardHandler.class);
        OthelloImpl othello = new OthelloImpl(obhMock, null, null);

        String playerId = "playerId";
        List<Node> nodeList = new ArrayList<>();

        when(obhMock.getValidMoves(playerId)).thenReturn(nodeList);
        Assert.assertFalse(othello.hasValidMove(playerId));
        nodeList.add(null);
        Assert.assertTrue(othello.hasValidMove(playerId));
    }

    @Test
    public void isActiveTest() {
        OthelloBoardHandler obhMock = Mockito.mock(OthelloBoardHandler.class);
        Player playerMock1 = Mockito.mock(Player.class);
        Player playerMock2 = Mockito.mock(Player.class);
        OthelloImpl othello = new OthelloImpl(obhMock, playerMock1, playerMock2);

        String playerId1 = "playerId1";
        String playerId2 = "playerId2";
        List<Node> nodeList1 = new ArrayList<>();
        List<Node> nodeList2 = new ArrayList<>();

        when(playerMock1.getId()).thenReturn(playerId1);
        when(playerMock2.getId()).thenReturn(playerId2);
        when(obhMock.getValidMoves(playerId1)).thenReturn(nodeList1);
        when(obhMock.getValidMoves(playerId2)).thenReturn(nodeList2);
        boolean test = othello.isActive();
        Assert.assertFalse(othello.isActive());

        nodeList1.add(null);
        Assert.assertTrue(othello.isActive());

        nodeList2.add(null);
        Assert.assertTrue(othello.isActive());

        nodeList1.clear();
        Assert.assertTrue(othello.isActive());
    }

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

    @Test(expected = IllegalArgumentException.class)
    public void moveTest() throws NoSuchFieldException, IllegalAccessException {
        OthelloBoardHandler obhMock = Mockito.mock(OthelloBoardHandler.class);
        Player p1Mock = Mockito.mock(Player.class);
        Player p2Mock = Mockito.mock(Player.class);

        OthelloImpl othello1 = new OthelloImpl(obhMock, p1Mock, p2Mock);

        Field playerInTurnField = othello1.getClass().getDeclaredField("playerInTurn");
        playerInTurnField.setAccessible(true);
        playerInTurnField.set(othello1, p1Mock);

        othello1 = spy(othello1);

        String playerId1 = "playerId1";
        String playerId2 = "playerId2";
        String nodeId = "nodeId";
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(Mockito.mock(Node.class));

        when(p1Mock.getId()).thenReturn(playerId1);
        when(p2Mock.getId()).thenReturn(playerId2);
        when(obhMock.move(playerId1, nodeId)).thenReturn(nodeList);
        doReturn(false).when(othello1).isActive();
        when(othello1.isActive()).thenReturn(false);

        List<Node> returnList = othello1.move(playerId1, nodeId);
        Assert.assertEquals(othello1.getPlayerInTurn(), null);
        Assert.assertEquals(returnList, nodeList);

        OthelloImpl othello2 = new OthelloImpl(obhMock, p1Mock, p2Mock);
        playerInTurnField.setAccessible(true);
        playerInTurnField.set(othello2, p1Mock);
        othello2 = spy(othello2);
        doReturn(true).when(othello2).isActive();

        returnList = othello2.move(playerId1, nodeId);
        Assert.assertEquals(othello2.getPlayerInTurn(), p2Mock);
        Assert.assertEquals(returnList, nodeList);

        OthelloImpl othelloError = new OthelloImpl(null, null, null);
        playerInTurnField.setAccessible(true);
        playerInTurnField.set(othelloError, p1Mock);
        othelloError.move(playerId2, nodeId);
    }

    @Test
    public void startTest() throws NoSuchFieldException, IllegalAccessException {
        OthelloBoardHandler obhMock = Mockito.mock(OthelloBoardHandler.class);
        Player p1Mock = Mockito.mock(Player.class);
        Player p2Mock = Mockito.mock(Player.class);
        OthelloImpl othello1 = new OthelloImpl(obhMock, p1Mock, p2Mock);

        String playerId1 = "playerId1";
        String playerId2 = "playerId2";
        when(p1Mock.getId()).thenReturn(playerId1);
        when(p2Mock.getId()).thenReturn(playerId2);

        Field playerInTurnField = othello1.getClass().getDeclaredField("playerInTurn");
        Assert.assertNull(playerInTurnField.get(othello1));
        othello1.start(playerId2);
        verify(obhMock).initializeStartingPositions(playerId2, playerId1);
        Assert.assertEquals(playerInTurnField.get(othello1), p2Mock);
    }
}