package system;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import bookings.Booking;
import bookings.BookingClass;
import comparator.BestPropertiesComparator;
import comparator.PropertyComparator;
import comparator.UserComparator;
import enums.Classification;
import enums.Status;
import enums.UserType;
import exceptions.BookingExceptions.NoBookingException;
import exceptions.BookingExceptions.NoBookingRejectionsException;
import exceptions.BookingExceptions.NotBookingGuestException;
import exceptions.BookingExceptions.NotBookingHostException;
import exceptions.BookingExceptions.BookingAlreadyReviewedException;
import exceptions.BookingExceptions.CannotConfirmException;
import exceptions.BookingExceptions.CannotPayBookingException;
import exceptions.BookingExceptions.CannotRejectException;
import exceptions.BookingExceptions.CannotReviewBookingException;
import exceptions.BookingExceptions.GuestHasNoBookingsException;
import exceptions.BookingExceptions.HostHasNoBookingsException;
import exceptions.DateExceptions.InvalidDateException;
import exceptions.PropertyExceptions.HasPropertyException;
import exceptions.PropertyExceptions.NoCapacityException;
import exceptions.PropertyExceptions.NoPropertyException;
import exceptions.PropertyExceptions.NoPropertyStaysException;
import exceptions.PropertyExceptions.NoSatisfyingPropertyException;
import exceptions.UserExceptions.HostHasNoPropertiesException;
import exceptions.UserExceptions.NoGlobeTrotterException;
import exceptions.UserExceptions.NoUserException;
import exceptions.UserExceptions.NoUsersException;
import exceptions.UserExceptions.NotGuestException;
import exceptions.UserExceptions.NotHostException;
import exceptions.UserExceptions.UnknownUserTypeException;
import exceptions.UserExceptions.UserExistsException;
import properties.EntirePlace;
import properties.EntirePlaceClass;
import properties.PrivateRoom;
import properties.PrivateRoomClass;
import properties.Property;
import reviews.Review;
import reviews.ReviewClass;
import users.Guest;
import users.GuestClass;
import users.Host;
import users.HostClass;
import users.User;

/**
 * <code>Class</code> that represents the system class.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */

public class RentingSystemClass implements RentingSystem {

// Class constants
	/**
	 * <code>int</code> with the minimum days necessary to confirm automatically a
	 * booking.
	 */
	private static final int MIN_AUTO_CONFIRM = 7;

	/**
	 * <code>String</code> with the booking id format.
	 */
	private static final String BOOKING_ID_FORMAT = "%s-%d";

// Class variables
	/**
	 * <code>Users sorted map</code> with the all the users in the application.
	 */
	private SortedMap<String, User> users;

	/**
	 * <code>Properties map</code> with the all the properties in the application.
	 */
	private Map<String, Property> properties;

	/**
	 * <code>Booking Map</code> with the all the bookings associated to a property.
	 */
	private Map<String, List<Booking>> bookingsOfProperty;

	/**
	 * <code>Property Map</code> with the all the properties associated to a
	 * location.
	 */
	private Map<String, List<Property>> propertiesByLocation;

	/**
	 * <code>Booking Map</code> with the all the bookings in the application.
	 */
	private Map<String, Booking> bookings;

	/**
	 * System class constructor.
	 */
	public RentingSystemClass() {
		users = new TreeMap<>(new UserComparator());
		properties = new HashMap<>();
		bookingsOfProperty = new HashMap<>();
		propertiesByLocation = new HashMap<>();
		bookings = new HashMap<>();
	}

	/**
	 * Returns the <code>User</code> by the given id.
	 * 
	 * @param userId - User identifier.
	 * @return - <code>User</code> by the id.
	 * @throws NoUserException - Exception thrown when an user does not exist.
	 */
	private User getUserById(String userId) throws NoUserException {
		if (users.containsKey(userId))
			return users.get(userId);
		else
			throw new NoUserException(userId);
	}

	/**
	 * Returns the <code>Host</code> by the given id.
	 * 
	 * @pre The user is in the system.
	 * @param hostId - Host identifier.
	 * @return - <code>Host</code> by the id.
	 * @throws NoUserException  - Exception thrown when an user does not exist.
	 * @throws NotHostException - Exception thrown when an user is not a Host.
	 * 
	 */
	private Host getHostById(String hostId) throws NoUserException, NotHostException {
		if (getUserById(hostId) instanceof Host)
			return (Host) getUserById(hostId);
		else
			throw new NotHostException(hostId);
	}

