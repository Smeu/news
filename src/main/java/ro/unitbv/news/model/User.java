package ro.unitbv.news.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model for a user.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class User {

	private long id;

	private String username;

	private String password;

	private List<User> followedUsers = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<User> getFollowedUsers() {
		return followedUsers;
	}

	public void addFollowedUser(User user) {
		followedUsers.add(user);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(username, user.username) &&
				Objects.equals(password, user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password);
	}
}

