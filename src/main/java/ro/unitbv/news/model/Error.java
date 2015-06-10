package ro.unitbv.news.model;

/**
 * Enum for the possible errors.
 *
 * @author Rares Smeu
 * @author Teodora Tanase
 */
public enum Error {
	/**
	 * Maximum length is exceeded.
	 */
	MAX_LENGTH_EXCEEDED,

	/**
	 * Minimum length is not reached.
	 */
	MIN_LENGTH_NOT_REACHED,

	/**
	 * Field is mandatory and it wasn't sent.
	 */
	FIELD_IS_MANDATORY,

	/**
	 * The given url doesn't exist.
	 */
	URL_NOT_AVAILABLE,

	/**
	 * The provided id is not valid.
	 */
	INVALID_ID,

	/**
	 * Field contains unwanted characters.
	 */
	ILLEGAL_CONTENT,

	/**
	 * Request could not be processed.
	 */
	FAILED_REQUEST
}
