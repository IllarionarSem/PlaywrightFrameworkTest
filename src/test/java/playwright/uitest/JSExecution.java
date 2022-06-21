package playwright.uitest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class JSExecution extends BaseTest {

    @Test
    public void jsExecution() {

        page.navigate("http://the-internet.herokuapp.com/");
        page.evaluate("() => {return document.body.style.backgroundColor='tomato'}");
        String message = "Hi from js executor";
        final StringBuilder dialogMessage = new StringBuilder("");
        page.onceDialog(dialog -> {
            dialogMessage.append(dialog.message());
            Assert.assertEquals(dialogMessage.toString(), message);
            dialog.accept();
        });
        // arguments could pass as an object (Map, array etc.) as second parameter
        page.evaluate("text => alert(text)", "Hi from js executor");
        System.out.println("Dialog message is: " + dialogMessage);
    }
}
