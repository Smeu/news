package ro.unitbv.news.service.classifier;

import java.util.List;

import ro.unitbv.news.model.Category;
import ro.unitbv.news.model.News;
import ro.unitbv.news.service.CategoryService;

/**
 * Class that is used to classify model objects.
 *
 * @author Teodora Tanase
 */
public class Classifier {

	private static final char SPACE = ' ';

	private CategoryService categoryService;

	public Classifier(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * Assigns appropriate categories to the given piece of news.
	 *
	 * @param news news to assign categories to.
	 */
	public void assignCategories(News news) {
		List<Category> categories = categoryService.getAll().getResponse();
		for (Category category : categories) {
			for (String keyword : category.getKeywords()) {
				if (containsWord(news.getContent(), keyword) || containsWord(news.getTitle(), keyword)) {
					news.addCategory(category);
					break;
				}
			}
		}
	}

	private boolean containsWord(String content, String word) {
		for (int index = 0; index < content.length(); index++) {
			int foundMatch = content.toLowerCase().indexOf(word.toLowerCase(), index);
			if (foundMatch == -1) {
				continue;
			}
			if (foundMatch == 0 || content.charAt(foundMatch - 1) == SPACE) {
				return true;
			}
		}
		return false;
	}
}
