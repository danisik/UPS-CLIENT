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
import windows.*;

public class mainclass extends Application {

	Window primaryWindow = null;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		primaryWindow = new LoginWindow(primaryWindow, stage);
		primaryWindow.showStage();
		
//		primaryWindow = new BoardWindow(primaryWindow, stage, draughts.enums.Color.White);
//		primaryWindow.showStage();
		
		
		//window.onCloseRequestProperty(); 
	}

}