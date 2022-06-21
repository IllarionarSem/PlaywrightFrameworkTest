package playwright.uitest;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeTest
    public void beforeTest() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setSlowMo(1000).setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterTest
    public void afterTest() {
        playwright.close();
    }
}
