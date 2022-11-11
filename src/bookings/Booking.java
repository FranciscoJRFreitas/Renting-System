package bookings;

import java.time.LocalDate;

import enums.Status;
import properties.Property;
import users.Guest;

/**
 * <code>Interface</code> that represents the <code>Booking</code> object.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public interface Booking {

	/**
	 * Returns <code>Booking</code> id;
	 * 
	 * @return - <code>Booking</code> id;
	 */
	String getBookingId();

	/**
	 * Returns <code>Property</code> property;
	 * 
	 * @return - <code>Property</code> property;
	 */
	Property getProperty();

	/**
	 * Returns <code>Booking</code> state;
	 * 
	 * @return - <code>Booking</code> state;
	 */
	Status getState();

	/**
	 * Returns the <code>Booking</code> arrival date.
	 * 
	 * @return - <code>Booking</code> arrival date.
	 */
	LocalDate getArrivalDate();

	/**
	 * Returns the <code>Booking</code> departure date.
	 * 
	 * @return - <code>Booking</code> departure date.
	 */
	LocalDate getDepartureDate();

	/**
	 * Returns the declared number of guests of a booking.
	 * 
	 * @return - <code>int</code> guestsNum.
	 */
	int getGuestsNum();

	/**
	 * Returns the <code>Guest</code> who made the booking.
	 * 
	 * @return - <code>Guest</code> guest.
	 */
	Guest getGuest();

	/**
	 * Changes the status of the <code>Booking</code> to requested.
	 * 
	 * @pre The booking exists in the system.
	 * @pre The booking can be in requested state.
	 */
	void requestBooking();

	/**
	 * Changes the status of the <code>Booking</code> to reviewed.
	 * 
	 * @pre The booking exists in the system.
	 * @pre The booking can be in reviewed state.
	 */
	void reviewBooking();

	/**
	 * Changes the status of the <code>Booking</code> to confirmed.
	 * 
	 * @pre The booking exists in the system.
	 * @pre The booking can be in confimed state.
	 */
	void confirmBooking();

	/**
	 * Changes the status of the <code>Booking</code> to rejected.
	 * 
	 * @pre The booking exists in the system.
	 * @pre The booking can be in rejected state.
	 */
	void rejectBooking();

	/**
	 * Changes the status of the <code>Booking</code> to paid.
	 * 
	 * @pre The booking exists in the system.
	 * @pre The booking can be in payed state.
	 */
	void payBooking();

	/**
	 * Changes the status of the <code>Booking</code> to cancelled.
	 * 
	 * @pre The booking exists in the system.
	 * @pre The booking can be in cancelled state.
	 */
	void cancelBooking();

	/**
	 * Checks if a <code>Booking</code> occurs at the same time as other.
	 * 
	 * @pre The bookings exist in the system.
	 * @param other - Another <code>Booking</code>.
	 * @return - <code>true</code> if a booking date is overlapped with another one;
	 *         <code>false</code>, otherwise.
	 */
	boolean hasOverlappedDate(Booking other);

	/**
	 * Returns the booking price.
	 * 
	 * @pre The booking is payed and reviewed.
	 * @return - <code>double</code> with the total price.
	 */
	double getTotalPrice();

	/**
	 * Returns a <code>double</code> with the total price after the booking being
	 * paid by their <code>Guest</code>.
	 *
	 * @pre The booking is payed and reviewed.
	 * @return -<code>double</code> with the total price after being paid.
	 */
	double getBookingPaidMoney();

}
