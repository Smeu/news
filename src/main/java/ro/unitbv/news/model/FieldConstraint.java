package ro.unitbv.news.model;

/**
 * Constraint to use as validation criteria.
 *
 * @author Teodora Tanase
 */
public class FieldConstraint {

	private int minLength;

	private int maxLength;

	private String regex = "(.)*";

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	@Override
	public String toString() {
		return "FieldConstraint{" + "minLength=" + minLength + ", maxLength=" + maxLength + '}';
	}
}
