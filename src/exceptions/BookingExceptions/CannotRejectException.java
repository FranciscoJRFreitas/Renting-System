package exceptions.BookingExceptions;

/**
 * <code>Exception</code> thrown when a certain booking cannot be rejected, due
 * to its current state.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class CannotRejectException extends Exception {
	private static final String MESSAGE = "Cannot reject booking %s that is in state %s.";

	private String bookingId;
	private String bookingState;

	public CannotRejectException(String bookingId, String bookingState) {
		super(MESSAGE);
		this.bookingId = bookingId;
		this.bookingState = bookingState;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, bookingId, bookingState);
	}
}
