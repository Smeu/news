package ro.unitbv.news.validator;

import ro.unitbv.news.model.Comment;
import ro.unitbv.news.model.FieldConstraint;

/**
 * Validator for a comment.
 *
 * @author Teodora Tanase
 */
public class CommentValidator {

	private static final int CONTENT_MAX_LENGTH = 1024;

	private static final String CONTENT = "content";

	private StringFieldValidator fieldValidator;

	private FieldConstraint contentConstraint;

	public CommentValidator(StringFieldValidator fieldValidator) {
		this.fieldValidator = fieldValidator;
		contentConstraint = new FieldConstraint();
		contentConstraint.setMaxLength(CONTENT_MAX_LENGTH);
	}

	/**
	 * Validates a comment.
	 * If it is not valid, it returns the errors.
	 *
	 * @param comment the comment to be validated.
	 * @return the validation result.
	 */
	public ValidationResult validate(Comment comment) {
		return fieldValidator.validateMandatory(CONTENT, comment.getContent(), contentConstraint);
	}
}
