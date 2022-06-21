package playwright.uitest;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ColorScheme;
import com.microsoft.playwright.options.Geolocation;
import com.microsoft.playwright.options.Media;
import org.testng.annotations.Test;

import java.util.List;

public class EmulationTest extends BaseTest {

    @Test
    public void emulation() {

        context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("My user Agent") // share user agent
                .setViewportSize(1280, 1024) // set view port size
                .setDeviceScaleFactor(2) // set device scale factor
                .setLocale("de-DE") // set locale
                .setTimezoneId("Europe/Berlin") // set timezone
                .setPermissions(List.of("notifications", "geolocation")) // Allow all pages in the context to show system notifications
                .setGeolocation(48.858455, 2.294474) // set geolocation (with geolocation permissions granted
                .setColorScheme(ColorScheme.DARK)); // change color scheme

        page = browser.newPage(new Browser.NewPageOptions()
                .setColorScheme(ColorScheme.DARK)); // create page with dark mode

        page.emulateMedia(new Page.EmulateMediaOptions()
                .setColorScheme(ColorScheme.DARK)
                .setMedia(Media.PRINT));

        context.setGeolocation(new Geolocation(29.979097, 31.134256)); // change geolocation

        context.grantPermissions(List.of("geolocation")); // Grant all pages in the existing context access to current location
        context.grantPermissions(List.of("notifications"),
                new BrowserContext.GrantPermissionsOptions()
                        .setOrigin("https://skype.com"));//Grant notifications access from a specific domain

        context.clearPermissions(); // clear all permissions

        page.setViewportSize(1600, 1200); // individual page size
    }
}
