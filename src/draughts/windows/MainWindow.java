package draughts.windows;

import javafx.application.Platform;
import javafx.event.EventHandler;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import draughts.connection.*;
import draughts.constants.*;
import draughts.enums.*;
import draughts.field.*;
import draughts.messages.*;
import draughts.piece.*;

public class MainWindow {
	
	//this
	private Stage primaryStage;
	private Connection connection = null;
	private Client client = null;
	private int game_ID = -1;
	public Stages stages = Stages.LOGIN;
	
	private Boolean firstClicked = false;
	private int firstRow = -1;
	private int firstCol = -1;
	private String firstColor = "";
	private String firstPiece = "";
	private Boolean opp_connection_lost = false;
	private Boolean login_processed = false;
	public Boolean play_processed = false;
	
	//login
	public Label nameOfGame = new Label(Constants.gameTitle);
	public Label loginLabel = new Label(Constants.loginName);
	public Button login = new Button(Constants.buttonLogin);
	public TextField enterName = new TextField();
	
	//lobby
	public Label nameOfPlayer = new Label();
	public Label name = new Label("Your name: ");
	public Button play = new Button("Play");
	
	//board
	private static InputStream streamWhite = null;
	private static Image imgWhite = null;
	private static InputStream streamBlack = null;
	private static Image imgBlack = null;
	private static InputStream streamBlackBlack = null;
	private static Image imgBlackBlack = null;
	private static InputStream streamBlackWhite = null;
	private static Image imgBlackWhite = null;
	private static InputStream streamBlackBlackKing = null;
	private static Image imgBlackBlackKing = null;
	private static InputStream streamBlackWhiteKing = null;	
	private static Image imgBlackWhiteKing = null;
	
	public GridPane board = new GridPane();
	public String player = null;
	public Label nowPlaying;
	public Label infoRowCol = new Label("row: NA, col: NA");
	public Label infoTypeColor = new Label("type: NA, color: NA");
	public Label infoPlayerColor = new Label();
	public Label infoPlayerName = new Label();
	//10x10 board, 4x5 pieces per player, white starts, pieces starts staying on black field
	int piecesCountPerRow = 5;
	int piecesRow = 2;
	int fieldsCount = 10;
	Fields fields = new Fields(fieldsCount);
	Field clickedField = null;
	Message message = null;
	
	//ask
	public Label result = new Label();
	public Label infoResult = new Label("Do you want play another game ?");
	public Button newGameYes = new Button("Yes");
	public Button newGameNo = new Button("No");
	
	public MainWindow(Stage stage, List<String> args) {
		this.connection = new Connection(args);
		//this.connection.connect(this);
		this.primaryStage = createLoginStage(stage);
		this.client = new Client("");
		client.setConnected(true);
	}
	
	public void Show() {
		this.primaryStage.show();
	}
	
	//-----------------------------------------
	//----------------STAGES-------------------
	//-----------------------------------------
	
	public Stage createLoginStage(Stage stage) {
		stage = onCloseEvent(stage);
		nameOfGame.setFont(new Font(20));
		
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
		
		GridPane loginPane = new GridPane();
		loginPane.setHgap(5);
		loginPane.setVgap(5);
		loginPane.add(nameOfGame, 6, 1, 1, 1);
		loginPane.add(loginLabel, 5, 6, 1, 1);
		loginPane.add(enterName, 6, 6, 1, 1);
		loginPane.add(login, 6, 7, 1, 1);
		
		BorderPane root = new BorderPane();
		root.setCenter(loginPane);
		
		Scene scene = new Scene(root, Constants.stageWidthLogin, Constants.stageHeightLogin);
		
		stage.setMinWidth(Constants.stageWidthLogin);
		stage.setMinHeight(Constants.stageHeightLogin);
		stage.setScene(scene);
		stage.setTitle(Constants.gameTitle);
		return stage;
	}	
	
