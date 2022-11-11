package users;

/**
 * <code>Interface</code> that represents the super class User.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public interface User {

	/**
	 * Returns the user id.
	 * 
	 * @return - <code>String</code> id.
	 */
	String getId();

	/**
	 * Returns the user name.
	 * 
	 * @return - <code>String</code> name.
	 */
	String getName();

	/**
	 * Returns the user nationality.
	 * 
	 * @return - <code>String</code> nationality.
	 */
	String getNationality();

	/**
	 * Returns the user email.
	 * 
	 * @return - <code>String</code> email.
	 */
	String getEmail();

}
