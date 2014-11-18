package kth.game.othello.player;

import kth.game.othello.player.movestrategy.MoveStrategy;

/**
 * This is the implementation of the Player interface.
 *
 * @author Lucas Wiener
 */
public abstract class PlayerImpl implements Player {
	String id;
	String name;
	Type type;
	MoveStrategy moveStrategy;

	/**
	 * Constructs an othello player structure object.
	 *
	 * @param id The id is a unique identifier of this player in the context of all players
	 * @param name The name of the player
	 * @param type The {@link Type} of the player
	 * @param moveStrategy The {@link MoveStrategy} of the player
	 */
	public PlayerImpl(String id, String name, Type type, MoveStrategy moveStrategy) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.moveStrategy = moveStrategy;
	}

	/**
	 * Constructs an othello player structure object, without a move strategy.
	 *
	 * @param id The id is a unique identifier of this player in the context of all players
	 * @param name The name of the player
	 * @param type The {@link Type} of the player
	 */
	public PlayerImpl(String id, String name, Type type) {
		this(id, name, type, null);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public MoveStrategy getMoveStrategy() {
		return moveStrategy;
	}

	@Override
	public void setMoveStrategy(MoveStrategy moveStrategy) {
		this.moveStrategy = moveStrategy;
	}
}
