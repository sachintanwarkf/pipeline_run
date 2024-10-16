package helpers.utilities;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasePage {
    private static Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

    private WebDriver driver;
    Actions actions;


    public BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver instance cannot be null");
        }
        this.driver = driver;
        actions = new Actions(this.driver);
    }

    /**
     * This will wait for the element for each 10 seconds
     */
    public WebElement fluentWaitFindElement(final WebElement element, final int timeOutInSeconds) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public List<WebElement> fluentWaitFindElements(final By locator, final int timeOutInSeconds) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void waitForActions(WebElement element,String action)
    {
        Wait wait=new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(5));
        switch (action)
        {
            case "click": wait.until(ExpectedConditions.elementToBeClickable(element));break;
            case "visible":wait.until(ExpectedConditions.visibilityOf(element));break;
            case "selected":wait.until(ExpectedConditions.elementToBeSelected(element));break;
            case "invisible":wait.until(ExpectedConditions.invisibilityOf(element));break;
        }
    }

    /**
     * Will make all the generic methods
     */

    protected <T> WebElement getElement(T elementAttr) {
        return fluentWaitFindElement((WebElement) elementAttr, 30);
    }

    protected List<WebElement> getElements(By locator) {
        return fluentWaitFindElements(locator, 30);
    }

    public <T> boolean is(T elementAttr)
    {
        WebElement element;
        try {
            element=getElement(elementAttr);
            waitForActions(element,"visible");
            return element.isDisplayed();
        }
        catch (Exception e)
        {
            LOGGER.error("Unable to verify that element is visible, error message: "+e.getMessage());
            return false;
        }
    }
    public <T> boolean isVisible(T elementAttr)
    {
        WebElement element;
        try {
            element=getElement(elementAttr);
            waitForActions(element,"visible");
            return element.isDisplayed();
        }
        catch (Exception e)
        {
            LOGGER.error("Unable to verify that element is visible, error message: "+e.getMessage());
            return false;
        }
    }

    public <T> boolean isSelected(T elementAttr)
    {
        WebElement element;
        try {
            element=getElement(elementAttr);
            waitForActions(element,"selected");
            return element.isSelected();
        }
        catch (Exception e)
        {
            LOGGER.error("Unable to verify that element is selected, error message: "+e.getMessage());
            return false;
        }
    }

    public void passInput(WebElement element,String input)
    {
       waitForActions(element,"visible");
       element.sendKeys(input);
    }

    public void click(WebElement element)
    {
        waitForActions(element,"visible");
        element.click();
    }

    public void confirm(WebElement element)
    {
        waitForActions(element,"visible");
        element.submit();
    }

    public String getAttributeValue(WebElement element,String attribute)
    {
        waitForActions(element,"visible");
        return element.getAttribute(attribute);
    }

    public String getText(WebElement element)
    {
        waitForActions(element,"visible");
        return element.getText();
    }

    public boolean scroll(WebElement element,String direction)
    {
        boolean isVisible=false;
        while(!isVisible)
        {
            if(element.isDisplayed()){isVisible=true;break;}
            else{actions.scrollToElement(element);}
        }
        return element.isDisplayed();
    }


    public static void assertElementDisplayed(String assertMessage, WebElement element, String condition) {
        try {
            LOGGER.info("Verifying if {} true or not", condition);
            Assert.assertNotNull(assertMessage, String.valueOf(element));
            if (element.isDisplayed()) {
                LOGGER.info("Validation successfull for {} ", condition);
            } else {
                Assert.fail(element + " is not displayed.");
            }
        } catch (Exception e) {
            LOGGER.info("");
        }
    }

    public static void assertTrueOrFalse(boolean asertTrueOrFalse, String assertMessage, boolean conditionToVerify, String condition) {
        if (asertTrueOrFalse == true) {
            LOGGER.info("Verifying if {} is true", condition);
            Assert.assertTrue(assertMessage, conditionToVerify);
            LOGGER.info("Validation successful for {} ", condition);
        } else {
            LOGGER.info("Verifying if {} is false", condition);
            Assert.assertFalse(assertMessage, conditionToVerify);
            LOGGER.info("Validation successful for {} ", condition);
        }
    }

    public <T> boolean isChecked(T elementAttr, String action) {
        WebElement element;
        try {
            element = getElement(elementAttr);
            switch (action) {
                case "checked":
                    waitForActions(element, "checked");
                    break;
                case "unchecked":
                    waitForActions(element, "unchecked");
                    break;
            }
            return element.isSelected();
        } catch (Exception e) {
            LOGGER.error("Unable to verify that element is selected, error message: " + e.getMessage());
            return false;
        }
    }

    public <T> boolean isVisible(T elementAttr, String action) {
        WebElement element;
        try {
            element = getElement(elementAttr);
            switch (action) {
                case "visible":
                    waitForActions(element, "visible");
                    break;
                case "invisible":
                    waitForActions(element, "invisible");
                    break;
            }
            return element.isDisplayed();
        } catch (Exception e) {
            LOGGER.error("Unable to verify that element is visible, error message: " + e.getMessage());
            return false;
        }
    }
    public <T> boolean isClickable(T elementAttr, String action) {
        WebElement element;
        try {
            element = getElement(elementAttr);
            if(action=="clickable")
            {
                waitForActions(element, "click");
            }
            return element.isDisplayed();
        } catch (Exception e) {
            LOGGER.error("Unable to verify that element is clickable, error message: " + e.getMessage());
            return false;
        }
    }

}
