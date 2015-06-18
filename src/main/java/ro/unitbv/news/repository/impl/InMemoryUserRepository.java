package ro.unitbv.news.repository.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.util.PasswordHash;

/**
 * In memory implementation for {@link ro.unitbv.news.repository.UserRepository}
 * Should not be used in multi-threading contexts.
 *
 * @author Teodora Tanase
 */
public class InMemoryUserRepository implements UserRepository {

	private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

	private List<User> users = new ArrayList<>();

	@Override
	public long create(User user) {
		users.add(user);
		return users.size() - 1;
	}

	@Override
	public User get(long id) {
		if (id < 0 || id >= users.size()) {
			throw new InvalidIdException();
		}
		return users.get((int) id);
	}

	@Override
	public User authenticate(String username, String password) {
		for (User user : users) {
			try {
				if (user.getUsername().equals(username) && PasswordHash.validatePassword(password, user.getPassword())) {
					return user;
				}
			}
			catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				log.error("Could not validate the password", e);
			}
		}
		return null;
	}

	@Override
	public boolean addFollowedUser(long id, User followedUser) {
		if (id < 0 || id >= users.size()) {
			throw new InvalidIdException();
		}
		User user = users.get((int) id);
		if (user.getFollowedUsers().contains(followedUser) || followedUser.getId() == id) {
			return false;
		}
		user.addFollowedUser(followedUser);
		return true;
	}

	@Override
	public List<User> getFollowedUsers(long id) {
		if (id < 0 || id >= users.size()) {
			throw new InvalidIdException();
		}
		return users.get((int) id).getFollowedUsers();
	}

	@Override
	public List<User> getAll() {
		return new ArrayList<>(users);
	}

	@Override
	public boolean delete(long id) {
		if (id < 0 || id >= users.size()) {
			throw new InvalidIdException();
		}
		users.set((int) id, null);
		return true;
	}
}
