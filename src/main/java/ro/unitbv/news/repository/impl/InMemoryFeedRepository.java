package ro.unitbv.news.repository.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.Feed;
import ro.unitbv.news.repository.FeedRepository;

/**
 * In memory implementation for {@link ro.unitbv.news.repository.FeedRepository}
 * Do not use when multi threading is involved.
 *
 * @author Rares Smeu
 */
public class InMemoryFeedRepository implements FeedRepository {

	private List<Feed> feeds = new ArrayList<>();

	@Override
	public long create(Feed feed) {
		feeds.add(feed);
		return feeds.size() - 1;
	}
}
