package enums;

/**
 * <code>Enum</code> that represents the different classification options with
 * the corresponding rating.
 * 
 * @author Alberto Serra / Francisco Freitas
 *
 */

public enum Classification {

	EXCELLENT("excellent", 5), GOOD("good", 4), AVERAGE("average", 3), POOR("poor", 2), TERRIBLE("terrible", 1),
	NOT_REVIEWED("", 0);

	private String classification;
	private int rating;

	private Classification(String classification, int rating) {
		this.classification = classification;
		this.rating = rating;
	}

	public String getClassification() {
		return classification;
	}

	public int getRating() {
		return rating;
	}

}
