package ro.unitbv.news.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;
import ro.unitbv.news.model.Response;
import ro.unitbv.news.repository.exception.InvalidIdException;

/**
 * Invocation handler for services.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public class ServiceInvocationHandler implements InvocationHandler {

	private Object implementation;

	public ServiceInvocationHandler(Object implementation) {
		this.implementation = implementation;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Logger log = LoggerFactory.getLogger(implementation.getClass());
		log.info("Method {} called with parameters {}", method.getName(), args);
		System.out.println(method.getReturnType());
		List<FieldError> errors = new ArrayList<>();
		try {
			Object response = method.invoke(implementation, args);
			log.info("Method {} returned {}", method.getName(), response);
			return response;
		}
		catch (InvocationTargetException e) {
			if (e.getCause() instanceof InvalidIdException) {
				log.info("Request made for invalid id");
				errors.add(new FieldError(proxy.getClass().getSimpleName(), Error.INVALID_ID));
			}
			else {
				log.error("Internal error in " + proxy.getClass().getSimpleName() + " " + method.getName(), e);
				errors.add(new FieldError(proxy.getClass().getSimpleName(), Error.FAILED_REQUEST));
			}
		}
		if (method.getReturnType() == Response.class) {
			return new Response<>(errors);
		}
		return null;
	}
}
