package ro.unitbv.news.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.UserService;

/**
 * Controller for the users page.
 *
 * @author Rares Smeu
 */
public class UsersController extends AbstractController {

	@FXML
	private VBox usersContainer;

	private UserService userService = ServiceFactory.getInstance().getUserService();

	private User loggedUser;

	public void init(User loggedUser, List<User> users) {
		this.loggedUser = loggedUser;
		addBackButton();
		List<User> followedUsers = userService.getFollowedUsers(loggedUser.getId()).getResponse();
		users.remove(loggedUser);
		for (User user : users) {
			HBox hBox = new HBox();
			hBox.setAlignment(Pos.TOP_CENTER);
			Label label = new Label(user.getUsername());
			label.setOnMouseClicked(event -> redirectToUserPage(user));
			label.setCursor(Cursor.HAND);
			Button button = new Button();
			if (followedUsers.contains(user)) {
				unFollowButton(button, user);
			}
			else {
				followButton(button, user);
			}
			hBox.getChildren().add(label);
			hBox.getChildren().add(button);
			usersContainer.getChildren().add(hBox);
		}
	}

	private void addBackButton() {
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.TOP_CENTER);
		hBox.setPadding(new Insets(200, 0, 100, 0));
		Button button = new Button("Homepage");
		button.setOnMouseClicked(event -> {
			HomePageController controller = redirectTo(Page.HOME_PAGE);
			controller.init(loggedUser);
		});
		hBox.getChildren().add(button);
		usersContainer.getChildren().add(hBox);
	}

	private void followButton(Button button, User user) {
		button.setText("Follow");
		button.setOnMouseClicked(event -> {
			userService.addFollowedUser(loggedUser.getId(), user);
			unFollowButton(button, user);
		});
	}

	private void unFollowButton(Button button, User user) {
		button.setText("Stop following");
		button.setOnMouseClicked(event -> {
			userService.deleteFollowedUser(loggedUser.getId(), user, loggedUser);
			followButton(button, user);
		});
	}

	private void redirectToUserPage(User user){
		if (!userService.getFollowedUsers(loggedUser.getId()).getResponse().contains(user)){
			return;
		}
		UserPageController userPageController = redirectTo(Page.USER_PAGE);
		userPageController.init(loggedUser, user);
	}

}
