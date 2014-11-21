package kth.game.othello.score;

import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ScoreImplTest {
	@Test
	public void constructorAddSelfAsObserverTest() {
		Node node1 = mock(Node.class);
		Node node2 = mock(Node.class);
		List<Node> nodes = new ArrayList<>();
		nodes.add(node1);
		nodes.add(node2);

		ScoreImpl score = new ScoreImpl(new ArrayList<>(), nodes);

		verify(node1, times(1)).addObserver(score);
		verify(node2, times(1)).addObserver(score);
	}

	@Test
	public void getPlayersScoreTest() {
		List<ScoreItem> scores = new ArrayList<>();
		scores.add(new ScoreItem("player1", 0));
		scores.add(new ScoreItem("player2", 0));
		ScoreImpl score = new ScoreImpl(scores, new ArrayList<>());

		Assert.assertArrayEquals(scores.toArray(), score.getPlayersScore().toArray());
	}

	@Test
	public void getPointsTest() {
		ScoreItem score1 = new ScoreItem("player1", 13);
		ScoreItem score2 = new ScoreItem("player2", 0);

		List<ScoreItem> scores = new ArrayList<>();
		scores.add(score1);
		scores.add(score2);
		ScoreImpl score = new ScoreImpl(scores, new ArrayList<>());

		Assert.assertEquals(score1.getScore(), score.getPoints("player1"));
		Assert.assertEquals(score2.getScore(), score.getPoints("player2"));
	}

	@Test
	public void updateTest() {
		Observer observer1 = mock(Observer.class);
		Observer observer2 = mock(Observer.class);

		ScoreItem score1 = new ScoreItem("player1", 13);
		ScoreItem score2 = new ScoreItem("player2", 0);

		List<ScoreItem> scores = new ArrayList<>();
		scores.add(score1);
		scores.add(score2);

		NodeImpl node1 = mock(NodeImpl.class);
		when(node1.getOccupantPlayerId()).thenReturn(score1.getPlayerId());

		ScoreImpl score = new ScoreImpl(scores, new ArrayList<>());
		score.addObserver(observer1);
		score.addObserver(observer2);

		score.update(node1, null);

		{
			List<String> changed = new ArrayList<>();
			changed.add(score1.getPlayerId());
			Assert.assertEquals(score1.getScore() + 1, score.getPoints(score1.getPlayerId()));
			Assert.assertEquals(score2.getScore(), score.getPoints(score2.getPlayerId()));
			verify(observer1, times(1)).update(eq(score), eq(changed));
			verify(observer2, times(1)).update(eq(score), eq(changed));
		}

		{
			Observer observer3 = mock(Observer.class);
			score.addObserver(observer3);

			List<String> changed = new ArrayList<>();
			changed.add(score1.getPlayerId());
			changed.add(score2.getPlayerId());

			score.update(node1, score2.getPlayerId());
			Assert.assertEquals(score1.getScore() + 2, score.getPoints(score1.getPlayerId()));
			Assert.assertEquals(score2.getScore() - 1, score.getPoints(score2.getPlayerId()));
			verify(observer3, times(1)).update(eq(score), eq(changed));
		}
	}
}
