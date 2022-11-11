package properties;

import java.time.LocalDate;
import java.util.Iterator;

import bookings.Booking;
import reviews.Review;
import users.Host;

/**
 * <code>Interface</code> that represents the super class Property.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public interface Property {

	/**
	 * Returns the property id.
	 * 
	 * @return - <code>String</code> id.
	 */
	String getId();

	/**
	 * Returns the property location.
	 * 
	 * @return - <code>String</code> with the location.
	 */
	String getLocation();

	/**
	 * Returns the property capacity.
	 * 
	 * @return - <code>int</code> with the capacity.
	 */
	int getCapacity();

	/**
	 * Returns the property price per night.
	 * 
	 * @return - <code>double</code> with the price per night.
	 */
	double getPricePerNight();

	/**
	 * Returns the property host.
	 * 
	 * @return - <code>Host</code> user.
	 */
	Host getHost();

	/**
	 * Returns the reviews list size.
	 * 
	 * @return - <code>int</code> counter.
	 */
	int reviewCounter();

	/**
	 * Returns the bookings list size.
	 * 
	 * @return - <code>int</code> counter.
	 */
	int bookingCounter();

	/**
	 * Returns the latest updated departure from a <code>Property</code>.
	 * 
	 * @return - latestUpdatedDeparture.
	 */
	LocalDate getLatestUpdatedDeparture();

	/**
	 * Adds a <code>Booking</code> to a certain <code>Property</code>.
	 * 
	 * @pre The booking does not exist already in the property.
	 * @param booking - guest booking.
	 */
	void addBooking(Booking booking);

	/**
	 * Adds a <code>Review</code> to a certain <code>Property</code>.
	 * 
	 * @pre The booking can be reviewed.
	 * @param review - guest review.
	 */
	void addReview(Review review);

	/**
	 * Sets the latest updated departure from a <code>Property</code>.
	 * 
	 * @param latestUpdatedDeparture - The latest updated departure from a
	 *                               <code>Property</code>.
	 */
	void setLatestUpdatedDeparture(LocalDate latestUpdatedDeparture);

	/**
	 * Checks if a certain <code>Booking</code> belongs to a Property.
	 * 
	 * @param bookingId- Booking identifier.
	 * @return - <code>true</code> if the booking belongs to a Property,
	 *         <code>false</code>, otherwise.
	 */
	boolean hasBooking(String bookingId);

	/**
	 * Checks if the <code>Property</code> has any bookings.
	 * 
	 * @return - <code>true</code> if the property has bookings, <code>false</code>,
	 *         otherwise.
	 */
	boolean hasBookings();

	/**
	 * Returns the given <code>Booking</code> state.
	 * 
	 * @pre The booking exists in the property.
	 * @param bookingId - Booking identifier.
	 * @return - <code>String</code> state of booking.
	 */
	String getBookingState(String bookingId);

	/**
	 * Returns the <code>Booking</code> object by the given id.
	 * 
	 * @pre The booking exists in the property.
	 * @param bookingId - Booking identifier.
	 * @return - <code>Booking</code> object by his id.
	 */
	Booking getBookingById(String bookingId);

	/**
	 * Rejects all the <code>Booking</code> objects of a certain property with an
	 * overlapping date.
	 * 
	 * @pre The booking exists in the property.
	 * @pre The booking can be in rejected state.
	 * @param bookingId - Booking identifier.
	 */
	void rejectOverlappingBookings(String bookingId);

	/**
	 * Lists all rejected <code>Booking</code> objects of a certain property.
	 * 
	 * @return - Booking <code>Iterator</code> with rejected bookings.
	 */
	Iterator<Booking> listRejectedBookings();

	/**
	 * Verifies if the given <code>Booking</code> has an conflicting date with
	 * another booking.
	 * 
	 * @pre The bookings exist in the property.
	 * @param booking - Booking object.
	 * @return - <code>true</code> if there is a date conflict, <code>false</code>,
	 *         otherwise.
	 */
	boolean conflictsWithOtherBookings(Booking booking);

	/**
	 * Rejects a given booking.
	 * 
	 * @pre The booking exists in the property.
	 * @param bookingId - Booking identifier.
	 */
	void rejectBooking(String bookingId);

	/**
	 * Lists all rejected bookings of a certain <code>Property</code>.
	 * 
	 * @return - Booking <code>Iterator</code> with rejected bookings.
	 */
	Iterator<Booking> listAllRejected();

	/**
	 * Verifies if the <code>Property</code> has capacity for the guests declared in
	 * the booking.
	 * 
	 * @pre The booking exists in the property.
	 * @param guestsNum - Number of guests of a booking.
	 * @return - <code>true</code> if the property has capacity, <code>false</code>,
	 *         otherwise.
	 */
	boolean hasCapacity(int guestsNum);

	/**
	 * Lists all changed bookings of a certain property after one being paid.
	 * 
	 * @return - Booking <code>Iterator</code> with changed bookings.
	 */
	Iterator<Booking> listAllChanged(Booking booking, LocalDate latestUpdatedPayment);

	/**
	 * Returns the property type: either entire place or private room.
	 * 
	 * @pre The property exists in the system.
	 * @return - <code>String</code> property type.
	 */
	String getPropertyType();

	/**
	 * Verifies if the <code>Property</code> has at least one stay of a guest.
	 * 
	 * @return - <code>true</code> if the property has at least one stay,
	 *         <code>false</code>, otherwise.
	 */
	boolean hasStays();

	/**
	 * Lists all stays of a certain <code>Property</code> after one being paid or
	 * reviewed.
	 * 
	 * @return - Booking <code>Iterator</code> with all the property stays.
	 */
	Iterator<Booking> listAllStays();

	/**
	 * Returns the property average rating, based on its <code>Reviews</code>
	 * classifications.
	 * 
	 * @pre There are reviewed bookings in the property (otherwise, average rating is zero).
	 * @return - <code>double</code> averageRating with the property average rating.
	 */
	public double getAverageRating();

}
