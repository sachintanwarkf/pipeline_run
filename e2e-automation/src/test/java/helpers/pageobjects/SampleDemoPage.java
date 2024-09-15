//package helpers.pageobjects;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.testng.Assert;
//import reusable.UIHelpers;
//
//import java.io.IOException;
//
//import static org.testng.Assert.assertTrue;
//
//public class SampleDemoPage extends UIHelpers {
//
//	@FindBy(xpath = "//div[@class='col-md-12 col-xs-12 p-l-0 p-r-0 display-flex insurance-sec overflow']//div[1]//div//div[contains(text(),'E-Card')]")
//	WebElement selectEcard;
//
//	@FindBy(xpath = "//input[@id='radio_empid']")
//	WebElement selectEmpIdRadioBtn;
//
//	@FindBy(xpath = "//span[contains(text(),'Start Consultation')]")
//	WebElement selectStartSession;
//
//	@FindBy(xpath = "(//div[@class='card card-body card-altered'])[1]")
//	WebElement callDoctor;
//
//	@FindBy(xpath = "//label [@for='mobNum']")
//	WebElement addMobNum;
//
//	@FindBy(xpath = "//input[@id='mobNum']")
//	WebElement addnum;
//
//	@FindBy(xpath = "//h1[text()='Medicine']")
//	WebElement selectMedicine;
//
//	@FindBy(xpath = "//span[@class='mm-chevron-down bold active-color']")
//	WebElement btndoubleClick;
//
//	@FindBy(xpath = "//input[@name='citySearch']")
//	WebElement selectCity;
//
//	@FindBy(xpath = "//div[text()='Chennai Area']")
//	WebElement addselectCity;
//
//	@FindBy(xpath = "//input[@id='action-input']")
//	WebElement addPinCode;
//
//	@FindBy(xpath = "//button[@type='submit']")
//	WebElement searchButton;
//
//	@FindBy(xpath = "//button[@class='loginBtn text-center mb-color mb-btn-login']")
//	WebElement submitBtn;
//
//	@FindBy(xpath = "//input[@name='emailid']")
//	WebElement textBoxEmailId;
//
//	public SampleDemoPage() throws IOException {
//		PageFactory.initElements(driver, this);
//	}
//
//	public void validateWebsitelaunch() throws InterruptedException {
//		Thread.sleep(5000);
//		UIHelpers.checkElementDisplayed(selectStartSession);
//		String act = selectStartSession.getText();
//		String exp = "Start Consultation";
//
//		Assert.assertEquals(act, exp);
//	}
//
//	public void clickOnclickHere() throws Exception {
//		Thread.sleep(5000);
//		scrollHighlightAndClick(selectStartSession);
//	}
//
//	public void switchToNewtab() throws InterruptedException {
//		Thread.sleep(5000);
//		handleNewTab(driver);
//	}
//
//	public void enterEmail(String mobile) throws Exception {
//		UIHelpers.checkElementDisplayed(callDoctor);
//
//		scrollHighlightAndClick(callDoctor);
//		UIHelpers.checkElementDisplayed(addMobNum);
//
//		Thread.sleep(3000);
//		jsClick(addMobNum, "User Enter the mobile number");
//		addnum.sendKeys("9677978785");
//
//		Thread.sleep(3000);
//		click(submitBtn, "Add mobile submit");
//
//		driver.close();
//	}
//
//	public void switchToParentTab() {
//		handleParentTab(driver);
//		assertTrue(selectStartSession.isDisplayed());
//	}
//
//	public void validatePageLaunch() throws Exception {
//		Thread.sleep(3000);
//		UIHelpers.checkElementDisplayed(selectMedicine);
//
//		UIHelpers.click(selectMedicine, "Select Medicine Field");
//	}
//
//	public void mouseOverClickonelement() throws Exception {
//		Thread.sleep(3000);
//		UIHelpers.moveToElementAndEnter(btndoubleClick);
//	}
//
//	public void getCityFromDropDown() throws Exception {
//		UIHelpers.jsClick(selectCity, "add city in search box click");
//		selectCity.sendKeys("chennai");
//
//		UIHelpers.jsClick(addselectCity, "add city in search box click");
//	}
//
//	public void addPinCode() throws Exception {
//		UIHelpers.jsClick(addPinCode, "add city pincode search box");
//		addPinCode.sendKeys("600020");
//
//		UIHelpers.jsClick(searchButton, "search button click");
//	}
//
//	public void validatePagesLaunch() throws InterruptedException {
//		Thread.sleep(7000);
//		UIHelpers.checkElementDisplayed(selectEcard);
//	}
//
//	public void clickOnEcard() throws Exception {
//		UIHelpers.scrollToWebElement(selectEcard);
//		UIHelpers.elementHighlight(selectEcard);
//		UIHelpers.clickWebElement(selectEcard);
//	}
//
//	public void validateEcardpageDisplay() throws InterruptedException {
//		Thread.sleep(5000);
//		UIHelpers.checkElementDisplayed(selectEmpIdRadioBtn);
//	}
//
//	public void clickOnEmpIdRadioButton() throws Exception {
//		UIHelpers.checkRadioBtn(selectEmpIdRadioBtn, "Select emp id radio button");
//		System.out.println("The radio button is clicked");
//	}
//}
