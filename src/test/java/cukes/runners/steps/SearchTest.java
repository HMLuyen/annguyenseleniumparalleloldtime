package cukes.runners.steps;


import com.BaseTest;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchTest extends BaseTest{


    @When("^the calculator is run Then the output should be \"([^\"]*)\"$")
    public void the_calculator_is_run_Then_the_output_should_be(String arg1) throws Throwable {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test1" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

    @Then("^i see the bug$")
    public void iSeeTheBug() throws Throwable {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test1" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

    @Given("^hai$")
    public void hai() throws Throwable {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test1" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

    @When("^hi$")
    public void hi() throws Throwable {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test1" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

    @Then("^bye$")
    public void bye() throws Throwable {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test1" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

    @Then("^aaa$")
    public void aaa() throws Throwable {
        System.out.println("Thread id = " + Thread.currentThread().getId());
        System.out.println(testData.user);
        page.homePage().search(testData_id + testData.user+ "test1" + helper.getUsername());
        page.homePage().showCurrentUser();
    }

}
