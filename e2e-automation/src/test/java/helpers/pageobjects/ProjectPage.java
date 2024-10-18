package helpers.pageobjects;

import helpers.utilities.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProjectPage extends BasePage {
    public ProjectPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//a[.=\"Consumption\"]")
    WebElement Tab_Consumption;

    public @FindBy(xpath = "//a[@id='Projects-1']")
    WebElement Tab_Project;

    @FindBy(xpath = "//div[@class=\"card-container\"]")
    WebElement Card_Container;

    @FindBy(xpath = "//div[@class=\"title\"]")
    WebElement Label_ProjectName;

    @FindBy(xpath = "//input[@id=\"select\"]")
    WebElement ChkBox_KFSelect;

    @FindBy(xpath="//label[@for='select']")
    WebElement Label_KFSelect;

    @FindBy(xpath = "//input[@id=\"assess\"]")
    WebElement ChkBox_KFAssess;

    @FindBy(xpath="//label[@for='assess']")
    WebElement Label_KFAssess;

    @FindBy(xpath = "//div[@id='serviceType']")
    WebElement Label_Administered;

    @FindBy(xpath = "//p[@class='active-text']")
    WebElement Label_ActiveStatus;

    @FindBy(xpath = "//div[@class='engagement-code-txt']")
    WebElement Label_EngagementCode;

    @FindBy(xpath = "//div[@class='participant']")
    WebElement Label_NumberOfParticipants;

    @FindBy(xpath = "//*[contains(text(),' billable')]")
    WebElement Label_Billable;

    @FindBy(xpath = "//*[contains(text(),'non-billable')]")
    WebElement Label_NonBillable;

    @FindBy(xpath = "//div[@class='potential-text']")
    WebElement Label_PontentialLevel;

    @FindBy(xpath = "//span[@class='date-txt']")
    WebElement Label_CreatedDate;

    @FindBy(xpath = "//div[.='KF Entry']")
    WebElement Label_ProjectType;

    @FindBy(xpath = "//div[.='KF Assess']")
    WebElement Label_ProductType;

    @FindBy(xpath = "(//span[@class=\"date-txt\"])[2]")
    WebElement Label_AddOns;

    @FindBy(xpath = "(//a[@id=\"manageProject\"])[1]")
    WebElement  Link_ViewDetails;

    @FindBy(xpath = "//div[@class='card-container']")
    List<WebElement> NumberOfContainer;



}
