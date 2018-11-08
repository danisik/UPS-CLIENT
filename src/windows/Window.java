package windows;

import java.io.FileNotFoundException;

import connection.Connection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class Window extends Stage {

	protected Window window;
	protected Stage primaryStage;
	protected int stageWidth = 0;
	protected int stageHeight = 0;
	protected Boolean logged = false;
	protected Connection connection = null;

	protected Stage createStage(Stage stage) {
		return null;		
	}
	
	public Scene createScene() {
		return null;
	}
	

	public BorderPane createObjects() {
		return null;
	}
	
	protected void setControlParameters() throws FileNotFoundException {
		
	}
	

	public void setEvents() {
	
	}
	
	protected void setClassVariables(Window window, int stageWidth, int stageHeight) {
		this.window = window;
		this.stageWidth = stageWidth;
		this.stageHeight = stageHeight;
	}
	
	public int getStageHeight() {
		return stageHeight;
	}

	public void setStageHeight(int stageHeight) {
		this.stageHeight = stageHeight;
	}

	public int getStageWidth() {
		return stageWidth;
	}

	public void setStageWidth(int stageWidth) {
		this.stageWidth = stageWidth;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void showStage() {
		this.primaryStage.show();
	}
	
	public Boolean getLogged() {
		return logged;
	}

	public void setLogged(Boolean logged) {
		this.logged = logged;
	}

	public Connection getSender() {
		return connection;
	}

	public void setSender(Connection sender) {
		this.connection = sender;
	}

}
