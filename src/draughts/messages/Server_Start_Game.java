package draughts.messages;

import draughts.enums.*;

public class Server_Start_Game extends Message{
	public Server_Start_Game(Color color) {
		this.name = Messages.SERVER_START_GAME;
		this.setColor(color);
	}
}
