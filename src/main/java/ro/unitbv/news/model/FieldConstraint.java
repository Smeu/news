package ro.unitbv.news.model;

/**
 * Constraint to use as validation criteria.
 *
 * @author Teodora Tanase
 */
public class FieldConstraint {

	private int minLength;
	private int maxLength;

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

	@Override
	public String toString() {
		return "FieldConstraint{" +
				"minLength=" + minLength +
				", maxLength=" + maxLength +
				'}';
	}
}
