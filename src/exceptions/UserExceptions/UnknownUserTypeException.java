package exceptions.UserExceptions;

/**
 * <code>Exception</code> thrown when a user type is unknown.
 * 
 * @author Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class UnknownUserTypeException extends Exception {

	private static final String MESSAGE = "Unknown user type";

	public UnknownUserTypeException() {
		super(MESSAGE);
	}

}
