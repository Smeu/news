package ro.unitbv.news.component;

/**
 * Enum for maintaining the component sources.
 *
 * @author Rares Smeu
 */
public enum Component {

	NEWS_CONTAINER("components/newscontainer.fxml"),

	NEWS_COMPONENT("components/news.fxml");

	private String path;


	Component(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

}

