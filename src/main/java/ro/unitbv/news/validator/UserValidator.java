package ro.unitbv.news.validator;

import ro.unitbv.news.model.User;

/**
 * Validator for a user.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class UserValidator {

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	private StringFieldValidator usernameValidator;
	private StringFieldValidator passwordValidator;

	public UserValidator(StringFieldValidator usernameValidator, StringFieldValidator passwordValidator) {
		this.usernameValidator = usernameValidator;
		this.passwordValidator = passwordValidator;
	}

	/**
	 * Validates the provided information for a user.
	 *
	 * @param user user to validate.
	 * @return result of the validation.
	 */
	public ValidationResult validate(User user) {
		ValidationResult result = new ValidationResult();
		ValidationResult usernameValidationResult = usernameValidator.validate(USERNAME, user.getUsername());
		ValidationResult passwordValidationResult = passwordValidator.validate(PASSWORD, user.getPassword());
		result.addErrors(usernameValidationResult.getErrors());
		result.addErrors(passwordValidationResult.getErrors());
		return result;
	}
}
