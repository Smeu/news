package ro.unitbv.news.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.unitbv.news.model.*;
import ro.unitbv.news.model.Error;
import ro.unitbv.news.repository.exception.InternalErrorException;
import ro.unitbv.news.repository.exception.InvalidIdException;

/**
 * Invocation handler for services.
 *
 * @author Teodora Tanase
 */
public class ServiceInvocationHandler implements InvocationHandler {

	private Object implementation;

	public ServiceInvocationHandler(Object implementation) {
		this.implementation = implementation;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Logger logger = LoggerFactory.getLogger(implementation.getClass());
		List<FieldError> errors = new ArrayList<>();
		try {
			return method.invoke(implementation, args);
		}
		catch (InvalidIdException e) {
			logger.info("Request made for invalid id", e);
			errors.add(new FieldError(proxy.getClass().getSimpleName(), Error.INVALID_ID));
		}
		catch (InternalErrorException | HibernateException e) {
			logger.error("Internal error in " + proxy.getClass().getSimpleName() + " " + method.getName(), e);
			errors.add(new FieldError(proxy.getClass().getSimpleName(), Error.FAILED_REQUEST));
		}
		return new Response<>(errors);
	}
}
