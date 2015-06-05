package ro.unitbv.news.repository.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.unitbv.news.model.News;
import ro.unitbv.news.repository.NewsRepository;

/**
 * Database implementation for {@link ro.unitbv.news.repository.NewsRepository}.
 *
 * @author Teodora Tanase
 */
public class DatabaseNewsRepository implements NewsRepository {

	private final static Logger logger = LoggerFactory.getLogger(DatabaseNewsRepository.class);

	@Override
	public long create(News news) {
		return 0;
	}

	@Override
	public News get(long id) {
		return null;
	}

	@Override
	public List<News> getAllForOwner(long ownerId) {
		return null;
	}
}
