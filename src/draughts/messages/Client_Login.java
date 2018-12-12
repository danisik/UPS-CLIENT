package draughts.messages;

import draughts.constants.*;
import draughts.enums.*;

public class Client_Login extends Message{

	private String userName = "";
	
	public Client_Login(String userName) {
		this.name = Messages.CLIENT_LOGIN;
		this.userName = userName;
	}

	@Override
	public String toString() {
		return this.name.toString() + Constants.valueSeparator + this.userName + Constants.valueSeparator + "\n";
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
