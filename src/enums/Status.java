package enums;

/**
 * <code>Enum</code> that represents all the possible status of a booking.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */
public enum Status {

	REQUESTED("requested"), CONFIRMED("confirmed"), REJECTED("rejected"), CANCELLED("cancelled"), PAID("paid"),
	REVIEWED("reviewed");

	private String status;

	private Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
