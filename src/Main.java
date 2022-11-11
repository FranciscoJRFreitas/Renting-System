import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;

import bookings.Booking;
import enums.Command;
import enums.PropertyType;
import enums.Status;
import exceptions.BookingExceptions.NoBookingException;
import exceptions.BookingExceptions.NoBookingRejectionsException;
import exceptions.BookingExceptions.NotBookingGuestException;
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
import exceptions.PropertyExceptions.NoPropertyTypeException;
import exceptions.PropertyExceptions.NoSatisfyingPropertyException;
import exceptions.BookingExceptions.NotBookingHostException;
import exceptions.UserExceptions.HostHasNoPropertiesException;
import exceptions.UserExceptions.NoGlobeTrotterException;
import exceptions.UserExceptions.NoUserException;
import exceptions.UserExceptions.NoUsersException;
import exceptions.UserExceptions.NotGuestException;
import exceptions.UserExceptions.NotHostException;
import exceptions.UserExceptions.UnknownUserTypeException;
import exceptions.UserExceptions.UserExistsException;
import properties.Property;
import system.RentingSystem;
import system.RentingSystemClass;
import users.Guest;
import users.User;

/**
 * 
 * <code>Main</code> class for the <code>application</code>. Containing all the
 * communication between the user and the application.
 * 
 * POO 2021 - Second Project - RentAway.
 * 
 * @author Alberto Serra 60988
 * @author Francisco Freitas 60313
 */
public class Main {

	// Command Help
	private static final String HELP = "register - adds a new user\r\n" + "users - list all the registered users\r\n"
			+ "property - adds a new property\r\n" + "properties - lists all properties of a host\r\n"
			+ "book - guest adds a new booking\r\n" + "confirm - host confirms a booking\r\n"
			+ "reject - host rejects a booking\r\n" + "rejections - lists all rejected bookings by a host\r\n"
			+ "pay - guest pays for a booking\r\n" + "review - adds a review to a stay\r\n"
			+ "guest - list statistical information about a guest\r\n" + "stays - list all stays of a property\r\n"
			+ "search - lists all properties in a location over a given capacity\r\n"
			+ "best - lists the best properties in a location\r\n"
			+ "globetrotter - lists the guest who has visited more locations\r\n"
			+ "help - shows the available commands\r\n" + "exit - terminates the execution of the program\r\n";

	// General showcase messages
	// Users messages
	private static final String USER_ADDED = "User %s was registered as %s.\n";
	private static final String LIST_USERS = "All registered users:";
	private static final String GUEST_INFO = "Guest %s bookings: paid %.2f euros\n";
	private static final String GLOBE_TROTTER = "Globe trotter %s has visited %d locations.\n";

	// Properties messages
	private static final String PROPERTY_ADDED = "Property %s was added to host %s.\n";
	private static final String LIST_PROPERTIES = "Properties of host %s:\n";
	private static final String LIST_PROPERTY_STAYS = "Property %s stays:\n";
	private static final String LIST_SEARCHED_PROPERTIES = "Properties in %s:\n";
	private static final String SEARCH_LIST = "%s: %.1f; %.2f; %d; %s\n";
	private static final String LIST_BEST_PROPERTIES = "The best properties in %s:\n";
	private static final String SEARCH_BEST_LIST = "%s: %.1f; %d; %.2f; %s\n";

	// Bookings messages
	private static final String BOOKING_REGISTED = "Booking %s was %s.\n";
	private static final String BOOKING_CONFIRMED = "Booking %s was %s.\n";
	private static final String BOOKING_REJECTED = "Booking %s was %s.\n";
	private static final String BOOKING_REJECTIONS = "%s: %s; %s; %s; %d\n";
	private static final String PAY_BOOKING = "Booking %s was paid: %.2f\n";
	private static final String LIST_REJECTED = "Bookings rejected by host %s:\n";
	private static final String LIST_CHANGED_BOOKINGS = "Booking %s was %s.\n";
	private static final String LIST_PAID_BOOKINGS_INFO = "%s: %s; %s; %s; %s; %s; %d; %s; %.2f\n";
	private static final String LIST_STAYS_INFO = "%s: %s; %s; %s; %s; %d; %.2f\n";

