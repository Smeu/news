package ro.unitbv.news.repository;

import ro.unitbv.news.model.User;

/**
 * Repository for {@link ro.unitbv.news.model.User}.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public interface UserRepository {

	/**
	 * Creates a new user.
	 *
	 * @param user user to be created.
	 * @return the id of the newly created user.
	 */
	long create(User user);

	/**
	 * Gets a user after its id.
	 *
	 * @param id the id of the user.
	 * @return the user.
	 */
	User get(long id);

	/**
	 * Attempts authentication of a user with the specified username and password.
	 *
	 * @param username user's provided username.
	 * @param password user's provided password.
	 * @return the corresponding user if authentication was successful or <code>null</code> otherwise.
	 */
	User authenticate(String username, String password);
}
