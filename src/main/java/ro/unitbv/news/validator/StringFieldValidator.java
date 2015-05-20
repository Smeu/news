package ro.unitbv.news.validator;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;

/**
 * Validator for a field of type {@link String}.
 *
 * @author Teodora Tanase
 */
public class StringFieldValidator {

	private int minLength;
	private int maxLength;

	public StringFieldValidator(int minLength, int maxLength) {
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	/**
	 * Validates a field's content.
	 *
	 * @param fieldName name of field that will be validated.
	 * @param field     field to validate.
	 * @return result of the validation.
	 */
	public ValidationResult validate(String fieldName, String field) {
		ValidationResult result = new ValidationResult();
		if (field == null) {
			result.addError(new FieldError(fieldName, Error.FIELD_IS_MANDATORY));
			return result;
		}
		if (field.trim().length() < minLength) {
			result.addError(new FieldError(fieldName, Error.MIN_LENGTH_NOT_REACHED));
		}
		if (field.trim().length() > maxLength) {
			result.addError(new FieldError(fieldName, Error.MAX_LENGTH_EXCEEDED));
		}
		return result;
	}
}
