package ro.unitbv.news.validator;

import ro.unitbv.news.model.*;
import ro.unitbv.news.model.Error;

/**
 * Validator for a user.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class UserValidator {

	private static final int USERNAME_MIN_LENGTH = 4;
	private static final int USERNAME_MAX_LENGTH = 32;
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	private StringFieldValidator validator;

	private FieldConstraint usernameConstraint;

	public UserValidator(StringFieldValidator validator) {
		this.validator = validator;
		usernameConstraint = new FieldConstraint();
		usernameConstraint.setMinLength(USERNAME_MIN_LENGTH);
		usernameConstraint.setMaxLength(USERNAME_MAX_LENGTH);
	}

	/**
	 * Validates the provided information for a user.
	 *
	 * @param user user to validateMandatory.
	 * @return result of the validation.
	 */
	public ValidationResult validate(User user) {
		ValidationResult result = new ValidationResult();
		ValidationResult usernameResult = validator.validateMandatory(USERNAME, user.getUsername(), usernameConstraint);
		if (user.getPassword() == null || user.getPassword().isEmpty()){
			result.addError(new FieldError(PASSWORD, Error.FIELD_IS_MANDATORY));
		}
		result.addErrors(usernameResult.getErrors());
		return result;
	}
}
