package ro.unitbv.news.component;

/**
 * A news component
 *
 * @author Rares Smeu
 */
public class NewsComponent extends AbstractComponent {


	/**
	 * @param news the news to be displayed.
	 * @param user the logged user.
	 */
	public NewsComponent() {
		loadComponent(Component.NEWS_COMPONENT);
	}

}


