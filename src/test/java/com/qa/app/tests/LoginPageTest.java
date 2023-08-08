package com.qa.app.tests;

import com.qa.app.Base.BaseTest;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void LoginToApp()
    {
        homepage.loginIntoApp();
        loginPage.loginApp(prop.getProperty("username").trim(),prop.getProperty("password").trim());
    }
}
