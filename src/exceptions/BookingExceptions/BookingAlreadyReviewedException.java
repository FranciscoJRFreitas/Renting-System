package exceptions.BookingExceptions;

/**
 * <code>Exception</code> thrown when a certain booking was already reviewed.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class BookingAlreadyReviewedException extends Exception {
	private static final String MESSAGE = "Booking %s was already reviewed.";

	private String bookingId;

	public BookingAlreadyReviewedException(String bookingId) {
		super(MESSAGE);
		this.bookingId = bookingId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, bookingId);
	}
}
