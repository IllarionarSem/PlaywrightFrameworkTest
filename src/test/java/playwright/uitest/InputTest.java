package playwright.uitest;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.KeyboardModifier;
import com.microsoft.playwright.options.MouseButton;
import com.microsoft.playwright.options.SelectOption;
import org.testng.annotations.Test;

import java.util.List;

public class InputTest extends BaseTest {

    @Test
    public void inputTests() {


        // to type text
        page.fill("selector", "value");
        page.type("", ""); // type text emulating keyboard interaction (char by char)

        // checkbox
        page.check("");
        boolean isChecked = page.isChecked("");
        page.uncheck("");

        // select
        page.selectOption("", "value");
        page.selectOption("", new SelectOption().setLabel("value"));
        page.selectOption("", new String[]{"red", "blue", "green"});
        ElementHandle option = page.querySelector("");
        page.selectOption("", option);

        // clicks
        page.click(""); // click
        page.dblclick(""); // double click
        page.click("", new Page.ClickOptions().setButton(MouseButton.RIGHT)); // right mouse button click
        page.click("", new Page.ClickOptions().setModifiers(List.of(KeyboardModifier.SHIFT))); // shift + click
        page.hover(""); // hover over
        page.click("", new Page.ClickOptions().setPosition(0, 0)); // click the top left corner
        page.click("", new Page.ClickOptions().setForce(true)); // force click (ignoring waiting element states)

        // short cuts
        page.press("", "Enter"); // press enter
        page.press("", "Control+ArrowRight");
        page.press("#value", "$");
    }
}
