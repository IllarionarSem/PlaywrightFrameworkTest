package playwright.uitest;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class ScreenshotTest extends BaseTest {

    private static final String URL = "https://en.wikipedia.org/wiki/JPEG";

    @Test
    public void fullPageScreenshot() {
        page.navigate(URL);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("screenshot/full_page_screenshot.png"))
                .setFullPage(true)); // save page screenshot
        byte[] buffer = page.screenshot(); // get screenshot as buffer
    }

    @Test
    public void elementScreenshot() {
        page.navigate(URL);
        page.locator("//caption[text()='JPEG']/..//img")
                .screenshot(new Locator.ScreenshotOptions()
                        .setPath(Paths.get("screenshot/element_screenshot.png")));
    }
}
