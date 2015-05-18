package ro.unitbv.news.model;

/**
 * Enum for the possible errors.
 *
 * @author Rares Smeu
 */
public enum Error {
  /**
   * Maximum length is exceeded.
   */
  MAX_LENGTH_EXCEEDED,

  /**
   *  Minimum length is not reached.
   */
  MIN_LENGTH_NOT_REACHED,

	/**
	 *  Field is mandatory and it wasn't send.
	 */
	FIELD_IS_MANDATORY,

	/**
	 * The given url doesn't exists.
	 */
	URL_NOT_AVAILABLE
}
