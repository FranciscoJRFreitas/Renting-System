package exceptions.UserExceptions;

/**
 * <code>Exception</code> thrown when a user is not a guest.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NotGuestException extends Exception {
	
	private static final String MESSAGE = "User %s is not a guest user.";

	private String userId;

	public NotGuestException(String userId) {
		super(MESSAGE);
		this.userId = userId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, userId);
	}

}
