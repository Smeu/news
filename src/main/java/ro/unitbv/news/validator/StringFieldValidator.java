package ro.unitbv.news.validator;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldConstraint;
import ro.unitbv.news.model.FieldError;

/**
 * Validator for a field of type {@link String}.
 *
 * @author Teodora Tanase
 */
public class StringFieldValidator {

	/**
	 * Validates a mandatory field's content against the specified constraint.
	 *
	 * @param fieldName  name of field that will be validated.
	 * @param field      field to validate.
	 * @param constraint constraint to validate against.
	 * @return result of the validation.
	 */
	public ValidationResult validateMandatory(String fieldName, String field, FieldConstraint constraint) {
		ValidationResult result = new ValidationResult();
		if (field == null) {
			result.addError(new FieldError(fieldName, Error.FIELD_IS_MANDATORY));
			return result;
		}
		if (field.trim().length() < constraint.getMinLength()) {
			result.addError(new FieldError(fieldName, Error.MIN_LENGTH_NOT_REACHED, constraint));
		}
		if (field.trim().length() > constraint.getMaxLength()) {
			result.addError(new FieldError(fieldName, Error.MAX_LENGTH_EXCEEDED, constraint));
		}
		return result;
	}
}
