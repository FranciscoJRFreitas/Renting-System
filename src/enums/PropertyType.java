package enums;

/**
 * <code>Enum</code> that represents all possible property types.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public enum PropertyType {

	PRIVATE_ROOM("private room"), ENTIRE_PLACE("entire place"), UNKNOWN_TYPE("");

	private String type;

	private PropertyType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
