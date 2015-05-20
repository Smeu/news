package ro.unitbv.news.repository.impl;

import org.junit.Before;
import org.junit.Test;

import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Teodora Tanase
 */
public class InMemoryUserRepositoryTest {

	private static final String EMPTY_STRING = "";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	private UserRepository repository;

	@Before
	public void init() {
		repository = new InMemoryUserRepository();
	}

	@Test(expected = InvalidIdException.class)
	public void testGetInvalidId() throws Exception {
		repository.get(0);
	}

	@Test
	public void testGetValidId() throws Exception {
		User addedUser = new User();
		repository.create(addedUser);

		User retrievedUser = repository.get(0);
		assertThat(retrievedUser, is(addedUser));
	}

	@Test
	public void testAuthenticateUnsuccessful() throws Exception {
		boolean result = repository.authenticate(EMPTY_STRING, EMPTY_STRING);

		assertThat(result, is(false));
	}

	@Test
	public void testAuthenticateSuccessful() throws Exception {
		User user = new User();
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);
		repository.create(user);

		boolean result = repository.authenticate(USERNAME, PASSWORD);
		assertThat(result, is(true));
	}
}
