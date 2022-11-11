package comparator;

import java.util.Comparator;

import bookings.Booking;

/**
 * <code>Class</code> that compares the bookings by their id using the
 * <code>Booking</code> object.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class BookingStatesComparator implements Comparator<Booking> {

	@Override
	public int compare(Booking booking1, Booking booking2) {
		return booking1.getBookingId().compareTo(booking2.getBookingId());
	}
}