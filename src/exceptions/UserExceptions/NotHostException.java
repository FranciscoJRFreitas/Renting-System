package exceptions.UserExceptions;

/**
 * <code>Exception</code> thrown when a user is not a host.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NotHostException extends Exception {
	
	private static final String MESSAGE = "User %s is not a host user.";
	
	private String userId;
	
	public NotHostException(String userId) {
		super(MESSAGE);
		this.userId = userId;
	}
	
	@Override
	public String getMessage() {
		return String.format(MESSAGE, userId);
	}
}
