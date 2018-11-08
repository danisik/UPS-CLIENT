package draughts.enums;

public enum Color {
	Black("black"), 
	White("white"),
	Red("red", "#f44242"),
	Green("green", "#5c9952"),
	Blue("blue", "#4c02f9")
	;
	
	private String name;
	private String hexColor;
	
	private Color(String name) {
		this.name = name;
		this.hexColor = null;
	}
	
	private Color(String name, String hexColor) {
		this.name = name;
		this.setHexColor(hexColor);
	}
	
	public String geColorName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}
}
