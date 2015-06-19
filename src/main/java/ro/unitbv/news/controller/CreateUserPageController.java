package ro.unitbv.news.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ro.unitbv.news.factory.ServiceFactory;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.service.UserService;
import ro.unitbv.news.util.PasswordHash;

/**
 * Controller for Create User Page.
 *
 * @author Rares Smeu
 */
public class CreateUserPageController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(CreateUserPageController.class);

	private static final String HIDDEN = "hidden";
	private static final String ERROR_BORDER = "errorBorder";

	private UserService userService = ServiceFactory.getInstance().getUserService();

	@FXML
	private PasswordField confirmPasswordInput;
	@FXML
	private Text usernameError;
	@FXML
	private Text passwordError;
	@FXML
	private Button createUser;
	@FXML
	private TextField usernameInput;
	@FXML
	private PasswordField passwordInput;
	@FXML
	private Button backToLogin;


	public void backToLogin() {
		redirectTo(Page.LOGIN_PAGE);
	}

	public void createUser() {
		resetErrors();
		if (!passwordCheckPassed()) {
			return;
		}
		String hashedPassword = null;
		try {
			hashedPassword = PasswordHash.createHash(passwordInput.getText());
		}
		catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("Couldn't generate the password hash", e);
			setPasswordError("Internal error, please try another password or contact an administrator");
			return;
		}
		User user = new User();
		user.setUsername(usernameInput.getText());
		user.setPassword(hashedPassword);

		Response<Long> response = userService.create(user);
		if (response.hasErrors()) {
			showErrors(response.getErrors());
			return;
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("User creation");
		alert.setHeaderText("User created");
		alert.setContentText("You can now login in the application!");
		alert.showAndWait();

		redirectTo(Page.LOGIN_PAGE);
	}

	private void showErrors(List<FieldError> errors) {
		for (FieldError error : errors) {
			switch (error.getFieldName()) {
				case "username":
					showErrorForField(usernameInput, usernameError, error);
					break;
				case "password":
					showErrorForField(passwordInput, passwordError, error);
					break;
				case "user":
					showErrorForField(usernameInput, usernameError, error);
					break;
			}
		}
	}

	private boolean passwordCheckPassed() {
		if (passwordInput.getText().length() < 8) {
			setPasswordError("Password must have at least \n 8 characters");
			return false;
		}

		if (passwordInput.getText().compareTo(confirmPasswordInput.getText()) != 0) {
			setPasswordError("Passwords don't match");
			return false;
		}
		return true;
	}

	private void setPasswordError(String errorMessage) {
		passwordError.setText(errorMessage);
		removeClassIfExists(passwordError, HIDDEN);
		addClassIfMissing(confirmPasswordInput, ERROR_BORDER);
		addClassIfMissing(passwordInput, ERROR_BORDER);
	}

	private void resetErrors() {
		addClassIfMissing(usernameError, HIDDEN);
		addClassIfMissing(passwordError, HIDDEN);
		removeClassIfExists(usernameInput, ERROR_BORDER);
		removeClassIfExists(passwordInput, ERROR_BORDER);
		removeClassIfExists(confirmPasswordInput, ERROR_BORDER);
	}
}
