package reviews;

/**
 * <code>Interface</code> that represents the <code>Review</code> of a certain
 * booking.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public interface Review {

	/**
	 * Returns the <code>Review</code> classification rating. (excellent - 5; good -
	 * 4; average - 3; poor - 2; terrible - 1)
	 * 
	 * @return - <code>int</code> with the classification rating.
	 */
	int getRating();
}
