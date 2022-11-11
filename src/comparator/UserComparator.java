package comparator;

import java.util.Comparator;

/**
 * <code>Class</code> that compares the users by their id.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public class UserComparator implements Comparator<String> {

	@Override
	public int compare(String userId1, String userId2) {
		return userId1.compareTo(userId2);
	}

}
