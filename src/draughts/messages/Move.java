package draughts.messages;

import draughts.constants.*;
import draughts.enums.*;
public class Move extends Message {
	
	/* game id */
	private int GID = -1;
	
	
	/* kill opponent*/
	private int killed_opponent = -1;
	/* change type */
	private int promote = -1;
	
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
	
	/*  server -> client */
	public Move(int GID, int killed_opponent, int promote) {
		this.name = Messages.SERVER_CORRECT_MOVE;
		this.GID = GID;
		this.killed_opponent = killed_opponent;
		this.promote = promote;
	}
	
	/* client -> server */
	public Move(int GID, int rowCurrent, int colCurrent, int rowDestination, int colDestination, 
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
	
	public String toStringServer() {
		return this.name.toString() + Constants.valueSeparator + this.GID + Constants.valueSeparator + this.killed_opponent
				+ Constants.valueSeparator + this.promote + Constants.valueSeparator;
	}
	
	//prikaz,gid,rowC_colC_rowD_colD
	public String toString() {
		return this.name.toString() + Constants.valueSeparator + this.GID + Constants.valueSeparator + this.rowCurrent 
				+ Constants.valueSeparator + this.colCurrent + Constants.valueSeparator + this.rowDestination
				+ Constants.valueSeparator + this.colDestination + Constants.valueSeparator
				+ this.color + Constants.valueSeparator + this.piece + Constants.valueSeparator;
	}
	
	public int getGID() {
		return GID;
	}

	public void setGID(int gID) {
		GID = gID;
	}

	public int getKilled_opponent() {
		return killed_opponent;
	}

	public void setKilled_opponent(int killed_opponent) {
		this.killed_opponent = killed_opponent;
	}

	public int getPromote() {
		return promote;
	}

	public void setPromote(int promote) {
		this.promote = promote;
	}

	public int getRowCurrent() {
		return rowCurrent;
	}

	public void setRowCurrent(int rowCurrent) {
		this.rowCurrent = rowCurrent;
	}

	public int getColCurrent() {
		return colCurrent;
	}

	public void setColCurrent(int colCurrent) {
		this.colCurrent = colCurrent;
	}

	public int getRowDestination() {
		return rowDestination;
	}

	public void setRowDestination(int rowDestination) {
		this.rowDestination = rowDestination;
	}

	public int getColDestination() {
		return colDestination;
	}

	public void setColDestination(int colDestination) {
		this.colDestination = colDestination;
	}
}
