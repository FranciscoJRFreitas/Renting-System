package exceptions.BookingExceptions;

/**
 * <code>Exception</code> thrown when a certain host has no appointed bookings.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class HostHasNoBookingsException extends Exception {

	private static final String MESSAGE = "User %s has no bookings.";

	private String userId;

	public HostHasNoBookingsException(String userId) {
		super(MESSAGE);
		this.userId = userId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, userId);
	}

}
