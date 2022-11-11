package exceptions.UserExceptions;

/**
 * <code>Exception</code> thrown when a user does not exist in the application.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoUserException extends Exception {

	private static final String MESSAGE = "User %s does not exist.";

	private String userId;

	public NoUserException(String userId) {
		super(MESSAGE);
		this.userId = userId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, userId);
	}

}
