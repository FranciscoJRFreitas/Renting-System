package users;

import java.util.Iterator;

import bookings.Booking;
import exceptions.PropertyExceptions.HasPropertyException;
import properties.EntirePlace;
import properties.PrivateRoom;
import properties.Property;

/**
 * <code>Interface</code> that represents one of the extended interfaces of the
 * two types of users.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public interface Host extends User {

	/**
	 * Add a property of of <code>Private Room</code> type.
	 * 
	 * @param propertyId
	 * @param location
	 * @param capacity
	 * @param price
	 * @param rooms
	 * @param privateroom
	 * @throws HasPropertyException
	 */
	void addPrivateRoom(String propertyId, String location, int capacity, int price, int rooms, PrivateRoom privateroom)
			throws HasPropertyException;

	/**
	 * Add a property of of <code>Entire place</code> type.
	 * 
	 * @param propertyId
	 * @param location
	 * @param capacity
	 * @param price
	 * @param roomsF
	 * @param entireplace
	 * @throws HasPropertyException
	 */
	void addEntirePlace(String propertyId, String location, int capacity, int price, int rooms, EntirePlace entireplace)
			throws HasPropertyException;

	/**
	 * Return the private room by the given id.
	 * 
	 * @param propertyId
	 * @return - <code>Private Room</code> property by the id.
	 */
	PrivateRoom getPrivateRoom(String propertyId);

	/**
	 * Return an <code>Iterator</code> of the host properties.
	 * 
	 * @return - Iterator of <code>Property</code> list.
	 */
	Iterator<Property> listProperties();

	/**
	 * Checks if the <code>Host</code>has properties.
	 * 
	 * @return - <code>true</code> if the properties list has a size > 0,
	 *         <code>false</code>, otherwise.
	 */
	boolean HasProperties();

	/**
	 * Checks if a certain <code>Booking</code> belongs to an Host.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>true</code> if the booking belongs to an Host,
	 *         <code>false</code>, otherwise.
	 */
	boolean hasBooking(String bookingId);

	/**
	 * Checks if the <code>Host</code> has any bookings.
	 * 
	 * @return - <code>true</code> if the host has bookings, <code>false</code>,
	 *         otherwise.
	 */
	boolean hasBookings();

	/**
	 * Confirms the certain <code>Booking</code>.
	 * 
	 * @param bookingId - Booking identifier.
	 */
	void confirmBooking(String bookingId);

	/**
	 * Rejects the certain <code>Booking</code>.
	 * 
	 * @param bookingId - Booking identifier.
	 */
	void rejectBooking(String bookingId);

	/**
	 * Return the <code>Booking</code> current state.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>Booking</code> state.
	 */
	String getBookingState(String bookingId);

	/**
	 * Checks if a certain <code>Booking</code> is in the requested state.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>true</code> if the booking is in the requested state,
	 *         <code>false</@Override code>, otherwise.
	 */
	boolean bookingIsRequested(String bookingId);

	/**
	 * Returns the property associated to a bookingId.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>Property</code> property.
	 */
	Property getPropertyByBookingId(String bookingId);

	/**
	 * Returns the Booking associated to a bookingId.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>Booking</code> booking.
	 */
	Booking getBookingById(String bookingId);

	/**
	 * Lists all rejected bookings from a host.
	 * 
	 * @return - Iterator of <code>Booking</code> list.
	 */
	Iterator<Booking> listAllRejected();

	/**
	 * Checks if a host has any rejected bookings.
	 * 
	 * @return - <code>true</code> if the <code>Host</code> has one or more rejected
	 *         bookings, <code>false</code>, otherwise.
	 */
	boolean hasRejectedBookings();
}
