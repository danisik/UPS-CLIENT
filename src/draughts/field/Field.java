package draughts.field;

import draughts.enums.Color;
import draughts.piece.*;
import javafx.scene.image.ImageView;

public class Field {
	
	private ImageView imageView = null;
	private Color color = null;
	private Piece piece = null;
	private int row, col;
	
	public Field(ImageView imageField, Color color, int row, int col) {
		this.setImageView(imageField);
		this.setColor(color);
		this.setRow(row);
		this.setCol(col);
	}
	

	public Piece getPiece() {
		return this.piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getCol() {
		return col;
	}


	public void setCol(int col) {
		this.col = col;
	}
}
