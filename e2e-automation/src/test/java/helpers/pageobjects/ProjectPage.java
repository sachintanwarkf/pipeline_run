package helpers.pageobjects;

import helpers.utilities.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectPage extends BasePage {
    public ProjectPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//a[.=\"Consumption\"]")
    WebElement Tab_Consumption;

    @FindBy(id = "Projects-1")
    WebElement Tab_Project;

    @FindBy(xpath = "//div[@class=\"cardContainer\"]")
    WebElement Card_Container;

    @FindBy(xpath = "//p[@class=\"title\"]")
    WebElement Label_ProjectName;

    @FindBy(xpath = "//input[@id=\"select\"]")
    WebElement ChkBox_KFSelect;

    @FindBy(xpath="//label[@for='select']")
    WebElement Label_KFSelect;

    @FindBy(xpath = "//input[@id=\"assess\"]")
    WebElement ChkBox_KFAssess;

    @FindBy(xpath="//label[@for='assess']")
    WebElement Label_KFAssess;

    @FindBy(xpath = "//div[.='Client Administered']")
    WebElement Label_Administered;

    @FindBy(xpath = "//p[@class='activeText']")
    WebElement Label_ActiveStatus;

    @FindBy(xpath = "//p[@class='engagementCodeTxt']")
    WebElement Label_EngagementCode;

    @FindBy(xpath = "//p[@class='engagementCodeTxt']")
    WebElement Label_NumberOfParticipants;

    @FindBy(xpath = "//span[.=\"1 billable\"]']")
    WebElement Label_Billable;

    @FindBy(xpath = "//span[.=\"1 non-billable\"]")
    WebElement Label_NonBillable;

    @FindBy(xpath = "//p[@class=\"potentialText\"]")
    WebElement Label_PontentialLevel;

    @FindBy(xpath = "//span[@class=\"dateTxt\"]")
    WebElement Label_CreatedDate;

    @FindBy(xpath = "//div[.='KF ENTRY']")
    WebElement Label_ProjectType;

    @FindBy(xpath = "//div[.='KF Assess']")
    WebElement Label_ProductType;

    @FindBy(xpath = "(//span[@class=\"dateTxt\"])[2]")
    WebElement Label_AddOns;

    @FindBy(xpath = "//a[@id=\"manageProject\"])[1]")
    WebElement  Link_ViewDetails;


}
