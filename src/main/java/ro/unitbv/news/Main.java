package ro.unitbv.news;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rares Smeu
 */
public class Main {

	private final static Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		log.trace("trace");
		log.debug("debug");
		log.info("info");
		log.error("error");
	}
}
