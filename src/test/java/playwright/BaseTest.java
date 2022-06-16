package playwright;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public abstract class BaseTest {

    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeTest
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
    }

    @BeforeMethod
    void beforeMethod() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    void afterMethod() {
        context.close();
    }

    @AfterTest
    void closeBrowser() {
        browser.close();
    }
}
