package playwright.uitest;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Download;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class DownloadAndUploadTest extends BaseTest {

    @Test
    public void download() {

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)
                .setDownloadsPath(Paths.get("downloads")));
        page.close();
        page = browser.newPage();
        page.navigate("http://the-internet.herokuapp.com/download");
        Download download = page.waitForDownload(() -> page.click("//a[text()='some-file.txt']"));
        String downloadPath = download.path().toString();
        BufferedReader reader = new BufferedReader(new InputStreamReader(download.createReadStream()));
        System.out.println(reader.lines().collect(Collectors.joining()));
        download.saveAs(Paths.get("downloads/some-file.txt"));
        System.out.println(downloadPath);
    }

    @Test
    public void uploadFile() {

    }
}