	public Stage createLobbyStage(Stage stage) {
		this.stages = Stages.LOBBY;
		stage = onCloseEvent(stage);
		play.setMaxWidth(75);
		nameOfGame.setFont(new Font(20));
		nameOfPlayer.setFont(new Font(20));
		nameOfPlayer.setText(getClient().getName());		
		nameOfPlayer.setTextFill(javafx.scene.paint.Color.web(Color.Blue.getHexColor()));
		
		if (play_processed) {
			client.setState(States.WANNA_PLAY);
			play.setDisable(true);
			play.setText("Queued");
		}
		else {
			play.setDisable(false);
			play.setText("Play");
			play.setOnAction(event -> {
				play();
			});	
		}
		
		GridPane lobbyPane = new GridPane();
		lobbyPane.setHgap(5);
		lobbyPane.setVgap(5);
		
		lobbyPane.add(nameOfGame, 6, 1, 1, 1);
		lobbyPane.add(name, 5, 6, 1, 1);
		lobbyPane.add(nameOfPlayer, 6, 6, 1, 1);
		lobbyPane.add(play, 6, 7, 1, 1);

		BorderPane root = new BorderPane();	
		root.setCenter(lobbyPane);

		Scene scene = new Scene(root, Constants.stageWidthLobby, Constants.stageHeightLobby);
		stage.setScene(scene);
		stage.setTitle(Constants.gameTitle);
		
		return stage;
	}
	
	public Stage createBoardStage(Stage stage, int game_ID) {
		this.stages = Stages.BOARD;
		this.game_ID = game_ID;
		stage = onCloseEvent(stage);
		
		infoPlayerColor.setText("Your color is: " + getClient().getColor().toString());
		infoPlayerColor.setTextFill(javafx.scene.paint.Color.web(getClient().getColor().getHexColor()));
		infoPlayerColor.setFont(new Font(15));
		
		infoPlayerName.setText("Your name is: " + getClient().getName());
		infoPlayerName.setTextFill(javafx.scene.paint.Color.web(draughts.enums.Color.Blue.getHexColor()));
		infoPlayerName.setFont(new Font(15));
		
		try {
			inicialize_images();
		}
		catch(Exception e) {
			
		}
		
		GridPane info = new GridPane();
		info.setHgap(60);
		info.setVgap(20);
		
		if (getClient().getColor().toString().equals(draughts.enums.Color.White.toString())) {
			client.setState(States.YOU_PLAYING);
			player = Constants.playerYou;
		}
		else {
			player = Constants.playerOpponent;
			client.setState(States.OPPONENT_PLAYING);
		}
		nowPlaying = new Label("Now playing: " + player);
		
		info.add(infoPlayerName, 1, 2);
		info.add(infoPlayerColor, 1, 3);
		info.add(infoRowCol, 1, 5);
		info.add(infoTypeColor, 1, 6);
		info.add(nowPlaying, 1, 12);
		
		generateFields();
		
		BorderPane root = new BorderPane();
		root.setCenter(info);
		root.setLeft(board);
		
		Scene scene = new Scene(root, Constants.stageWidthBoard, Constants.stageHeightBoard);
		stage.setScene(scene);
		stage.setTitle(Constants.gameTitle);
		
		return stage;
	}
	
	public Stage createAskStage(Stage stage, String yourResult) {
		this.stages = Stages.ASK;
		stage = onCloseEvent(stage);
		
		GridPane info = new GridPane();
		info.setHgap(5);
		info.setVgap(20);
		
		newGameYes.setMinWidth(60);
		newGameYes.setOnAction(event -> {
			wanna_play_next();
		});
		
		newGameNo.setMinWidth(60);
		newGameNo.setOnAction(event -> {
			connection.write(new Client_Next_Game_No());
			connection.closeConnection();	
			System.exit(0);
		});
		
		String fullText = "";
		Color color = null;
		switch(yourResult) {
			case "win":
				fullText = "You WIN!";
				color = Color.Green;
				break;
			case "draw":
				fullText = "It's a DRAW!";
				color = Color.Yellow;
				break;
			case "lose":
				fullText = "You LOSE!";
				color = Color.Red;
				break;
		}
		result.setText(fullText);
		result.setTextFill(javafx.scene.paint.Color.web(color.getHexColor()));
		result.setStyle("-fx-font-weight: bold");
		result.setFont(new Font(30));
		info.add(result, 1, 1);
		info.add(infoResult, 1, 3);
		info.add(newGameYes, 3, 5);
		info.add(newGameNo, 4, 5);
		
		
		BorderPane root = new BorderPane();
		generateFields();
		generatePieces();
		root.setCenter(info);
		
		Scene scene = new Scene(root, Constants.stageWidthAsk, Constants.stageHeightAsk);
		stage.setScene(scene);
		stage.setTitle(Constants.gameTitle);	
		
		return stage;
	}
	
