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

	private StringFieldValidator validator = new StringFieldValidator();

	private ValidationConstraint usernameConstraint;
	private ValidationConstraint passwordConstraint;

	public UserValidator(ValidationConstraint usernameConstraint, ValidationConstraint passwordConstraint) {
		this.usernameConstraint = usernameConstraint;
		this.passwordConstraint = passwordConstraint;
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
