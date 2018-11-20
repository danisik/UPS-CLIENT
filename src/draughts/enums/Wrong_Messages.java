package draughts.enums;

public enum Wrong_Messages {
	
	BAD_COLOR_OR_TYPE(0, "Color or type is NA"),
	OPPONNENTS_PIECE(1, "You can't move opponents piece"),
	ONLY_DIAGONALLY(2, "You can move only diagonally"),
	NO_PIECE(3, "You did not select any your piece"),
	FORWARD(4, "You can move your man only forward"),
	DESTROY_MY_PIECE(5, "You can't destroy your piece"),
	HOP_TWO_FIELDS(6, "You can't move by 2 fields when you do not hop over opponents piece"),
	NOT_EMPTY_FIELD(7, "You can't move to fields, because other piece is here"),
	HOP_MORE_FIELDS(8, "You can move your man by only 1 field per move")
	;
	
	private int ID;
	private String message;
	private Wrong_Messages(int ID, String message) {
		this.ID = ID;
		this.message = message;
	}
	
	public int getMessageID() {
		return this.ID;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public static Wrong_Messages getMessageByName(int ID) {
		Wrong_Messages messages = null;
		for(Wrong_Messages message : Wrong_Messages.values()) {
			if (message.getMessageID() == ID) {
				messages = message;
				break;
			}
		}
		
		return messages;
	}
}
