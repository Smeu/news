package ro.unitbv.news.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.UserService;
import ro.unitbv.news.util.PasswordHash;

/**
 * Controller for the login page.
 *
 * @author Rares Smeu
 */
public class LoginPageController {

	private UserService userService = ServiceFactory.getInstance().getUserService();

	private Stage primaryStage;

	@FXML
	private Button loginButton;
	@FXML
	public Label errorLabel;
	@FXML
	private TextField usernameInput;
	@FXML
	private PasswordField passwordInput;


	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Login a user
	 *
	 * @param event the button pressed event
	 */
	public void loginUser(Event event) throws Exception {
		User user = userService.authenticate(usernameInput.getText(), PasswordHash.createHash(passwordInput.getText()));
		if (user == null){
			passwordInput.setText("");
			errorLabel.setText("Invalid Credentials");
			errorLabel.setVisible(true);
			return;
		}
		//TODO redirect to next page
	}
}
