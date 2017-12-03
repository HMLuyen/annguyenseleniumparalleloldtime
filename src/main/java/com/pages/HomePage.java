package com.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.common.Helper;

public class HomePage extends AbstractPage {
    HomePage(WebDriver driver, Helper helper, PageController page) {
        super(driver, helper, page);
    }

    @FindBy(id = "lst-ib")
    public WebElement searchBox;

    public void search(String text) {
        searchBox.sendKeys(text);
    }

    public void showCurrentUser() {
        System.out.print("Current user is: " + helper.getUsername());
    }

}
