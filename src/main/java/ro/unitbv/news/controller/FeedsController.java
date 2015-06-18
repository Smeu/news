package ro.unitbv.news.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.FeedService;

/**
 * Controller for the feeds page.
 *
 * @author Teodora Tanase
 */
public class FeedsController extends AbstractController {

	@FXML
	private VBox feedsContainer;

	private User user;

	private FeedService feedService;

	public void init(User user) {
		this.user = user;
		feedsContainer.setSpacing(3);
		feedService = ServiceFactory.getInstance().getFeedService();
		List<Feed> feeds = feedService.getAll(user).getResponse();
		for (Feed feed : feeds) {
			Button deleteButton = new Button("Delete Feed");
			deleteButton.setOnMouseClicked(event -> {
				feedService.delete(feed.getId(), user);
				FeedsController feedsController = redirectTo(Page.FEEDS_PAGE);
				feedsController.init(user);
			});
			VBox feedContent = new VBox(5);
			feedContent.getChildren().add(new Label(feed.getName()));
			feedContent.getChildren().add(new Label(feed.getUrl()));
			feedContent.getChildren().add(deleteButton);
			feedContent.getChildren().add(new Separator(Orientation.HORIZONTAL));
			feedsContainer.getChildren().add(feedContent);
		}
	}

	public void redirectToHomepage() {
		HomePageController homePageController = redirectTo(Page.HOME_PAGE);
		homePageController.init(user);
	}
}
