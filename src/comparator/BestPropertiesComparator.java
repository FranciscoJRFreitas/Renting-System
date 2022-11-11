package comparator;

import java.util.Comparator;

import properties.Property;

/**
 * <code>Class</code> that compares the properties by their decreasing average
 * rating.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class BestPropertiesComparator implements Comparator<Property> {

	@Override
	public int compare(Property p1, Property p2) {
		int cmp = 0;
		cmp = p1.getAverageRating() > p2.getAverageRating() ? -1 : 1;
		if (p1.getAverageRating() == p2.getAverageRating())
			cmp = 0;
		if (cmp == 0) {
			cmp = p1.getCapacity() > p2.getCapacity() ? -1 : 1;
			if (p1.getCapacity() == p2.getCapacity())
				cmp = 0;
			if (cmp == 0)
				cmp = p1.getId().compareTo(p2.getId());
		}
		return cmp;
	}
}