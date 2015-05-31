package ro.unitbv.news.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Abstract Controller that
 *
 * @author Rares Smeu
 */
public abstract class AbstractController {

	private static final Logger log = LoggerFactory.getLogger(AbstractController.class);

	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	protected void redirectTo(Page page) {
		FXMLLoader loader = new FXMLLoader(AbstractController.class.getClassLoader().getResource(page.getPagePath()));
		Pane myPane;
		try {
			myPane = loader.load();
		}
		catch (IOException e) {
			log.error("Couldn't proccess the file: {} ", page.getPagePath(), e);
			return;
		}

		AbstractController controller = loader.getController();
		controller.setPrimaryStage(primaryStage);

		Scene scene = new Scene(myPane);
		primaryStage.setTitle(page.getTitle());
		primaryStage.setScene(scene);
	}
}
