package comparator;

import java.util.Comparator;

/**
 * <code>Class</code> that compares the bookings by their id.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class BookingComparator implements Comparator<String> {

	@Override
	public int compare(String bookingId1, String bookingId2) {
		return bookingId1.compareTo(bookingId2);
	}

}
