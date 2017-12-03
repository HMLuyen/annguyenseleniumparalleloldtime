package demo;

import com.pages.HomePage;
import org.testng.annotations.Test;
import com.BaseTest;
import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class FirstRun extends BaseTest {

    @Test
    public void test01_Method() {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test1" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

    @Test
    public void test02_Method() {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user + "test2" + helper.getUsername());
        HomePage debug1 = page.homePage();
        page.homePage().search(testData_id + testData.user + "test2" + helper.getUsername());
        HomePage debug2 = page.homePage();
        page.homePage().search(testData_id + testData.user + "test2" + helper.getUsername());
        page.homePage().showCurrentUser();
        assertTrue(false);
    }

}
