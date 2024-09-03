package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import logger.Log;
import utilities.ElementsUtil;
import utilities.TimeUtil;

public class ApplicationPage {

	WebDriver driver;
	ElementsUtil elUtil;

	private By emailId = By.xpath("//div[@id='test-1-div']//input[@id='inputEmail']");
	private By password = By.xpath("//div[@id='test-1-div']//input[@id='inputPassword']");
	private By login_btn = By.xpath("//div[@id='test-1-div']//button[@type='submit']");
	private By list_items = By.xpath("//div[@id='test-2-div']//li");
	private By list_items_badge = By.xpath("//div[@id='test-2-div']//li/span");
	private By dropDownBtn = By.xpath("//div[@id='test-3-div']//div[@class='dropdown']//button");
	private By dropDownItems = By.xpath("//div[@id='test-3-div']//a");
	private By test4_btn1 = By.xpath("//div[@id='test-4-div']//button[1]");
	private By test4_btn2 = By.xpath("//div[@id='test-4-div']//button[2]");
	private By test5_btn = By.xpath("//div[@id='test-5-div']//button");
	private By successMsg = By.xpath("//div[@id='test-5-div']//div[@role='alert']");

	public ApplicationPage(WebDriver driver) {
		this.driver = driver;
		elUtil = new ElementsUtil(driver);
	}

	public void doLogin(String username, String pwd) {
		Log.info("*** Entering Username: " + username + " ***");
		elUtil.doSendKeys(emailId, username);
		Log.info("*** Entering Password: " + pwd + " ***");
		elUtil.doSendKeys(password, pwd);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		Log.info("*** Clicking Submit button ***");
//		elUtil.doClick(login_btn);
	}

	public boolean checkEmailInputBoxPresent() {
		Log.info("*** Checking Email input is present ***");
		return elUtil.doIsDisplayed(emailId);
	}

	public boolean checkPasswordInputBoxPresent() {
		Log.info("*** Checking Password input is present ***");
		return elUtil.doIsDisplayed(password);
	}

	public boolean checkLoginButtonPresent() {
		Log.info("*** Checking Submit button is present ***");
		return elUtil.doIsDisplayed(login_btn);
	}

	public int getSizeOfListItems() {
		Log.info("*** Getting Size Of List Items ***");
		return elUtil.doGetSize(list_items);
	}

	public String getListItemValue(int index) {
		elUtil.scrollToElement(list_items);
		Log.info("*** Getting Value Of List Item No: " + index +" ***");
		String text = elUtil.doGetText(list_items, index);
		String childText = elUtil.doGetText(list_items_badge, index);
		return text.replaceFirst(childText, "").trim();

	}

	public String getListItemBadgeValue(int index) {
		Log.info("*** Getting Badge Value Of Item No: " + index +" ***");
		return elUtil.doGetText(list_items_badge, index).trim();
	}

	public String getDropDownButtonText() {
		Log.info("*** Getting Drop Down Button Text ***");
		return elUtil.doGetText(dropDownBtn).trim();
	}

	public void selectDropDownItem(int index) {
		elUtil.doClick(dropDownBtn);
		Log.info("*** Selecting Dropdown Item No: " + index +" ***");
		elUtil.doClick(dropDownItems, index);
	}
	
	public boolean checkFirstBtnIsEnabled() {
		elUtil.scrollToElement(dropDownBtn);
		Log.info("*** Validating First Button Is Enabled ***");
		return elUtil.doIsEnabled(test4_btn1);
	}
	
	public boolean checkSecondBtnIsEnabled() {
		Log.info("*** Validating Second Button Is Disabled ***");
		return elUtil.doIsEnabled(test4_btn2);
	}
	
	public boolean checkSuccessMsgDisplayed() {
		Log.info("*** Waiting for Element to be Visible and Click ***");
		elUtil.doWaitAndClick(test5_btn, TimeUtil.DEFAULT_MEDIUM_TIME);
		Log.info("*** Validating Success Message After Click ***");
		return elUtil.doIsDisplayed(successMsg);
	}
	
	public boolean checkTest5BtnIsEnabled() {
		Log.info("*** Validating Button Is Disabled ***");
		return elUtil.doIsEnabled(test5_btn);
	}
	
	public String getCellValue(int row, int col) {
		Log.info("*** Getting Cell Value At Location: ["+row+","+col+"] ***");
		elUtil.scrollPageDown();
		String cellXpath = "//div[@id='test-6-div']//table//tr["+(row+1)+"]/td["+(col+1)+"]";
		return elUtil.doGetText(By.xpath(cellXpath));		
	}
	

}
