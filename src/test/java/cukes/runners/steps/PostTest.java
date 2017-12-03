package cukes.runners.steps;

import com.BaseTest;
import cucumber.api.java.en.Given;

/**
 * Created by NightStalker on 2/8/17.
 */
public class PostTest extends BaseTest{

    @Given("^the input \"([^\"]*)\"$")
    public void the_input(String arg1) throws Throwable {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test1" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

}