	/**
	 * Returns the <code>Guest</code> by the given id.
	 * 
	 * @pre The user is in the system. 
	 * @param guestId - Guest identifier.
	 * @return - <code>Guest</code> by the id.
	 * @throws NoUserException   - Exception thrown when an user does not exist.
	 * @throws NotGuestException - Exception thrown when an user is not a guest.
	 */
	private Guest getGuestById(String guestId) throws NoUserException, NotGuestException {
		if (getUserById(guestId) instanceof Guest)
			return (Guest) getUserById(guestId);
		else
			throw new NotGuestException(guestId);
	}

	/**
	 * Returns the <code>Property</code> by the given id.
	 * 
	 * @param propertyId - Property identifier.
	 * @return - <code>Property</code> by the id.
	 * @throws NoPropertyException - Exception thrown if there is no property.
	 */
	private Property getProperty(String propertyId) throws NoPropertyException {
		if (properties.containsKey(propertyId))
			return properties.get(propertyId);
		else
			throw new NoPropertyException(propertyId);
	}

	@Override
	public void addUser(String type, String id, String name, String nationality, String email)
			throws UnknownUserTypeException, UserExistsException {
		UserType userType = UserType.UNKNOWN_TYPE;
		for (UserType u : UserType.values())
			if (u.getType().equals(type))
				userType = u;
		switch (userType) {
		case HOST:
			addHost(id, name, nationality, email);
			break;
		case GUEST:
			addGuest(id, name, nationality, email);
			break;
		default:
			throw new UnknownUserTypeException();
		}
	}

	/**
	 * Adds a new host to the <code>User list</code>.
	 * 
	 * @pre The host does not exist.
	 * @param hostId      - Host identifier.
	 * @param name        - Host name.
	 * @param nationality - Host nationality.
	 * @param email       - Host email.
	 * @throws UserExistsException - Exception thrown when a user with the same
	 *                             identifier already exists.
	 */
	private void addHost(String hostId, String name, String nationality, String email) throws UserExistsException {
		if (users.containsKey(hostId))
			throw new UserExistsException(hostId);
		else
			users.put(hostId, new HostClass(hostId, name, nationality, email));
	}

	/**
	 * Adds a new guest to the <code>User list</code>.
	 * 
	 * @pre The guest does not exist.
	 * @param guestId     - Guest identifier.
	 * @param name        - Guest name.
	 * @param nationality - Guest nationality.
	 * @param email       - Guest email.
	 * @throws UserExistsException - Exception thrown when a user with the same
	 *                             identifier already exists.
	 */
	private void addGuest(String guestId, String name, String nationality, String email) throws UserExistsException {
		if (users.containsKey(guestId))
			throw new UserExistsException(guestId);
		else
			users.put(guestId, new GuestClass(guestId, name, nationality, email));
	}

	@Override
	public Iterator<User> listUsers() throws NoUsersException {
		if (users.isEmpty())
			throw new NoUsersException();
		else
			return users.values().iterator();
	}

	@Override
	public void addPrivateRoom(String propertyId, String hostId, String location, int capacity, int price, int rooms)
			throws NoUserException, HasPropertyException, NotHostException {
		if (properties.containsKey(propertyId)) {
			throw new HasPropertyException(propertyId);
		} else {
			PrivateRoom privateRoom = new PrivateRoomClass(propertyId, location, capacity, price, getHostById(hostId),
					rooms);
			getHostById(hostId).addPrivateRoom(hostId, location, capacity, price, rooms, privateRoom);
			properties.put(propertyId, privateRoom);
			List<Property> propertyList = propertiesByLocation.get(location);
			if (propertyList == null) {
				propertyList = new LinkedList<>();
				propertiesByLocation.put(location, propertyList);
			}
			propertyList.add(privateRoom);
		}
	}

