package draughts.messages;

import draughts.constants.*;
import draughts.enums.*;
public class Client_Move extends Message {
	
	/* game id */
	private int GID = -1;
	/* current row position of piece */
	private int rowCurrent = -1;
	/* current col position of piece */
	private int colCurrent = -1;
	/* destination row position of piece */
	private int rowDestination = -1;
	/* destination col position of piece */
	private int colDestination = -1;
	/* color of piece */
	private String color = "";
	/* type of piece */
	private String piece = "";
	
	/* client -> server */
	public Client_Move(int GID, int rowCurrent, int colCurrent, int rowDestination, int colDestination, 
			String color, String piece) {
		this.name = Messages.CLIENT_MOVE;
		this.GID = GID;
		this.rowCurrent = rowCurrent;
		this.colCurrent = colCurrent;
		this.rowDestination = rowDestination;
		this.colDestination = colDestination;
		this.color = color;
		this.piece = piece;
	}
	
	public String toString() {
		return this.name.toString() + Constants.valueSeparator + this.GID + Constants.valueSeparator + this.rowCurrent 
				+ Constants.valueSeparator + this.colCurrent + Constants.valueSeparator + this.rowDestination
				+ Constants.valueSeparator + this.colDestination + Constants.valueSeparator
				+ this.color + Constants.valueSeparator + this.piece + Constants.valueSeparator;
	}
}
