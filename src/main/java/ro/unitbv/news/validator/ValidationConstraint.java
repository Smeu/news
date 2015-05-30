package ro.unitbv.news.validator;

/**
 * Constraint to use as validation criteria.
 *
 * @author Teodora Tanase
 */
public class ValidationConstraint {

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
}
