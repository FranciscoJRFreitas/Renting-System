package exceptions.PropertyExceptions;

/**
 * <code>Exception</code> thrown when a property does not exist in the system.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoPropertyException extends Exception {

	private static final String MESSAGE = "Property %s does not exist.";

	private String propertyId;

	public NoPropertyException(String propertyId) {
		super(MESSAGE);
		this.propertyId = propertyId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, propertyId);
	}
}
