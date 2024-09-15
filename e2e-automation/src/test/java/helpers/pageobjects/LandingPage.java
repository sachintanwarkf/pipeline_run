package helpers.pageobjects;

import helpers.utilities.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends BasePage {
    public LandingPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@id='email']")
    WebElement text_Email;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btn_Login;

    @FindBy(xpath = "//button[@id='details-button']")
    WebElement btn_Advanced;

    @FindBy(xpath = "//a[@id='proceed-link']")
    WebElement link_ToContinue;
}
