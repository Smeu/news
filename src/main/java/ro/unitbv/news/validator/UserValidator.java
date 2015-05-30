package ro.unitbv.news.validator;

import ro.unitbv.news.model.User;

/**
 * Validator for a user.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class UserValidator {

	private static final int USERNAME_MIN_LENGTH = 4;
	private static final int USERNAME_MAX_LENGTH = 32;
	private static final int PASSWORD_MIN_LENGTH = 8;
	private static final int PASSWORD_MAX_LENGTH = 32;

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	private StringFieldValidator validator = new StringFieldValidator();

	private ValidationConstraint usernameConstraint = new ValidationConstraint();
	private ValidationConstraint passwordConstraint = new ValidationConstraint();

	public UserValidator() {
		usernameConstraint.setMinLength(USERNAME_MIN_LENGTH);
		usernameConstraint.setMaxLength(USERNAME_MAX_LENGTH);
		passwordConstraint.setMinLength(PASSWORD_MIN_LENGTH);
		passwordConstraint.setMaxLength(PASSWORD_MAX_LENGTH);
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
		ValidationResult passwordResult = validator.validateMandatory(PASSWORD, user.getPassword(), passwordConstraint);
		result.addErrors(usernameResult.getErrors());
		result.addErrors(passwordResult.getErrors());
		return result;
	}
}
