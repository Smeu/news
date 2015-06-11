package ro.unitbv.news.component;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import ro.unitbv.news.controller.AbstractController;
import ro.unitbv.news.controller.NewsContainerController;
import ro.unitbv.news.model.News;
import ro.unitbv.news.model.User;

/**
 * Container for the news
 *
 * @author Rares Smeu
 */
public class NewsContainer extends AbstractComponent {

	private NewsContainerController controller;

	private User user;

	public NewsContainer(User user, AbstractController mainController) {
		super(mainController);
		this.user = user;
		loadComponent(Component.NEWS_CONTAINER);
		controller = loader.getController();
	}

	public void setNews(List<News> news) {
		List<Pane> newsUI = new ArrayList<>();
		news.forEach((newsComponent) -> newsUI.add((new NewsComponent(newsComponent, user, mainController)).getComponent()));
		controller.setMainController(mainController);
		controller.init(newsUI);
	}
}