	@Override
	public void addEntirePlace(String propertyId, String hostId, String location, int capacity, int price, int rooms,
			String propertyType) throws NoUserException, HasPropertyException, NotHostException {
		if (properties.containsKey(propertyId))
			throw new HasPropertyException(propertyId);
		else {
			EntirePlace entirePlace = new EntirePlaceClass(propertyId, location, capacity, price, getHostById(hostId),
					rooms);
			getHostById(hostId).addEntirePlace(propertyId, location, capacity, price, rooms, entirePlace);
			properties.put(propertyId, entirePlace);
			List<Property> propertyList = propertiesByLocation.get(location);
			if (propertyList == null) {
				propertyList = new LinkedList<>();
				propertiesByLocation.put(location, propertyList);
			}
			propertyList.add(entirePlace);
		}
	}

	@Override
	public void addAmenity(String hostId, String propertyId, String amenity) throws NoUserException, NotHostException {
		getHostById(hostId).getPrivateRoom(propertyId).addAmenity(amenity);
	}

	@Override
	public Iterator<Property> listProperties(String userId)
			throws NoUserException, NotHostException, HostHasNoPropertiesException {
		if (!users.containsKey(userId))
			throw new NoUserException(userId);
		else if (!(getUserById(userId) instanceof Host))
			throw new NotHostException(userId);
		else if (!(getHostById(userId).HasProperties()))
			throw new HostHasNoPropertiesException(userId);
		else
			return getHostById(userId).listProperties();
	}

	@Override
	public void addBooking(String guestId, String propertyId, LocalDate arrival, LocalDate departure, int guestsNum,
			Status status) throws NoUserException, NotGuestException, NoPropertyException, InvalidDateException,
			NoCapacityException, InvalidDateException {

		Guest guest = getGuestById(guestId);
		Property property = getProperty(propertyId);

		if (property.hasCapacity(guestsNum))
			throw new NoCapacityException(propertyId, property.getCapacity());
		else if (!(hasValidBookingDate(arrival, departure, propertyId, guest)))
			throw new InvalidDateException();
		else {
			List<Booking> bookingList = bookingsOfProperty.get(propertyId);
			if (bookingList == null) {
				bookingList = new LinkedList<>();
				bookingsOfProperty.put(propertyId, bookingList);
			}

			int bookingNum = bookingsOfProperty.get(propertyId).size() + 1;
			String bookingId = String.format(RentingSystemClass.BOOKING_ID_FORMAT, propertyId, bookingNum);
			Booking booking = new BookingClass(property, bookingId, arrival, departure, status, guestsNum, guest);

			if (property.conflictsWithOtherBookings(booking)) {
				if (booking.getState().equals(Status.CONFIRMED))
					booking.requestBooking();
			}

			bookings.put(bookingId, booking);
			bookingList.add(booking);
			property.addBooking(booking);
			guest.addBooking(booking);
		}
	}

	@Override
	public String getBookingId(String propertyId) {
		Booking lastBookingOfProperty = bookingsOfProperty.get(propertyId)
				.get(bookingsOfProperty.get(propertyId).size() - 1);
		return lastBookingOfProperty.getBookingId();
	}

	/**
	 * Checks if date of the <code>Booking</code> is valid for that property.
	 * 
	 * @pre arrival.isBefore(departure);
	 * @param arrival    - Date of the arrival.
	 * @param departure  - Date of the departure.
	 * @param propertyId - Property identifier.
	 * @return - <code>true</code>, if the date of the booking is valid;
	 *         <code>false</code>, otherwise.
	 */
	private boolean hasValidPropertyBookingDate(LocalDate arrival, LocalDate departure, String propertyId) {
		if (bookingsOfProperty.get(propertyId) == null)
			return true;
		for (Booking b : bookingsOfProperty.get(propertyId)) {
			if (b.getState().equals(Status.PAID))
				if (b.getDepartureDate().isAfter(arrival))
					return false;
			if (b.getState().equals(Status.CONFIRMED))
				if (b.hasOverlappedDate(new BookingClass(null, null, arrival, departure, null, 0, null)))
					return false;
		}
		return true;
	}

