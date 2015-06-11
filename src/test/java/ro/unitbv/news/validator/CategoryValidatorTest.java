package ro.unitbv.news.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.*;
import ro.unitbv.news.model.Error;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryValidatorTest {

	private static final String INVALID_WORD = "!.";
	private static final String VALID_WORD = "word";

	private static final String NAME = "name";

	@Mock
	private StringFieldValidator fieldValidator;

	private CategoryValidator validator;

	@Before
	public void init() {
		validator = new CategoryValidator(fieldValidator);
	}

	@Test
	public void testValidateWithInvalidInput() throws Exception {
		ValidationResult fieldResult = new ValidationResult();
		fieldResult.addError(new FieldError(NAME, Error.ILLEGAL_CONTENT));
		when(fieldValidator.validateMandatory(anyString(), anyString(), any())).thenReturn(fieldResult);
		Category category = new Category();
		category.setName(INVALID_WORD);

		ValidationResult result = validator.validate(category);

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().size(), is(1));
	}

	@Test
	public void testValidateWithValidInput() throws Exception {
		when(fieldValidator.validateMandatory(anyString(), anyString(), any())).thenReturn(new ValidationResult());
		Category category = new Category();
		category.setName(VALID_WORD);

		ValidationResult result = validator.validate(category);
		assertThat(result.hasErrors(), is(false));
	}
}
