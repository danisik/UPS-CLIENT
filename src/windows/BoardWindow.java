package windows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import connection.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import draughts.constants.Constants;
import draughts.enums.*;
import draughts.field.*;
import draughts.messages.*;
import draughts.piece.*;

public class BoardWindow extends Window {
	
	private static InputStream streamWhite = null;
	private static InputStream streamBlack = null;
	
	private static InputStream streamBlackBlack = null;
	private static InputStream streamBlackWhite = null;
	
	private static InputStream streamBlackBlackKing = null;
	private static InputStream streamBlackWhiteKing = null;
	
	
	private static Image imgWhite = null;
	private static Image imgBlack = null;
	
	private static Image imgBlackBlack = null;
	private static Image imgBlackWhite = null;
	
	private static Image imgBlackBlackKing = null;
	private static Image imgBlackWhiteKing = null;
	
	GridPane board = new GridPane();
	
	String player = Constants.playerYou;
	
	Label nowPlaying;
	Label infoRowCol = new Label("row: NA, col: NA");
	Label infoTypeColor = new Label("type: NA, color: NA");
	Label infoPlayerColor = new Label();
	Label infoPlayerName = new Label();
	Button endMove;
	
	//10x10 board, 4x5 pieces per player, white starts, pieces starts staying on black field
	int piecesCountPerRow = 5;
	int piecesRow = 4;
	
	int fieldsCount = 10;
	Fields fields = new Fields(fieldsCount);
	Field clickedField = null;
	
	Message message = null;
	
	public BoardWindow(Window window, Stage stage, Color color) {
		this.window = window;
		this.window.client.setColor(color);
		this.setControlParameters();
		this.setClassVariables(window, Constants.stageWidthBoard, Constants.stageHeightBoard);
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
		Scene scene = new Scene(createObjects(), this.stageWidth, this.stageHeight);	
		return scene;
	}
	
	@Override
	public BorderPane createObjects() {
		BorderPane root = new BorderPane();
		createBoard();
		root.setTop(createTop());
		root.setCenter(createInfo());
		root.setLeft(board);
		return root;
	}
	
	public void createBoard() {	
		generateFields();
		generatePieces();
	}
	
	public GridPane createInfo() {		
		GridPane info = new GridPane();
		info.setHgap(60);
		info.setVgap(20);
		
		nowPlaying = new Label("Now playing: " + player);
		endMove = new Button(Constants.endMove);
		
		endMove.setOnAction(event -> 
		{
			if (player.equals(Constants.playerYou)) player = Constants.playerOpponent;
			else player = Constants.playerYou;
			nowPlaying.setText(Constants.nowPlaying + player);
		});
		
		info.add(infoPlayerName, 1, 2);
		info.add(infoPlayerColor, 1, 3);
		info.add(infoRowCol, 1, 5);
		info.add(infoTypeColor, 1, 6);
		info.add(nowPlaying, 1, 12);
		info.add(endMove, 1, 13);
		return info;
	}
	
	public GridPane createTop() {
		GridPane top = new GridPane();
		
		return top;
	}
	
	private void generateFields() {
		//10x10, starts with white (start is on top)
				
		for(int i = 0; i < fieldsCount; i++) {
			for(int j = 0; j < fieldsCount; j++) {
				//board.add(new Button(i + " " + j), i, j);
				ImageView view = new ImageView();
				Color fieldColor = null;
				
				final int col = i;
				final int row = j;
				
				view.setOnMouseClicked(event -> 
				{
					if(clickedField != null && fields.getField(row, col).getRow() == clickedField.getRow() 
							&& fields.getField(row, col).getCol() == clickedField.getCol()) {
						clickedField = null;
						infoRowCol.setText("row: NA, col: NA");
						infoTypeColor.setText("type: NA, color: NA");
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
					}
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
					Man man = new Man(pieceColor);
					fields.getFields()[row+i][col+j].setPiece(man);
					
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
	
	@Override
	public void setControlParameters() {
		infoPlayerColor.setText("Your color is: " + window.client.getColor().toString());
		infoPlayerColor.setTextFill(javafx.scene.paint.Color.web(window.client.getColor().getHexColor()));
		infoPlayerColor.setFont(new Font(15));
		
		infoPlayerName.setText("Your name is: " + window.client.getName());
		infoPlayerName.setTextFill(javafx.scene.paint.Color.web(draughts.enums.Color.Blue.getHexColor()));
		infoPlayerName.setFont(new Font(15));
		
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
		}
		catch(Exception e) {
		}
	}
	
	@Override
	public void setEvents() {
		
	}
}
