package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ro.unitbv.news.model.News;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.NewsRepository;
import ro.unitbv.news.service.NewsService;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Teodora Tanase
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultNewsServiceTest {

	private static final long OWNER_ID = 1;

	private NewsService service;

	@Mock
	private NewsRepository repository;

	@Before
	public void init() {
		service = new DefaultNewsService(repository);
	}


	@Test
	public void testGetNewsNullUser() throws Exception {
		Response<List<News>> response = service.getAll(null);

		assertThat(response.hasErrors(), is(true));
		assertThat(response.getErrors().size(), is(1));
	}

	@Test
	public void testGetNewsCorrectUser() throws Exception {
		List<News> newsList = new ArrayList<>();
		when(repository.getAllForOwner(OWNER_ID)).thenReturn(newsList);

		User user = new User();
		user.setId(OWNER_ID);
		Response<List<News>> response = service.getAll(user);

		assertThat(response.hasErrors(), is(false));
		assertThat(response.getResponse(), is(newsList));
	}
}
