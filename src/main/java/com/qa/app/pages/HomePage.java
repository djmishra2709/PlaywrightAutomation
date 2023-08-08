package com.qa.app.pages;

import com.microsoft.playwright.Page;

import java.util.List;

public class HomePage {

    private Page page;

    //Object repository
    private String login = "//a[text()='Log in']";
    private String contactUs= "//a[text()='Contact']";
    private String aboutUs ="//a[text()='About us']";

    private String Homemenu ="//a[contains(text(),'PRODUCT STORE')]/..//a[@class='nav-link']";
    //page constructor
    public HomePage(Page page)
    {
        this.page=page;
    }

    //page action methods
    public String getHomepageTitle()
    {
        return page.title();
    }

    public List<String> getAllMenuOptions()
    {
       return page.locator(Homemenu).allTextContents();
    }
    public String getHomepageURL()
    {
        return page.url();
    }
    public void loginIntoApp()
    {
        page.click(login);
    }
    public void clickContactUs()
    {
        page.click(contactUs);
    }
    public void ClickaboutUs()
    {
        page.click(aboutUs);
    }
}
