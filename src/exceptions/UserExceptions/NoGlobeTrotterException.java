package exceptions.UserExceptions;

/**
 * <code>Exception</code> thrown when a there is not yet a global trotter in the
 * application.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoGlobeTrotterException extends Exception {

	private static final String MESSAGE = "No globe trotter.";

	public NoGlobeTrotterException() {
		super(MESSAGE);
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE);
	}

}
