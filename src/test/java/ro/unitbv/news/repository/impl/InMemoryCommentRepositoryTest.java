package ro.unitbv.news.repository.impl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ro.unitbv.news.model.Comment;
import ro.unitbv.news.repository.CommentRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Teodora Tanase
 */
public class InMemoryCommentRepositoryTest {

	private static final long INVALID_ID = -1;
	private static final long FIRST_ID = 0;
	private static final long NEWS_ID = 1;

	private CommentRepository repository;

	@Before
	public void init() {
		repository = new InMemoryCommentRepository();
	}

	@Test(expected = InvalidIdException.class)
	public void testGetInvalidId() throws Exception {
		repository.get(INVALID_ID);
	}

	@Test
	public void testGetValidId() throws Exception {
		Comment addedComment = new Comment();
		repository.create(addedComment);

		Comment retrievedComment = repository.get(FIRST_ID);
		assertThat(retrievedComment, is(addedComment));
	}

	@Test
	public void testGetAllForUser() throws Exception {
		Comment addedComment = new Comment();
		addedComment.setNewsId(NEWS_ID);
		repository.create(addedComment);

		List<Comment> comments = repository.getAllForNews(NEWS_ID);
		assertEquals(NEWS_ID, comments.get((int) FIRST_ID).getNewsId());
	}
}
