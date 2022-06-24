package playwright.uitest;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;

public class FrameTest extends BaseTest {

    @Test
    public void frameTest() {

        // via Locator
        page.navigate("http://the-internet.herokuapp.com/nested_frames");
        Locator frameText = page.frameLocator("//frame[@name='frame-top']")
                .frameLocator("//frame[@name='frame-left']")
                .locator("//body");
        System.out.println(frameText.innerText());

        // via Frame, found by frame name
        Frame frame = page.frame("frame-left");
        System.out.println(frame.innerText("//body"));

        // iFrame
        page.navigate("http://the-internet.herokuapp.com/iframe");
        Locator iFrame = page.frameLocator("//iframe")
                .locator("//body/p");
        System.out.println(iFrame.innerText());
    }
}
