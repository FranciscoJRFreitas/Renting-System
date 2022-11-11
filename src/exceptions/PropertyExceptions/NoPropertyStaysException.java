package exceptions.PropertyExceptions;

/**
 * <code>Exception</code> thrown when a property does not have any stays appointed.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoPropertyStaysException extends Exception {

	private static final String MESSAGE = "Property %s does not have any stays.";

	private String propertyId;

	public NoPropertyStaysException(String propertyId) {
		super(MESSAGE);
		this.propertyId = propertyId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, propertyId);
	}
}
