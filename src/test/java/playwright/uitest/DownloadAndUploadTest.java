package playwright.uitest;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.FilePayload;
import io.netty.handler.codec.http.multipart.FileUpload;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
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

        page.navigate("http://the-internet.herokuapp.com/upload");
        page.locator("//input[@type='file']").first().setInputFiles(Paths.get("downloads/some-file.txt"));
        page.click("//input[@type='submit']");

        //remove all selected files
        page.locator("//input[@type='file']").first().setInputFiles(new Path[0]);

        //upload multiple files
        page.locator("//input[@type='file']").first().setInputFiles(
                new Path[]{Paths.get("downloads/some-file.txt"), Paths.get("anotherfile")});

        // upload buffer from memory
        page.setInputFiles("input", new FilePayload("file.txt", "text/plain", "this is test".getBytes(StandardCharsets.UTF_8)));

        // if there are no input element
        FileChooser fileChooser = page.waitForFileChooser(() -> page.locator("//input[@type='file']").first().click());
        fileChooser.setFiles(Paths.get("downloads/some-file.txt"));
    }
}
