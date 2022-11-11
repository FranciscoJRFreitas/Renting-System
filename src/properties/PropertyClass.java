package properties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import bookings.Booking;
import comparator.BookingComparator;
import enums.Status;
import reviews.Review;
import users.Host;

/**
 * <code>Abstract class</code> that represent the basic form of the property
 * object. It will represent the super class of the two types of properties
 * object.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
abstract class PropertyClass implements Property {

	/**
	 * <code>String</code> with the property id.
	 */
	private String id;
	/**
	 * <code>String</code> with the property location.
	 */
	private String location;
	/**
	 * <code>int</code> with the total property capacity.
	 */
	private int capacity;
	/**
	 * <code>int</code> with the price of the property per night.
	 */
	private int pricePerNight;
	/**
	 * <code>Host</code> with the host of the property.
	 */
	private Host host;

	/**
	 * <code>Review list</code> with the all the property reviews.
	 */
	private List<Review> reviews;

	/**
	 * <code>Booking list</code> with the all the property bookings.
	 */
	private SortedMap<String, Booking> bookings;

	/**
	 * <code>Booking Set</code> with the all the rejected bookings.
	 */
	private Set<Booking> allRejectedBookings;

	/**
	 * <code>LocalDate</code> with the latest updated payment day.
	 */
	private LocalDate latestUpdatedDeparture;

	/**
	 * Class constructor.
	 * 
	 * @param id            - Property identifier.
	 * @param location      - Property location.
	 * @param capacity      - Property rental capacity.
	 * @param pricePerNight - Property price per night.
	 * @param host          - Host object.
	 */
	protected PropertyClass(String id, String location, int capacity, int pricePerNight, Host host) {
		this.id = id;
		this.location = location;
		this.capacity = capacity;
		this.pricePerNight = pricePerNight;
		this.host = host;
		reviews = new LinkedList<>();
		bookings = new TreeMap<>(new BookingComparator());
		allRejectedBookings = new HashSet<>();
		latestUpdatedDeparture = LocalDate.MIN;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PropertyClass))
			return false;
		PropertyClass other = (PropertyClass) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public abstract String getPropertyType();

	@Override
	public abstract String toString();

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public double getPricePerNight() {
		return pricePerNight;
	}

	@Override
	public Host getHost() {
		return host;
	}

	@Override
	public int reviewCounter() {
		return reviews.size();
	}

	@Override
	public int bookingCounter() {
		return bookings.size();
	}

	@Override
	public LocalDate getLatestUpdatedDeparture() {
		return latestUpdatedDeparture;
	}

	@Override
	public void addBooking(Booking booking) {
		bookings.put(booking.getBookingId(), booking);
	}

	@Override
	public void addReview(Review review) {
		reviews.add(review);
	}

	@Override
	public void setLatestUpdatedDeparture(LocalDate latestUpdatedDeparture) {
		this.latestUpdatedDeparture = latestUpdatedDeparture;
	}

	@Override
	public boolean hasBooking(String bookingId) {
		return bookings.containsKey(bookingId);
	}

	@Override
	public Booking getBookingById(String bookingId) {
		return bookings.get(bookingId);
	}

	@Override
	public String getBookingState(String bookingId) {
		return getBookingById(bookingId).getState().getStatus();
	}

	@Override
	public boolean conflictsWithOtherBookings(Booking booking) {
		for (Booking b : bookings.values())
			if (booking.hasOverlappedDate(b))
				return true;
		return false;
	}

	@Override
	public void rejectBooking(String bookingId) {
		allRejectedBookings.add(getBookingById(bookingId));
	}

	@Override
	public void rejectOverlappingBookings(String bookingId) {
		List<Booking> requestedBookings = new LinkedList<>();
		for (Booking b : bookings.values())
			if (b.getState().equals(Status.REQUESTED))
				requestedBookings.add(b);

		for (int i = 0; i < requestedBookings.size(); i++)
			if (getBookingById(bookingId).hasOverlappedDate(requestedBookings.get(i)))
				requestedBookings.get(i).rejectBooking();
	}

	@Override
	public Iterator<Booking> listRejectedBookings() {
		List<Booking> rejectedBookings = new LinkedList<>();
		for (Booking b : bookings.values())
			if (b.getState().equals(Status.REJECTED)) {
				if (!allRejectedBookings.contains(b))
					rejectedBookings.add(b);
				allRejectedBookings.add(b);
			}
		return rejectedBookings.iterator();
	}

	@Override
	public Iterator<Booking> listAllRejected() {
		List<Booking> rejectedBookings = new LinkedList<>();
		for (Booking b : bookings.values())
			if (allRejectedBookings.contains(b))
				rejectedBookings.add(b);
		return rejectedBookings.iterator();
	}

	@Override
	public Iterator<Booking> listAllChanged(Booking booking, LocalDate latestUpdatedPayment) {
		List<Booking> changedBookings = new LinkedList<>();
		LocalDate date = latestUpdatedPayment;
		this.setLatestUpdatedDeparture(date);
		for (Booking b : bookings.values()) {
			if (b.getProperty().equals(this) && !b.equals(booking)) {
				if (date.isAfter(b.getDepartureDate())) {
					if (b.getState().equals(Status.CONFIRMED)) {
						b.cancelBooking();
						changedBookings.add(b);
					} else if (b.getState().equals(Status.REQUESTED)) {
						b.rejectBooking();
						changedBookings.add(b);
					}
				}
			}
		}
		return changedBookings.iterator();
	}

	@Override
	public boolean hasBookings() {
		return bookings.values().size() > 0;
	}

	@Override
	public boolean hasCapacity(int guestsNum) {
		return getCapacity() < guestsNum;
	}

	@Override
	public boolean hasStays() {
		for (Booking b : bookings.values())
			if (b.getState().equals(Status.PAID) || b.getState().equals(Status.REVIEWED))
				return true;
		return false;
	}

	@Override
	public Iterator<Booking> listAllStays() {
		List<Booking> stays = new ArrayList<>(), reverseStaysOrder = new ArrayList<>();
		for (Booking b : bookings.values())
			if (b.getState().equals(Status.PAID) || b.getState().equals(Status.REVIEWED)) {
				stays.add(b);
				reverseStaysOrder.add(b);
			}
		int j = 0;
		for (int i = stays.size() - 1; i >= 0; i--) {
			reverseStaysOrder.set(j, stays.get(i));
			j++;
		}
		return reverseStaysOrder.iterator();
	}

	@Override
	public double getAverageRating() {
		if (reviews.isEmpty())
			return 0;
		double averageRating = 0;
		for (Review r : reviews)
			averageRating += r.getRating();
		return (averageRating / reviewCounter());
	}

}
