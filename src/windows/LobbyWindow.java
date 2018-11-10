package windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.swing.text.Position;

import connection.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.*;

import draughts.constants.Constants;
import draughts.enums.*;
import draughts.enums.Color;
//import draughts.field.*;
import draughts.messages.*;
import draughts.piece.*;
//import draughts.player.Player;

public class LobbyWindow extends Window {
	
	Label nameOfGame = new Label(Constants.gameTitle);
	Label nameOfPlayer = new Label();
	Label name = new Label("Your name: ");
	Button play = new Button("Play");
	
	public LobbyWindow(Window window, Stage stage) {
		this.window = window;
		this.setControlParameters();
		this.setClassVariables(window, Constants.stageWidthLobby, Constants.stageHeightLobby);
		this.primaryStage = createStage(stage);
		this.setEvents();
		this.primaryStage.setResizable(false);	
	}
	
	@Override
	public Stage createStage(Stage stage) {
		stage.setScene(createScene());
		stage.setTitle(Constants.gameTitle);
		return stage;
	}
	
	@Override
	public Scene createScene() {
		Scene scene = new Scene(createObjects(), stageWidth, stageHeight);
		return scene;
	}
	
	@Override
	public BorderPane createObjects() {
		BorderPane root = new BorderPane();	
		root.setCenter(createLobby());
		return root;
	}
	
	public GridPane createLobby() {		
		GridPane lobbyPane = new GridPane();
		lobbyPane.setHgap(5);
		lobbyPane.setVgap(5);
		
		lobbyPane.add(nameOfGame, 6, 1, 1, 1);
		lobbyPane.add(name, 5, 6, 1, 1);
		lobbyPane.add(nameOfPlayer, 6, 6, 1, 1);
		lobbyPane.add(play, 6, 7, 1, 1);
		return lobbyPane;
	}
	
	public void setControlParameters() {
		play.setMaxWidth(75);
		nameOfGame.setFont(new Font(20));
		nameOfPlayer.setFont(new Font(20));
		nameOfPlayer.setText(window.getClient().getName());		
		nameOfPlayer.setTextFill(javafx.scene.paint.Color.web(Color.Blue.getHexColor()));
		
		play.setOnAction(event -> {
			play();
		});
	}
	
	public void play() {
		//check if server is on
		Alert alert = new Alert(AlertType.ERROR);
		
		this.window.connection.write(new Client_Play_Game());
		Message message = this.window.connection.read();
		
		if (message.name == Messages.SERVER_START_GAME) {
			window = new BoardWindow(window, primaryStage, message.getColor());
			window.showStage();	
		}
		else {
			alert.setHeaderText("ERROR");
			alert.setContentText("ERROR");
			alert.show();
		}
	}
}
