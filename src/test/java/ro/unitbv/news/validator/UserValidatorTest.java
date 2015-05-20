package ro.unitbv.news.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.User;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	private UserValidator validator;

	@Mock
	private StringFieldValidator usernameValidator;

	@Mock
	private StringFieldValidator passwordValidator;

	@Before
	public void init() {
		validator = new UserValidator(usernameValidator, passwordValidator);
	}

	@Test
	public void testValidateWithErrors() throws Exception {
		ValidationResult usernameValidationResult = new ValidationResult();
		ValidationResult passwordValidationResult = new ValidationResult();
		usernameValidationResult.addError(new FieldError(USERNAME, Error.FIELD_IS_MANDATORY));
		passwordValidationResult.addError(new FieldError(PASSWORD, Error.FIELD_IS_MANDATORY));
		when(usernameValidator.validate(anyString(), anyString())).thenReturn(usernameValidationResult);
		when(passwordValidator.validate(anyString(), anyString())).thenReturn(passwordValidationResult);
		User user = new User();
		user.setUsername(null);
		user.setPassword(null);

		ValidationResult result = validator.validate(user);

		assertThat(result.hasErrors(), is(true));
		assertThat(result.getErrors().size(), is(2));
	}

	@Test
	public void testValidateWithoutErrors() throws Exception {
		when(usernameValidator.validate(anyString(), anyString())).thenReturn(new ValidationResult());
		when(passwordValidator.validate(anyString(), anyString())).thenReturn(new ValidationResult());
		User user = new User();
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);

		ValidationResult result = validator.validate(user);

		assertThat(result.hasErrors(), is(false));
	}
}
