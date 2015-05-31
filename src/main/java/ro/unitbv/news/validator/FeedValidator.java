package ro.unitbv.news.validator;

import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.FieldConstraint;

/**
 * Validator for a feed.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class FeedValidator {

	private static final int NAME_MIN_LENGTH = 4;
	private static final int NAME_MAX_LENGTH = 32;
	private static final int DESCRIPTION_MAX_LENGTH = 256;

	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";

	private UrlValidator urlValidator;

	public FeedValidator(UrlValidator urlValidator) {
		this.urlValidator = urlValidator;
	}

	/**
	 * Validates a feed.
	 * If is not valid, it returns the errors.
	 *
	 * @param feed the feed to be validated.
	 * @return the validation result.
	 */
	public ValidationResult validate(Feed feed) {
		ValidationResult result = new ValidationResult();
		StringFieldValidator stringValidator = new StringFieldValidator();
		FieldConstraint nameConstraint = new FieldConstraint();
		FieldConstraint descriptionConstraint = new FieldConstraint();
		nameConstraint.setMinLength(NAME_MIN_LENGTH);
		nameConstraint.setMaxLength(NAME_MAX_LENGTH);
		descriptionConstraint.setMaxLength(DESCRIPTION_MAX_LENGTH);
		ValidationResult nameResult = stringValidator.validateMandatory(NAME, feed.getName(), nameConstraint);
		ValidationResult descriptionResult = stringValidator.validateNonMandatory(DESCRIPTION, feed.getDescription(),
				descriptionConstraint);
		result.addErrors(nameResult.getErrors());
		result.addErrors(descriptionResult.getErrors());
		result.addErrors(urlValidator.validate(feed.getUrl()).getErrors());
		return result;
	}
}
