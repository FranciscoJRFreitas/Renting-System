package exceptions.BookingExceptions;

/**
 * <code>Exception</code> thrown when a certain guest has no appointed bookings.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class GuestHasNoBookingsException extends Exception {

	private static final String MESSAGE = "Guest %s has no bookings.";

	private String guestId;

	public GuestHasNoBookingsException(String guestId) {
		super(MESSAGE);
		this.guestId = guestId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, guestId);
	}

}
