package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.Category;
import ro.unitbv.news.repository.CategoryRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * In memory implementation for {@link ro.unitbv.news.repository.CategoryRepository}.
 * Not to be used in multi-threading contexts.
 *
 * @author Teodora Tanase
 */
public class InMemoryCategoryRepository implements CategoryRepository {

	private List<Category> categories = new ArrayList<>();

	@Override
	public long create(Category category) {
		categories.add(category);
		return categories.size() - 1;
	}

	@Override
	public Category get(long id) {
		if (id < 0 || id >= categories.size()) {
			throw new InvalidIdException();
		}
		return categories.get((int) id);
	}

	@Override
	public List<Category> getAll() {
		return categories;
	}

	@Override
	public void addKeyword(Category category, String keyword) {
		throw new NotImplementedException();
	}
}
