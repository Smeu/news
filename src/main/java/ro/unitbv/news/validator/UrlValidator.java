package ro.unitbv.news.validator;

import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.unitbv.news.model.Error;
import ro.unitbv.news.model.FieldError;

/**
 * Validator for urls.
 *
 * @author Rares Smeu
 */
public class UrlValidator {

	private static final Logger log = LoggerFactory.getLogger(UrlValidator.class);

	private static final String URL = "url";


	/**
	 * Validates if the given url exists or not.
	 *
	 * @param url url to be checked.
	 * @return false if urls doesn't exist or there is no internet connection.
	 */
	public ValidationResult validate(String url) {
		ValidationResult result = new ValidationResult();
		if (url == null) {
			result.addError(new FieldError(URL, Error.FIELD_IS_MANDATORY));
			return result;
		}
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con =
					(HttpURLConnection) new URL(url).openConnection();
			con.setRequestMethod("HEAD");
			if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				result.addError(new FieldError(URL, Error.URL_NOT_AVAILABLE));
			}
		}
		catch (Exception e) {
			log.error("Couldn't check the validity of url " + url, e);
			result.addError(new FieldError(URL, Error.URL_NOT_AVAILABLE));
		}
		return result;
	}

}
