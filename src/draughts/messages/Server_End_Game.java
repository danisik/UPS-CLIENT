package draughts.messages;

import draughts.constants.*;
import draughts.enums.*;

public class Server_End_Game extends Message{
	
	private String winner;

	/* end game */
	public Server_End_Game(String winner) {
		this.name = Messages.SERVER_END_GAME;
		this.winner = winner;
	}
	
	/* client timeout */
	public Server_End_Game() {
		this.name = Messages.SERVER_END_GAME_TIMEOUT;
	}
	
	@Override
	public String toString() {
		return this.name.toString() + Constants.valueSeparator + this.winner + Constants.valueSeparator; 
	}
	
	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
}
