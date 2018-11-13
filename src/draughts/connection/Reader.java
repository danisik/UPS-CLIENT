package draughts.connection;

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
import draughts.windows.MainWindow;
import javafx.application.Platform;


public class Reader implements Runnable {

	private BufferedReader reader = null;
	private Message receivedMessage = null;
	private MainWindow mainWindow = null;
	
	public Reader(Socket socket, MainWindow mainWindow) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			//e.printStackTrace();
		}
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void run() {
		String message = null;
		while(true) {
			try { 
				message = reader.readLine();
				if (message != null) {
					System.out.println("Message Received: " + message);
					processMessage(message);
				}
				else {
					//error - chyba spojení
				}
			} catch (Exception e) {
				e.printStackTrace();
				//
			}	
		}
	}
	
	public void stop() {
		try {
			reader.close();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		this.stop();
	}

	public void processMessage(String stringMessage) {
		Message message = null;
		String[] values = stringMessage.split(";");
		Messages messages = Messages.getMessageByName(values[0]);
		switch(messages) {
			case SERVER_ONLINE_PLAYERS:
				break;
			case SERVER_LOGIN_OK:
				Platform.runLater(() -> {
					mainWindow.processLogin(new Server_Login_OK());
				});
				break;
			case SERVER_LOGIN_FALSE:
				Platform.runLater(() -> {
					mainWindow.processLogin(new Server_Login_False(values[1]));
				});
				break;
			case SERVER_START_GAME:
				Platform.runLater(() -> {
					Color color = Color.getColor(values[1]);
					mainWindow.processPlay(new Server_Start_Game(color));
				});
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
	}

	public Message getReceivedMessage() {
		return receivedMessage;
	}

	public void setReceivedMessage(Message receivedMessage) {
		this.receivedMessage = receivedMessage;
	}
}
