package ro.unitbv.news.service;

import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;

/**
 * Service for operations with {@link ro.unitbv.news.model.User}.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public interface UserService {

	/**
	 * Creates a new user.
	 *
	 * @param user user to be created.
	 * @return response holding id of the user or the errors.
	 */
	Response<Long> create(User user);

	/**
	 * Gets a user after its id.
	 *
	 * @param id the id of the user.
	 * @return response holding the user or the errors.
	 */
	Response<User> get(long id);

	/**
	 * Authenticates a user after its username and password.
	 *
	 * @param username the user's username.
	 * @param password the user's password.
	 * @return the authenticated user.
	 */
	User authenticate(String username, String password);
}
