package ro.unitbv.news.validator;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
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

	/**
	 * Validates a user's credentials.
	 *
	 * @param user user to validate.
	 * @return result of the validation.
	 */
	public ValidationResult validate(User user) {
		ValidationResult result = new ValidationResult();
		if (user.getUsername().trim().length() < USERNAME_MIN_LENGTH) {
			result.addError(new FieldError(USERNAME, Error.MIN_LENGTH_NOT_REACHED));
		}
		if (user.getUsername().trim().length() > USERNAME_MAX_LENGTH) {
			result.addError(new FieldError(USERNAME, Error.MAX_LENGTH_EXCEEDED));
		}
		if (user.getPassword().trim().length() < PASSWORD_MIN_LENGTH) {
			result.addError(new FieldError(PASSWORD, Error.MIN_LENGTH_NOT_REACHED));
		}
		if (user.getPassword().trim().length() > PASSWORD_MAX_LENGTH) {
			result.addError(new FieldError(PASSWORD, Error.MAX_LENGTH_EXCEEDED));
		}
		return result;
	}
}
