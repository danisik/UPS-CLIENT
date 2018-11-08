package draughts.enums;

public enum Messages {
	SERVER_ONLINE_PLAYERS("online_players"),
	
	CLIENT_LOGIN("login"),
	SERVER_LOGIN_OK("login_ok"),
	SERVER_LOGIN_FALSE("login_false"),
	
	SERVER_START_GAME("start"),
	
	SERVER_MOVE("server_move"),
	CLIENT_MOVE("client_move"),
	SERVER_INCORRECT_MOVE("incorrect_move"),
	CLIENT_END_MOVE("end_move"),
	
	SERVER_PLAY_NEXT_PLAYER("play_next_player"),
	
	SERVER_END_GAME("end_game"),
	SERVER_END_GAME_TIMEOUT("end_game_timeout"),
	
	CLIENT_NEXT_GAME_YES("new_game_yes"),
	CLIENT_NEXT_GAME_NO("new_game_no"),
	
	SERVER_OPPONENT_CONNECTION_LOST("opponent_connection_lost"),
	SERVER_CLIENT_CONNECTION_RESTORED("connection_restored")
	;
	
	private String name;
	private Messages(String name) {
		this.name = name;
	}
	
	public String getMessageName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public static Messages getMessageByName(String name) {
		Messages messages = null;
		for(Messages message : Messages.values()) {
			if (message.getMessageName().equals(name)) {
				messages = message;
				break;
			}
		}
		
		return messages;
	}
}
