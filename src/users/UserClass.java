package users;

/**
 * <code>Abstract class</code> that represent the basic form of a user object.
 * It will represent the super class of the two types of users object.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
abstract class UserClass implements User {

	/**
	 * <code>String</code> with the user id.
	 */
	private String id;
	/**
	 * <code>String</code> with the user name.
	 */
	private String name;
	/**
	 * <code>String</code> with the user nationality.
	 */
	private String nationality;
	/**
	 * <code>String</code> with the user email.
	 */
	private String email;

	/**
	 * Class Constructor.
	 * 
	 * @param id          - User identifier.
	 * @param name        - User name.
	 * @param nationality - User nationality.
	 * @param email       - User email.
	 */
	protected UserClass(String id, String name, String nationality, String email) {
		this.id = id;
		this.name = name;
		this.nationality = nationality;
		this.email = email;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNationality() {
		return nationality;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		UserClass other = (UserClass) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public abstract String toString();

}
