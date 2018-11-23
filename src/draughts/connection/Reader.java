package draughts.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

import draughts.constants.Constants;
import draughts.enums.*;
import draughts.messages.*;
import draughts.windows.MainWindow;
import javafx.application.Platform;


public class Reader implements Runnable {

	private BufferedReader reader = null;
	private Message receivedMessage = null;
	private MainWindow mainWindow = null;
	private Connection connection = null;
	
	public Reader(Socket socket, MainWindow mainWindow, Connection connection) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.connection = connection;
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
				connection.connect(mainWindow);
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
		String[] values = stringMessage.split(";");
		Messages messages = Messages.getMessageByName(values[0]);
		switch(messages) {
			case SERVER_LOGIN_OK:
				Platform.runLater(() -> {
					mainWindow.processLogin(new Server_Login_OK());
				});
				break;
			case SERVER_LOGIN_FALSE:
				Platform.runLater(() -> {
					int err = Integer.parseInt(values[1]);
					String errMessage = "";
					switch (err) {
						case 1:
							errMessage = "Too much Players online";
							break;
						case 2:
							errMessage = "This name is already taken";
							break;
					}
					mainWindow.processLogin(new Server_Login_False(errMessage));
				});
				break;
			case SERVER_START_GAME:
				Platform.runLater(() -> {
					Color color = Color.getColor(values[1]);
					int game_ID = Integer.parseInt(values[2]);
					mainWindow.processPlay(new Server_Start_Game(color), game_ID);
				});
				break;
			case SERVER_UPDATE_GAME_ID:
					int game_ID = Integer.parseInt(values[1]);
					mainWindow.updateGameID(game_ID);
				break;
			case SERVER_CORRECT_MOVE:
				Platform.runLater(() -> {
					int positions = Integer.parseInt(values[1]);
					
					switch(positions) {
						case 2:
							mainWindow.movePiece(Integer.parseInt(values[2]), Integer.parseInt(values[3]), 
									Integer.parseInt(values[4]), Integer.parseInt(values[5]));
							mainWindow.removePiece(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
							break;
						case 3:
							mainWindow.movePiece(Integer.parseInt(values[2]), Integer.parseInt(values[3]), 
									Integer.parseInt(values[6]), Integer.parseInt(values[7]));
							mainWindow.removePiece(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
							mainWindow.removePiece(Integer.parseInt(values[4]), Integer.parseInt(values[5]));
							break;
						default:
							break;
					}
				});
				break;
			case SERVER_WRONG_MOVE:
				Platform.runLater(() -> {
					String errMessage = Wrong_Messages.getMessageByName(Integer.parseInt(values[1])).getMessage();
					mainWindow.incorrect(errMessage);
				});
				break;
			case SERVER_END_MOVE:
				Platform.runLater(() -> {
					mainWindow.setPlayer(Constants.playerOpponent);
				});
				break;
			case SERVER_PLAY_NEXT_PLAYER:
				Platform.runLater(() -> {
					mainWindow.setPlayer(Constants.playerYou);
					//mmainWindow.nowPlayingYou();
				});
				break;
			case SERVER_PROMOTE_PIECE:
				Platform.runLater(() -> {
					mainWindow.promote(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
				});
				break;
			case SERVER_END_GAME:
				Platform.runLater(() -> {
					mainWindow.endGame(values[1]);
				});
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
