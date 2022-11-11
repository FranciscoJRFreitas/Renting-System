package reviews;

import bookings.Booking;
import enums.Classification;
import users.Guest;

/**
 * <code>Class</code> that represents a review object - ReviewClass.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class ReviewClass implements Review {

	/**
	 * <code>Booking</code> with the booking to be reviewed.
	 */
	@SuppressWarnings("unused")
	private Booking booking;

	/**
	 * <code>Guest</code> with the reviewing author.
	 */
	@SuppressWarnings("unused")
	private Guest author;
	/**
	 * <code>String</code> with the review comment.
	 */
	@SuppressWarnings("unused")
	private String comment;

	/**
	 * <code>Classification</code> with the review classification.
	 */
	private Classification classification;

	/**
	 * Class constructor.
	 * 
	 * @param booking        - Booking object.
	 * @param author         - Booking author.
	 * @param comment        - Review comment.
	 * @param classification - Review classification.
	 */
	public ReviewClass(Booking booking, Guest author, String comment, Classification classification) {
		this.booking = booking;
		this.author = author;
		this.comment = comment;
		this.classification = classification;

	}

	@Override
	public int getRating() {
		return classification.getRating();
	}

}