	// Reviews messages
	private static final String REVIEW_ADDED = "Review for %s was registered.\n";

	// Date formats
	private static final String DATE_PATTERN = "dd-MM-yyyy";

	/**
	 * Main method that initiates the <code>program</code>.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Main.runSystem();
	}

	/**
	 * Runs the <code>program</code>, containing the <code>Scanner</code>, the
	 * <code>RentingSystem</code> object and all the commands in the runCommands
	 * method.
	 */
	private static void runSystem() {
		Scanner in = new Scanner(System.in);
		RentingSystem rs = new RentingSystemClass();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		runCommands(in, rs, formatter);
		in.close();
	}

	/**
	 * Returns the user input <code>Command</code> with the chosen command. The
	 * commands are <code>case insensitive</code> in this application, so in the
	 * next method is necessary an equalsIgnoreCase to ignore the case.
	 * 
	 * @param in - Scanner object.
	 * @return - <code>Command</code> with the chosen command.
	 */
	private static Command getCommand(Scanner in) {
		String input = in.next().toLowerCase().trim();
		for (Command c : Command.values())
			if (c.getCommandInfo().equalsIgnoreCase(input))
				return c;
		return Command.UNKNOWN_COMMAND;
	}

	/**
	 * Selects which <code>commands</code> to use and which methods are executed.
	 * The while keeps the cycle running with a <code>switch</code> (to select the
	 * option), until the user chose to exit.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void runCommands(Scanner in, RentingSystem rs, DateTimeFormatter formatter) {
		Command comm = getCommand(in);
		while (!comm.equals(Command.EXIT)) {
			switch (comm) {
			case REGISTER:
				registUser(in, rs);
				break;
			case USERS:
				listUsers(in, rs);
				break;
			case PROPERTY:
				addProperty(in, rs);
				break;
			case PROPERTIES:
				listProperties(in, rs);
				break;
			case BOOK:
				addBooking(in, rs, formatter);
				break;
			case CONFIRM:
				confirmBooking(in, rs);
				break;
			case REJECT:
				rejectBooking(in, rs);
				break;
			case REJECTIONS:
				listRejections(in, rs);
				break;
			case PAY:
				payBooking(in, rs);
				break;
			case REVIEW:
				addReview(in, rs);
				break;
			case GUEST:
				listGuestInfo(in, rs, formatter);
				break;
			case STAYS:
				listStays(in, rs, formatter);
				break;
			case SEARCH:
				listSearchedProperties(in, rs);
				break;
			case BEST:
				listBestProperties(in, rs);
				break;
			case GLOBETROTTER:
				listGlobalTrotter(rs);
				break;
			case HELP:
				helpMenu();
				break;
			default:
				System.out.println(Command.UNKNOWN_COMMAND.getCommandInfo());
			}
			comm = getCommand(in);
		}
		System.out.println(Command.BYE.getCommandInfo());
	}

	/**
	 * Adds a new user to the <code>Renting System</code>.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void registUser(Scanner in, RentingSystem rs) {
		try {
			String type = in.next();
			String id = in.nextLine().trim();
			String name = in.nextLine().trim();
			String nationality = in.nextLine().trim();
			String email = in.nextLine().trim();

			rs.addUser(type, id, name, nationality, email);
			System.out.printf(Main.USER_ADDED, id, type);
		} catch (UnknownUserTypeException | UserExistsException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Lists all users in the <code>Renting System</code>.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void listUsers(Scanner in, RentingSystem rs) {
		try {
			Iterator<User> it = rs.listUsers();
			System.out.println(Main.LIST_USERS);
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} catch (NoUsersException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Returns the user input property type with the chosen
	 * <code>Property Type</code>.
	 * 
	 * @param type - string type.
	 * @return - <code>Property Type</code> with the chosen property type.
	 */
	private static PropertyType getPropertyType(String type) {
		for (PropertyType t : PropertyType.values())
			if (t.getType().equals(type))
				return t;
		return PropertyType.UNKNOWN_TYPE;
	}

