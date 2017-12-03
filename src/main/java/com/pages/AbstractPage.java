package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import com.common.Helper;

public abstract class AbstractPage {
    AbstractPage(WebDriver driver, Helper helper, PageController page) {
        this.helper = helper;
        this.driver = driver;
        this.page = page;
        loadPage();
    }

    public WebDriver driver;
    public Helper helper;
    public PageController page;

    public void loadPage() {
        PageFactory.initElements(driver, this);
    }

}
