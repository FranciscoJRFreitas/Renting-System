package exceptions.PropertyExceptions;

/**
 * <code>Exception</code> thrown when a property already exists in the system.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class HasPropertyException extends Exception {

	private static final String MESSAGE = "Property %s already exists.";

	private String propertyId;

	public HasPropertyException(String propertyId) {
		super(MESSAGE);
		this.propertyId = propertyId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, propertyId);
	}
}
