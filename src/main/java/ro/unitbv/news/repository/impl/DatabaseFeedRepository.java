package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ro.unitbv.news.entity.FeedEntity;
import ro.unitbv.news.factory.RepositoryFactory;
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

	private ModelEntityConverter converter = new ModelEntityConverter();

	@Override
	public long create(Feed feed) {
		FeedEntity feedEntity = converter.toFeedEntity(feed);
		RepositoryFactory repositoryFactory = new RepositoryFactory();
		UserRepository userRepository = repositoryFactory.getUserRepository();
		User owner = userRepository.get(feed.getOwnerId());
		feedEntity.setOwner(converter.toUserEntity(owner));
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(feedEntity);
			session.getTransaction().commit();
			return feedEntity.getId();
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
			Query query = session.createQuery("from FeedEntity feed where feed.owner.id = :owner_id");
			query.setParameter("owner_id", ownerId);
			List<FeedEntity> feedEntities = query.list();
			session.getTransaction().commit();
			List<Feed> feeds = new ArrayList<>();
			for (FeedEntity feedEntity : feedEntities) {
				Feed feed = converter.toFeedModel(feedEntity);
				feeds.add(feed);
			}
			return feeds;
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
			FeedEntity feedEntity = (FeedEntity) session.get(FeedEntity.class, id);
			if (feedEntity == null) {
				throw new InvalidIdException();
			}
			session.getTransaction().commit();
			return converter.toFeedModel(feedEntity);
		}
		finally {
			session.close();
		}
	}
}
