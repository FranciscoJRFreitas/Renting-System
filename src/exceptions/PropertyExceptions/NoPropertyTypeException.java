package exceptions.PropertyExceptions;

/**
 * <code>Exception</code> thrown when a rental property type is unknown.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
@SuppressWarnings("serial")
public class NoPropertyTypeException extends Exception {

	private static final String MESSAGE = "Unknown rental property type.";

	public NoPropertyTypeException() {
		super(MESSAGE);
	}
	
}
