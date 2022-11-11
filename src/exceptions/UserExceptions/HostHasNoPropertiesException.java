package exceptions.UserExceptions;

/**
 * <code>Exception</code> thrown when a host has no registered properties in the
 * application.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class HostHasNoPropertiesException extends Exception {

	private static final String MESSAGE = "Host %s has no registered properties.";

	private String userId;

	public HostHasNoPropertiesException(String userId) {
		super(MESSAGE);
		this.userId = userId;
	}

	@Override
	public String getMessage() {
		return String.format(MESSAGE, userId);
	}
}
