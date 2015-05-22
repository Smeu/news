package ro.unitbv.news.parser.exception;

/**
 * Exception that is raised when it is impossible to keep parsing rss correctly.
 *
 * @author Teodora Tanase
 */
public class RssParsingException extends RuntimeException {

	public RssParsingException() {
		super();
	}

	public RssParsingException(String message) {
		super(message);
	}

	public RssParsingException(Exception exception) {
		super(exception);
	}
}
