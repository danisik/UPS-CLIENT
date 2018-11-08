package windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.swing.text.Position;

import connection.Connection;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.*;

import draughts.constants.Constants;
import draughts.enums.Messages;
import draughts.messages.*;

public class LoginWindow extends Window {
	
	Label nameOfGame = new Label(Constants.gameTitle);
	Label loginLabel = new Label(Constants.loginName);
	Button login = new Button(Constants.buttonLogin);
	TextField enterName = new TextField();
	
	Label currentStatus = new Label(Constants.currentStatus);
	Label onlinePlayers = new Label(Constants.onlinePlayers);
	
	String  pass = "ahoj";
	
	public LoginWindow(Window window, Stage stage) {
		this.setControlParameters();
		this.setClassVariables(window, Constants.stageWidthLogin, Constants.stageHeightLogin);
		this.primaryStage = createStage(stage);
		this.setEvents();
		this.primaryStage.setResizable(false);
		this.connection = new Connection();
		connection.connect();
	}

	@Override
	public Stage createStage(Stage stage) {
		stage.setMinWidth(this.stageWidth);
		stage.setMinHeight(this.stageHeight);
		stage.setScene(createScene());
		stage.setTitle(Constants.gameTitle);
		return stage;
	}
	
	@Override
	public Scene createScene() {
		Scene scene = new Scene(createObjects(), this.stageWidth, this.stageHeight);
		return scene;
	}
	
	@Override
	public BorderPane createObjects() {
		BorderPane root = new BorderPane();	
		root.setCenter(createLogin());
		return root;
	}
	
	public GridPane createLogin() {		
		GridPane loginPane = new GridPane();
		loginPane.setHgap(5);
		loginPane.setVgap(5);
		
		loginPane.add(nameOfGame, 6, 1, 1, 1);
		loginPane.add(loginLabel, 5, 6, 1, 1);
		loginPane.add(enterName, 6, 6, 1, 1);
		loginPane.add(login, 6, 7, 1, 1);
		loginPane.add(currentStatus, 6, 16, 1, 1);
		loginPane.add(onlinePlayers, 6, 17, 1, 1);
		
		return loginPane;
	}
	
	@Override
	public void setControlParameters() {
		nameOfGame.setFont(new Font(20));
		
		//nameOfGame.setTextFill(javafx.scene.paint.Color.web(Color.Red.getHexColor()));
		
		login.setMaxWidth(75);
		enterName.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                login();	      
	            }
	        }
	    });
		login.setOnAction(event -> {
			login();
		});
		
	}
	private void login() {
		Alert alert = new Alert(AlertType.WARNING);
		if (enterName.getText().length() < 1) {
			alert.setHeaderText("Username was not set");
			alert.setContentText("Username was not set. Please set your username.");
			alert.show();
		}
		else if (enterName.getText().length() > 20) {
			alert.setHeaderText("Username is too long.");
			alert.setContentText("Username is too long. Please cut your username.");
			alert.show();				
		}
		else {
			connection.write(new Client_Login(enterName.getText()));
			Message message = connection.read();
			
			if (message.name == Messages.SERVER_LOGIN_FALSE) {
				
				alert.setHeaderText(message.getMessage());
				alert.setContentText(message.getMessage());
				alert.show();
			}
			else {
				this.setLogged(true);
			
				alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Login was successful");
				alert.setContentText("Login was successful.");
				alert.show();	
				window = new LobbyWindow(window, primaryStage);
				window.showStage();	
			}
		}
	}
}