	/**
	 * Adds a new property to the given<code>Host</code>, containing a switch for
	 * better coding purpose. The Switch has two private methods for each
	 * <code>Property</code> type.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void addProperty(Scanner in, RentingSystem rs) {
		try {
			String type = in.nextLine().trim();
			String propertyId = in.next();
			String hostId = in.nextLine().trim();
			String location = in.nextLine().trim();
			int capacity = in.nextInt();
			int price = in.nextInt();
			int rooms = in.nextInt();
			PropertyType propertyType = getPropertyType(type);
			switch (propertyType) {
			case ENTIRE_PLACE:
				addEntirePlace(in, rs, propertyId, hostId, location, capacity, price, rooms);
				break;
			case PRIVATE_ROOM:
				addPrivateRoom(in, rs, propertyId, hostId, location, capacity, price, rooms);
				break;
			default:
				in.nextLine();
				throw new NoPropertyTypeException();
			}

		} catch (NoPropertyTypeException | NoUserException | HasPropertyException | NotHostException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Adds a new property of type Private Room to the given<code>Host</code>.
	 * 
	 * @param in         - Scanner object.
	 * @param rs         - RentingSystem object.
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
	private static void addPrivateRoom(Scanner in, RentingSystem rs, String propertyId, String hostId, String location,
			int capacity, int price, int rooms) throws NoUserException, NotHostException {
		try {
			in.nextLine();
			rs.addPrivateRoom(propertyId, hostId, location, capacity, price, rooms);
			for (int i = 0; i < rooms; i++) {
				String amenity = in.nextLine();
				rs.addAmenity(hostId, propertyId, amenity);
			}
			System.out.printf(Main.PROPERTY_ADDED, propertyId, hostId);
		} catch (HasPropertyException e) {
			// Discard Amenities in console.
			for (int i = 0; i < rooms; i++)
				in.nextLine();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Adds a new property of type Entire Place to the given<code>Host</code>.
	 * 
	 * @param in         - Scanner object.
	 * @param rs         - RentingSystem object.
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
	private static void addEntirePlace(Scanner in, RentingSystem rs, String propertyId, String hostId, String location,
			int capacity, int price, int rooms) throws NoUserException, HasPropertyException, NotHostException {
		String propertyType = in.nextLine().trim();
		rs.addEntirePlace(propertyId, hostId, location, capacity, price, rooms, propertyType);
		System.out.printf(Main.PROPERTY_ADDED, propertyId, hostId);
	}

	/**
	 * Lists all properties of a given <code>User</code> in the
	 * <code>Renting System</code>.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void listProperties(Scanner in, RentingSystem rs) {
		try {
			String hostId = in.nextLine().trim();
			Iterator<Property> it = rs.listProperties(hostId);
			System.out.printf(Main.LIST_PROPERTIES, hostId);
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} catch (HostHasNoPropertiesException | NoUserException | NotHostException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Adds a new booking to the given <code>Guest</code> in the
	 * <code>Renting System</code>.
	 * 
	 * @param in        - Scanner object.
	 * @param rs        - RentingSystem object.
	 * @param formatter - Date formatter.
	 */
	private static void addBooking(Scanner in, RentingSystem rs, DateTimeFormatter formatter) {
		try {
			String guestId = in.next();
			String propertyId = in.next();
			LocalDate arrival = LocalDate.parse(in.next(), formatter);
			LocalDate departure = LocalDate.parse(in.next(), formatter);
			int guestsNum = in.nextInt();
			if (rs.IsAutoConfirmedBooking(propertyId, arrival, departure, guestId))
				rs.addBooking(guestId, propertyId, arrival, departure, guestsNum, Status.CONFIRMED);
			else
				rs.addBooking(guestId, propertyId, arrival, departure, guestsNum, Status.REQUESTED);
			String bookingId = rs.getBookingId(propertyId);
			System.out.printf(Main.BOOKING_REGISTED, bookingId, rs.getBookingStatus(bookingId));
		} catch (NoUserException | NotGuestException | NoPropertyException | NoCapacityException
				| InvalidDateException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The <code>Host</code> confirms a <code>Guest</code> booking that is in
	 * requested state.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void confirmBooking(Scanner in, RentingSystem rs) {
		try {
			String bookingId = in.next();
			String hostId = in.next();
			rs.confirmBooking(bookingId, hostId);
			System.out.printf(Main.BOOKING_CONFIRMED, bookingId, Status.CONFIRMED.getStatus());
			Iterator<Booking> it = rs.listRejectedBookings(bookingId, hostId);
			while (it.hasNext()) {
				Booking b = it.next();
				System.out.printf(Main.BOOKING_REJECTED, b.getBookingId(), Status.REJECTED.getStatus());
			}
		} catch (NoUserException | NotHostException | NoBookingException | NotBookingHostException
				| CannotConfirmException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The <code>Host</code> rejects a <code>Guest</code> booking that is in
	 * requested state.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void rejectBooking(Scanner in, RentingSystem rs) {
		try {
			String bookingId = in.next();
			String hostId = in.next();
			rs.rejectBooking(bookingId, hostId);
			System.out.printf(Main.BOOKING_REJECTED, bookingId, Status.REJECTED.getStatus());
		} catch (NoUserException | NotHostException | NoBookingException | NotBookingHostException
				| CannotRejectException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Lists all rejected <code>Booking</code> objects of a given <code>Host</code>.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void listRejections(Scanner in, RentingSystem rs) {
		try {
			String hostId = in.nextLine().trim();
			Iterator<Booking> it = rs.listRejectedBookingsOfHost(hostId);
			System.out.printf(Main.LIST_REJECTED, hostId);
			while (it.hasNext()) {
				Booking b = it.next();
				Property p = b.getProperty();
				Guest g = b.getGuest();
				System.out.printf(Main.BOOKING_REJECTIONS, b.getBookingId(), p.getId(), g.getId(), g.getNationality(),
						b.getGuestsNum());
			}
		} catch (NoUserException | NotHostException | HostHasNoBookingsException | NoBookingRejectionsException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * The <code>Booking</code> object of a given <code>Guest</code> is paid,
	 * representing the value of the payment and listing all the bookings that
	 * changed state.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void payBooking(Scanner in, RentingSystem rs) {
		try {
			String bookingId = in.next();
			String guestId = in.nextLine().trim();
			Iterator<Booking> it = rs.listAfterPayingBookings(bookingId, guestId);
			System.out.printf(Main.PAY_BOOKING, bookingId, rs.getBookingPrice(bookingId));
			while (it.hasNext()) {
				Booking b = it.next();
				System.out.printf(Main.LIST_CHANGED_BOOKINGS, b.getBookingId(), b.getState().getStatus());
			}
		} catch (NoUserException | NotGuestException | NoBookingException | NotBookingGuestException
				| CannotPayBookingException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Adds a review, made by a given <code>Guest</code> to an already paid
	 * <code>Booking</code>.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void addReview(Scanner in, RentingSystem rs) {
		try {
			String bookingId = in.next();
			String guestId = in.nextLine().trim();
			String comment = in.nextLine();
			String classification = in.nextLine();
			rs.addReview(bookingId, guestId, comment, classification);
			System.out.printf(Main.REVIEW_ADDED, bookingId);
		} catch (NoUserException | NotGuestException | NoBookingException | NotBookingGuestException
				| BookingAlreadyReviewedException | CannotReviewBookingException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Presents information about the <code>Bookings</code> of a given
	 * <code>Guest</code>,as well as the guest total payment value for the paid
	 * bookings.
	 * 
	 * @param in        - Scanner object.
	 * @param rs        - RentingSystem object.
	 * @param formatter - Date formatter.
	 */
	private static void listGuestInfo(Scanner in, RentingSystem rs, DateTimeFormatter formatter) {
		try {
			String guestId = in.nextLine().trim();
			Iterator<Booking> it = rs.listGuestBookingsInfo(guestId);
			System.out.printf(Main.GUEST_INFO, guestId, rs.getTotalPaidMoney(guestId));
			while (it.hasNext()) {
				Booking b = it.next();
				Property p = b.getProperty();
				System.out.printf(Main.LIST_PAID_BOOKINGS_INFO, b.getBookingId(), p.getId(), p.getPropertyType(),
						p.getLocation(), formatter.format(b.getArrivalDate()), formatter.format(b.getDepartureDate()),
						b.getGuestsNum(), b.getState().getStatus(), b.getBookingPaidMoney());
			}
		} catch (NoUserException | NotGuestException | GuestHasNoBookingsException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Lists all stays from the <code>Guests</code> at a given
	 * <code>Property</code>.
	 * 
	 * @param in        - Scanner object.
	 * @param rs        - RentingSystem object.
	 * @param formatter - Date formatter.
	 */
	private static void listStays(Scanner in, RentingSystem rs, DateTimeFormatter formatter) {
		try {
			String propertyId = in.nextLine().trim();
			Iterator<Booking> it = rs.listPropertyStays(propertyId);
			System.out.printf(Main.LIST_PROPERTY_STAYS, propertyId);
			while (it.hasNext()) {
				Booking b = it.next();
				System.out.printf(Main.LIST_STAYS_INFO, b.getBookingId(), formatter.format(b.getArrivalDate()),
						formatter.format(b.getDepartureDate()), b.getGuest().getId(), b.getGuest().getNationality(),
						b.getGuestsNum(), b.getTotalPrice());
			}
		} catch (NoPropertyException | NoPropertyStaysException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Lists all properties by a given location and number of guests of a
	 * <code>Property</code>.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void listSearchedProperties(Scanner in, RentingSystem rs) {
		try {
			String location = in.nextLine().trim();
			int guestsNum = in.nextInt();
			Iterator<Property> it = rs.listSearchedProperties(location, guestsNum);
			System.out.printf(Main.LIST_SEARCHED_PROPERTIES, location);
			while (it.hasNext()) {
				Property p = it.next();
				System.out.printf(Main.SEARCH_LIST, p.getId(), p.getAverageRating(), p.getPricePerNight(),
						p.getCapacity(), p.getPropertyType());
			}

		} catch (NoSatisfyingPropertyException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Lists all properties from the highest to the lowest rated.
	 * 
	 * @param in - Scanner object.
	 * @param rs - RentingSystem object.
	 */
	private static void listBestProperties(Scanner in, RentingSystem rs) {
		try {
			String location = in.nextLine().trim();
			Iterator<Property> it = rs.listBestProperties(location);
			System.out.printf(Main.LIST_BEST_PROPERTIES, location);
			while (it.hasNext()) {
				Property p = it.next();
				System.out.printf(Main.SEARCH_BEST_LIST, p.getId(), p.getAverageRating(), p.getCapacity(),
						p.getPricePerNight(), p.getPropertyType());
			}

		} catch (NoSatisfyingPropertyException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Shows the guest that has visited more distinct locations.
	 * 
	 * @param rs - RentingSystem object.
	 */
	private static void listGlobalTrotter(RentingSystem rs) {
		try {
			Guest guest = rs.getGlobalTrotter();
			System.out.printf(Main.GLOBE_TROTTER, guest.getId(), guest.getStaysLocationsNum());
		} catch (NoGlobeTrotterException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Shows the help menu, when a command is not valid.
	 */
	private static void helpMenu() {
		System.out.printf(Main.HELP);
	}

}
