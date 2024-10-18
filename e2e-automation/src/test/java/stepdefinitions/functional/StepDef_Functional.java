package stepdefinitions.functional;

import helpers.pageFactory.PageInstanceFactory;
import org.openqa.selenium.WebDriver;
import stepdefinitions.BaseStepDef;
import helpers.utilities.BasePage;
import helpers.utilities.PageEnum;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebElement;

import javax.lang.model.element.Element;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class StepDef_Functional {

    public BaseStepDef baseStepDef;


    public StepDef_Functional(){
        baseStepDef = new BaseStepDef();
    }

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
    public void clicks_element(String elementName, PageEnum pageName) throws IllegalAccessException, InterruptedException {
        Thread.sleep(5000);
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
    @Then("^User verify the \"([^\"]*)\" is (VISIBLE|INVISIBLE) on \"([^\"]*)\"$")
    public void verify_element(String elementName, String visibility, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage = PageInstanceFactory.getPageInstance(pageName, baseStepDef.driver);  // Use the driver from BaseStepDef
        WebElement element = baseStepDef.getElementUsingReflection(basePage, elementName);
        if ("VISIBLE".equals(visibility)) {
            basePage.isVisible(element,"visible");
        } else if ("INVISIBLE".equals(visibility)) {
            basePage.isVisible(element,"invisible");
        } else {
            throw new IllegalArgumentException("Invalid visibility state: " + visibility);
        }
    }


    @Then("^User verify the \"([^\"]*)\" is (CLICKABLE) on \"([^\"]*)\"$")
    public void verify_element_ISClickable(String elementName, String visibility, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage = PageInstanceFactory.getPageInstance(pageName, baseStepDef.driver);  // Use the driver from BaseStepDef
        WebElement element = baseStepDef.getElementUsingReflection(basePage, elementName);
        if ("CLICKABLE".equals(visibility)) {
            basePage.isClickable(element,"clickable");

        } else {
            throw new IllegalArgumentException("Invalid visibility state: " + visibility);
        }
    }

    @Then("^User verify the \"([^\"]*)\" is (CHECKED|UNCHECKED) on \"([^\"]*)\"$")
    public void verify_element_isChecked(String elementName, String visibility, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage = PageInstanceFactory.getPageInstance(pageName, baseStepDef.driver);  // Use the driver from BaseStepDef
        WebElement element = baseStepDef.getElementUsingReflection(basePage, elementName);
        if ("CHECKED".equals(visibility)) {
            basePage.isChecked(element,"checked");
        } else if ("UNCHECKED".equals(visibility)) {
            basePage.isChecked(element,"unchecked");
        } else {
            throw new IllegalArgumentException("Invalid visibility state: " + visibility);
        }
    }

    @Then("^User verify the \"([^\"]*)\" is (SELECTED|UNSELECTED) on \"([^\"]*)\"$")
    public void verify_element_isSelected(String elementName, String visibility, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage = PageInstanceFactory.getPageInstance(pageName, baseStepDef.driver);  // Use the driver from BaseStepDef
        WebElement element = baseStepDef.getElementUsingReflection(basePage, elementName);
        if ("SELECTED".equals(visibility)) {
            basePage.isChecked(element,"selected");
        } else if ("UNSELECTED".equals(visibility)) {
            basePage.isChecked(element,"unselected");
        } else {
            throw new IllegalArgumentException("Invalid visibility state: " + visibility);
        }
    }



    @Then("^User compare the \"([^\"]*)\" is (MATCHED|UNMATCHED|PARTIALLY-MATCHED) with \"([^\"]*)\" on \"([^\"]*)\"$")
    public void verify_element_text_comparision(String expectedText, String visibility, String elementName, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage=PageInstanceFactory.getPageInstance(pageName,baseStepDef.driver);
        WebElement element=baseStepDef.getElementUsingReflection(basePage,elementName);
        String actualText=element.getText().trim();
        switch(visibility){
            case "MATCHED":
                assert actualText.equals(expectedText):"Expected text '"+expectedText+"' but found '"+actualText+"' in element: "+elementName;
                break;
            case "UNMATCHED":
                assert !actualText.equals(expectedText):"Expected text to be different from '"+expectedText+"' but it matched in element: "+elementName;
                break;
            case "PARTIALLY-MATCHED":
                assert actualText.contains(expectedText):"Expected text to partially match '"+expectedText+"' but it did not in element: "+elementName;
                break;
            default:
                throw new IllegalArgumentException("Invalid visibility state: "+visibility);
        }
    }



    @Then("^User selects \"([^\"]*)\" as \"([^\"]*)\" on \"([^\"]*)\"$")
    public void selects_date(String date, String elementName, PageEnum pageName) throws IllegalAccessException {
        BasePage basePage = PageInstanceFactory.getPageInstance(pageName, baseStepDef.driver);  // Use the driver from BaseStepDef
        WebElement element = baseStepDef.getElementUsingReflection(basePage, elementName);

    }

    @Then("User print the {string} and {string}")
    public void print_values(String value, String name) {
        System.out.println("Value is: " + value);
        System.out.println("Name is: " + name);
    }
}
