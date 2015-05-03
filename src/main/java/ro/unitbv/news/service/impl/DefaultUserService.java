package ro.unitbv.news.service.impl;

import ro.unitbv.news.model.Response;
import ro.unitbv.news.model.User;
import ro.unitbv.news.repository.UserRepository;
import ro.unitbv.news.service.UserService;
import ro.unitbv.news.validator.UserValidator;
import ro.unitbv.news.validator.ValidationResult;

/**
 * Default implementation of {@link ro.unitbv.news.service.UserService}
 *
 * @author Rares Smeu
 */
public class DefaultUserService implements UserService {

	private UserValidator validator = new UserValidator();

	private UserRepository repository;

  @Override
  public Response<Long> create(User user) {
		ValidationResult result = validator.validate(user);
		if (result.hasErrors()){
			 return new Response<>(result.getErrors());
		}
		long id = repository.create(user);
    return new Response<>(id);
  }

  @Override
  public Response<User> get(long id) {
    return null;
  }

  @Override
  public boolean authenticate(String username, String password) {
    return false;
  }
}
