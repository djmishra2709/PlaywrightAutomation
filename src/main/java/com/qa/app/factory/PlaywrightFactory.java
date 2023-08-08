package com.qa.app.factory;

import com.microsoft.playwright.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext bcontext;
    Page page;
    Properties prop;

    private static ThreadLocal<Browser> tlbrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlbrowsercontext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlpage = new ThreadLocal<>();
    private static ThreadLocal<Playwright> tlplaywright = new ThreadLocal<>();

    public static Playwright getPlaywright()
    {
        return tlplaywright.get();
    }
    public static Browser getBrowser()
    {
        return tlbrowser.get();
    }
    public static BrowserContext getBrowserContext()
    {
        return tlbrowsercontext.get();
    }
    public static Page getPage()
    {
        return tlpage.get();
    }
    public Page initBrowser(Properties prop)
    {
        String browsername = prop.getProperty("browser").trim();
        Boolean headlessCondition = Boolean.parseBoolean(prop.getProperty("headless").trim());
        System.out.println("Browser name is :"+browsername);
        //playwright = Playwright.create();
        tlplaywright.set(Playwright.create());

        switch (browsername.toLowerCase()){
            case "chromium":
              // browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headlessCondition));
              tlbrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headlessCondition)));
                break;
            case "chrome":
                //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headlessCondition));
                tlbrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headlessCondition)));

                break;
            case "safari":
                //browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headlessCondition));
                tlbrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headlessCondition)));
                break;
            case "firefox":
                //browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headlessCondition));
                tlbrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headlessCondition)));

                break;
            default:
                System.out.println("Please provide valid Browser name...");
                break;
        }

        /*page = browser.newContext().newPage();
        page.navigate(prop.getProperty("url").trim());
        return page;*/

        tlbrowsercontext.set(getBrowser().newContext());
        tlpage.set(getBrowserContext().newPage());
        getPage().navigate(prop.getProperty("url").trim());

        return getPage();


    }

    public Properties init_PropFile() throws IOException {
        FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
        prop =new Properties();
        prop.load(ip);

        return prop;
    }

    /**
     * take screenshot
     *
     */

    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
        //getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));

        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64Path = Base64.getEncoder().encodeToString(buffer);

        return base64Path;
    }
}
