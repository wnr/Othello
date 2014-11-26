package kth.game.othello.player.movestrategy;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import kth.game.othello.Othello;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.BoardHandler;
import kth.game.othello.board.BoardHandlerFactory;

public class MoveStrategyTestHelper {
	public static Othello getMockedOthello() {
		BoardImpl mockedBoard = mock(BoardImpl.class);
		when(mockedBoard.copyWithoutObservers()).thenReturn(null);
		Othello mockedOthello = mock(Othello.class);
		when(mockedOthello.getBoard()).thenReturn(mockedBoard);
		return mockedOthello;
	}

	public static BoardHandlerFactory getMockedBoardHandlerFactory(BoardHandler boardHandler) {
		BoardHandlerFactory mockedFactory = mock(BoardHandlerFactory.class);
		when(mockedFactory.createOthelloBoardHandler(anyObject())).thenReturn(boardHandler);
		return mockedFactory;
	}
}
