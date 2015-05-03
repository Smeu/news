package ro.unitbv.news.repository;

import ro.unitbv.news.model.User;

/**
 * @author Rares Smeu
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
}
