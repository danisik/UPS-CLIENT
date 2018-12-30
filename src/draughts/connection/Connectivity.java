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
	
	public Connectivity(MainWindow mainWindow, Connection connection) {
		this.connection = connection;
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void run() {
		while(mainWindow.getClient().isConnected()) {
			try {
				mainWindow.getClient().setConnected(false);
				System.out.println("Connectivity active");
				Connectivity.sleep(15 * 1000);
			} catch (InterruptedException e) {
				Platform.runLater(() -> {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("Connection lost");
					alert.setContentText("Connection lost");
					alert.showAndWait();
					end();
				});
			}
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
