package playwright.uitest;

import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;

public class CustomSelectorTest extends BaseTest {

    @Test
    public void customSelector() {

        // Must be a script that evaluates to a selector engine instance.
        String createTagNameEngine = "{\n" +
                "  // Returns the first element matching given selector in the root's subtree.\n" +
                "  query(root, selector) {\n" +
                "    return root.querySelector(selector);\n" +
                "  },\n" +
                "\n" +
                "  // Returns all elements matching given selector in the root's subtree.\n" +
                "  queryAll(root, selector) {\n" +
                "    return Array.from(root.querySelectorAll(selector));\n" +
                "  }\n" +
                "}";
        // register selector with prefix e.g. 'tag'
        playwright.selectors().register("tag", createTagNameEngine);

        // usage
        Locator button = page.locator("tag=button");
        button.click();
    }
}
