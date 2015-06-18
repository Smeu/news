package ro.unitbv.news.repository;

import java.util.List;

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

	/**
	 * Adds a new followed user for the one with the specified id.
	 *
	 * @param id           of the user that follows.
	 * @param followedUser user that is followed.
	 * @return <code>true</code> if the followed user was successfully added, or <code>false</code> if that user was
	 * already being followed.
	 */
	boolean addFollowedUser(long id, User followedUser);

	/**
	 * Retrieves all users that the given one is following.
	 *
	 * @param id id of the given user.
	 * @return all users that the given one is following.
	 */
	List<User> getFollowedUsers(long id);

	/**
	 * Retrieves all users.
	 *
	 * @return users.
	 */
	List<User> getAll();

	/**
	 * Deletes a user after its id.
	 *
	 * @param id id of the user to be deleted.
	 * @return <code>true</code> if deletion was performed or <code>false</code> otherwise.
	 */
	boolean delete(long id);

	/**
	 * Deletes a followed user from the one with the specified id.
	 *
	 * @param id           id of the user that follows.
	 * @param followedUser user that is followed.
	 * @return <code>true</code> if deletion was performed or <code>false</code> otherwise.
	 */
	boolean deleteFollowedUser(long id, User followedUser);
}
