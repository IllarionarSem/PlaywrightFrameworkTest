package playwright;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PlaywrightParallelExecution extends BaseTest {

    @Test
    void shouldClickButton() {
        page.navigate("data:text/html,<script>var result;</script><button onclick='result=\"Clicked\"'>Go</button>");
        page.click("button");
        assertEquals("Clicked", page.evaluate("result"));
        System.out.println(Thread.currentThread().getId());
    }

    @Test
    void shouldCheckTheBox() {
        page.setContent("<input id='checkbox' type='checkbox'></input>");
        page.check("input");
        assertTrue((Boolean) page.evaluate("() => window['checkbox'].checked"));
        System.out.println(Thread.currentThread().getId());
    }

    @Test
    void shouldSearchWiki() {
        page.navigate("https://www.wikipedia.org/");
        page.click("input[name=\"search\"]");
        page.fill("input[name=\"search\"]", "playwright");
        page.press("input[name=\"search\"]", "Enter");
        assertEquals("https://en.wikipedia.org/wiki/Playwright", page.url());
        System.out.println(Thread.currentThread().getId());
    }
}
