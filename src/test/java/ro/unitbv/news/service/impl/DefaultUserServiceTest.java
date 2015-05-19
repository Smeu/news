package ro.unitbv.news.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.service.UserService;
import ro.unitbv.news.validator.UserValidator;
import ro.unitbv.news.validator.ValidationResult;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceTest {

	private UserService service;

	@Mock
	private UserRepository repository;

	@Mock
	private UserValidator validator;

	@Before
	public void init() {
		service = new DefaultUserService(repository, validator);
	}

	@Test
	public void testCreateWithErrors() throws Exception {
		ValidationResult result = new ValidationResult();
		result.addError(new FieldError("password", Error.MIN_LENGTH_NOT_REACHED));
		when(validator.validate(any())).thenReturn(result);

		Response<Long> response = service.create(new User());

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors(), is(result.getErrors()));
	}

	@Test
	public void testCreateWithoutErrors() throws Exception {
		long id = 1L;
		when(validator.validate(any())).thenReturn(new ValidationResult());
		when(repository.create(any())).thenReturn(id);

		Response<Long> response = service.create(new User());

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(id));
	}

	@Test
	public void testGetWithErrors() throws Exception {
		long id = -1;
		when(repository.get(id)).thenThrow(new InvalidIdException());

		Response<User> response = service.get(id);

		assertThat(response.hasErrors(), is(true));
	}

	@Test
	public void testGetWithoutErrors() throws Exception {
		long id = 1L;
		User user = new User();
		when(repository.get(id)).thenReturn(user);

		Response<User> response = service.get(id);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(user));
	}

	@Test
	public void testAuthenticateIncorrectly() throws Exception {
		when(repository.authenticate(any(), any())).thenReturn(false);

		boolean result = service.authenticate("", "");
		assertThat(result, is(false));
	}

	@Test
	public void testAuthenticateCorrectly() throws Exception {
		when(repository.authenticate(any(), any())).thenReturn(true);

		boolean result = service.authenticate("username", "password");
		assertThat(result, is(true));
	}
}
