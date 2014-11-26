package kth.game.othello.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;

/**
 * A factory for producing Othello Boards.
 *
 * @author Erik Odenman
 * @author Lucas Wiener
 */
public class BoardFactory {

	/**
	 * Creates a quadratic Othello board of size 8x8. The four central nodes will be occupied by the given players.
	 *
	 * @return an Othello Board of size 8
	 */
	public BoardImpl createOthelloBoard(Player player1, Player player2) {
		List<Player> players = new ArrayList<>();
		players.add(player1);
		players.add(player2);
		return createBoard(new Square().getNodes(8, players));
	}

	/**
	 * Create a board using the specified node data
	 *
	 * @param nodeDataSet A set containing that make up the board
	 * @return A board
	 */
	public BoardImpl createBoard(Set<NodeData> nodeDataSet) {
		return new BoardImpl(convertNodeDataSet(nodeDataSet));
	}

	private static NodeImpl convertNodeData(NodeData nodeData) {
		return new NodeImpl(nodeData.getOccupantPlayerId(), nodeData.getXCoordinate(), nodeData.getYCoordinate());
	}

	private static Collection<NodeImpl> convertNodeDataSet(Set<NodeData> nodeDataSet) {
		return nodeDataSet.stream().map(BoardFactory::convertNodeData).collect(Collectors.toList());
	}

}
