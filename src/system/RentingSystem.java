package system;

import java.time.LocalDate;
import java.util.Iterator;

import bookings.Booking;
import enums.Status;
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
import properties.Property;
import users.Guest;
import users.User;

/**
 * <code>Interface</code> that represents the system interface.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public interface RentingSystem {

	/**
	 * Adds a new user to the <code>User list</code>. The user can be either a guest
	 * or a host.
	 * 
	 * @pre The user does not exist in the system.
	 * @param type        - User type (Guest or Host).
	 * @param id          - User identifier.
	 * @param name        - User s name.
	 * @param nationality - User s nationality.
	 * @param email       - User s email.
	 * @throws UnknownUserTypeException - Exception thrown when the user type is not
	 *                                  host nor guest.
	 * @throws UserExistsException      - Exception thrown when a user with the same
	 *                                  identifier already exists.
	 */
	void addUser(String type, String id, String name, String nationality, String email)
			throws UnknownUserTypeException, UserExistsException;

	/**
	 * Lists all users in the <code>Renting System</code> application.
	 * 
	 * @pre The application has at least one registered user.
	 * @return - User <code>Iterator</code> with all the users.
	 * @throws NoUsersException - Exception thrown when there are no users
	 *                          registered.
	 */
	Iterator<User> listUsers() throws NoUsersException;

	/**
	 * Adds an amenity to the <code>List of Amenities</code> of the private room of
	 * the <code>Host</code>.
	 * 
	 * @pre The host exists in the system.
	 * @param hostId     - Host identifier.
	 * @param propertyId - Property identifier.
	 * @param amenity    - Property amenity.
	 * @throws NoUserException - Exception thrown when a user does not exist.
	 */
	void addAmenity(String hostId, String propertyId, String amenity) throws NoUserException, NotHostException;

	/**
	 * Adds a private room type of property to the <code>List of Properties</code>
	 * of the given <code>Host</code>.
	 * 
	 * @pre The host exists in the system.
	 * @pre The property does not exist in the system.
	 * @param propertyId - Property identifier.
	 * @param hostId     - Host identifier.
	 * @param location   - Property location.
	 * @param capacity   - Property rental capacity.
	 * @param price      - Property price per night.
	 * @param rooms      - Property total rooms.
	 * @throws NoUserException      - Exception thrown when user does not exist.
	 * @throws HasPropertyException - Exception thrown when a property already
	 *                              exists.
	 */
	void addPrivateRoom(String propertyId, String hostId, String location, int capacity, int price, int rooms)
			throws NoUserException, HasPropertyException, NotHostException;

	/**
	 * Adds an entire place type of property to the <code>List of Properties</code>
	 * of the given <code>Host</code>.
	 * 
	 * @pre The host exists in the system.
	 * @pre The property does not exist in the system.
	 * @param hostId   - Host identifier.
	 * @param location - Property location.
	 * @param capacity - Property rental capacity.
	 * @param price    - Property price per night.
	 * @param rooms    - Property total rooms.
	 * @throws NoUserException      - Exception thrown when user does not exist.
	 * @throws HasPropertyException - Exception thrown when a property already
	 *                              exists.
	 */
	void addEntirePlace(String propertyId, String hostId, String location, int capacity, int price, int rooms,
			String propertyType) throws NoUserException, HasPropertyException, NotHostException;

	/**
	 * Lists all the <code>properties</code> of a given <code>Host</code>.
	 * 
	 * @pre The host exists in the system.
	 * @pre The property belongs to the host.
	 * @param userId - User identifier.
	 * @return - Property <code>Iterator</code> with all the properties os a host.
	 * @throws NoUserException              - Exception thrown when user does not
	 *                                      exist.
	 * @throws NotHostException             - Exception thrown when the user is not
	 *                                      a host.
	 * @throws HostHasNoPropertiesException - Exception thrown when a host has no
	 *                                      properties registered.
	 */
	Iterator<Property> listProperties(String userId)
			throws NoUserException, NotHostException, HostHasNoPropertiesException;

	/**
	 * Adds a booking to a <code>Guest</code>.
	 * 
	 * @pre The guest exists in the system.
	 * @pre The property exists in the system.
	 * @pre The property has enough room for the booking.
	 * @pre The date is valid.
	 * @param guestId    - Guest identifier.
	 * @param propertyId - Property identifier.
	 * @param arrival    - Date of the arrival.
	 * @param departure  - Date of the departure.
	 * @param guestsNum  - Number of guests.
	 * @param status     - Booking status.
	 * @throws NoUserException      - Exception thrown when user does not exist.
	 * @throws NotGuestException    - Exception thrown when the user is not a guest.
	 * @throws NoPropertyException  - Exception thrown if there is no property.
	 * @throws InvalidDateException - Exception thrown if the date is invalid.
	 * @throws NoCapacityException  - Exception thrown when property capacity is
	 *                              surpassed.
	 */
	void addBooking(String guestId, String propertyId, LocalDate arrival, LocalDate departure, int guestsNum,
			Status status) throws NoUserException, NotGuestException, NoPropertyException, InvalidDateException,
			NoCapacityException, InvalidDateException;

	/**
	 * Checks if a <code>Booking</code> is automatically confirmed.
	 * 
	 * @pre The guest exists in the system.
	 * @pre The property exists in the system.
	 * @param propertyId - Property identifier.
	 * @param arrival    - Date of the arrival.
	 * @param departure  - Date of the departure.
	 * @param guestId    - Guest identifier.
	 * @return - <code>true</code>, if the booking is in conditions to be confirmed;
	 *         <code>false</code>, otherwise.
	 * @throws NoUserException     - Exception thrown when user does not exist.
	 * @throws NotGuestException   - Exception thrown when the user is not a guest.
	 * @throws NoPropertyException - Exception thrown if there is no property.
	 */
	boolean IsAutoConfirmedBooking(String propertyId, LocalDate arrival, LocalDate departure, String guestId)
			throws NoPropertyException, NoUserException, NotGuestException;

	/**
	 * Returns <code>Booking</code> id giving the correspondent property;
	 * 
	 * @pre The property exists in the system.
	 * @param propertyId - Property identifier.
	 * @return - <code> Booking</code> id;
	 */
	String getBookingId(String propertyId);

	/**
	 * Host confirms a guest <code>Booking</code>.
	 * 
	 * @pre The host exists in the system.
	 * @pre The booking exists in the system.
	 * @pre The host is the booking owner.
	 * @pre The booking can be confirmed.
	 * @param bookingId - Booking identifier.
	 * @param hostId    - Host identifier.
	 * @throws NotHostException        - Exception thrown when the user is not a
	 *                                 host.
	 * @throws NoUserException         - Exception thrown when user does not exist.
	 * @throws NoBookingException      - Exception thrown when there is no booking.
	 * @throws CannotConfirmException  - Exception thrown when the
	 *                                 <code>Booking</code> state is not requested.
	 * @throws NotBookingHostException - Exception thrown when the host doesnt have
	 *                                 the given booking.
	 */
	void confirmBooking(String bookingId, String hostId) throws NoUserException, NotHostException, NoBookingException,
			CannotConfirmException, NotBookingHostException;

	/**
	 * Lists all rejected bookings of a certain property.
	 * 
	 * @pre The host exists in the system.
	 * @param bookingId - Booking identifier.
	 * @param hostId    - Host identifier.
	 * @return - Booking <code>Iterator</code> with rejected bookings.
	 * @throws NoUserException  - Exception thrown when user does not exist.
	 * @throws NotHostException - Exception thrown when the user is not a host.
	 */
	Iterator<Booking> listRejectedBookings(String bookingId, String hostId) throws NoUserException, NotHostException;

	/**
	 * Host rejects a guest <code>Booking</code>.
	 * 
	 * @pre The host exists in the system.
	 * @pre The host is the booking owner.
	 * @pre The booking exists in the system.
	 * @pre The booking can be rejected.
	 * @param bookingId - Booking identifier.
	 * @param hostId    - Host identifier.
	 * @throws NotHostException        - Exception thrown when the user is not a
	 *                                 host.
	 * @throws NoUserException         - Exception thrown when user does not exist.
	 * @throws NoBookingException      - Exception thrown when there are no
	 *                                 bookings.
	 * @throws CannotRejectException   - Exception thrown when the
	 *                                 <code>Booking</code> state is not requested.
	 * @throws NotBookingHostException - Exception thrown when the host doesnt have
	 *                                 the given booking.
	 */
	void rejectBooking(String bookingId, String hostId) throws NoUserException, NotHostException, NoBookingException,
			CannotRejectException, NotBookingHostException;

	/**
	 * Lists all rejected bookings of a certain host.
	 * 
	 * @pre The host exists in the system.
	 * @pre The host has booking offers.
	 * @pre The system has rejected bookings.
	 * @param hostId - Host Identifier.
	 * @return - Booking <code>Iterator</code> with rejected bookings.
	 * @throws NoUserException              - Exception thrown when user does not
	 *                                      exist.
	 * @throws NotHostException             - Exception thrown when the user is not
	 *                                      a host.
	 * @throws HostHasNoBookingsException   - Exception thrown when there are no
	 *                                      bookings of a host.
	 * @throws NoBookingRejectionsException - Exception thrown when the host has no
	 *                                      booking rejections.
	 */
	Iterator<Booking> listRejectedBookingsOfHost(String hostId)
			throws NoUserException, NotHostException, HostHasNoBookingsException, NoBookingRejectionsException;

	/**
	 * Guest pays for a booking.
	 * 
	 * @pre The guest exists in the system.
	 * @pre The system has registered bookings.
	 * @pre The guest has made the booking.
	 * @pre The booking can be payed.
	 * @param bookingId - Booking Identifier.
	 * @param guestId   - Guest identifier.
	 * 
	 * @return - Booking <code>Iterator</code> with changed bookings.
	 * 
	 * @throws NoUserException           - Exception thrown when user does not
	 *                                   exist.
	 * @throws NotGuestException         - Exception thrown when the user is not a
	 *                                   guest.
	 * @throws NoBookingException        - Exception thrown when there are no
	 *                                   bookings.
	 * @throws NotBookingGuestException  - Exception thrown when the guest doesnt
	 *                                   have the given booking.
	 * @throws CannotPayBookingException - Exception thrown when the guest cannot
	 *                                   pay the booking because of its own current
	 *                                   state.
	 */
	Iterator<Booking> listAfterPayingBookings(String bookingId, String guestId) throws NoUserException,
			NotGuestException, NoBookingException, NotBookingGuestException, CannotPayBookingException;

	/**
	 * Returns the full price of a stay.
	 * 
	 * @pre The booking exists in the system.
	 * @param bookingId - Booking identifier.
	 * @return - <code>double</code> with the booking price.
	 */
	double getBookingPrice(String bookingId);

	/**
	 * Adds a guest <code>Review</code> to a given booking.
	 * 
	 * @pre The guest exists in the system.
	 * @pre The system has registered bookings.
	 * @pre The guest has made the booking.
	 * @pre The booking is not already reviewed.
	 * @pre The booking can be reviewed.
	 * @param bookingId      - Booking Identifier.
	 * @param guestId        - Guest identifier.
	 * @param comment        - review comment.
	 * @param classification - review classification.
	 * @throws NoUserException                 - Exception thrown when user does not
	 *                                         exist.
	 * @throws NotGuestException               - Exception thrown when the user is
	 *                                         not a guest.
	 * @throws NoBookingException              - Exception thrown when there are no
	 *                                         bookings.
	 * @throws NotBookingGuestException        - Exception thrown when the guest
	 *                                         doesnt have the given booking.
	 * @throws BookingAlreadyReviewedException - Exception thrown when the given
	 *                                         booking has already been reviewed.
	 * @throws CannotReviewBookingException    - Exception thrown when the booking
	 *                                         is not in state paid.
	 */
	void addReview(String bookingId, String guestId, String comment, String classification)
			throws NoUserException, NotGuestException, NoBookingException, NotBookingGuestException,
			BookingAlreadyReviewedException, CannotReviewBookingException;

	/**
	 * Returns the booking current status.
	 * 
	 * @pre The booking exists in the system.
	 * @param bookingId - Booking identifier.
	 * @return - current <code>String</code> status.
	 */
	String getBookingStatus(String bookingId);

	/**
	 * Returns the total money that the <code>Guest</code> has paid for his
	 * Bookings.
	 * 
	 * @pre The guest exists in the system.
	 * @pre The guest has made the booking.
	 * @param guestId - Guest identifier.
	 * @return - the total money that the <code>Guest</code> has paid for his
	 *         Bookings.
	 * @throws NoUserException             - Exception thrown when user does not
	 *                                     exist.
	 * @throws NotGuestException           - Exception thrown when the user is not a
	 *                                     guest.
	 * @throws GuestHasNoBookingsException - Exception thrown when there are no
	 *                                     bookings of a guest.
	 */
	double getTotalPaidMoney(String guestId) throws NoUserException, NotGuestException, GuestHasNoBookingsException;

	/**
	 * 
	 * Return an iterator object with all the <code>Guest</code> bookings that has
	 * been paid.
	 * 
	 * @pre The guest exists in the system.
	 * @pre The guest has made the booking.
	 * @param guestId - Guest identifier.
	 * @return - total money that the <code>Guest</code> has paid for his Bookings.
	 * @throws NoUserException             - Exception thrown when user does not
	 *                                     exist.
	 * @throws NotGuestException           - Exception thrown when the user is not a
	 *                                     guest.
	 * @throws GuestHasNoBookingsException - Exception thrown when there are no
	 *                                     bookings of a guest.
	 */
	Iterator<Booking> listGuestBookingsInfo(String guestId)
			throws NoUserException, NotGuestException, GuestHasNoBookingsException;

	/**
	 * Lists all the stays of the guests of a given <code>Property</code>.
	 * 
	 * @pre The property exists in the application.
	 * @pre The property has registered stays.
	 * @param propertyId - Property identifier.
	 * @return - Booking <code>Iterator</code> with all the stays.
	 * @throws NoPropertyException      - Exception thrown when there is no property
	 *                                  in the system.
	 * @throws NoPropertyStaysException - Exception thrown when the property does
	 *                                  not have any registered stays.
	 */
	Iterator<Booking> listPropertyStays(String propertyId) throws NoPropertyException, NoPropertyStaysException;

	/**
	 * Lists all properties for a given location and number of guests.
	 * 
	 * @pre The property satisfy the given criterions.
	 * @param location  - <code>Booking</code> location.
	 * @param guestsNum - <code>Booking</code> number of guests.
	 * @return - Property <code>Iterator</code> with all the properties of a given
	 *         location and number of guests.
	 * @throws NoSatisfyingPropertyException - Exception thrown when there are no
	 *                                       satisfying properties within the given
	 *                                       parametres.
	 */
	Iterator<Property> listSearchedProperties(String location, int guestsNum) throws NoSatisfyingPropertyException;

	/**
	 * Lists all properties of a given location sorted by rating.
	 * 
	 * @pre The property satisfy the given criterions.
	 * @param location - <code>Booking</code> location.
	 * @return - Property <code>Iterator</code> with all the properties of a given
	 *         location sorted by their ratings.
	 * @throws NoSatisfyingPropertyException - Exception thrown when there are no
	 *                                       satisfying properties within the given
	 *                                       parametres.
	 */
	Iterator<Property> listBestProperties(String location) throws NoSatisfyingPropertyException;

	/**
	 * Shows the <code>Guest</code> that has visited more distinct locations and its
	 * respective number.
	 * 
	 * @pre The system does not have any globe trotter candidates.
	 * @return - <code>Guest</code> with respective number of stays of a diferent
	 *         location.
	 * @throws NoGlobeTrotterException - Exception thrown when there are no guests
	 *                                 or no guest has any stays.
	 */
	Guest getGlobalTrotter() throws NoGlobeTrotterException;

}