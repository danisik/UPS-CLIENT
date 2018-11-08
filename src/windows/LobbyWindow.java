package windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.swing.text.Position;

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
	
	public LobbyWindow(Window window, Stage stage) {
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
		
		lobbyPane.add(nameOfGame, 20, 1, 1, 1);	
		return lobbyPane;
	}
	
	public void setControlParameters() {	
		nameOfGame.setFont(new Font(20));
//		
//		//nameOfGame.setTextFill(javafx.scene.paint.Color.web(Color.Red.getHexColor()));
//		
//		login.setMaxWidth(75);
//		login.setOnAction(event -> 
//		{
//			Alert alert = new Alert(AlertType.WARNING);
//			alert.setHeaderText("This name is used");
//			alert.setContentText("This name is used by another player, please select different name.");
//			alert.show();
//		});
//		
	}
	
	@Override
	public void setEvents() {
		
	}
}
