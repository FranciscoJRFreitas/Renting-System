package exceptions.UserExceptions;

/**
 * <code>Exception</code> thrown when there are no useres registered in the
 * application.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoUsersException extends Exception {

	private static final String MESSAGE = "No users registered.";

	public NoUsersException() {
		super(MESSAGE);
	}

}
