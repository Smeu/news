package ro.unitbv.news.validator;

import java.util.LinkedList;
import java.util.List;

import ro.unitbv.news.model.FieldError;

/**
 * Class for holding the result of a validation.
 *
 * @author Rares Smeu
 */
public class ValidationResult {

	private List<FieldError> errors;

	public ValidationResult() {
		errors = new LinkedList<>();
	}

	/**
	 * Checks if the response has any errors.
	 *
	 * @return true if it has errors, false otherwise.
	 */
	public boolean hasErrors() {
		return errors != null && !errors.isEmpty();
	}

	/**
	 * Adds the error to the errors list.
	 *
	 * @param error error to be added.
	 */
	public void addError(FieldError error) {
		errors.add(error);
	}

	/**
	 * Adds the errors to the errors list.
	 *
	 * @param errors errors to be added.
	 */
	public void addErrors(List<FieldError> errors) {
		this.errors.addAll(errors);
	}

	public List<FieldError> getErrors() {
		return errors;
	}
}
