package com.qa.app.Base;

import com.microsoft.playwright.Page;
import com.qa.app.factory.PlaywrightFactory;
import com.qa.app.pages.HomePage;
import com.qa.app.pages.LoginPage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class BaseTest {

    PlaywrightFactory pf;
    Page page;
    protected HomePage homepage;
    protected LoginPage loginPage;
    protected Properties prop;
    @BeforeTest
    public void setup() throws IOException {
        pf = new PlaywrightFactory();
        prop =pf.init_PropFile();
        page = pf.initBrowser(prop);
        homepage =new HomePage(page);
        loginPage =new LoginPage(page);
    }

    @AfterTest
    public void teardown()
    {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
        page.context().browser().close();
    }
}
