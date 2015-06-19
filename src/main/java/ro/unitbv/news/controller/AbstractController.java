package ro.unitbv.news.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ro.unitbv.news.model.FieldError;

/**
 * Abstract Controller that serves as common guideline for all page controllers.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public abstract class AbstractController {

	private static final String HIDDEN = "hidden";
	private static final String ERROR_BORDER = "errorBorder";

	private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

	private Stage primaryStage;

	protected AbstractController mainController;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	protected <T extends AbstractController> T redirectTo(Page page) {
		if (mainController != null) {
			return mainController.redirectTo(page);
		}
		FXMLLoader loader = new FXMLLoader(AbstractController.class.getClassLoader().getResource(page.getPagePath()));
		Pane myPane;
		try {
			myPane = loader.load();
		}
		catch (IOException e) {
			log.error("Couldn't process the file: {} ", page.getPagePath(), e);
			return null;
		}

		T controller = loader.getController();
		controller.setPrimaryStage(primaryStage);

		Scene scene = new Scene(myPane);
		primaryStage.setTitle(page.getTitle());
		primaryStage.setScene(scene);

		return controller;
	}

	protected void showErrorForField(TextField input, Text errorArea, FieldError error) {
		addClassIfMissing(input, ERROR_BORDER);
		removeClassIfExists(errorArea, HIDDEN);
		switch (error.getError()) {
			case FIELD_IS_MANDATORY:
				errorArea.setText("The " + error.getFieldName() + " is mandatory");
				break;
			case MIN_LENGTH_NOT_REACHED:
				errorArea.setText("The " + error.getFieldName() + " must have at least " +
						error.getConstraint().getMinLength() + " characters");
				break;
			case MAX_LENGTH_EXCEEDED:
				errorArea.setText("The " + error.getFieldName() + " must have maximum " +
						error.getConstraint().getMaxLength() + " characters");
				break;
			case URL_NOT_AVAILABLE:
				errorArea.setText("This url is not available");
				break;
			case ILLEGAL_CONTENT:
				errorArea.setText("Invalid characters");
				break;
			case FAILED_REQUEST:
				errorArea.setText("Request could not be completed");
				break;
		}
	}

	protected void removeClassIfExists(Node node, String cssClass) {
		if (node.getStyleClass().contains(cssClass)) {
			node.getStyleClass().remove(cssClass);
		}
	}

	protected void addClassIfMissing(Node node, String cssClass) {
		if (!node.getStyleClass().contains(cssClass)) {
			node.getStyleClass().add(cssClass);
		}
	}

	public void setMainController(AbstractController mainController) {
		this.mainController = mainController;
	}
}