	//-----------------------------------------
	//--------------DISP-METHODS---------------
	//-----------------------------------------
	
	private void generateFields() {
		//10x10, starts with black (start is on top)
				
		for(int i = 0; i < fieldsCount; i++) {
			for(int j = 0; j < fieldsCount; j++) {
				ImageView view = new ImageView();
				Color fieldColor = null;
				
				final int row = j;
				final int col = i;
				
				view.setOnMouseClicked(event -> 
				{
					fieldClicked(row, col);
				});
				
				if((i+j) % 2 == 0) {
					view.setImage(imgWhite);
					fieldColor = Color.White;
				}
				else {
					view.setImage(imgBlack);					
					fieldColor = Color.Black;
				}
				
				fields.add(new Field(view, fieldColor, row, col));
				board.add(view, i, j);
			}			
		}
	}
	
	private void generatePieces() {
		
		int row = 0;
		int col = 0;
		generatePieces(Color.Black, row, col);
		
		row = (fieldsCount - piecesRow);
		col = 0;
		generatePieces(Color.White, row, col);
	}
	
	private void generatePieces(Color pieceColor, int row, int col) {	
		for(int i = 0; i < piecesRow; i++) {
			for (int j = 0; j < fieldsCount; j++) {
				
				if (( (row+i) + (col+j) ) % 2 != 0) {
					Piece piece = new Man(pieceColor);
					fields.getFields()[row+i][col+j].setPiece(piece);
					
					switch(pieceColor) {
						case Black:
							fields.getFields()[row+i][col+j].getImageView().setImage(imgBlackBlack);
							break;
						case White:
							fields.getFields()[row+i][col+j].getImageView().setImage(imgBlackWhite);
							break;
					default:
						break;
					}
				}
			}
		}
	}
	
	//-----------------------------------------
	//-----------CONNECT-METHODS---------------
	//-----------------------------------------	
	
	private void login() {
		Alert alert = new Alert(AlertType.WARNING);
		String name = enterName.getText();
		connection.connect(this);
		if (!connection.getConnected()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText("Server is currently offline");
			alert.setContentText("Server is currently offline");
			alert.setResizable(true);
			alert.show();
			return;
		}
		
		if (name.length() < 1) {
			alert.setHeaderText("Username was not set");
			alert.setContentText("Username was not set. Please set your username.");
			alert.setResizable(true);
			alert.show();
			return;
		}
		else if (name.length() > 20) {
			alert.setHeaderText("Username is too long.");
			alert.setContentText("Username is too long. Please cut your username.");
			alert.setResizable(true);
			alert.show();			
			return;
		}
		else {
			if (!login_processed) {
				login_processed = true;
				client.setName(name);
				connection.write(new Client_Login(name));	
			}
		}
	}
	
	public void processLogin(Message message) {
		Alert alert = new Alert(AlertType.WARNING);
		
		if (message.getName() == Messages.SERVER_LOGIN_FALSE) {
			
			alert.setHeaderText(message.getMessage());
			alert.setContentText(message.getMessage());
			alert.setResizable(true);
			alert.show();
			login_processed = false;
			return;
		}
		else {			
			primaryStage = createLobbyStage(primaryStage);
			alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Login was successful");
			alert.setContentText("Login was successful.");
			alert.setResizable(true);
			alert.show();
			connection.setConnectivity(new Connectivity(this, connection));
			connection.getConnectivity().start();
			return;
		}
	}
	
	public void play() {
		Alert alert = new Alert(AlertType.ERROR);
		if (!connection.getConnected()) {
			alert.setHeaderText("Server is currently offline");
			alert.setContentText("Server is currently offline");
			alert.setResizable(true);
			alert.show();
			return;
		}
		else if (!client.isConnected()) {			
			alert.setHeaderText("Connection lost or Port is blocked");
			alert.setContentText("Connection lost or Port is blocked");
			alert.show();
			return;
		}
		else {
			connection.write(new Client_Play_Game());
			this.play_processed = true;
			client.setState(States.WANNA_PLAY);
			play.setDisable(true);
			play.setText("Queued");
		}
	}
	
