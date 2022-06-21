package playwright.uitest;

import com.microsoft.playwright.Dialog;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.function.Consumer;

public class AlertTest extends BaseTest {

    @Test
    public void dialogScenarios() {

        Consumer<Dialog> acceptAlert = dialog -> {
            Assert.assertEquals(dialog.message(), "I am a JS Alert");
            dialog.accept();
        };
        Consumer<Dialog> acceptConfirm = dialog -> {
            Assert.assertEquals(dialog.message(), "I am a JS Confirm");
            dialog.accept();
        };
        Consumer<Dialog> dismiss = dialog -> {
            Assert.assertEquals(dialog.message(), "I am a JS Confirm");
            dialog.dismiss();
        };
        String text = "aaa";
        Consumer<Dialog> typeText = dialog -> {
            Assert.assertEquals(dialog.message(), "I am a JS prompt");
            dialog.accept(text);
        };
        page.navigate("http://the-internet.herokuapp.com/javascript_alerts");
        //accept alert
        page.onceDialog(acceptAlert);
        page.click("//button[contains(text(),'JS Alert')]");
        Assert.assertEquals(page.locator("//p[@id='result']").textContent(), "You successfully clicked an alert");

        //accept and cancel alert
        page.onceDialog(acceptConfirm);
        page.click("//button[contains(text(),'JS Confirm')]");
        Assert.assertEquals(page.locator("//p[@id='result']").textContent(), "You clicked: Ok");
        page.onceDialog(dismiss);
        page.click("//button[contains(text(),'JS Confirm')]");
        Assert.assertEquals(page.locator("//p[@id='result']").textContent(), "You clicked: Cancel");

        //prompt
        page.onDialog(typeText);
        page.click("//button[contains(text(),'JS Prompt')]");
        Assert.assertEquals(page.locator("//p[@id='result']").textContent(), String.format("You entered: %s", text));
    }
}
