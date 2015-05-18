package ro.unitbv.news.validator;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.FieldError;

/**
 * Validator for a feed.
 *
 * @author Rares Smeu
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
		if (feed.getName() == null) {
			result.addError(new FieldError(NAME, Error.FIELD_IS_MANDATORY));
		}
		else {
			if (feed.getName().trim().length() < NAME_MIN_LENGTH) {
				result.addError(new FieldError(NAME, Error.MIN_LENGTH_NOT_REACHED));
			}
			if (feed.getName().trim().length() > NAME_MAX_LENGTH) {
				result.addError(new FieldError(NAME, Error.MAX_LENGTH_EXCEEDED));
			}
		}
		if (feed.getDescription() != null && feed.getDescription().trim().length() > DESCRIPTION_MAX_LENGTH) {
			result.addError(new FieldError(DESCRIPTION, Error.MIN_LENGTH_NOT_REACHED));
		}
		result.addErrors(urlValidator.validate(feed.getUrl()).getErrors());

		return result;
	}
}