	public void processPlay(Message message, int game_ID) {
		Alert alert = new Alert(AlertType.ERROR);
		
		if (message.name == Messages.SERVER_START_GAME) {
			getClient().setColor(message.getColor());
			this.game_ID = game_ID;
			primaryStage = createBoardStage(primaryStage, this.game_ID);
			generatePieces();
		}
		else {
			alert.setHeaderText("ERROR");
			alert.setContentText("ERROR");
			alert.setResizable(true);
			alert.show();
			return;
		}
	}
	
	public void wanna_play_next() {
		this.client.setState(States.IN_LOBBY);
		this.play_processed = false;
		this.primaryStage = createLobbyStage(this.primaryStage);
	}
	
	public void quit() {
		try {
			this.primaryStage.fireEvent(new WindowEvent(this.primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));	
			this.primaryStage.close();
			Platform.exit();
			System.exit(0);
		}
		catch (Exception e) {
			this.primaryStage.close();
			Platform.exit();
			System.exit(0);
		}
	}
	
	public void updateGameID(int game_ID) {
		this.game_ID = game_ID;
	}
	
	public void removePiece(int row, int col) {
		fields.getFields()[row][col].getImageView().setImage(imgBlack);
		fields.getFields()[row][col].setPiece(null);
	}
	
	public void movePiece(int row_piece, int col_piece, int row_dest, int col_dest) {
		Image img = fields.getFields()[row_piece][col_piece].getImageView().getImage();
		Piece piece = fields.getFields()[row_piece][col_piece].getPiece();
		fields.getFields()[row_dest][col_dest].getImageView().setImage(img);
		fields.getFields()[row_dest][col_dest].setPiece(piece);
	}
	
	public void incorrect(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(message);
		alert.setContentText(message);
		alert.setResizable(true);
		alert.setWidth(alert.getWidth() + 200);
		alert.show();
		return;
	}
	
	public void setPlayer(String player) {
		this.player = player;
		this.nowPlaying.setText("Now playing: " + player);
	}
	
