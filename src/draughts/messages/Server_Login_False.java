package draughts.messages;

import draughts.enums.*;

public class Server_Login_False extends Message{
	
	public Server_Login_False() {
		this.name = Messages.SERVER_LOGIN_FALSE;
	}
	
	public Server_Login_False(String message) {
		this();
		this.setMessage(message);
	}
	
}
