package ro.unitbv.news.service.impl;

import java.util.ArrayList;
import java.util.List;

import ro.unitbv.news.model.*;
import ro.unitbv.news.model.Error;
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
}
