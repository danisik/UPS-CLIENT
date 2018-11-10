package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

import draughts.enums.*;
import draughts.messages.*;


public class Connection {

	private Socket socket = null;
	private String address = "127.0.0.1";
	private InetAddress inetAddr = null;
	private int localPort = 10000;
	private OutputStreamWriter sender = null;
	private BufferedReader reader = null;
	
	public void write(Message message) {
		try {
			sender.write(message.toString());
			sender.flush();
			System.out.println("Writed message: " + message.toString());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Message read() {
		String message = null;
		try {
			message = reader.readLine();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}		

		System.out.println("Message Received: " + message);
		
		return processMessage(message);
	}
	
	public void closeConnection() {
		try {
			reader.close();
			sender.close();
			socket.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Message processMessage(String stringMessage) {
		Message message = null;
		String[] values = stringMessage.split(";");
		Messages messages = Messages.getMessageByName(values[0]);
		switch(messages) {
			case SERVER_ONLINE_PLAYERS:
				break;
			case SERVER_LOGIN_OK:
				message = new Server_Login_OK();
				break;
			case SERVER_LOGIN_FALSE:
				message = new Server_Login_False(values[1]);
				break;
			case SERVER_START_GAME:
				Color color = Color.getColor(values[1]);
				message = new Server_Start_Game(color);
				break;
			case SERVER_MOVE:
				break;
			case SERVER_INCORRECT_MOVE:
				break;
			case SERVER_PLAY_NEXT_PLAYER:
				break;
			case SERVER_END_GAME:
				break;
			case SERVER_END_GAME_TIMEOUT:
				break;
			case SERVER_OPPONENT_CONNECTION_LOST:
				break;
			case SERVER_CLIENT_CONNECTION_RESTORED:
				break;
			default: 
				break;
		}
		
		return message;
	}
	
	public boolean connect() {
		try {
			socket = new Socket(address, localPort);
			sender = new OutputStreamWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
			
			inetAddr = socket.getInetAddress();
			System.out.println("Pripojuju se na : " + inetAddr.getHostAddress() + " se jmenem : " + inetAddr.getHostName());
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
}
