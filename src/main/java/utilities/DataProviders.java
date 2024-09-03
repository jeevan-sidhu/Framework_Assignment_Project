package utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="loginData")
	public Object[][] getLoginData() {
		return ExcelUtil.getData("login");
	}

}
