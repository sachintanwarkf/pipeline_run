package stepdefinitions.functional;

import helpers.pageFactory.PageInstanceFactory;
import stepdefinitions.BaseStepDef;
import helpers.utilities.BasePage;
import helpers.utilities.PageEnum;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;

public class StepDef_Functional {

    private BaseStepDef baseStepDef = new BaseStepDef();

    @Given("^Initialize the \"([^\"]*)\" browser$")
    public void initializeBrowser(String browserName) {
        baseStepDef.setDriver(browserName);  // Use the BaseStepDef instance
    }

    @Given("^User open the url \"([^\"]*)\"$")
    public void openUrl(String url) {
        if (baseStepDef.driver != null) {
            baseStepDef.openURL(url);
        } else {
            throw new IllegalStateException("Driver is not initialized. Please call initializeBrowser() first.");
        }
    }

    @Then("^User clicks \"([^\"]*)\" on \"([^\"]*)\"$")
    public void clicks_element(String elementName, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage = PageInstanceFactory.getPageInstance(pageName, baseStepDef.driver);  // Use the driver from BaseStepDef
        WebElement element = baseStepDef.getElementUsingReflection(basePage, elementName);
        basePage.click(element);
    }

    @Then("^User verify the \"([^\"]*)\" exists on \"([^\"]*)\"$")
    public void verify_element(String elementName, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage = PageInstanceFactory.getPageInstance(pageName, baseStepDef.driver);  // Use the driver from BaseStepDef
        WebElement element = baseStepDef.getElementUsingReflection(basePage, elementName);
        basePage.isVisible(element);
    }

    @Then("^User enter \"([^\"]*)\" in \"([^\"]*)\" on \"([^\"]*)\"$")
    public void enters_text_in_element(String input,String elementName, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage = PageInstanceFactory.getPageInstance(pageName, baseStepDef.driver);  // Use the driver from BaseStepDef
        WebElement element = baseStepDef.getElementUsingReflection(basePage, elementName);
        basePage.passInput(element,input);
    }

    @Then("^User enter for \"([^\"]*)\" on \"([^\"]*)\"$")
    public void click_enter_on_element(String elementName, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage = PageInstanceFactory.getPageInstance(pageName, baseStepDef.driver);  // Use the driver from BaseStepDef
        WebElement element = baseStepDef.getElementUsingReflection(basePage, elementName);
        basePage.confirm(element);
    }

    @Then("^User wait for \"([^\"]*)\" seconds$")
    public void thread_sleep(String timeout) throws IllegalAccessException, InterruptedException {
        int Time=Integer.parseInt(timeout);
        int timeInMiliSec=Time*1000;
        Thread.sleep(timeInMiliSec);
    }

}
