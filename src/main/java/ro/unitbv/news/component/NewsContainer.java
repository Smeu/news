package ro.unitbv.news.component;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import ro.unitbv.news.controller.NewsContainerController;
import ro.unitbv.news.model.News;

/**
 * Container for the news
 *
 * @author Rares Smeu
 */
public class NewsContainer extends AbstractComponent {

	private NewsContainerController controller;

	public NewsContainer() {
		loadComponent(Component.NEWS_CONTAINER);
		controller = loader.getController();
	}

	public void setNews(List<News> news) {
		List<Pane> newsUI = new ArrayList<>();
		news.forEach((newsComponent) -> newsUI.add((new NewsComponent()).getComponent()));
		controller.init(newsUI);
	}
}
