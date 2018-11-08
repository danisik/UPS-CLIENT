package draughts.piece;

import draughts.enums.*;

public abstract class Piece {
	private Color color = null;
	private Type type = null;
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
}
