package ro.unitbv.news.repository.exception;

/**
 * Exception for an invalid id.
 *
 * @author Teodora Tanase
 */
public class InvalidIdException extends RuntimeException {

	public InvalidIdException() {
		super();
	}

	public InvalidIdException(String message) {
		super(message);
	}
}
