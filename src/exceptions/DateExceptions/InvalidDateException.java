package exceptions.DateExceptions;

/**
 * <code>Exception</code> thrown when a certain booking date is invalid, due to
 * many different reasons.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class InvalidDateException extends Exception {
	private static final String MESSAGE = "Invalid booking dates.";

	public InvalidDateException() {
		super(MESSAGE);
	}

}
