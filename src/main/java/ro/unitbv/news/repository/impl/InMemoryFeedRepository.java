package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.Feed;
import ro.unitbv.news.repository.FeedRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;

/**
 * In memory implementation for {@link ro.unitbv.news.repository.FeedRepository}.
 * Do not use when multi threading is involved.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class InMemoryFeedRepository implements FeedRepository {

	private List<Feed> feeds = new ArrayList<>();

	@Override
	public long create(Feed feed) {
		feeds.add(feed);
		return feeds.size() - 1;
	}

	@Override
	public List<Feed> getAllForOwner(long ownerId) {
		List<Feed> selectedFeeds = new ArrayList<>();
		for (Feed feed : feeds) {
			if (feed.getOwnerId() == ownerId) {
				selectedFeeds.add(feed);
			}
		}
		return selectedFeeds;
	}

	@Override
	public Feed get(long id) {
		if (id < 0 || id >= feeds.size()) {
			throw new InvalidIdException();
		}
		return feeds.get((int) id);
	}
}
