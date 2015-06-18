package ro.unitbv.news.service;

import java.util.List;

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
	 * Gets all the users.
	 *
	 * @return response holding the users or the errors.
	 */
	Response<List<User>> getAll();

	/**
	 * Authenticates a user after its username and password.
	 *
	 * @param username the user's username.
	 * @param password the user's password.
	 * @return response holding the authenticated user or the errors.
	 */
	Response<User> authenticate(String username, String password);

	/**
	 * Adds a new followed user for the one with the specified id.
	 *
	 * @param id           of the user that follows.
	 * @param followedUser user that is followed.
	 * @return response holding <code>true</code> if the followed user was successfully added, <code>false</code> if
	 * that user was already being followed, or the errors in case there are any.
	 */
	Response<Boolean> addFollowedUser(long id, User followedUser);

	/**
	 * Retrieves all users that the given one is following.
	 *
	 * @param id id of the given user.
	 * @return response holding all users that the given one is following or the errors.
	 */
	Response<List<User>> getFollowedUsers(long id);

	/**
	 * Deletes a user after its id.
	 *
	 * @param id        id of the user to delete.
	 * @param performer user that performs deletion.
	 * @return response holding <code>true</code> if deletion was performed, <code>false</code> if deletion was not
	 * performed or the errors.
	 */
	Response<Boolean> delete(long id, User performer);

	/**
	 * Deletes a followed user from the one with the specified id.
	 *
	 * @param id           id of the user that follows.
	 * @param followedUser user that is followed.
	 * @param performer    user that performs deletion.
	 * @return response holding <code>true</code> if deletion was performed, <code>false</code> if deletion was not
	 * performed or the errors.
	 */
	Response<Boolean> deleteFollowedUser(long id, User followedUser, User performer);
}
