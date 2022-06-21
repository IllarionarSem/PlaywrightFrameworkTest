import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ColorScheme;
import org.testng.annotations.Test;

import java.util.Arrays;

public class PlaywrightTest {

    @Test
    public void test() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1")
                .setViewportSize(375, 812)
                .setDeviceScaleFactor(3)
                .setIsMobile(true)
                .setHasTouch(true)
                .setPermissions(Arrays.asList("geolocation"))
                .setGeolocation(52.52, 13.39)
                .setColorScheme(ColorScheme.DARK)
                .setLocale("de-DE"));
        Page page = context.newPage();
    }

    @Test
    public void browserContext() {
        Playwright playwright = Playwright.create();
        BrowserType chrome = playwright.chromium();
        Browser browser = chrome.launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext userContext = browser.newContext();
        BrowserContext adminContext = browser.newContext();
        Page userPage = userContext.newPage();
        Page adminPage = adminContext.newPage();
        System.out.println();
    }
}
