package draughts.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import draughts.constants.Constants;
import draughts.enums.*;
import draughts.messages.*;
import draughts.windows.MainWindow;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Connectivity extends Thread {

	private MainWindow mainWindow = null;
	private Connection connection = null;
	private int timeLostConnection = 0;
	
	public Connectivity(MainWindow mainWindow, Connection connection) {
		this.connection = connection;
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void run() {
		while(true) {
			if (!mainWindow.getClient().isCheckingConnected()) {				
				if (timeLostConnection == 0) {
					System.out.println("Connection lost, trying to reconnect");
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("Connection lost");
						alert.setContentText("Connection lost");
						alert.showAndWait();
					});	
				}
				mainWindow.getClient().setConnected(false);
				mainWindow.getClient().setState(States.DISCONNECT);
				timeLostConnection += 1;
			}
			else {
				if (timeLostConnection > 0) {
					System.out.println("Connectivity ok");
					Platform.runLater(() -> {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Connection restored");
						alert.setContentText("Connection restored");
						alert.showAndWait();
						
						if (mainWindow.stages == Stages.LOBBY) {
							mainWindow.play.setDisable(false);
							mainWindow.play.setText("Play");
							mainWindow.play_processed = false;
							mainWindow.getClient().setState(States.IN_LOBBY);
							mainWindow.play.setOnAction(event -> {
								mainWindow.play();
							});		
						}
						else if (mainWindow.stages == Stages.ASK) {
							mainWindow.getClient().setState(States.YOU_PLAYING);
						}
					});
				}
				
				timeLostConnection = 0;
				mainWindow.getClient().setCheckingConnected(false);
				mainWindow.getClient().setConnected(true);
			}
			
			try {
				Connectivity.sleep(1 * 1000);
			} catch (InterruptedException e) {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("Timeout");
					alert.setContentText("Timeout, closing app");
					alert.showAndWait();
				});
			}
			if (timeLostConnection == 120) break;
		}
		
		System.out.println("Connectivity lost");
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Connection lost");
			alert.setContentText("Connection lost");
			alert.showAndWait();
			end();
		});
	}
	
	public void end() {
		mainWindow.quit();
	}
}
