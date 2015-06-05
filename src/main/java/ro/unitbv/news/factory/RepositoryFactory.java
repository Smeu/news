package ro.unitbv.news.factory;

import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.impl.DatabaseFeedRepository;
import ro.unitbv.news.repository.impl.DatabaseUserRepository;
import ro.unitbv.news.repository.impl.InMemoryFeedRepository;
import ro.unitbv.news.repository.impl.InMemoryNewsRepository;
import ro.unitbv.news.repository.impl.InMemoryUserRepository;

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
		newsRepository = new InMemoryNewsRepository();
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
