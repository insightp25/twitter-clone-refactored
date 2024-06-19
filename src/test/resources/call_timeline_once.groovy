import static net.grinder.script.Grinder.grinder
import static org.junit.Assert.*
import static org.hamcrest.Matchers.*
import net.grinder.script.GTest
import net.grinder.script.Grinder
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
// import static net.grinder.util.GrinderUtils.* // You can use this if you're using nGrinder after 3.2.3
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

import org.ngrinder.http.HTTPRequest
import org.ngrinder.http.HTTPRequestControl
import org.ngrinder.http.HTTPResponse
import org.ngrinder.http.cookie.Cookie
import org.ngrinder.http.cookie.CookieManager

import groovy.json.JsonSlurper

/**
 * A simple example using the HTTP plugin that shows the retrieval of a single page via HTTP.
 *
 * This script is automatically generated by ngrinder.
 *
 * @author admin
 */
@RunWith(GrinderRunner)
class TestRunner {

    public static GTest test
    public static HTTPRequest request
    public static Map<String, String> headers = [:]
    public static Map<String, Object> params = [:]
    //public static String userBody = "{\n    \"email\" : \"haro73786@gmail.com\",\n    \"password\" : \"password\"\n}"
    public static List<Cookie> cookies = []
    def jsonSlurper = new JsonSlurper()

    @BeforeProcess
    public static void beforeProcess() {
        HTTPRequestControl.setConnectionTimeout(300000)
        test = new GTest(1, "223.130.138.96")
        request = new HTTPRequest()

        // Set header data
        headers.put("Content-Type", "application/json")
        grinder.logger.info("before process.")

        String emailPrefix = "haro";
        String emailDomain = "@gmail.com";

        int lowerBound = 1;
        int upperBound = 2200;

        Random random = new Random();
        int randomNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;

        String email = emailPrefix + randomNumber + emailDomain;
        String password = "password";
        String userBody = "{\n    \"email\" : \"" + email + "\",\n    \"password\" : \"" + password + "\"\n}";

        HTTPResponse response = request.POST("http://223.130.138.96:8080/users/signin", userBody.getBytes())

        cookies = CookieManager.getCookies()

    }

    @BeforeThread
    public void beforeThread() {
        test.record(this, "test")
        grinder.statistics.delayReports = true
        grinder.logger.info("before thread.")
    }

    @Before
    public void before() {
        request.setHeaders(headers)
        CookieManager.addCookies(cookies)
        grinder.logger.info("before. init headers and cookies")
    }

    @Test
    public void test() {
        HTTPResponse response = request.GET("http://223.130.138.96:8080/tweets/timeline",)
    }
}
