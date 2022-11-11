package exceptions.BookingExceptions;

/**
 * <code>Exception</code> thrown when a certain host has not rejected any bookings.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoBookingRejectionsException extends Exception {
	
	private static final String MESSAGE = "Host %s has not rejected any booking.";

	private String hostId;
	
	public NoBookingRejectionsException(String hostId) {
		super(MESSAGE);
		this.hostId = hostId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, hostId);
	}
}
