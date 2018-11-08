package draughts.messages;

import draughts.constants.*;
import draughts.enums.*;
public class Server_Online_Players extends Message{

	private int playersCount = -1;
	
	public Server_Online_Players(int playersCount) {
		this.name = Messages.SERVER_ONLINE_PLAYERS;
		this.playersCount = playersCount;
	}

	@Override
	public String toString() {
		return this.name.toString() + Constants.valueSeparator + this.playersCount + Constants.valueSeparator; 
	}
	
	public int getPlayersCount() {
		return playersCount;
	}

	public void setPlayersCount(int playersCount) {
		this.playersCount = playersCount;
	}
}
