package ro.unitbv.news.repository.impl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.util.PasswordHash;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Teodora Tanase
 */
public class InMemoryUserRepositoryTest {

	private static final String EMPTY_STRING = "";
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";

	private static final long INVALID_ID = -1;
	private static final long VALID_ID = 0;

	private UserRepository repository;

	@Before
	public void init() {
		repository = new InMemoryUserRepository();
	}

	@Test(expected = InvalidIdException.class)
	public void testGetInvalidId() throws Exception {
		repository.get(INVALID_ID);
	}

	@Test
	public void testGetValidId() throws Exception {
		User addedUser = new User();
		repository.create(addedUser);

		User retrievedUser = repository.get(VALID_ID);
		assertThat(retrievedUser, is(addedUser));
	}

	@Test
	public void testAuthenticateUnsuccessful() throws Exception {
		User authenticatedUser = repository.authenticate(EMPTY_STRING, EMPTY_STRING);

		assertEquals(null, authenticatedUser);
	}

	@Test
	public void testAuthenticateSuccessful() throws Exception {
		User user = new User();
		user.setUsername(USERNAME);
		user.setPassword(PasswordHash.createHash(PASSWORD));
		repository.create(user);

		User authenticatedUser = repository.authenticate(USERNAME, PASSWORD);
		assertThat(authenticatedUser, is(user));
	}

	@Test(expected = InvalidIdException.class)
	public void testAddFollowedUserInvalidId() throws Exception {
		repository.addFollowedUser(INVALID_ID, new User());
	}

	@Test
	public void testAddFollowedUserUnsuccessful() throws Exception {
		User followingUser = new User();
		User followedUser = new User();
		followingUser.setId(repository.create(followingUser));
		repository.addFollowedUser(followingUser.getId(), followedUser);

		boolean result = repository.addFollowedUser(followingUser.getId(), followedUser);
		assertThat(result, is(false));
	}

	@Test
	public void testAddFollowedUserSuccessful() throws Exception {
		User followingUser = new User();
		User followedUser = new User();
		followingUser.setId(repository.create(followingUser));

		boolean result = repository.addFollowedUser(followingUser.getId(), followedUser);
		assertThat(result, is(true));
	}

	@Test(expected = InvalidIdException.class)
	public void testGetFollowedUsersInvalidId() throws Exception {
		repository.getFollowedUsers(INVALID_ID);
	}

	@Test
	public void testGetFollowedUsersValidId() throws Exception {
		User followingUser = new User();
		User followedUser = new User();
		followingUser.setId(repository.create(followingUser));
		repository.addFollowedUser(followingUser.getId(), followedUser);

		List<User> followedUsers = repository.getFollowedUsers(followingUser.getId());
		assertThat(followedUsers.contains(followedUser), is(true));
	}
}
