package kth.game.othello.player;

/**
 * This is the implementation of the Player interface.
 *
 * @author Lucas Wiener
 */
public abstract class PlayerImpl implements Player {
	String id;
	String name;
	Type type;

	/**
	 * Constructs an othello player structure object.
	 *
	 * @param id The id is a unique identifier of this player in the context of all players
	 * @param name The name of the player
	 * @param type The {@link Type} of the player
	 */
	public PlayerImpl(String id, String name, Type type) {
		this.id = id;
		this.name = name;
		this.type = type;
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
}
