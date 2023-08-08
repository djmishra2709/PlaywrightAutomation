package com.qa.app.pages;

import com.microsoft.playwright.Page;
import com.qa.app.factory.PlaywrightFactory;

import java.io.IOException;
import java.util.Properties;

public class LoginPage {
    private Page page;
    public LoginPage(Page page) throws IOException {
        this.page=page;
     }


    //Object repository
    private String loginButton = "//button[text()='Log in']";
    private String username= "//input[@id='loginusername']";
    private String password ="//input[@id='loginpassword']";


    public void loginApp(String username1,String password1)
    {
        page.fill(username,username1);
        page.fill(password,password1);
        page.click(loginButton);
    }
}
