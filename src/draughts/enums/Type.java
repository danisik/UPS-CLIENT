package draughts.enums;

public enum Type {
	Man("man"), 
	King("king")
	;
	
	private String name;
	private Type(String name) {
		this.name = name;
	}
	
	public String geTypeName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
