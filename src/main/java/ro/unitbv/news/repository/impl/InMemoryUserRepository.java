package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;

/**
 * In memory implementation for {@link ro.unitbv.news.repository.UserRepository}
 * Should not be used in multi-threading contexts.
 *
 * @author Teodora Tanase
 */
public class InMemoryUserRepository implements UserRepository {

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
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
}
