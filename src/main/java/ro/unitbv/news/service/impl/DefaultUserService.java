package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.*;
import ro.unitbv.news.model.Error;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.service.UserService;
import ro.unitbv.news.validator.StringFieldValidator;
import ro.unitbv.news.validator.UserValidator;
import ro.unitbv.news.validator.ValidationResult;

/**
 * Default implementation of {@link ro.unitbv.news.service.UserService}.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class DefaultUserService implements UserService {

	private static final String PERFORMER = "performer";

	private UserValidator validator;
	private UserRepository repository;

	public DefaultUserService(UserRepository repository, UserValidator validator) {
		this.repository = repository;
		this.validator = validator;
	}

	@Override
	public Response<Long> create(User user) {
		ValidationResult result = validator.validate(user);
		if (result.hasErrors()) {
			return new Response<>(result.getErrors());
		}
		long id = repository.create(user);
		return new Response<>(id);
	}

	@Override
	public Response<User> get(long id) {
		User user = repository.get(id);
		return new Response<>(user);
	}

	@Override
	public Response<User> authenticate(String username, String password) {
		User user = repository.authenticate(username, password);
		return new Response<>(user);
	}

	@Override
	public Response<Boolean> addFollowedUser(long id, User followedUser) {
		boolean result = repository.addFollowedUser(id, followedUser);
		return new Response<>(result);
	}

	@Override
	public Response<List<User>> getFollowedUsers(long id) {
		List<User> followedUsers = repository.getFollowedUsers(id);
		return new Response<>(followedUsers);
	}

	@Override
	public Response<List<User>> getAll() {
		Response<List<User>> response = new Response<>();
		response.setResponse(repository.getAll());
		return response;
	}

	@Override
	public Response<Boolean> delete(long id, User performer) {
		if (!performer.getType().equals(UserType.ADMIN)) {
			List<FieldError> errorList = new ArrayList<>();
			errorList.add(new FieldError(PERFORMER, Error.FAILED_REQUEST));
			return new Response<>(errorList);
		}
		return new Response<>(repository.delete(id));
	}
}
