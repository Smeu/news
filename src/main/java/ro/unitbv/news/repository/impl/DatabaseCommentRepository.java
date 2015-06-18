package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ro.unitbv.news.entity.CommentEntity;
import ro.unitbv.news.factory.RepositoryFactory;
import ro.unitbv.news.model.Comment;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.CommentRepository;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.converter.ModelEntityConverter;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.util.HibernateUtil;

/**
 * Database implementation for {@link ro.unitbv.news.repository.CommentRepository}.
 *
 * @author Teodora Tanase
 */
public class DatabaseCommentRepository implements CommentRepository {

	private ModelEntityConverter converter = new ModelEntityConverter();

	@Override
	public long create(Comment comment) {
		CommentEntity commentEntity = converter.toCommentEntity(comment);
		RepositoryFactory repositoryFactory = new RepositoryFactory();
		UserRepository userRepository = repositoryFactory.getUserRepository();
		NewsRepository newsRepository = repositoryFactory.getNewsRepository();
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
		finally {
			session.close();
		}
	}

	@Override
	public List<Comment> getAllForNews(long newsId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from CommentEntity comment where comment.news.id = :news_id");
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
		finally {
			session.close();
		}
	}

	@Override
	public boolean delete(long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			CommentEntity commentEntity = (CommentEntity) session.get(CommentEntity.class, id);
			if (commentEntity == null) {
				return false;
			}
			session.delete(commentEntity);
			session.getTransaction().commit();
			return true;
		}
		finally {
			session.close();
		}
	}
}
