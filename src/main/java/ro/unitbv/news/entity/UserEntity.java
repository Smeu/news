package ro.unitbv.news.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import ro.unitbv.news.model.UserType;

/**
 * Database entity for a user.
 *
 * @author Teodora Tanase
 */
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String username;

	private String password;

	private UserType type;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_followedUser", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "followedUser_id", referencedColumnName = "id"))
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

	public void removeFollowedUser(UserEntity followedUser) {
		followedUsers.remove(followedUser);
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || getClass() != object.getClass()) {
			return false;
		}
		UserEntity userEntity = (UserEntity) object;
		return Objects.equals(id, userEntity.id) &&
				Objects.equals(username, userEntity.username) &&
				Objects.equals(password, userEntity.password) &&
				Objects.equals(type, userEntity.type);
	}
}
