package properties;

/**
 * <code>Interface</code> that represents one of the extended interfaces of the
 * two types of properties.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public interface PrivateRoom extends Property {

	/**
	 * Adds an <code>amenity</code> to the <code>List of amenities</code> of the the
	 * private room.
	 * 
	 * @pre The property exists in the system.
	 * @param amenity - Property amenity.
	 */
	void addAmenity(String amenity);

}
