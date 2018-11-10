package draughts.field;

import javafx.scene.image.ImageView;

public class Fields {

	private int fieldsCount = 0;
	private Field[][] fields = null;
	
	public Fields(int fieldsCount) {
		this.fieldsCount = fieldsCount;
		fields = new Field[fieldsCount][fieldsCount];
	}
	
	public Field getField(int row, int col) {
		return fields[row][col];
	}
	
	public Field getField(ImageView view) {
		
		for(int i = 0; i < fieldsCount; i++) {
			for(int j = 0; j < fieldsCount; j++) {
				if(fields[i][j].getImageView() == view) return fields[i][j];
			}
		}
		return null;
	}
	
	public void add(Field field) {
		fields[field.getRow()][field.getCol()] = field;
	}
	
	public Field[][] getFields() {
		return fields;
	}
	
}
