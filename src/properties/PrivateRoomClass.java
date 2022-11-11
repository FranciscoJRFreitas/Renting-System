package properties;

import java.util.ArrayList;
import java.util.List;

import enums.PropertyType;
import users.Host;

/**
 * <code>Class</code> that represents a specific form of the property object -
 * PrivateRoomClass. It will represent one of the extended classes of the two
 * types of Property object.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class PrivateRoomClass extends PropertyClass implements PrivateRoom {

	/**
	 * static final <code>String</code> to list private room.
	 */
	public static final String SHOW_PRIVATE_ROOM = "%s: %s %d %.0f [%s %d %d]";

	/**
	 * <code>String List</code> with the property amenities.
	 */
	private List<String> amenities;

	/**
	 * Class constructor.
	 * 
	 * @param id            - Property identifier.
	 * @param location      - Property location.
	 * @param capacity      - Property rental capacity.
	 * @param pricePerNight - Property price per night.
	 * @param host          - Host object.
	 * @param rooms         - Property total rooms.
	 */
	public PrivateRoomClass(String id, String location, int capacity, int pricePerNight, Host host, int rooms) {
		super(id, location, capacity, pricePerNight, host);
		amenities = new ArrayList<String>(rooms);
	}

	@Override
	public void addAmenity(String amenity) {
		amenities.add(amenity);
	}

	@Override
	public String toString() {
		return String.format(PrivateRoomClass.SHOW_PRIVATE_ROOM, getId(), getLocation(), getCapacity(),
				getPricePerNight(), getPropertyType(), bookingCounter(), reviewCounter());
	}

	@Override
	public String getPropertyType() {
		return PropertyType.PRIVATE_ROOM.getType();
	}

}
