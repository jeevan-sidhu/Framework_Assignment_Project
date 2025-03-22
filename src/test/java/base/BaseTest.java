package base;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import factory.DriverFactory;
import logger.Log;
import pages.ApplicationPage;
import utilities.ExtentReportUtil;

public class BaseTest {
	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	protected ApplicationPage ap;
	protected SoftAssert sf;

	@Parameters({ "browser" })
	@BeforeTest(groups= {"Sanity", "Regression", "Functional", "DataDriven"})
	public void setUp(@Optional String browserName) {
		Log.info("*** Starting Test ***");
		df = new DriverFactory();
		prop = df.init_prop();
		if (browserName != null) {
			prop.setProperty("browser", browserName);
		}
		driver = df.init_driver(prop);
		ap = new ApplicationPage(driver);
	}

	@AfterTest(groups= {"Sanity", "Regression", "Functional", "DataDriven"})
	public void tearDown() {
		if (DriverFactory.getDriver() != null) {
			DriverFactory.getDriver().quit();
		}
		Log.info("*** Test Completed ***");
	}

	@AfterSuite(groups= {"Sanity", "Regression", "Functional", "DataDriven"})
	public void openReport() {
		File report = new File(System.getProperty("user.dir") + "\\reports\\" + ExtentReportUtil.repName);
		try {
			Desktop.getDesktop().browse(report.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
