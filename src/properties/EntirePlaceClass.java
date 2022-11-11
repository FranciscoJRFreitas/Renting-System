package properties;

import enums.PropertyType;
import users.Host;

/**
 * <code>Class</code> that represents a specific form of the property object -
 * EntirePlaceClass. It will represent one of the extended classes of the two
 * types of Property object.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class EntirePlaceClass extends PropertyClass implements EntirePlace {

	/**
	 * static final <code>String</code> to list entire place.
	 */
	public static final String SHOW_ENTIRE_PLACE = "%s: %s %d %.0f [%s %d %d]";

	/**
	 * <code>int</code> with the number of rooms.
	 */
	@SuppressWarnings("unused")
	private int rooms;

	/**
	 * Class Constructor.
	 * 
	 * @param id            - Property identifier.
	 * @param location      - Property location.
	 * @param capacity      - Property rental capacity.
	 * @param pricePerNight - Property price per night.
	 * @param host          - Host object.
	 * @param rooms         - Property total rooms.
	 */
	public EntirePlaceClass(String id, String location, int capacity, int pricePerNight, Host host, int rooms) {
		super(id, location, capacity, pricePerNight, host);
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return String.format(EntirePlaceClass.SHOW_ENTIRE_PLACE, getId(), getLocation(), getCapacity(),
				getPricePerNight(), getPropertyType(), bookingCounter(), reviewCounter());
	}

	@Override
	public String getPropertyType() {
		return PropertyType.ENTIRE_PLACE.getType();
	}

}
