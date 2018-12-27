package draughts.enums;

public enum Messages {
	
	CLIENT_LOGIN("login"),
	CLIENT_PLAY_GAME("play"),
	CLIENT_MOVE("client_move"),
	CLIENT_NEXT_GAME_NO("new_game_no"),
	CLIENT_APP_END("app_end"),
	
	SERVER_ALREADY_WANNA_PLAY("already_wanna_play"),
	SERVER_IS_CONNECTED("is_connected"),
	SERVER_LOGIN_OK("login_ok"),
	SERVER_LOGIN_FALSE("login_false"),
	SERVER_START_GAME("start_game"),
	SERVER_WRONG_MOVE("wrong_move"),
	SERVER_CORRECT_MOVE("correct_move"),
	SERVER_END_MOVE("end_move"),
	SERVER_UPDATE_GAME_ID("update_game_ID"),
	SERVER_PLAY_NEXT_PLAYER("play_next_player"),
	SERVER_END_GAME("end_game"),
	SERVER_END_GAME_LEFT("end_game_left"),
	SERVER_END_GAME_TIMEOUT("end_game_timeout"),
	SERVER_OPPONENT_CONNECTION_LOST("opponent_connection_lost"), 
	SERVER_CLIENT_CONNECTION_RESTORED("connection_restored"),
	SERVER_PROMOTE_PIECE("promote"),
	SERVER_RESTORE_BOARD("board")
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
