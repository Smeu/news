package ro.unitbv.news.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import ro.unitbv.news.model.UserType;

@Entity
@Table(name = "user")
@Embeddable
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String username;

	private String password;

	private UserType type;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "user_followedUser", joinColumns = @JoinColumn(name = "followedUser_id"))
	private List<UserEntity> followedUsers = new ArrayList<>();

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

	public List<UserEntity> getFollowedUsers() {
		return followedUsers;
	}

	public void setFollowedUsers(List<UserEntity> followedUsers) {
		this.followedUsers = followedUsers;
	}

	public void addFollowedUser(UserEntity followedUser) {
		followedUsers.add(followedUser);
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
}
