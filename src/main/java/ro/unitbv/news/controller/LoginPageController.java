package ro.unitbv.news.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.UserService;

/**
 * Controller for the login page.
 *
 * @author Rares Smeu
 */
public class LoginPageController extends AbstractController {

	private UserService userService = ServiceFactory.getInstance().getUserService();

	@FXML
	private Button loginButton;
	@FXML
	public Label errorLabel;
	@FXML
	private TextField usernameInput;
	@FXML
	private PasswordField passwordInput;
	@FXML
	private Button createUserButton;
	@FXML
	private VBox test;

	/**
	 * Login a user
	 *
	 * @param event the button pressed event
	 */
	public void loginUser(Event event) throws Exception {
		User user = userService.authenticate(usernameInput.getText(), passwordInput.getText());
		if (user == null) {
			passwordInput.setText("");
			errorLabel.setText("Invalid Credentials");
			errorLabel.setVisible(true);
			return;
		}
		HomePageController controller = redirectTo(Page.HOME_PAGE);
		controller.init();
	}

	/**
	 * Redirects to the create user page.
	 */
	public void createUser() {
		redirectTo(Page.CREATE_USER_PAGE);
	}
}
