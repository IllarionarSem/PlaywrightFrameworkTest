package playwright.APITest;

import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.APIUtil;

import java.util.Objects;

public abstract class BaseTest {

    protected Playwright playwright;
    protected APIUtil apiUtil;

    @BeforeTest
    public void initiateAPI() {
        playwright = Playwright.create();
        apiUtil = new APIUtil(playwright);
    }

    @AfterTest
    public void terminateAPIConnection() {
        if (Objects.nonNull(playwright)) {
            apiUtil.dispose();
            playwright.close();
        }
        playwright = null;
    }
}
