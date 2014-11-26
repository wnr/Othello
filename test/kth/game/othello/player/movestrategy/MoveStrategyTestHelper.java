package kth.game.othello.player.movestrategy;

import kth.game.othello.Othello;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.OthelloBoardHandler;
import kth.game.othello.board.OthelloBoardHandlerFactory;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoveStrategyTestHelper {
    public static Othello getMockedOthello() {
        BoardImpl mockedBoard = mock(BoardImpl.class);
        when(mockedBoard.copyWithoutObservers()).thenReturn(null);
        Othello mockedOthello = mock(Othello.class);
        when(mockedOthello.getBoard()).thenReturn(mockedBoard);
        return mockedOthello;
    }

    public static OthelloBoardHandlerFactory getMockedBoardHandlerFactory(OthelloBoardHandler boardHandler) {
        OthelloBoardHandlerFactory mockedFactory = mock(OthelloBoardHandlerFactory.class);
        when(mockedFactory.createOthelloBoardHandler(anyObject())).thenReturn(boardHandler);
        return mockedFactory;
    }
}
