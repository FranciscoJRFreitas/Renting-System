package exceptions.BookingExceptions;

/**
 * <code>Exception</code> thrown when a certain host is not the host owner of
 * the searched booking.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NotBookingHostException extends Exception {

	private static final String MESSAGE = "User %s is not the host of booking %s.";

	private String userId;

	private String bookingId;

	public NotBookingHostException(String userId, String bookingId) {
		super(MESSAGE);
		this.userId = userId;
		this.bookingId = bookingId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, userId, bookingId);
	}
}
