package ro.unitbv.news.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Model for a response.
 *
 * @author Rares Smeu
 */
public class Response<T> {

	private T response;

	private List<FieldError> errors;

	public Response() {
		errors = new LinkedList<>();
	}

	public Response(List<FieldError> errors) {
		this.errors = errors;
	}

	public Response(T response) {
		this.response = response;
	}

	/**
	 * Checks if the response has any errors.
	 *
	 * @return true if it has errors, false otherwise.
	 */
	public boolean hasErrors() {
		return errors != null && errors.size() != 0;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public List<FieldError> getErrors() {
		return errors;
	}

	public void setErrors(List<FieldError> errors) {
		this.errors = errors;
	}
}
