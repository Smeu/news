package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.converter.ModelEntityConverter;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.util.HibernateUtil;

/**
 * Database implementation for {@link ro.unitbv.news.repository.FeedRepository}.
 *
 * @author Teodora Tanase
 */
public class DatabaseFeedRepository implements FeedRepository {

	private final static Logger logger = LoggerFactory.getLogger(DatabaseFeedRepository.class);

	private UserRepository userRepository = new DatabaseUserRepository();

	private ModelEntityConverter converter = new ModelEntityConverter();

	@Override
	public long create(Feed feed) {
		ro.unitbv.news.entity.Feed entityFeed = converter.toEntityFeed(feed);
		User owner = userRepository.get(feed.getOwnerId());
		entityFeed.setOwner(converter.toEntityUser(owner));
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(entityFeed);
			session.getTransaction().commit();
			return feed.getId();
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
	public List<Feed> getAllForOwner(long ownerId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Feed feed where feed.owner.id = :owner_id");
			query.setParameter("owner_id", ownerId);
			List<ro.unitbv.news.entity.Feed> entityFeeds = query.list();
			session.getTransaction().commit();
			List<Feed> feeds = new ArrayList<>();
			for (ro.unitbv.news.entity.Feed entityFeed : entityFeeds) {
				Feed feed = converter.toModelFeed(entityFeed);
				feeds.add(feed);
			}
			return feeds;
		}
		catch (HibernateException e) {
			logger.error("Get All error", e);
			throw new InvalidIdException();
		}
		finally {
			session.close();
		}
	}

	@Override
	public Feed get(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			ro.unitbv.news.entity.Feed entityFeed = (ro.unitbv.news.entity.Feed) session.get(ro.unitbv.news.entity.Feed
					.class, id);
			session.getTransaction().commit();
			return converter.toModelFeed(entityFeed);
		}
		catch (HibernateException e) {
			logger.error("Get error", e);
			throw new InvalidIdException();
		}
		finally {
			session.close();
		}
	}
}
