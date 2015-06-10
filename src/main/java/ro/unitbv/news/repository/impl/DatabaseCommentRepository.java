package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.unitbv.news.entity.CommentEntity;
import ro.unitbv.news.model.Comment;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.CommentRepository;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.converter.ModelEntityConverter;
import ro.unitbv.news.repository.exception.InternalErrorException;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.util.HibernateUtil;

/**
 * Database implementation for {@link ro.unitbv.news.repository.CommentRepository}.
 *
 * @author Teodora Tanase
 */
public class DatabaseCommentRepository implements CommentRepository {

	private final static Logger logger = LoggerFactory.getLogger(DatabaseCommentRepository.class);

	private UserRepository userRepository = new DatabaseUserRepository();

	private NewsRepository newsRepository = new DatabaseNewsRepository();

	private ModelEntityConverter converter = new ModelEntityConverter();

	@Override
	public long create(Comment comment) {
		CommentEntity commentEntity = converter.toCommentEntity(comment);
		User owner = userRepository.get(comment.getOwnerId());
		commentEntity.setOwner(converter.toUserEntity(owner));
		News news = newsRepository.get(comment.getNewsId());
		commentEntity.setNews(converter.toNewsEntity(news));
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(commentEntity);
			session.getTransaction().commit();
			return commentEntity.getId();
		}
		catch (HibernateException e) {
			logger.error("Error creating comment", e);
			throw new InternalErrorException();
		}
		finally {
			session.close();
		}
	}

	@Override
	public Comment get(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			CommentEntity commentEntity = (CommentEntity) session.get(CommentEntity.class, id);
			if (commentEntity == null) {
				throw new InvalidIdException();
			}
			session.getTransaction().commit();
			return converter.toCommentModel(commentEntity);
		}
		catch (HibernateException e) {
			logger.error("Error getting comment", e);
			throw new InternalErrorException();
		}
		finally {
			session.close();
		}
	}

	@Override
	public List<Comment> getAllForNews(long newsId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from NewsEntity news where news.id = :news_id");
			query.setParameter("news_id", newsId);
			List<CommentEntity> commentEntities = query.list();
			session.getTransaction().commit();
			List<Comment> comments = new ArrayList<>();
			for (CommentEntity commentEntity : commentEntities) {
				Comment comment = converter.toCommentModel(commentEntity);
				comments.add(comment);
			}
			return comments;
		}
		catch (HibernateException e) {
			logger.error("Error getting all comments for news", e);
			throw new InternalErrorException();
		}
		finally {
			session.close();
		}
	}
}
