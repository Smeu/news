package ro.unitbv.news.component;

import ro.unitbv.news.controller.NewsController;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;

/**
 * A news component
 *
 * @author Rares Smeu
 */
public class NewsComponent extends AbstractComponent {


	/**
	 * @param news the news to be displayed.
	 * @param user the logged user.
	 */
	public NewsComponent(News news, User user) {
		NewsController controller = loadComponent(Component.NEWS_COMPONENT);
		controller.init(news, user);
	}

}


