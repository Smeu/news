package ro.unitbv.news.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.Feed;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.FeedService;

/**
 * Controller for the Add Feed Page.
 *
 * @author Teodora Tanase
 */
public class AddFeedPageController extends AbstractController {

	private static final String HIDDEN = "hidden";
	private static final String ERROR_BORDER = "errorBorder";
	private static final String URL = "url";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";

	private static final Logger log = LoggerFactory.getLogger(AddFeedPageController.class);

	private FeedService feedService = ServiceFactory.getInstance().getFeedService();

	@FXML
	private TextField urlInput;
	@FXML
	private TextField nameInput;
	@FXML
	private TextField descriptionInput;
	@FXML
	private Text urlError;
	@FXML
	private Text nameError;
	@FXML
	private Text descriptionError;
	@FXML
	private Button addFeed;
	@FXML
	private Button cancelButton;

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public void addFeed() {
		resetErrors();
		Feed feed = new Feed();
		feed.setUrl(urlInput.getText());
		feed.setName(nameInput.getText());
		feed.setDescription(descriptionInput.getText());
		feed.setOwnerId(user.getId());
		Response<Long> response = feedService.create(feed);
		if (response.hasErrors()) {
			showErrors(response.getErrors());
			return;
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Feed addition");
		alert.setHeaderText("Feed added successfully");
		alert.showAndWait();
		HomePageController controller = redirectTo(Page.HOME_PAGE);
		controller.init(user);
	}

	private void resetErrors() {
		addClassIfMissing(urlError, HIDDEN);
		addClassIfMissing(nameError, HIDDEN);
		addClassIfMissing(descriptionError, HIDDEN);
		removeClassIfExists(urlInput, ERROR_BORDER);
		removeClassIfExists(nameInput, ERROR_BORDER);
		removeClassIfExists(descriptionInput, ERROR_BORDER);
	}

	private void showErrors(List<FieldError> errors) {
		for (FieldError error : errors) {
			switch (error.getFieldName()) {
				case URL:
					showErrorForField(urlInput, urlError, error);
					break;
				case NAME:
					showErrorForField(nameInput, nameError, error);
					break;
				case DESCRIPTION:
					showErrorForField(descriptionInput, descriptionError, error);
					break;
			}
		}
	}

	public void cancel() {
		HomePageController homePageController = redirectTo(Page.HOME_PAGE);
		homePageController.init(user);
	}
}
