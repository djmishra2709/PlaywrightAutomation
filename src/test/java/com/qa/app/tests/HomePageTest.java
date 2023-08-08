package com.qa.app.tests;

import com.qa.app.Base.BaseTest;
import com.qa.app.Constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {


    @Test
    public void homepagetitle()
    {
        Assert.assertEquals(homepage.getHomepageTitle(), AppConstants.PAGE_Title);
    }

    @Test
    public void checkallMenuText()
    {
        System.out.println(homepage.getAllMenuOptions().toString());
        Assert.assertTrue(homepage.getAllMenuOptions().contains("Log in"));
        Assert.assertTrue(homepage.getAllMenuOptions().contains("Contact"));
        Assert.assertTrue(homepage.getAllMenuOptions().contains("About us"));
        Assert.assertTrue(homepage.getAllMenuOptions().contains("Cart"));
    }

}
