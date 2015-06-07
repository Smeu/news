package ro.unitbv.news.factory;

import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.impl.DatabaseFeedRepository;
import ro.unitbv.news.repository.impl.DatabaseNewsRepository;
import ro.unitbv.news.repository.impl.DatabaseUserRepository;

/**
 * Factory for repositories.
 *
 * @author Rares Smeu
 */
public class RepositoryFactory {

	private FeedRepository feedRepository;
	private NewsRepository newsRepository;
	private UserRepository userRepository;

	public RepositoryFactory() {
		feedRepository = new DatabaseFeedRepository();
		newsRepository = new DatabaseNewsRepository();
		userRepository = new DatabaseUserRepository();
	}

	public FeedRepository getFeedRepository() {
		return feedRepository;
	}

	public NewsRepository getNewsRepository() {
		return newsRepository;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}
}
