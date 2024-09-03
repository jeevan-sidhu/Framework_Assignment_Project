package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import logger.Log;
import utilities.DataProviders;

public class ApplicationTest extends BaseTest{
	
	@Test(priority=0, groups={"Sanity", "Functional"})
	public void test1_VerifyLogin(){
		Log.info("*** Starting Test 1 ***");
		sf.assertEquals(ap.checkEmailInputBoxPresent(), true, "Email input box is not present on the page");
		sf.assertEquals(ap.checkPasswordInputBoxPresent(), true, "Password input box is not present on the page");
		sf.assertEquals(ap.checkLoginButtonPresent(), true, "Login Button is not present on the page");
		
		Log.info("*** Entering user credentials and Submit ***");
		ap.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Log.info("*** Test 1 Finished ***");
		
		sf.assertAll();
	}
	
	@Test(priority=1, dataProvider="loginData", dataProviderClass=DataProviders.class, groups= {"DataDriven"})
	public void LoginDataDrivenTest(String uname, String pwd){
		Log.info("*** Starting Login Data Driven Test ***");
		sf.assertEquals(ap.checkEmailInputBoxPresent(), true, "Email input box is not present on the page");
		sf.assertEquals(ap.checkPasswordInputBoxPresent(), true, "Password input box is not present on the page");
		sf.assertEquals(ap.checkLoginButtonPresent(), true, "Login Button is not present on the page");
		
		Log.info("*** Entering user credentials and Submit ***");
		ap.doLogin(uname, pwd);
		Log.info("*** Login Data Driven Test Finished ***");
		
		sf.assertAll();
	}
	
	@Test(priority=2, groups={"Sanity", "Regression"})
	public void test2_VerifyListItems(){
		Log.info("*** Starting Test 2 ***");
		sf.assertEquals(ap.getSizeOfListItems(), 3, "Number of List Items Mismatch");
		sf.assertEquals(ap.getListItemValue(2), "List Item 2", "List Item Value Mismatch");
		sf.assertEquals(ap.getListItemBadgeValue(2), "6", "List Item Badge Value Mismatch");
		Log.info("*** Test 2 Finished ***");
		sf.assertAll();
	}
	
	@Test(priority=3, groups={"Sanity"})
	public void test3_VerifyDropDown(){
		Log.info("*** Starting Test 3 ***");
		sf.assertEquals(ap.getDropDownButtonText(), "Option 1", "Default selected value Mismatch");
		ap.selectDropDownItem(3);
		Log.info("*** Test 3 Finished ***");
		sf.assertAll();
	}
	
	@Test(priority=4, groups={"Regression", "Functional"})
	public void test4_VerifyButtonsEnabled(){
		Log.info("*** Starting Test 4 ***");
		sf.assertEquals(ap.checkFirstBtnIsEnabled(), true, "First Button is Disabled");
		sf.assertEquals(ap.checkSecondBtnIsEnabled(), false, "Second Button is Enabled");
		Log.info("*** Test 4 Finished ***");
		sf.assertAll();
	}
	
	@Test(priority=5, groups={"Regression"})
	public void test5_VerifyWaitClickAndSuccessMsg(){
		Log.info("*** Starting Test 5 ***");
		sf.assertEquals(ap.checkSuccessMsgDisplayed(), true, "Success Message Not Displayed");
		sf.assertEquals(ap.checkTest5BtnIsEnabled(), false, "Button Still Enabled");
		Log.info("*** Test 5 Finished ***");
		sf.assertAll();
	}
	
	@Test(priority=6, groups={"Regression"})
	public void test6_VerifyWebTable(){
		Log.info("*** Starting Test 6 ***");
		String cellValue = ap.getCellValue(2, 2);
		sf.assertEquals(cellValue, "Ventosanzap", "Cell Value Mismatch");
		Log.info("*** Test 6 Finished ***");
		sf.assertAll();
	}

}
