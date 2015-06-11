package ro.unitbv.news.factory;

import ro.unitbv.news.validator.CategoryValidator;
import ro.unitbv.news.validator.CommentValidator;
import ro.unitbv.news.validator.FeedValidator;
import ro.unitbv.news.validator.StringFieldValidator;
import ro.unitbv.news.validator.UrlValidator;
import ro.unitbv.news.validator.UserValidator;

/**
 * Factory for validators;
 *
 * @author Rares Smeu
 */
public class ValidatorFactory {

	private FeedValidator feedValidator;
	private StringFieldValidator stringFieldValidator;
	private UrlValidator urlValidator;
	private UserValidator userValidator;
	private CommentValidator commentValidator;
	private CategoryValidator categoryValidator;

	public ValidatorFactory() {
		stringFieldValidator = new StringFieldValidator();
		urlValidator = new UrlValidator();
		feedValidator = new FeedValidator(urlValidator);
		userValidator = new UserValidator(stringFieldValidator);
		commentValidator = new CommentValidator(stringFieldValidator);
		categoryValidator = new CategoryValidator(stringFieldValidator);
	}


	public FeedValidator getFeedValidator() {
		return feedValidator;
	}

	public StringFieldValidator getStringFieldValidator() {
		return stringFieldValidator;
	}

	public UrlValidator getUrlValidator() {
		return urlValidator;
	}

	public UserValidator getUserValidator() {
		return userValidator;
	}

	public CommentValidator getCommentValidator() {
		return commentValidator;
	}

	public CategoryValidator getCategoryValidator() {
		return categoryValidator;
	}
}