	/**
	 * Checks if date of the <code>Booking</code> is valid for that guest and
	 * property.
	 * 
	 * @pre arrival.isBefore(departure);
	 * @param arrival    - Date of the arrival.
	 * @param departure  - Date of the departure.
	 * @param propertyId - Property identifier.
	 * @param guest      - Guest.
	 * @return - <code>true</code>, if the date of the booking is valid;
	 *         <code>false</code>, otherwise.
	 */
	private boolean hasValidBookingDate(LocalDate arrival, LocalDate departure, String propertyId, Guest guest) {
		return hasValidPropertyBookingDate(arrival, departure, propertyId)
				&& guest.hasValidBookingDate(arrival, departure);
	}

	@Override
	public boolean IsAutoConfirmedBooking(String propertyId, LocalDate arrival, LocalDate departure, String guestId)
			throws NoPropertyException, NoUserException, NotGuestException {
		boolean isEntirePlace = getProperty(propertyId) instanceof EntirePlace;
		boolean isValidAutoConfirmMinTime = Duration.between(arrival.atStartOfDay(), departure.atStartOfDay())
				.toDays() > RentingSystemClass.MIN_AUTO_CONFIRM;
		return (isEntirePlace && isValidAutoConfirmMinTime
				&& hasValidBookingDate(arrival, departure, propertyId, getGuestById(guestId)));
	}

	@Override
	public void confirmBooking(String bookingId, String hostId) throws NoUserException, NotHostException,
			NoBookingException, CannotConfirmException, NotBookingHostException {
		Host host = getHostById(hostId);
		Property property = host.getPropertyByBookingId(bookingId);
		if (!hasBooking(bookingId))
			throw new NoBookingException(bookingId);
		else if (!host.bookingIsRequested(bookingId))
			throw new CannotConfirmException(bookingId, host.getBookingState(bookingId));
		else if (!host.hasBooking(bookingId))
			throw new NotBookingHostException(hostId, bookingId);
		else {
			host.confirmBooking(bookingId);
			property.rejectOverlappingBookings(bookingId);
		}
	}

	/**
	 * Verifies if a booking exists in the application.
	 * 
	 * @param bookingId - Booking identifier.
	 * @return - <code>true</code> if the given booking exists; <code>false</code>
	 *         if not.
	 */
	private boolean hasBooking(String bookingId) {
		return bookings.containsKey(bookingId);
	}

	@Override
	public Iterator<Booking> listRejectedBookings(String bookingId, String hostId)
			throws NoUserException, NotHostException {
		Host host = getHostById(hostId);
		return host.getPropertyByBookingId(bookingId).listRejectedBookings();
	}

	@Override
	public void rejectBooking(String bookingId, String hostId) throws NoUserException, NotHostException,
			NoBookingException, CannotRejectException, NotBookingHostException {
		Host host = getHostById(hostId);
		Property property = host.getPropertyByBookingId(bookingId);
		if (!hasBooking(bookingId))
			throw new NoBookingException(bookingId);
		else if (!host.bookingIsRequested(bookingId))
			throw new CannotRejectException(bookingId, host.getBookingState(bookingId));
		else if (!host.hasBooking(bookingId))
			throw new NotBookingHostException(hostId, bookingId);
		else {
			host.rejectBooking(bookingId);
			property.rejectBooking(bookingId);
		}
	}

	@Override
	public Iterator<Booking> listRejectedBookingsOfHost(String hostId)
			throws NoUserException, NotHostException, HostHasNoBookingsException, NoBookingRejectionsException {
		Host host = getHostById(hostId);
		if (!host.hasBookings())
			throw new HostHasNoBookingsException(hostId);
		else if (!host.hasRejectedBookings())
			throw new NoBookingRejectionsException(hostId);
		else
			return host.listAllRejected();
	}

	@Override
	public Iterator<Booking> listAfterPayingBookings(String bookingId, String guestId) throws NoUserException,
			NotGuestException, NoBookingException, NotBookingGuestException, CannotPayBookingException {
		Guest guest = getGuestById(guestId);
		if (!hasBooking(bookingId))
			throw new NoBookingException(bookingId);
		else if (!guest.hasBooking(bookingId))
			throw new NotBookingGuestException(guestId, bookingId);
		else if (!guest.canPayBooking(bookingId))
			throw new CannotPayBookingException(bookingId, guest.getBookingState(bookingId));
		else {
			guest.payBooking(bookingId);
			return guest.listAllChanged(bookingId);
		}
	}

