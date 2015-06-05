package ro.unitbv.news.repository.impl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.converter.ModelEntityConverter;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.util.HibernateUtil;
import ro.unitbv.news.util.PasswordHash;

/**
 * Database implementation for {@link ro.unitbv.news.repository.UserRepository}.
 *
 * @author Teodora Tanase
 */
public class DatabaseUserRepository implements UserRepository {

	private final static Logger logger = LoggerFactory.getLogger(DatabaseUserRepository.class);

	private ModelEntityConverter converter = new ModelEntityConverter();

	@Override
	public long create(User user) {
		ro.unitbv.news.entity.User entityUser = converter.toEntityUser(user);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(entityUser);
			session.getTransaction().commit();
			return user.getId();
		}
		catch (HibernateException e) {
			logger.error("Create error", e);
			throw new InvalidIdException();
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
			ro.unitbv.news.entity.User entityUser = (ro.unitbv.news.entity.User) session.get(ro.unitbv.news.entity.User
					.class, id);
			session.getTransaction().commit();
			User user = converter.toModelUser(entityUser);
			for (ro.unitbv.news.entity.User followedUser : entityUser.getFollowedUsers()) {
				user.addFollowedUser(converter.toModelUser(followedUser));
			}
			return user;
		}
		catch (HibernateException e) {
			logger.error("Get error", e);
			throw new InvalidIdException();
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
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.error("Authenticate error", e);
			throw new InvalidIdException();
		}
		return null;
	}

	@Override
	public boolean addFollowedUser(long id, User followedUser) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			ro.unitbv.news.entity.User existingUser = (ro.unitbv.news.entity.User) session.get(ro.unitbv.news.entity.User
					.class, id);
			existingUser.addFollowedUser(converter.toEntityUser(followedUser));
			session.update(existingUser);
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			logger.error("Add Follower error", e);
			throw new InvalidIdException();
		}
		finally {
			session.close();
		}
		return true;
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
			List<ro.unitbv.news.entity.User> entityUsers = session.createQuery("from User").list();
			session.getTransaction().commit();
			List<User> users = new ArrayList<>();
			for (ro.unitbv.news.entity.User entityUser : entityUsers) {
				User user = converter.toModelUser(entityUser);
				for (ro.unitbv.news.entity.User followedUser : entityUser.getFollowedUsers()) {
					user.addFollowedUser(converter.toModelUser(followedUser));
				}
				users.add(user);
			}
			return users;
		}
		catch (HibernateException e) {
			logger.error("Get All error", e);
			throw new InvalidIdException();
		}
		finally {
			session.close();
		}
	}
}
