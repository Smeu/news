package ro.unitbv.news.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.User;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

	private static final String FIELD = "field";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	@Mock
	private StringFieldValidator fieldValidator;

	@Test
	public void testValidateWithErrors() throws Exception {
		UserValidator validator = new UserValidator(fieldValidator);

		ValidationResult fieldResult = new ValidationResult();
		fieldResult.addError(new FieldError(FIELD, Error.FIELD_IS_MANDATORY));
		when(fieldValidator.validateMandatory(anyString(), anyString(), any())).thenReturn(fieldResult);
		User user = new User();
		user.setUsername(null);
		user.setPassword(null);

		ValidationResult result = validator.validate(user);

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().size(), is(2));
	}

	@Test
	public void testValidateWithoutErrors() throws Exception {
		UserValidator validator = new UserValidator(fieldValidator);
		when(fieldValidator.validateMandatory(anyString(), anyString(), any())).thenReturn(new ValidationResult());
		User user = new User();
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);

		ValidationResult result = validator.validate(user);

		assertThat(result.hasErrors(), is(false));
	}
}
