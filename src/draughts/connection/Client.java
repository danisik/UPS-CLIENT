package draughts.connection;

import draughts.enums.Color;
import draughts.enums.States;

public class Client {
	private String name = "";
	private Color color = null;
	private States state = States.LOGGING;
	
	public Client(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}
}
