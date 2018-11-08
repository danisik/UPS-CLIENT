package draughts.messages;

import java.util.ArrayList;
import java.util.List;

import draughts.constants.Constants;
import draughts.enums.*;
import draughts.piece.*;

public class Server_Client_Connection_Restored extends Message {
	
	private List<String> positions = new ArrayList<>();

	public Server_Client_Connection_Restored() {
		this.name = Messages.SERVER_CLIENT_CONNECTION_RESTORED;
	}
	
//	//row_col_color_type
//	public Fields extractPositions(Fields fields) {
//		
//		for(String position: positions) {
//			String[] values = position.split(Constants.pieceSeparator);
//			
//			int row = Integer.parseInt(values[0]);
//			int col = Integer.parseInt(values[1]);
//			
//			Color color = Color.valueOf(values[2]);
//			
//			Piece piece = null;
//			Type type = Type.valueOf(values[3]);
//			
//			switch(type) {
//				case Man: 
//					piece = new Man(color);
//					break;
//				case King:
//					piece = new King(color);
//					break;
//				default:
//					break;
//			}
//			
//			fields.getFields()[row][col].setPiece(piece);
//		}
//		
//		return fields;
//	}
	
	public List<String> getPositions() {
		return positions;
	}

	public void setPositions(List<String> positions) {
		this.positions = positions;
	}
	
	public void addPosition(String position) {
		this.positions.add(position);
	}
	
}
