package ro.unitbv.news.controller;

import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 * Controller for the news container
 *
 * @author Rares Smeu
 */
public class NewsContainerController extends AbstractController {

	private final static int NEWS_PER_UPDATE = 3;

	@FXML
	public ScrollPane scrollPane;

	@FXML
	private VBox newsContainer;

	private List<Pane> news;

	private int lastNewsIndex;

	public void init(List<Pane> news) {
		this.news = news;
		lastNewsIndex = 0;
		scrollPane.setMinHeight(Screen.getPrimary().getBounds().getHeight() - 60);
		scrollPane.addEventFilter(ScrollEvent.SCROLL, this::scroll);
		scrollPane.addEventFilter(MouseEvent.MOUSE_RELEASED, this::scroll);
		updatePageNews();
	}

	public void updatePageNews() {
		newsContainer.getChildren().clear();
		for (int i = 0; i < news.size() && i < 10; ++i) {
			lastNewsIndex = i + 1;
			newsContainer.getChildren().add(news.get(i));
		}
	}

	public void scroll(Event event) {
		if (scrollPane.getVvalue() > 0.75) {
			int newIndex = lastNewsIndex + NEWS_PER_UPDATE;
			if (newIndex > news.size()) {
				newIndex = news.size();
			}
			newsContainer.getChildren().addAll(news.subList(lastNewsIndex, newIndex));
			lastNewsIndex = newIndex;
		}
	}
}
