package users;

import java.time.LocalDate;
import java.util.Iterator;

import bookings.Booking;
import reviews.Review;

/**
 * <code>Interface</code> that represents one of the extended interfaces of the
 * two types of users.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public interface Guest extends User {

	/**
	 * Adds a <code>Booking</code> to a certain <code>Guest</code>.
	 * 
	 * @param booking - guest booking.
	 */
	void addBooking(Booking booking);

	/**
	 * Pays a <code>Booking</code> with the given bookingId.
	 * 
	 * @param bookingId - Booking identifier.
	 */
	void payBooking(String bookingId);

	/**
	 * Verifies if a <code>Guest</code> has the booking by the given bookingId.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>true</code> if the <code>Guest</code> has the given booking;
	 *         <code>false</code> otherwise.
	 */
	boolean hasBooking(String bookingId);

	/**
	 * Checks if the <code>Guest</code> can pay the booking.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>true</code> if the <code>Guest</code> can pay the given
	 *         booking; <code>false</code> otherwise.
	 */
	boolean canPayBooking(String bookingId);

	/**
	 * Returns the <code>Booking</code> by the given bookingId.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>Booking</code> by id.
	 */
	Booking getBookingById(String bookingId);

	/**
	 * Returns the current booking state of a <code>Booking</code> by the given
	 * bookingId.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>String</code> state.
	 */
	String getBookingState(String bookingId);

	/**
	 * Lists all changed <code>Bookings</code> of a all properties after one them
	 * has been paid.
	 * 
	 * @return - Booking <code>Iterator</code> with changed bookings.
	 */
	Iterator<Booking> listAllChanged(String bookingId);

	/**
	 * Checks if the <code>Guest</code> has already reviewed the given booking.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>true</code> if the <code>Guest</code> already reviewd the
	 *         given booking; <code>false@Override
	</code> otherwise.
	 */
	boolean hasAlreadyReviewedBooking(String bookingId);

	/**
	 * Review the given <code>Booking</code> with a certain comment and
	 * classification.
	 * 
	 * @param review    - Booking review.
	 * @param bookingId - Booking identifier.
	 */
	void reviewBooking(Review review, String bookingId);

	/**
	 * Checks if date of the <code>Booking</code> is valid for that guest.
	 * 
	 * @pre arrival.isBefore(departure);
	 * @param arrival   - Date of the arrival.
	 * @param departure - Date of the departure.
	 * @param guestId   - Guest identifier.
	 * @return - <code>true</code>, if the date of the booking is valid;
	 *         <code>false</code>, otherwise.
	 */
	boolean hasValidBookingDate(LocalDate arrival, LocalDate departure);

	/**
	 * Checks if a booking is in paid state.
	 * 
	 * @param bookingId - booking Identifier.
	 * @return - <code>true</code>, if the state of the booking is paid;
	 *         <code>false</code>, otherwise.
	 */
	boolean isInPaidState(String bookingId);

	/**
	 * Checks if the <code>Guest</code> has any bookings.
	 * 
	 * @return - <code>true</code> if the guest has bookings, <code>false</code>,
	 *         otherwise.
	 */
	boolean hasBookings();

	/**
	 * Lists all<code>Bookings</code> of the guest with their respective info.
	 * 
	 * @return - Booking <code>Iterator</code> with their respective info.
	 */
	Iterator<Booking> listBookingsInfo();

	/**
	 * Returns the <code>Guest</code> total paid bookings money.
	 * 
	 * @return - <code>double</code> with the total paid bookings money.
	 */
	double getTotalPaidMoney();

	/**
	 * Returns the <code>Guest</code> number of different location s stays.
	 * 
	 * @return - <code>int</code> with the different location s stays.
	 */
	int getStaysLocationsNum();

	/**
	 * Verifies if a <code>Guest</code> has any stays.
	 * 
	 * @return - <code>true</code> if the guest has any stays, <code>false</code>,
	 *         otherwise.
	 */
	boolean hasStays();

	/**
	 * Checks which guest is the best qualified for globetrotter.
	 * 
	 * @param other - <code>Guest</code> - guest.
	 * @return - <code>true</code> if the guest is the better globetrotter,
	 *         <code>false</code>, otherwise.
	 */
	boolean isGlobeTrotter(Guest other);

	/**
	 * Returns the <code>Guest</code> total number of bookings.
	 * 
	 * @return - <code>int</code> with the guest total number of bookings.
	 */
	int getAllBookingsNum();

}
