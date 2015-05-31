package ro.unitbv.news.model;

/**
 * Model for a field error.
 *
 * @author Rares Smeu
 */
public class FieldError {

  private String fieldName;

  private Error error;

	private FieldConstraint constraint;

  public FieldError(String fieldName, Error error, FieldConstraint constraint) {
    this.fieldName = fieldName;
    this.error = error;
		this.constraint = constraint;
  }

	public FieldError(String fieldName, Error error) {
		this.fieldName = fieldName;
		this.error = error;
	}

	public String getFieldName() {
    return fieldName;
  }

  public Error getError() {
    return error;
  }

	public FieldConstraint getConstraint() {
		return constraint;
	}

	@Override
	public String toString() {
		return "FieldError{" +
				"fieldName='" + fieldName + '\'' +
				", error=" + error +
				", constraint=" + constraint +
				'}';
	}
}
