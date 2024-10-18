package stepdefinitions.functional;

import helpers.pageFactory.PageInstanceFactory;
import helpers.pageobjects.ProjectPage;
import helpers.utilities.BasePage;
import helpers.utilities.PageEnum;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import stepdefinitions.BaseStepDef;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProjectPageStepDef {
    public BaseStepDef baseStepDef;
    WebDriver driver= new ChromeDriver();
    ProjectPage pg = new ProjectPage(driver);
    public List<WebElement>  ProjectContainer =driver.findElements(By.xpath("//p[@class=\"title\"]"));//List of projects in the page
    //separate list for each items in project page
    public List<String> projectNameList = new ArrayList<String>();
    public List<String> administeredList = new ArrayList<String>();
    public List<String> activeList = new ArrayList<String>();
    public List<String> engagementCodeList = new ArrayList<String>();
    public List<String> numberOfParticipantsList = new ArrayList<String>();
    public List<Integer> numberOfBillableList = new ArrayList<Integer>();
    public List<Integer> NumberOfNonBillableList = new ArrayList<Integer>();
    public List<String> potentialLevelList = new ArrayList<String>();
    public List<String> createdDateList = new ArrayList<String>();
    public List<String> projectTypeList = new ArrayList<String>();
    public List<String> productTypeList = new ArrayList<String>();
    public List<String> addOnsList = new ArrayList<String>();
    public ProjectPageStepDef(){

        baseStepDef = new BaseStepDef();
    }
    @Then("^User copies data of \"([^\"]*)\" from \"([^\"]*)\"$")
    public void user_copy_data_to_List(String elementName, PageEnum pageName) throws IllegalAccessException, InterruptedException {
        driver.get("https://analytics.kornferrytalent-dev.com/");
        driver.manage().window().maximize();
        Thread.sleep(5000);
        pg.Tab_Project.click();
        Thread.sleep(5000);

        ProjectContainer = driver.findElements(By.xpath("//div[@class='card-container']"));
        int NUmerOfProjectsAsInProjectPage = ProjectContainer.size();
        int NumberOfProjectsAsPerData=20;//Trying to get randon number before pagination
        //checking for number of projects is as per data
        Assert.assertEquals("The number of projects is not as per data", NUmerOfProjectsAsInProjectPage, NumberOfProjectsAsPerData);

        //checking for administered list is visible or not and getting data from each card
        List<WebElement> lableAdministered= driver.findElements(By.xpath("//div[@id='serviceType']"));
        Assert.assertFalse("Client Administered is not vissible",lableAdministered.isEmpty());
        for (WebElement webElement : lableAdministered)
        {
            administeredList.add(webElement.getText());

        }

        //Checking for active status on card and getting data from each card
        List<WebElement> lableActive= driver.findElements(By.xpath("//p[@class='active-text']"));
        Assert.assertFalse("Client Administered is not vissible",lableActive.isEmpty());
        for (WebElement webElement : lableActive) {

            activeList.add(webElement.getText());
        }

        //Checking for project name on card and getting name of each card
        List<WebElement> lableProjectName= driver.findElements(By.xpath("//div[@class=\"title\"]"));
        Assert.assertFalse("Client Administered is not vissible",lableProjectName.isEmpty());
        for (WebElement webElement : lableProjectName) {
            projectNameList.add(webElement.getText());
        }

        //Checking for engagement code of project tab and getting data
        List<WebElement> lableEngagementCode= driver.findElements(By.xpath("//div[@class='engagement-code-txt']"));
        Assert.assertFalse("Engagement code is not vissible",lableEngagementCode.isEmpty());
        for (WebElement webElement : lableEngagementCode) {
            String Input = webElement.getText();
            String[] part = Input.split(": ");
            String eCode=part[1].trim();//to get engadgement code after from each card
            engagementCodeList.add(eCode);
        }

        //Checking for number of participants in card and getting data
        List<WebElement> labelParticipants= driver.findElements(By.xpath("//div[@class='participant']"));
        Assert.assertFalse("Engagement code is not vissible",labelParticipants.isEmpty());
        for (WebElement webElement : labelParticipants) {
            numberOfParticipantsList.add(webElement.getText());
        }

        //Checking for Number of Billable and non-billable is visible and getting data
        List<WebElement> labelBillableandNonBillable= driver.findElements(By.xpath("//div[@class=\"sub-title\"]"));
        int billable =0, nonBillable=0;
        Assert.assertFalse("Engagement code is not vissible",labelBillableandNonBillable.isEmpty());
        for (WebElement webElement : labelBillableandNonBillable) {
            String bAndNB = webElement.getText();
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(bAndNB);

            //find first two integer in text
            if(matcher.find())
            {
                billable=Integer.parseInt(matcher.group());
                numberOfBillableList.add(billable);

            }
            if(matcher.find())
            {
                nonBillable=Integer.parseInt(matcher.group());
                NumberOfNonBillableList.add(nonBillable);
            }


        }
        //Checking for Potential Level and storing it in list
        List<WebElement> labelPotentialLevel= driver.findElements(By.xpath("//div[@class='potential-text']"));
        Assert.assertFalse("Potential level is not vissible",labelPotentialLevel.isEmpty());
        for (WebElement webElement : labelPotentialLevel) {
            potentialLevelList.add(webElement.getText());
        }

        //Checking for Created Date and storing in list

        List<WebElement> labelCreatedDate= driver.findElements(By.xpath("//span[@class='date-txt']"));
        Assert.assertFalse("Potential level is not vissible",labelCreatedDate.isEmpty());
        for (WebElement webElement : labelCreatedDate) {
            createdDateList.add(webElement.getText());
        }

        List<WebElement> labelProjectTypeAndProjectType= driver.findElements(By.xpath("//div[@class=\"pill-item square \"]"));
        Assert.assertFalse("Potential level is not vissible",labelProjectTypeAndProjectType.isEmpty());
        for(int i=2;i<=ProjectContainer.size()*3;i=i+3)
        {

            WebElement element1 = driver.findElement(By.xpath("(//div[@class=\"pill-item square \"])["+i+"]"));
            String name = element1.getText();
            projectTypeList.add(name);
        }
        for(int i=3;i<=ProjectContainer.size()*3;i=i+3)
        {
            WebElement element1 = driver.findElement(By.xpath("(//div[@class=\"pill-item square \"])["+i+"]"));
            String name = element1.getText();
            productTypeList.add(name);
        }


    }
}
