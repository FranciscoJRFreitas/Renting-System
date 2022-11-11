package exceptions.BookingExceptions;

/**
 * <code>Exception</code> thrown when a certain booking cannot be payed, due to
 * its current state.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class CannotPayBookingException extends Exception {

	private static final String MESSAGE = "Cannot pay booking %s that is in state %s.";

	private String bookingId;

	private String bookingState;

	public CannotPayBookingException(String bookingId, String bookingState) {
		super(MESSAGE);
		this.bookingId = bookingId;
		this.bookingState = bookingState;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, bookingId, bookingState);
	}

}
