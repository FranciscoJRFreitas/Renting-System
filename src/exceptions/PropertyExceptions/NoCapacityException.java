package exceptions.PropertyExceptions;

/**
 * <code>Exception</code> thrown when a property has exceeded its limit guests
 * capacity.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoCapacityException extends Exception {

	private static final String MESSAGE = "Property %s has a capacity limit of %d guests.";

	private String propertyId;

	private int capacity;

	public NoCapacityException(String propertyId, int capacity) {
		super(MESSAGE);
		this.propertyId = propertyId;
		this.capacity = capacity;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, propertyId, capacity);
	}
}
