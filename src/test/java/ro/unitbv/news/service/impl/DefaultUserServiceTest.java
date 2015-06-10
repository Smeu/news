package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import ro.unitbv.news.service.UserService;
import ro.unitbv.news.validator.UserValidator;
import ro.unitbv.news.validator.ValidationResult;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceTest {

	private static final String EMPTY_STRING = "";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	private static final long ID = 1;

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
		result.addError(new FieldError(PASSWORD, Error.MIN_LENGTH_NOT_REACHED));
		when(validator.validate(any())).thenReturn(result);

		Response<Long> response = service.create(new User());

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors(), is(result.getErrors()));
	}

	@Test
	public void testCreateWithoutErrors() throws Exception {
		when(validator.validate(any())).thenReturn(new ValidationResult());
		when(repository.create(any())).thenReturn(ID);

		Response<Long> response = service.create(new User());

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(ID));
	}

	@Test
	public void testGetHappyFlow() throws Exception {
		User user = new User();
		when(repository.get(ID)).thenReturn(user);

		Response<User> response = service.get(ID);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(user));
	}

	@Test
	public void testAuthenticateIncorrectly() throws Exception {
		when(repository.authenticate(anyString(), anyString())).thenReturn(null);

		Response<User> response = service.authenticate(EMPTY_STRING, EMPTY_STRING);

		assertEquals(null, response.getResponse());
	}

	@Test
	public void testAuthenticateCorrectly() throws Exception {
		User user = new User();
		when(repository.authenticate(anyString(), anyString())).thenReturn(user);

		Response<User> response = service.authenticate(USERNAME, PASSWORD);

		assertThat(response.getResponse(), is(user));
	}

	@Test
	public void testAddFollowedUserHappyFlow() throws Exception {
		User followedUser = new User();
		when(repository.addFollowedUser(ID, followedUser)).thenReturn(true);

		Response<Boolean> response = service.addFollowedUser(ID, followedUser);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(true));
	}

	@Test
	public void testGetFollowedUsersHappyFlow() throws Exception {
		List<User> followedUsers = new ArrayList<>();
		when(repository.getFollowedUsers(ID)).thenReturn(followedUsers);

		Response<List<User>> response = service.getFollowedUsers(ID);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(followedUsers));
	}
}
