package ro.unitbv.news.component;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import ro.unitbv.news.controller.AbstractController;

/**
 * Abstract component that serves as guideline for all components.
 *
 * @author Rares Smeu
 */
public abstract class AbstractComponent {

	private static final Logger log = LoggerFactory.getLogger(AbstractComponent.class);

	protected AbstractController mainController;

	protected FXMLLoader loader;

	protected Pane component;

	protected AbstractComponent(AbstractController mainController) {
		this.mainController = mainController;
	}

	protected <T extends AbstractController> T loadComponent(Component component) {
		loader = new FXMLLoader(this.getClass().getClassLoader().getResource(component.getPath()));
		try {
			this.component = loader.load();
		}
		catch (IOException e) {
			log.error("Couldn't process the {} file", component.getPath(), e);
		}
		return loader.getController();
	}

	public Pane getComponent() {
		return component;
	}
}
