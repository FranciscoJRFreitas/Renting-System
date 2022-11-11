package users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import bookings.Booking;
import enums.Status;
import exceptions.PropertyExceptions.HasPropertyException;
import properties.EntirePlace;
import properties.EntirePlaceClass;
import properties.PrivateRoom;
import properties.PrivateRoomClass;
import properties.Property;

/**
 * <code>Class</code> that represents a specific form of the user object -
 * HostClass. It will represent one of the extended classes of the two types of
 * user object.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class HostClass extends UserClass implements Host {

	/**
	 * <code>String</code> with the host list format in users command.
	 */
	private static final String SHOW_HOST = "%s: %s from %s with email %s [host user has %d properties]";

	/**
	 * <code>Property list</code> with the all the user properties.
	 */
	private List<Property> properties;

	/**
	 * Class Constructor.
	 * 
	 * @param id          - User identifier.
	 * @param name        - User name.
	 * @param nationality - User nationality.
	 * @param email       - User email.
	 */
	public HostClass(String id, String name, String nationality, String email) {
		super(id, name, nationality, email);
		properties = new ArrayList<>();
	}

	@Override
	public String toString() {
		return String.format(HostClass.SHOW_HOST, getId(), getName(), getNationality(), getEmail(), properties.size());
	}

	@Override
	public void addPrivateRoom(String propertyId, String location, int capacity, int price, int rooms,
			PrivateRoom privateroom) throws HasPropertyException {
		if (properties.contains(new PrivateRoomClass(propertyId, location, capacity, price, this, rooms)))
			throw new HasPropertyException(propertyId);
		else
			properties.add(privateroom);
	}

	@Override
	public void addEntirePlace(String propertyId, String location, int capacity, int price, int rooms,
			EntirePlace entireplace) throws HasPropertyException {
		if (properties.contains(new EntirePlaceClass(propertyId, location, capacity, price, this, rooms)))
			throw new HasPropertyException(propertyId);
		else
			properties.add(entireplace);
	}

	@Override
	public PrivateRoom getPrivateRoom(String propertyId) {
		return (PrivateRoom) properties.get(properties.indexOf(new PrivateRoomClass(propertyId, null, 0, 0, null, 0)));
	}

	@Override
	public Iterator<Property> listProperties() {
		return properties.iterator();
	}

	@Override
	public boolean HasProperties() {
		return properties.size() != 0;
	}

	@Override
	public boolean hasBooking(String bookingId) {
		for (int i = 0; i < properties.size(); i++) {
			if (properties.get(i).hasBooking(bookingId))
				return true;
		}
		return false;
	}

	@Override
	public Iterator<Booking> listAllRejected() {
		List<Booking> allRejected = new LinkedList<>();
		for (Property p : properties) {
			Iterator<Booking> it = p.listAllRejected();
			while (it.hasNext())
				allRejected.add(it.next());
		}
		return allRejected.iterator();
	}

	@Override
	public boolean hasRejectedBookings() {
		return listAllRejected().hasNext();
	}

	@Override
	public boolean hasBookings() {
		for (Property p : properties)
			if (p.hasBookings())
				return true;
		return false;
	}

	@Override
	public Booking getBookingById(String bookingId) {
		for (int i = 0; i < properties.size(); i++)
			if (properties.get(i).getBookingById(bookingId) != null)
				return properties.get(i).getBookingById(bookingId);
		return null;
	}

	@Override
	public Property getPropertyByBookingId(String bookingId) {
		for (int i = 0; i < properties.size(); i++)
			if (properties.get(i).getBookingById(bookingId) != null)
				return properties.get(i);
		return null;
	}

	@Override
	public void confirmBooking(String bookingId) {
		getBookingById(bookingId).confirmBooking();
	}

	@Override
	public void rejectBooking(String bookingId) {
		getBookingById(bookingId).rejectBooking();
	}

	@Override
	public String getBookingState(String bookingId) {
		return getBookingById(bookingId).getState().getStatus();
	}

	@Override
	public boolean bookingIsRequested(String bookingId) {
		if (getBookingById(bookingId) == null)
			return true;
		return getBookingState(bookingId).equals(Status.REQUESTED.getStatus());
	}

}
