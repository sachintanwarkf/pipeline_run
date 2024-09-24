package helpers.pageFactory;

import helpers.pageobjects.DashboardPage;
import helpers.pageobjects.LandingPage;
import helpers.pageobjects.LoginPage;
import helpers.pageobjects.ProjectPage;
import helpers.utilities.BasePage;
import helpers.utilities.PageEnum;
import org.openqa.selenium.WebDriver;

public class PageInstanceFactory {
    public static BasePage getPageInstance(PageEnum pageEnum, WebDriver driver)
    {
        BasePage result=null;
        switch (pageEnum)
        {
            case LOGIN_PAGE:        result=new LoginPage(driver);break;
            case DASHBOARD_PAGE:    result=new DashboardPage(driver);break;
            case LANDING_PAGE:      result=new LandingPage(driver);break;
            case PROJECT_PAGE:      result=new ProjectPage(driver);break;
        }
        return result;
    }
}
