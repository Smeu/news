package ro.unitbv.news.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Controller for the news container
 *
 * @author Rares Smeu
 */
public class NewsContainerController extends AbstractController {

	private static final int NEWS_PER_PAGE = 3;

	@FXML
	private Button previousButton;

	@FXML
	private Button nextButton;

	@FXML
	private VBox newsContainer;

	private List<Pane> news;

	private int currentPage;

	private int numberOfPages;


	public void init(List<Pane> news) {
		this.news = news;
		currentPage = 0;
		numberOfPages = news.size() / NEWS_PER_PAGE + ((news.size() % NEWS_PER_PAGE > 0) ? 1 : 0);
		updatePageNews();
		updateButtons();
	}

	public void updatePageNews() {
		newsContainer.getChildren().clear();
		int from = currentPage * NEWS_PER_PAGE;
		int to = from + NEWS_PER_PAGE;
		if (to > news.size()) {
			to = news.size();
		}
		newsContainer.getChildren().addAll(news.subList(from, to));
	}

	public void updateButtons() {
		previousButton.setVisible(true);
		nextButton.setVisible(true);
		if (currentPage == 0) {
			previousButton.setVisible(false);
		}
		if (currentPage == numberOfPages - 1) {
			nextButton.setVisible(false);
		}
	}

	public void nextPage() {
		currentPage++;
		updatePageNews();
		updateButtons();
	}

	public void previousPage() {
		currentPage--;
		updatePageNews();
		updateButtons();
	}
}
