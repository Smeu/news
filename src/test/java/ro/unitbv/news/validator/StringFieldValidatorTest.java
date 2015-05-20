package ro.unitbv.news.validator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Teodora Tanase
 */
public class StringFieldValidatorTest {

	private static final String FIELD_NAME = "field_name";
	private static final String EMPTY_STRING = "";
	private static final String LONG_STRING = "long string long string";
	private static final String VALID_CONTENT = "content";

	private static final int MIN_LENGTH = 4;
	private static final int MAX_LENGTH = 10;

	private StringFieldValidator validator;

	@Before
	public void init() {
		validator = new StringFieldValidator(MIN_LENGTH, MAX_LENGTH);
	}

	@Test
	public void testValidateNullField() throws Exception {
		ValidationResult result = validator.validate(FIELD_NAME, null);

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().size(), is(1));
	}

	@Test
	public void testValidateFieldTooShort() throws Exception {
		ValidationResult result = validator.validate(FIELD_NAME, EMPTY_STRING);

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().size(), is(1));
	}

	@Test
	public void testValidateFieldTooLong() throws Exception {
		ValidationResult result = validator.validate(FIELD_NAME, LONG_STRING);

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().size(), is(1));
	}

	@Test
	public void testValidateWithoutErrors() throws Exception {
		ValidationResult result = validator.validate(FIELD_NAME, VALID_CONTENT);

		assertThat(result.hasErrors(), is(false));
	}
}
