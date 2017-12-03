package demo;

import org.testng.annotations.Test;
import com.BaseTest;

public class SecondRun extends BaseTest{

    @Test
    public void test03_Method() {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test4" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

    @Test
    public void test04_Method() {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test5" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

}
