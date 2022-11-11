package exceptions.UserExceptions;

/**
 * <code>Exception</code> thrown when a user already exists in the application.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class UserExistsException extends Exception {

	private static final String MESSAGE = "User %s already exists.";

	private String userId;

	public UserExistsException(String userId) {
		super(MESSAGE);
		this.userId = userId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, userId);
	}
}
