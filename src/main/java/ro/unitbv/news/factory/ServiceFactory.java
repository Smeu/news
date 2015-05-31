package ro.unitbv.news.factory;

import ro.unitbv.news.service.FeedService;
import ro.unitbv.news.service.NewsService;
import ro.unitbv.news.service.UserService;
import ro.unitbv.news.service.impl.DefaultFeedService;
import ro.unitbv.news.service.impl.DefaultNewsService;
import ro.unitbv.news.service.impl.DefaultUserService;

/**
 * Factory for services.
 *
 * @author Rares Smeu
 */
public class ServiceFactory {

	private static ServiceFactory serviceFactory;

	private FeedService feedService;
	private NewsService newsService;
	private UserService userService;

	private ServiceFactory() {
		RepositoryFactory repositoryFactory = new RepositoryFactory();
		ValidatorFactory validatorFactory = new ValidatorFactory();

		feedService = new DefaultFeedService(repositoryFactory.getFeedRepository(), validatorFactory.getFeedValidator());
		newsService = new DefaultNewsService(repositoryFactory.getNewsRepository());
		userService = new DefaultUserService(repositoryFactory.getUserRepository(), validatorFactory.getUserValidator());
	}

	public FeedService getFeedService() {
		return feedService;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public UserService getUserService() {
		return userService;
	}

	public static ServiceFactory getInstance() {
		if (serviceFactory == null) {
			serviceFactory = new ServiceFactory();
		}
		return serviceFactory;
	}
}
