package enums;

/**
 * <code>Enum</code> that represents all possible user types.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public enum UserType {
	
	GUEST("guest"), HOST("host"), UNKNOWN_TYPE("");
	
	private String type;
	
	private UserType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}