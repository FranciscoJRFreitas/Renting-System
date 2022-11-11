package exceptions.BookingExceptions;

/**
 * <code>Exception</code> thrown when a certain guest has not appointed the
 * searched booking.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NotBookingGuestException extends Exception {

	private static final String MESSAGE = "User %s is not the guest of booking %s.";

	private String guestId;

	private String bookingId;

	public NotBookingGuestException(String guestId, String bookingId) {
		super(MESSAGE);
		this.guestId = guestId;
		this.bookingId = bookingId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, guestId, bookingId);
	}
}
