package users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bookings.Booking;
import bookings.BookingClass;
import comparator.BookingStatesComparator;
import enums.Status;
import properties.Property;
import reviews.Review;

/**
 * <code>Class</code> that represents a specific form of the user object -
 * GuestClass. It will represent one of the extended classes of the two types of
 * user object.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class GuestClass extends UserClass implements Guest {

	/**
	 * <code>String</code> with the host list format in users command.
	 */
	private static final String SHOW_GUEST = "%s: %s from %s with email %s [guest user has %d bookings]";

	/**
	 * <code>Booking Map</code> with the all the guests bookings by their Id.
	 */
	private Map<String, Booking> bookingsById;

	/**
	 * <code>Property Map</code> with the all the guests bookings by their
	 * properties.
	 */
	private Map<Booking, Property> bookingsByProperties;

	/**
	 * <code>Review Map</code> with the all the guests reviews by their bookings.
	 */
	private Map<String, Review> bookingsByReviews;

	/**
	 * <code>Booking List</code> with the all the bookings by insertion order.
	 */
	private List<Booking> bookingByOrder;

	/**
	 * Class Constructor.
	 * 
	 * @param id          - Guest identifier.
	 * @param name        - Guest name.
	 * @param nationality - Guest nationality.
	 * @param email       - Guest email.
	 */
	public GuestClass(String id, String name, String nationality, String email) {
		super(id, name, nationality, email);
		bookingsById = new HashMap<>();
		bookingsByProperties = new HashMap<>();
		bookingsByReviews = new HashMap<>();
		bookingByOrder = new ArrayList<>();
	}

	@Override
	public String toString() {
		return String.format(GuestClass.SHOW_GUEST, getId(), getName(), getNationality(), getEmail(),
				bookingsById.size());
	}

	@Override
	public void addBooking(Booking booking) {
		bookingsById.put(booking.getBookingId(), booking);
		bookingsByProperties.put(booking, booking.getProperty());
		bookingByOrder.add(booking);
	}

	@Override
	public boolean hasValidBookingDate(LocalDate arrival, LocalDate departure) {
		for (Booking b : bookingsById.values()) {
			if (b.getState().equals(Status.PAID))
				if (b.getDepartureDate().isAfter(arrival))
					return false;
			if (b.getState().equals(Status.CONFIRMED))
				if (b.hasOverlappedDate(new BookingClass(null, null, arrival, departure, null, 0, null)))
					return false;
		}
		return true;
	}

	@Override
	public void payBooking(String bookingId) {
		getBookingById(bookingId).payBooking();
	}

	@Override
	public boolean hasBooking(String bookingId) {
		return bookingsById.containsKey(bookingId);
	}

	@Override
	public boolean canPayBooking(String bookingId) {
		return getBookingById(bookingId).getState().equals(Status.CONFIRMED);
	}

	@Override
	public String getBookingState(String bookingId) {
		return getBookingById(bookingId).getState().getStatus();
	}

	@Override
	public Booking getBookingById(String bookingId) {
		return bookingsById.get(bookingId);
	}

	@Override
	public Iterator<Booking> listAllChanged(String bookingId) {
		List<Booking> changedBookings = new LinkedList<>();
		Booking booking = getBookingById(bookingId);
		for (Property p : bookingsByProperties.values()) {
			Iterator<Booking> it = p.listAllChanged(booking, booking.getDepartureDate());
			while (it.hasNext())
				changedBookings.add(it.next());
		}
		changedBookings.sort(new BookingStatesComparator());
		return changedBookings.iterator();
	}

	@Override
	public boolean hasAlreadyReviewedBooking(String bookingId) {
		return bookingsByReviews.get(bookingId) != null;
	}

	@Override
	public void reviewBooking(Review review, String bookingId) {
		Booking booking = getBookingById(bookingId);
		bookingsByReviews.put(bookingId, review);
		booking.getProperty().addReview(review);
		booking.reviewBooking();
	}

	@Override
	public boolean isInPaidState(String bookingId) {
		return getBookingById(bookingId).getState().equals(Status.PAID);
	}

	/**
	 * Checks if a booking is in reviewed state.
	 * 
	 * @param bookingId - booking Identifier.
	 * @return - <code>true</code>, if the state of the booking is reviewed;
	 *         <code>false</code>, otherwise.
	 */
	private boolean isInReviewdState(String bookingId) {
		return getBookingById(bookingId).getState().equals(Status.REVIEWED);
	}

	@Override
	public boolean hasBookings() {
		return bookingsById.size() > 0;
	}

	@Override
	public Iterator<Booking> listBookingsInfo() {
		List<Booking> localBookingsInfo = new ArrayList<>();
		for (int i = 0; i < bookingByOrder.size(); i++) {
			Booking b = bookingByOrder.get(i);
			localBookingsInfo.add(b);
		}
		return localBookingsInfo.iterator();
	}

	@Override
	public double getTotalPaidMoney() {
		double totalPaidMoney = 0;
		for (Booking b : bookingsById.values())
			if (isInPaidState(b.getBookingId()) || isInReviewdState(b.getBookingId()))
				totalPaidMoney += b.getTotalPrice();
		return totalPaidMoney;
	}

	@Override
	public int getStaysLocationsNum() {
		List<String> locations = new LinkedList<>();
		for (Booking b : bookingsById.values())
			if (isInPaidState(b.getBookingId()) || isInReviewdState(b.getBookingId()))
				if (!locations.contains(b.getProperty().getLocation()))
					locations.add(b.getProperty().getLocation());
		return locations.size();
	}

	@Override
	public int getAllBookingsNum() {
		return bookingsById.size();
	}

	@Override
	public boolean hasStays() {
		for (Booking b : bookingsById.values())
			if (isInPaidState(b.getBookingId()) || isInReviewdState(b.getBookingId()))
				return true;
		return false;
	}

	@Override
	public boolean isGlobeTrotter(Guest other) {
		if (this.getStaysLocationsNum() > other.getStaysLocationsNum())
			return true;
		if (this.getStaysLocationsNum() < other.getStaysLocationsNum())
			return false;
		if (this.getAllBookingsNum() > other.getAllBookingsNum())
			return true;
		if (this.getAllBookingsNum() < other.getAllBookingsNum())
			return false;
		if (this.getId().compareTo(other.getId()) > 0)
			return true;
		return false;
	}
}
