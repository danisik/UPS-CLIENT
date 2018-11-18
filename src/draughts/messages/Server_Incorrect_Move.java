package draughts.messages;

import draughts.constants.*;
import draughts.enums.*;

public class Server_Incorrect_Move extends Message{
	
	private int GID = -1;
	
	public Server_Incorrect_Move(int GID) {
		this.name = Messages.SERVER_WRONG_MOVE;
	}
	
	@Override
	public String toString() {
		return this.name.toString() + Constants.valueSeparator + this.GID + Constants.valueSeparator;
	}
}
