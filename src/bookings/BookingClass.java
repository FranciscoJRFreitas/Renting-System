package bookings;

import java.time.Duration;
import java.time.LocalDate;

import enums.Status;
import properties.Property;
import users.Guest;

/**
 * <code>Class</code> that represents a Booking object. It will represent the
 * property booking.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class BookingClass implements Booking {

	/**
	 * <code>Property</code> with the property.
	 */
	private Property property;

	/**
	 * <code>String</code> with the bookingId.
	 */
	private String bookingId;

	/**
	 * <code>LocalDate</code> with arrival date.
	 */
	private LocalDate arrival;

	/**
	 * <code>LocalDate</code> with departure date.
	 */
	private LocalDate departure;

	/**
	 * <code>Status</code> with the booking current status.
	 */
	private Status status;

	/**
	 * <code>int</code> with the declared number of guests.
	 */
	private int guestsNum;

	/**
	 * <code>Guest</code> who made the booking.
	 */
	private Guest guest;

	/**
	 * Class constructor.
	 * 
	 * @param arrival
	 * @param departure
	 * @param status
	 */
	public BookingClass(Property property, String bookingId, LocalDate arrival, LocalDate departure, Status status,
			int guestsNum, Guest guest) {
		this.property = property;
		this.arrival = arrival;
		this.departure = departure;
		this.status = status;
		this.bookingId = bookingId;
		this.guestsNum = guestsNum;
		this.guest = guest;
	}

	@Override
	public String getBookingId() {
		return bookingId;
	}

	@Override
	public Property getProperty() {
		return property;
	}

	@Override
	public LocalDate getArrivalDate() {
		return arrival;
	}

	@Override
	public LocalDate getDepartureDate() {
		return departure;
	}

	@Override
	public int getGuestsNum() {
		return guestsNum;
	}

	@Override
	public Guest getGuest() {
		return guest;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingClass other = (BookingClass) obj;
		if (bookingId == null) {
			if (other.bookingId != null)
				return false;
		} else if (!bookingId.equals(other.bookingId))
			return false;
		return true;
	}

	@Override
	public Status getState() {
		return status;
	}

	@Override
	public void requestBooking() {
		this.status = Status.REQUESTED;
	}

	@Override
	public void confirmBooking() {
		this.status = Status.CONFIRMED;
	}

	@Override
	public void rejectBooking() {
		this.status = Status.REJECTED;
	}

	@Override
	public void payBooking() {
		this.status = Status.PAID;
	}

	@Override
	public void reviewBooking() {
		this.status = Status.REVIEWED;
	}

	@Override
	public boolean hasOverlappedDate(Booking other) {
		if (this.departure.isAfter(other.getArrivalDate()) && this.arrival.isBefore(other.getDepartureDate()))
			return true;
		if (this.arrival.isBefore(other.getDepartureDate()) && this.departure.isAfter(other.getArrivalDate()))
			return true;
		return false;
	}

	@Override
	public double getTotalPrice() {
		float days = Duration.between(arrival.atStartOfDay(), departure.atStartOfDay()).toDays();
		return days * property.getPricePerNight();
	}

	@Override
	public double getBookingPaidMoney() {
		if (this.getState().equals(Status.PAID) || this.getState().equals(Status.REVIEWED))
			return getTotalPrice();
		else
			return 0;
	}

	@Override
	public void cancelBooking() {
		this.status = Status.CANCELLED;
	}

}
