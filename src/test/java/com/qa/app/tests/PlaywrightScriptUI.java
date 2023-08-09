package com.qa.app.tests;

import com.microsoft.playwright.*;
import org.testng.annotations.Test;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PlaywrightScriptUI {

    //launching chromium /webkit/Firefox
    @Test
    public void test1() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        System.out.println(page.title());
        page.navigate("https://playwright.dev/");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example3.png")));
        browser.close();

         }

         // launching simple browser
    @Test
    public void test2() {

        Playwright playwright = Playwright.create();
       /* BrowserType.LaunchOptions lo = new BrowserType.LaunchOptions();
        lo.setChannel("chrome");
        lo.setHeadless(false);
        Browser browser = playwright.chromium().launch(lo);*/
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false));
        Page page = browser.newPage();
        System.out.println(page.title());
        page.navigate("https://playwright.dev/");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example3.png")));
        browser.close();

    }

    // creating locator =webelement and clicking
    @Test
    public void test3() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

        BrowserContext bcontext = browser.newContext();
        Page page = bcontext.newPage();

        System.out.println(page.title());
        page.navigate("https://playwright.dev/");
        //single element
        /*Locator button = page.locator("text=Get started");
        button.click();*/

        //mutiple element
        Locator button = page.locator("text=Node.js");
        button.first().click();

        Locator list = page.locator("li");
        List<String> a =  list.allTextContents();
        for(String s :a)
           System.out.println(s);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example3.png")));
        browser.close();

    }

    //to capture the text
    @Test
    public void test4() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        Page page = browser.newPage();

        System.out.println(page.title());
        page.navigate("https://playwright.dev/");
        String s1 = page.locator("//h1[@class='hero__title heroTitle_ohkl']").textContent();
        System.out.println(s1);
        browser.close();

    }

    //handling dynamic tables
    @Test
    public void test5() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        Page page = browser.newPage();

        System.out.println(page.title());
        page.navigate("https://datatables.net/extensions/select/examples/initialisation/checkbox.html");
        Locator row = page.locator("//table[@id='example']//tr");
        row.locator(":scope",new Locator.LocatorOptions().setHasText("Cedric Kelly"))
                                        .locator("//td[@class='  select-checkbox']")
                                                                .click();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example3.png")));
        row.locator(":scope").allTextContents().forEach(e->System.out.println(e));
        browser.close();

    }

    //handling dynamic tables 2
    @Test
    public void test6() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        Page page = browser.newPage();

        System.out.println(page.title());
        page.navigate("https://datatables.net/extensions/select/examples/initialisation/checkbox.html");
        Locator row = page.locator("//table[@id='example']//tr");
        row.locator(":scope",new Locator.LocatorOptions().setHasText("Cedric Kelly"))
                        .allInnerTexts().forEach(e->System.out.println(e));
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example3.png")));
        browser.close();

    }

    //handling auto session login
    @Test
    public void test7() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        BrowserContext bcontext = browser.newContext();
        Page page = bcontext.newPage();

        page.navigate("https://www.demoblaze.com/index.html");
        page.locator("//a[text()='Log in']").click();
        page.locator("//input[@id='loginusername']").fill("test");
        page.locator("//input[@id='loginpassword']").fill("test");
        page.locator("//button[text()='Log in']").click();
        System.out.println("Welcome...You are in...");
        bcontext.storageState(new BrowserContext.StorageStateOptions()
                .setPath(Paths.get("login.json")));

    }

    @Test
    public void test8() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext br =
                browser.newContext(new Browser.NewContextOptions()
                        .setStorageStatePath(Paths.get("login.json")));
        Page page = br.newPage();
        page.navigate("https://www.demoblaze.com/index.html");
        browser.close();
    }

    //handling alert automatically by playwright
    @Test
    public void test9() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        BrowserContext bcontext = browser.newContext();
        Page page = bcontext.newPage();

        page.navigate("https://www.demoblaze.com/index.html");
        page.locator("//a[text()='Log in']").click();
        page.locator("//input[@id='loginusername']").fill("test");
        page.locator("//input[@id='loginpassword']").fill("test");
        page.locator("//button[text()='Log in']").click();

        //capture alert message with playwright listener
        page.onDialog(e -> {
                    System.out.println(e.message());
                    e.accept();
                }
                );

        System.out.println("Welcome...You are in...");
        List lo = page.locator("//a[@class='nav-link']").allTextContents();
        System.out.println(lo.toString());
        page.close();
        browser.close();
        playwright.close();

    }

    //upload file
    @Test
    public void test10() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext bcontext = browser.newContext();
        Page page = bcontext.newPage();

        page.navigate("https://davidwalsh.name/demo/multiple-file-upload.php");

        // Select single files
        page.locator("input#filesToUpload").setInputFiles(Paths.get("Sample.pdf"));

        // Select multiple files
        page.locator("input#filesToUpload").setInputFiles(new Path[] {Paths.get("Sample.pdf"), Paths.get("Sample1.pdf")});

        // Remove all the selected files
        page.locator("input#filesToUpload").setInputFiles(new Path[0]);

        page.close();
        browser.close();
        playwright.close();

    }

    //select from dropdown
    @Test
    public void test11() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext bcontext = browser.newContext();
        Page page = bcontext.newPage();

        page.navigate("https://formstone.it/components/dropdown/demo/");

        // Single selection matching the value
       List lr =  page.locator("select#demo_basic").selectOption("Two");
        System.out.println(lr.toString());
      /*   // Single selection matching the label
        page.getByLabel("Choose a color").selectOption(new SelectOption().setLabel("Blue"));

        // Multiple selected items
        page.getByLabel("Choose multiple colors").selectOption(new String[] {"red", "green", "blue"});*/

        page.close();
        browser.close();
        playwright.close();

    }

    //download the file and save it to specific location
    @Test
    public void test12() {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        BrowserContext bcontext = browser.newContext();
        Page page = bcontext.newPage();

        page.navigate("https://chromedriver.storage.googleapis.com/index.html?path=114.0.5735.90/");

        // Wait for the download to start
        Download download = page.waitForDownload(() -> {
            // Perform the action that initiates download
            //page.click("//a[text()='chromedriver_win32.zip']");
            page.locator("//a[text()='chromedriver_win32.zip']").click();
        });

        // Save downloaded file somewhere
        download.saveAs(Paths.get("C:\\Users\\dheer\\Desktop\\Personal\\chromedriver.zip"));
        page.onDownload(d -> System.out.println(d.path()));

        page.close();
        browser.close();
        playwright.close();

    }

    //launch browser in full size
    @Test
    public void test13() {

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int width =(int)screensize.getWidth();
        int height =(int)screensize.getHeight();

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        BrowserContext bcontext = browser.newContext(new Browser.NewContextOptions().setViewportSize(width,height));
        Page page = bcontext.newPage();
        page.navigate("https://www.amazon.com");

        page.close();
        browser.close();
        playwright.close();

    }

    //recording videos
    @Test
    public void test14() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

        BrowserContext bcontext = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("videos/test"))
                .setRecordVideoSize(400, 480));

        Page page = bcontext.newPage();
        page.navigate("https://www.amazon.com");

        page.close();
        browser.close();
        playwright.close();

    }
    //Handle browser window popup and new tab
    @Test
    public void test15() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
        BrowserContext bcontext =browser.newContext();
        Page page = bcontext.newPage();
        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Get popup after a specific action (e.g., click)
        Page popup = page.waitForPopup(() -> {
            page.locator("//a[@href='https://twitter.com/orangehrm?lang=en']").click();
        });
        popup.waitForLoadState();
        System.out.println(popup.title());
        popup.locator("text=Create account").click();
        popup.close();
        page.locator("//a[@href='https://www.facebook.com/OrangeHRM/']").click();
        page.close();
        browser.close();
        playwright.close();

    }
}