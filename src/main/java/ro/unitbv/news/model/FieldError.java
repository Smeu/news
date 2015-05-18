package ro.unitbv.news.model;

/**
 * Model for a field error.
 *
 * @author Rares Smeu
 */
public class FieldError {

  private String name;

  private Error error;

  public FieldError(String name, Error error) {
    this.name = name;
    this.error = error;
  }

  public String getName() {
    return name;
  }

  public Error getError() {
    return error;
  }

	@Override
	public String toString() {
		return "FieldError{" +
				"name='" + name + '\'' +
				", error=" + error +
				'}';
	}
}
