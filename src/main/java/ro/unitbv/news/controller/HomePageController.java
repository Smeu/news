package ro.unitbv.news.controller;

import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import ro.unitbv.news.component.NewsContainer;
import ro.unitbv.news.model.News;

/**
 * Controller for the home page.
 *
 * @author Rares Smeu
 */
public class HomePageController extends AbstractController {

	@FXML
	private VBox homePageContainer;


	public void init() {
		homePageContainer.setAlignment(Pos.TOP_CENTER);
		NewsContainer container = new NewsContainer();
		container.setNews(Arrays.asList(new News(), new News(), new News(), new News(), new News(), new News(), new News()));
		homePageContainer.getChildren().add(container.getComponent());
	}

}
