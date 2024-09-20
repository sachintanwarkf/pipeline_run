package stepdefinitions;

import helpers.utilities.BasePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.addFields;

public class BaseStepDef {
    public WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseStepDef.class);


    public void setUp() {
        this.driver = setDriver("edge");
    }


    public void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }


    public WebDriver getCurrentDriverInstance(String browserName) {
        WebDriver driver = null;
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                addCommonOptions(chromeOptions);
                driver = new ChromeDriver(chromeOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                addCommonOptions(edgeOptions);
                driver = new EdgeDriver(edgeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                addCommonOptions(firefoxOptions);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            // Add more cases for other browsers if needed
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        LOGGER.info("Driver initialized for browser: " + browserName);
        return driver;
    }

    public WebDriver setDriver(String browserName) {
                this.driver = getCurrentDriverInstance(browserName);
        LOGGER.info("Driver initialized for browser: " + browserName);
        return null;
    }

    public String getSessionId() {
        if (this.driver != null && this.driver instanceof RemoteWebDriver) {
            return ((RemoteWebDriver) this.driver).getSessionId().toString();
        }
        return null;
    }

    public void openURL(String url) {
        if (driver != null) {
            driver.get(url);
            LOGGER.info("URL is opened");
        } else {
            LOGGER.error("Driver is not initialized. Cannot open URL.");
        }
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        } else {
            LOGGER.error("Driver is not initialized. Cannot quit driver.");
        }
    }
    public <TPage extends BasePage> WebElement getElementUsingReflection(TPage pageObject, String fieldName) throws IllegalAccessException {
        WebElement webElement = null;
        Field field;
        field = getFieldFromPageObjectOrItsBaseClass(pageObject, fieldName);
        field.setAccessible(true);
        try {
            webElement = (WebElement) field.get(pageObject);
        } catch (IllegalAccessException err) {
            LOGGER.error("Unable to get value for the field " + fieldName + ". Error received: " + err.getMessage());
            throw err;
        }
        return webElement;
    }
    public <TPage extends BasePage> List<WebElement> getElementsUsingReflection(TPage pageObject, String fieldName) throws IllegalAccessException {
        List<WebElement> listOfWebElement = new ArrayList<>();
        List<Field> fields = getFieldsFromPageObjectOrItsBaseClass(pageObject, fieldName);
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(List.class) && WebElement.class.isAssignableFrom((Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0])) {
                    List<WebElement> elements = (List<WebElement>) field.get(pageObject);
                    listOfWebElement.addAll(elements);
                } else {
                    LOGGER.warn("Field " + fieldName + " is not of type List<WebElement> in class " + pageObject.getClass());
                }
            } catch (IllegalAccessException err) {
                LOGGER.error("Unable to get value for the field " + fieldName + ". Error received: " + err.getMessage());
                throw err;
            }
        }
        return listOfWebElement;
    }
    private <TPage extends BasePage> Field getFieldFromPageObjectOrItsBaseClass(TPage pageObject, String fieldName) {
        Field field = null;
        try {
            field = pageObject.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // If field is not found in the passed pageobject, we look for the field in the parent class.
            LOGGER.error(String.format("Could not find the field %s in class %s", fieldName, pageObject.getClass()));
            LOGGER.error("Error received: " + e.getMessage());
            Class parentClass = pageObject.getClass().getSuperclass();
            try {
                field = parentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ex) {
                LOGGER.error(String.format("Could not find the field %s in the superclass %s of class %s.",
                        fieldName, parentClass, pageObject.getClass()));
                Assert.fail("Not able to find field due to:" + ex.getMessage());
            }
        }
        Assert.assertNotNull("Field " + fieldName + " is set to null. Please check the class : " + pageObject.getClass(), String.valueOf(field));
        return field;
    }
    private <TPage extends BasePage> List<Field> getFieldsFromPageObjectOrItsBaseClass(TPage pageObject, String fieldName) {
        List<Field> fields = new ArrayList<>();
        // Add fields from the current class
        // Add fields from the superclass
        Class<?> parentClass = pageObject.getClass().getSuperclass();
        addFields(fields, parentClass, fieldName);
        return fields;
    }

    private void addFields(List<Field> fields,Class<?> classes,String fieldName)
    {
        try
        {
            Field[] declaredFields=classes.getDeclaredFields();
            for(Field field:declaredFields)
            {
                if(field.getName().equals(fieldName))
                {
                    field.setAccessible(true);
                    fields.add(field);
                }
            }
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Add common options that apply to all WebDriver instances.
     *
     * @param options the WebDriver options object
     */
    private void addCommonOptions(Object options) {
        if (options instanceof ChromeOptions) {
            ((ChromeOptions) options).addArguments("--ignore-ssl-errors=yes");
            ((ChromeOptions) options).addArguments("--ignore-certificate-errors");
        } else if (options instanceof EdgeOptions) {
            ((EdgeOptions) options).addArguments("--ignore-ssl-errors=yes");
            ((EdgeOptions) options).addArguments("--ignore-certificate-errors");
        } else if (options instanceof FirefoxOptions) {
            ((FirefoxOptions) options).addArguments("--ignore-ssl-errors=yes");
            ((FirefoxOptions) options).addArguments("--ignore-certificate-errors");
        }
        // Add more options for other browsers if necessary
    }
}
