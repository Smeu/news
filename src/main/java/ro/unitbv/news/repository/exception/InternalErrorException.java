package ro.unitbv.news.repository.exception;

/**
 * Exception for a database internal error.
 *
 * @author Teodora Tanase
 */
public class InternalErrorException extends RuntimeException {

	public InternalErrorException() {
		super();
	}

	public InternalErrorException(String message) {
		super(message);
	}
}
