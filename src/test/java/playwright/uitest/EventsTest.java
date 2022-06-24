package playwright.uitest;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public class EventsTest extends BaseTest {

    private static final String URL = "https://wikipedia.org";

    @Test
    public void eventsTest() {

        // The callback lambda defines scope of the code that is expected to trigger request
        Request request = page.waitForRequest("**/*.png", () -> page.navigate(URL));
        //wait for popup
        Page popup = page.waitForPopup(() -> page.evaluate("window.open()"));
        popup.navigate(URL);
        System.out.println();
    }

    @Test
    public void eventListener() {
        //add listeners
        Consumer<Request> onRequest = request -> System.out.println("Request sent: " + request.url());
        Consumer<Request> onRequestFinish = request -> System.out.println("Request finished: " + request.url());
        page.onRequest(onRequest);
        page.onRequestFinished(onRequestFinish);
        page.navigate(URL);

        //remove listeners
        page.offRequestFinished(onRequestFinish);
        page.navigate(URL);
    }
}
