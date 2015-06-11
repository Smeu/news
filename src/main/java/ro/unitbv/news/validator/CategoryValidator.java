package ro.unitbv.news.validator;

import ro.unitbv.news.model.Category;
import ro.unitbv.news.model.FieldConstraint;

/**
 * Validator for a {@link ro.unitbv.news.model.Category}.
 *
 * @author Teodora Tanase
 */
public class CategoryValidator {

	private static final String WORD_REGEX = "[a-z]+";

	private static final int WORD_MIN_LENGTH = 2;
	private static final int WORD_MAX_LENGTH = 32;

	private static final String NAME = "name";
	private static final String KEYWORD = "keyword";

	private StringFieldValidator fieldValidator;
	private FieldConstraint fieldConstraint;

	public CategoryValidator(StringFieldValidator fieldValidator) {
		this.fieldValidator = fieldValidator;
		fieldConstraint = new FieldConstraint();
		fieldConstraint.setMinLength(WORD_MIN_LENGTH);
		fieldConstraint.setMaxLength(WORD_MAX_LENGTH);
		fieldConstraint.setRegex(WORD_REGEX);
	}

	public ValidationResult validate(Category category) {
		ValidationResult validationResult = new ValidationResult();
		ValidationResult nameResult = fieldValidator.validateMandatory(NAME, category.getName(), fieldConstraint);
		validationResult.addErrors(nameResult.getErrors());
		for (String keyword : category.getKeywords()) {
			ValidationResult keywordResult = fieldValidator.validateMandatory(KEYWORD, keyword, fieldConstraint);
			validationResult.addErrors(keywordResult.getErrors());
		}
		return validationResult;
	}
}
