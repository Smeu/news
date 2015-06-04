package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.repository.exception.InvalidIdException;
import ro.unitbv.news.service.UserService;
import ro.unitbv.news.validator.UserValidator;
import ro.unitbv.news.validator.ValidationResult;

/**
 * Default implementation of {@link ro.unitbv.news.service.UserService}.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class DefaultUserService implements UserService {

	private UserValidator validator;
	private UserRepository repository;

	private static final String ID = "id";

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
		try {
			User user = repository.get(id);
			return new Response<>(user);
		}
		catch (InvalidIdException e) {
			List<FieldError> errorList = new ArrayList<>();
			errorList.add(new FieldError(ID, Error.INVALID_ID));
			return new Response<>(errorList);
		}
	}

	@Override
	public User authenticate(String username, String password) {
		return repository.authenticate(username, password);
	}

	@Override
	public Response<Boolean> addFollowedUser(long id, User followedUser) {
		try {
			boolean result = repository.addFollowedUser(id, followedUser);
			return new Response<>(result);
		}
		catch (InvalidIdException e) {
			List<FieldError> errors = new ArrayList<>();
			errors.add(new FieldError(ID, Error.INVALID_ID));
			return new Response<>(errors);
		}
	}

	@Override
	public Response<List<User>> getFollowedUsers(long id) {
		try {
			List<User> followedUsers = repository.getFollowedUsers(id);
			return new Response<>(followedUsers);
		}
		catch (InvalidIdException e) {
			List<FieldError> errors = new ArrayList<>();
			errors.add(new FieldError(ID, Error.INVALID_ID));
			return new Response<>(errors);
		}
	}

	@Override
	public Response<List<User>> getAll() {
		Response<List<User>> response = new Response<>();
		response.setResponse(repository.getAll());
		return response;
	}
}
