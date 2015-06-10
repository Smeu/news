package ro.unitbv.news.repository.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import ro.unitbv.news.entity.UserEntity;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.converter.ModelEntityConverter;
import ro.unitbv.news.repository.exception.InternalErrorException;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.util.HibernateUtil;
import ro.unitbv.news.util.PasswordHash;

/**
 * Database implementation for {@link ro.unitbv.news.repository.UserRepository}.
 *
 * @author Teodora Tanase
 */
public class DatabaseUserRepository implements UserRepository {

	private ModelEntityConverter converter = new ModelEntityConverter();

	@Override
	public long create(User user) {
		UserEntity userEntity = converter.toUserEntity(user);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(userEntity);
			session.getTransaction().commit();
			return userEntity.getId();
		}
		finally {
			session.close();
		}
	}

	@Override
	public User get(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			UserEntity userEntity = (UserEntity) session.get(UserEntity.class, id);
			if (userEntity == null) {
				throw new InvalidIdException();
			}
			session.getTransaction().commit();
			User user = converter.toUserModel(userEntity);
			for (UserEntity followedUser : userEntity.getFollowedUsers()) {
				user.addFollowedUser(converter.toUserModel(followedUser));
			}
			return user;
		}
		finally {
			session.close();
		}
	}

	@Override
	public User authenticate(String username, String password) {
		List<User> users = getAll();
		try {
			for (User user : users) {
				if (user.getUsername().equals(username) && PasswordHash.validatePassword(password, user.getPassword())) {
					return user;
				}
			}
			return null;
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new InternalErrorException();
		}
	}

	@Override
	public boolean addFollowedUser(long id, User followedUser) {
		if (followedUser.getId() == id) {
			throw new InvalidIdException();
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			UserEntity existingUserEntity = (UserEntity) session.get(UserEntity.class, id);
			if (existingUserEntity == null) {
				throw new InvalidIdException();
			}
			existingUserEntity.addFollowedUser(converter.toUserEntity(followedUser));
			session.update(existingUserEntity);
			session.getTransaction().commit();
			return true;
		}
		finally {
			session.close();
		}
	}

	@Override
	public List<User> getFollowedUsers(long id) {
		User followingUser = get(id);
		return followingUser.getFollowedUsers();
	}

	@Override
	public List<User> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			List<UserEntity> userEntities = session.createQuery("from UserEntity").list();
			session.getTransaction().commit();
			List<User> users = new ArrayList<>();
			for (UserEntity userEntity : userEntities) {
				User user = converter.toUserModel(userEntity);
				for (UserEntity followedUser : userEntity.getFollowedUsers()) {
					user.addFollowedUser(converter.toUserModel(followedUser));
				}
				users.add(user);
			}
			return users;
		}
		finally {
			session.close();
		}
	}
}
