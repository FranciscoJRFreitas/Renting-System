package exceptions.BookingExceptions;

/**
 * <code>Exception</code> thrown when a certain searched booking does not exist
 * in the application.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoBookingException extends Exception {

	private static final String MESSAGE = "Booking %s does not exist.";

	private String bookingId;

	public NoBookingException(String bookingId) {
		super(MESSAGE);
		this.bookingId = bookingId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, bookingId);
	}
}
