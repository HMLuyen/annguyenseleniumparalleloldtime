package com.pages;

import com.common.Helper;
import org.openqa.selenium.WebDriver;


public class PageController {
    public WebDriver driver;
    public Helper helper;

    public PageController(WebDriver driver, Helper helper) {
        this.driver = driver;
        this.helper = helper;
    }

    public HomePage homePage;

    //Pages
    public HomePage homePage() {
        if (homePage == null)
            return homePage = new HomePage(driver, helper, this);
        return homePage;
    }

}
