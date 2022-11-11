package exceptions.PropertyExceptions;

/**
 * <code>Exception</code> thrown when a property does not satisfy the given criterion.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoSatisfyingPropertyException extends Exception {

	private static final String MESSAGE = "No property was found in %s.";

	private String location;

	public NoSatisfyingPropertyException(String location) {
		super(MESSAGE);
		this.location = location;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, location);
	}

}
