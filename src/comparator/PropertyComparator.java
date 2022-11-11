package comparator;

import java.util.Comparator;

import properties.Property;

/**
 * <code>Class</code> that compares the properties by increasing price, in case
 * of draw, by decreasing capacity and lastly by alphabetical order of the
 * property identifier.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class PropertyComparator implements Comparator<Property> {

	@Override
	public int compare(Property p1, Property p2) {
		int cmp = 0;
		cmp = p1.getPricePerNight() - p2.getPricePerNight() > 0 ? 1 : -1;
		if (p1.getPricePerNight() == p2.getPricePerNight())
			cmp = 0;
		if (cmp == 0) {
			cmp = p2.getCapacity() - p1.getCapacity();
			if (cmp == 0)
				cmp = p1.getId().compareTo(p2.getId());
		}
		return cmp;
	}
}