	@Override
	public double getBookingPrice(String bookingId) {
		return bookings.get(bookingId).getTotalPrice();
	}
	
	/**
	 * Returns the guest <code>Classification</code>.
	 * 
	 * @param classification - Review classification.
	 * @return - <code>Classification</code> with the chosen classification.
	 */
	private Classification getClassification(String classification) {
		for (Classification c : Classification.values())
			if (c.getClassification().equals(classification))
				return c;
		return Classification.NOT_REVIEWED;
	}

	@Override
	public void addReview(String bookingId, String guestId, String comment, String classification)
			throws NoUserException, NotGuestException, NoBookingException, NotBookingGuestException,
			BookingAlreadyReviewedException, CannotReviewBookingException {
		Guest guest = getGuestById(guestId);
		if (!hasBooking(bookingId))
			throw new NoBookingException(bookingId);
		else if (!guest.hasBooking(bookingId))
			throw new NotBookingGuestException(guestId, bookingId);
		else if (guest.hasAlreadyReviewedBooking(bookingId))
			throw new BookingAlreadyReviewedException(bookingId);
		else if (!guest.isInPaidState(bookingId))
			throw new CannotReviewBookingException(bookingId, getBookingStatus(bookingId));
		else {
			Booking booking = bookings.get(bookingId);
			Review review = new ReviewClass(booking, guest, comment, getClassification(classification));
			guest.reviewBooking(review, bookingId);
		}
	}

	@Override
	public String getBookingStatus(String bookingId) {
		return bookings.get(bookingId).getState().getStatus();
	}

	@Override
	public double getTotalPaidMoney(String guestId)
			throws NoUserException, NotGuestException, GuestHasNoBookingsException {
		Guest guest = getGuestById(guestId);
		if (!guest.hasBookings())
			throw new GuestHasNoBookingsException(guestId);
		return guest.getTotalPaidMoney();
	}

	@Override
	public Iterator<Booking> listGuestBookingsInfo(String guestId)
			throws NoUserException, NotGuestException, GuestHasNoBookingsException {
		Guest guest = getGuestById(guestId);
		if (!guest.hasBookings())
			throw new GuestHasNoBookingsException(guestId);
		return guest.listBookingsInfo();
	}

	@Override
	public Iterator<Booking> listPropertyStays(String propertyId) throws NoPropertyException, NoPropertyStaysException {
		Property property = getProperty(propertyId);
		if (!property.hasStays())
			throw new NoPropertyStaysException(propertyId);
		return property.listAllStays();
	}

	@Override
	public Iterator<Property> listSearchedProperties(String location, int guestsNum)
			throws NoSatisfyingPropertyException {
		if (!propertiesByLocation.containsKey(location))
			throw new NoSatisfyingPropertyException(location);

		List<Property> searchedList = new LinkedList<>();
		for (Property p : propertiesByLocation.get(location))
			if (p.getCapacity() >= guestsNum)
				searchedList.add(p);

		if (searchedList.isEmpty())
			throw new NoSatisfyingPropertyException(location);

		searchedList.sort(new PropertyComparator());
		return searchedList.iterator();
	}

	@Override
	public Iterator<Property> listBestProperties(String location) throws NoSatisfyingPropertyException {
		if (propertiesByLocation.size() == 0)
			throw new NoSatisfyingPropertyException(location);
		else {
			propertiesByLocation.get(location).sort(new BestPropertiesComparator());
			return propertiesByLocation.get(location).iterator();
		}
	}

	@Override
	public Guest getGlobalTrotter() throws NoGlobeTrotterException {
		Guest maxLocationsGuest = getFirstGuest();
		for (User user : users.values()) {
			if (user instanceof Guest && ((Guest) user).hasStays() && ((Guest) user).isGlobeTrotter(maxLocationsGuest))
				maxLocationsGuest = (Guest) user;
		}
		return maxLocationsGuest;
	}

	private Guest getFirstGuest() throws NoGlobeTrotterException {
		for (User user : users.values())
			if (user instanceof Guest && ((Guest) user).hasStays())
				return (Guest) user;
		throw new NoGlobeTrotterException();
	}

}