	public void nowPlayingYou() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("You are on move");
		alert.setContentText("You are on move");
		alert.setResizable(true);
		alert.setWidth(alert.getWidth() + 200);
		alert.show();
	}
	
	public void promote(int dp_row, int dp_col) {
		try {
			Piece oldPiece = fields.getFields()[dp_row][dp_col].getPiece();
			fields.getFields()[dp_row][dp_col].setPiece(new King(oldPiece.getColor()));
		}
		catch (Exception e) {}
		Color color = null;
		while (color == null) {
			try {
				color = fields.getFields()[dp_row][dp_col].getPiece().getColor();
			}
			catch (Exception e) {
				//vlakno jeste nestihlo zpracovat presun
			}
		}
		
		if (color == Color.Black) {
			fields.getFields()[dp_row][dp_col].getImageView().setImage(imgBlackBlackKing);
		}
		else {
			fields.getFields()[dp_row][dp_col].getImageView().setImage(imgBlackWhiteKing);
		}
	}
	
	public void endGame(String state) {
		this.primaryStage = createAskStage(primaryStage, state);
	}

	public void endGameNotProperly(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(message);
		alert.setContentText(message);
		alert.setResizable(true);
		alert.showAndWait();
		
		this.primaryStage = createAskStage(primaryStage, "win");
	}
	
	public void restoreBoard(String[] values) {
		int offset = 6;
		this.setClient(new Client(values[1]));
		switch(values[2]) {
			case "black":
				getClient().setColor(Color.Black);
				break;
			case "white":
				getClient().setColor(Color.White);
				break;
		}
		
		this.primaryStage = createBoardStage(this.primaryStage, Integer.parseInt(values[3]));
		
		switch(values[5]) {
			case "you":
				player = Constants.playerYou;
				client.setState(States.YOU_PLAYING);
				break;
			case "opponent":
				player = Constants.playerOpponent;
				client.setState(States.OPPONENT_PLAYING);
				break;
		}
		
		this.nowPlaying.setText("Now playing: " + player);
		
		inicialize_images();
		
		for(int i = offset; i < (Integer.parseInt(values[4]) * 2) + offset; i+=4) {
			int row = Integer.parseInt(values[i]);
			int col = Integer.parseInt(values[i+1]);
			String clr = values[i+2];
			String tp = values[i+3];
			
			Color color = null;
			Image img = null;
			switch(clr) {
				case "black":
					color = Color.Black;
					break;
				case "white":
					color = Color.White;
					break;
			}
			
			Piece piece = null;
			switch(tp) {
				case "man":
					piece = new Man(color);	
					
					if (color == Color.Black) img = imgBlackBlack;
					else img = imgBlackWhite;
					
					break;
				case "king":
					piece = new King(color);
					
					if (color == Color.Black) img = imgBlackBlackKing;
					else img = imgBlackWhiteKing;
					
					break;			
			}
			
			fields.getFields()[row][col].getImageView().setImage(img);
			fields.getFields()[row][col].setPiece(piece);
			System.out.println("row: " + row + "; col: " + col + "; type: " + tp + "; color: " + clr);
		}
	}
	
	public void opponent_connection_lost() {
		opp_connection_lost = true;
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Opponent lost connection");
		alert.setContentText("Please wait until opponent restore his connection");
		alert.show();
		alert.setResizable(true);
	}
	
	public void opponent_connection_restored() {
		opp_connection_lost = false;
		Alert alert = new Alert(AlertType.WARNING);
		alert.setAlertType(AlertType.INFORMATION);
		alert.setHeaderText("Opponent restore connection");
		alert.setContentText("Opponent is now ready to play!");
		alert.show();
		alert.setResizable(true);
	}
	
	public void already_wanna_play() {
		this.play_processed = true;
	}
	
	//------------------------------------------
	//---------------FUNC-METHOD----------------
	//------------------------------------------
	
	public Stage onCloseEvent(Stage stage) {
		stage.setOnCloseRequest(event -> {
			connection.write(new Client_App_End());
		    System.exit(0);
		    return;
		});
		
		return stage;
	}
	
	public void fieldClicked(int row, int col) {
		if (!client.isConnected()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Connection lost or Port is blocked");
			alert.setContentText("Connection lost or Port is blocked");
			alert.showAndWait();
			this.quit();			
		}
		
		if (opp_connection_lost) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Opponent lost connection");
			alert.setContentText("Please wait until opponent restore his connection");
			alert.show();
			alert.setResizable(true);
			return;
		}
		
		if (player.equals(Constants.playerOpponent)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Now playing opponent");
			alert.setContentText("You can't perform move, because opponent now playing");
			alert.show();
			alert.setResizable(true);
			return;
		}
		
		if(clickedField != null && fields.getField(row, col).getRow() == clickedField.getRow() 
				&& fields.getField(row, col).getCol() == clickedField.getCol()) {
			clickedField = null;
			infoRowCol.setText("row: NA, col: NA");
			infoTypeColor.setText("type: NA, color: NA");
			firstClicked = false;
		}
		else {
			clickedField = fields.getField(row, col);
			
			Piece piece = clickedField.getPiece();
			String typeName = "";
			String colorName = "";
			if (piece == null) {
				typeName = "NA";
				colorName = "NA";
			}
			else {
				typeName = piece.getType().geTypeName();
				colorName = piece.getColor().geColorName();
			}
			
			infoRowCol.setText("row: " + row + ", col: " + col);
			infoTypeColor.setText("type: " + typeName + ", color: " + colorName);
			
			if (firstClicked) {
				connection.write(new Client_Move(game_ID, firstRow, firstCol, row, col, firstColor, firstPiece));		
				firstRow = -1;
				firstCol = -1;
				firstClicked = false;
				firstColor = "";
				firstPiece = "";
				infoRowCol.setText("row: NA, col: NA");
				infoTypeColor.setText("type: NA, color: NA");
				clickedField = null;
			}
			else {
				firstClicked = true;
				firstRow = row;
				firstCol = col;
				firstColor = colorName;
				firstPiece = typeName;
			}
		}
	}
	
	public void inicialize_images() {
		try {
			streamWhite = new FileInputStream("img/white.png");
			streamBlack = new FileInputStream("img/black.png");
			
			streamBlackBlack = new FileInputStream("img/black-black.png");
			streamBlackWhite = new FileInputStream("img/black-white.png");
		
			streamBlackBlackKing = new FileInputStream("img/black-black-king.png");
			streamBlackWhiteKing = new FileInputStream("img/black-white-king.png");
		
			imgWhite = new Image(streamWhite);
			imgBlack = new Image(streamBlack);
		
			imgBlackBlack = new Image(streamBlackBlack);
			imgBlackWhite = new Image(streamBlackWhite);
		
			imgBlackBlackKing = new Image(streamBlackBlackKing);
			imgBlackWhiteKing = new Image(streamBlackWhiteKing);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
