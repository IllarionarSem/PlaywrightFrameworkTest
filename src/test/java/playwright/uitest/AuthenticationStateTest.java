package playwright.uitest;

import com.microsoft.playwright.*;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class AuthenticationStateTest {

    String email = "";
    String password = "";
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;
    String path = "src/test/resources/storage.json";

    @Test
    public void checkIsUserLoggedIn() {
        page.navigate("https://github.com");
        System.out.println();
    }

    @BeforeSuite
    public void beforeSuite() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setSlowMo(1000).setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.navigate("https://github.com/login");
        page.fill("input[name='login']", email);
        page.fill("input[name='password']", password);
        page.click("//input[@type='submit']");
        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("src/test/resources/storage.json")));
        browser.close();
    }

    @BeforeTest
    public void beforeTest() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setSlowMo(1000).setHeadless(false));
        context = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get(path)));
        page = context.newPage();
    }
}
