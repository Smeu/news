package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ro.unitbv.news.entity.NewsEntity;
import ro.unitbv.news.factory.RepositoryFactory;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.converter.ModelEntityConverter;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.util.HibernateUtil;

/**
 * Database implementation for {@link ro.unitbv.news.repository.NewsRepository}.
 *
 * @author Teodora Tanase
 */
public class DatabaseNewsRepository implements NewsRepository {

	private ModelEntityConverter converter = new ModelEntityConverter();

	@Override
	public long create(News news) {
		NewsEntity newsEntity = converter.toNewsEntity(news);
		RepositoryFactory repositoryFactory = new RepositoryFactory();
		UserRepository userRepository = repositoryFactory.getUserRepository();
		FeedRepository feedRepository = repositoryFactory.getFeedRepository();
		User owner = userRepository.get(news.getOwnerId());
		Feed feed = feedRepository.get(news.getFeedId());
		newsEntity.setOwner(converter.toUserEntity(owner));
		newsEntity.setFeed(converter.toFeedEntity(feed));
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(newsEntity);
			session.getTransaction().commit();
			return newsEntity.getId();
		}
		finally {
			session.close();
		}
	}

	@Override
	public News get(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			NewsEntity newsEntity = (NewsEntity) session.get(NewsEntity.class, id);
			if (newsEntity == null) {
				throw new InvalidIdException();
			}
			session.getTransaction().commit();
			return converter.toNewsModel(newsEntity);
		}
		finally {
			session.close();
		}
	}

	@Override
	public List<News> getAllForOwner(long ownerId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from NewsEntity news where news.owner.id = :owner_id");
			query.setParameter("owner_id", ownerId);
			List<NewsEntity> newsEntities = query.list();
			session.getTransaction().commit();
			List<News> newsList = new ArrayList<>();
			for (NewsEntity newsEntity : newsEntities) {
				News news = converter.toNewsModel(newsEntity);
				newsList.add(news);
			}
			return newsList;
		}
		finally {
			session.close();
		}
	}
}
