package draughts.messages;

import draughts.constants.Constants;
import draughts.enums.*;

public abstract class Message {
	public Messages name = null;
	private String message = "";
	
	@Override
	public String toString() {
		return name.toString() + Constants.valueSeparator;
	}

	public Messages getName() {
		return name;
	}

	public void setName(Messages name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
