package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.News;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;

/**
 * In memory implementation for {@link ro.unitbv.news.repository.NewsRepository}.
 * Should not be used in multi-threading contexts.
 *
 * @author Teodora Tanase
 */
public class InMemoryNewsRepository implements NewsRepository {

	private List<News> newsList = new ArrayList<>();

	@Override
	public long create(News news) {
		newsList.add(news);
		return newsList.size() - 1;
	}

	@Override
	public News get(long id) {
		if (id < 0 || id >= newsList.size()) {
			throw new InvalidIdException();
		}
		return newsList.get((int) id);
	}

	@Override
	public List<News> getAllForOwner(long ownerId) {
		List<News> selectedNews = new ArrayList<>();
		for (News news : newsList) {
			if (news.getOwnerId() == ownerId) {
				selectedNews.add(news);
			}
		}
		return selectedNews;
	}

	@Override
	public boolean delete(long id) {
		if (id < 0 || id >= newsList.size()) {
			throw new InvalidIdException();
		}
		newsList.set((int) id, null);
		return true;
	}
}