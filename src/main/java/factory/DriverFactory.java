package factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import constants.AppConstants;
import logger.Log;

public class DriverFactory {

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	Properties prop;
	OptionsManager optionsManager;

	public WebDriver init_driver(Properties prop) {
		
		String browser = prop.getProperty("browser");
		optionsManager = new OptionsManager(prop);

		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			
			Log.info("*** Launching browser: " + browser + " on Grid ***");
			URL hubUrl = null;
			try {
				hubUrl = new URI(prop.getProperty("huburl")).toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			switch (browser.toLowerCase().trim()) {
			case "chrome":
				tlDriver.set(new RemoteWebDriver(hubUrl, optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(new RemoteWebDriver(hubUrl, optionsManager.getFirefoxOptions()));
				break;
			case "edge":
				tlDriver.set(new RemoteWebDriver(hubUrl, optionsManager.getEdgeOptions()));
				break;
			default:
				System.out.println("*** Please pass the right browser on grid..." + browser + " ***");
				Log.error("*** Please pass the right browser on grid..." + browser + " ***");
				throw new IllegalArgumentException("Invalid browser: " + browser);
			}
		} else {
			Log.info("*** Launching browser: " + browser + " on Local ***");
			switch (browser.toLowerCase().trim()) {
			case "chrome":
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
				break;
			case "edge":
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
				break;
			default:
				System.out.println("*** Please pass the right browser name..." + browser + " ***");
				Log.error("*** Please pass the right browser name..." + browser + " ***");
				throw new IllegalArgumentException("Invalid browser: " + browser);
			}
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		Log.info("*** Opening Home Page URL ***");
		getDriver().get(System.getProperty("user.dir") + prop.getProperty("app_url"));
//		getDriver().get(prop.getProperty("app_url"));
		return getDriver();
	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties init_prop() {
		prop = new Properties();
		String env = System.getProperty("env");
		Log.info("*** Running Test Suite on " + env + " Environment ***");
		FileInputStream file = null;

		if (env == null) {
			try {
				Log.info("*** No environment provided, Running on QA environment ***");
				file = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
			} catch (FileNotFoundException e) {
				Log.error("FILE NOT FOUND", e);
				e.printStackTrace();
			}
		} else {
			try {
				switch (env.toLowerCase().trim()) {
				case "qa":
					file = new FileInputStream(AppConstants.CONFIG_QA_FILE_PATH);
					break;
				case "dev":
					file = new FileInputStream(AppConstants.CONFIG_DEV_FILE_PATH);
					break;
				case "stage":
					file = new FileInputStream(AppConstants.CONFIG_STAGE_FILE_PATH);
					break;
				case "uat":
					file = new FileInputStream(AppConstants.CONFIG_UAT_FILE_PATH);
					break;
				case "prod":
					file = new FileInputStream(AppConstants.CONFIG_FILE_PATH);
					break;
				default:
					System.out.println("Please pass the right environment name..." + env);
					Log.error("*** Please pass the right environment name..." + env + " ***");
					throw new IllegalArgumentException("Invalid environment: " + env);
				}
			} catch (FileNotFoundException e) {
				Log.error("FILE NOT FOUND", e);
				e.printStackTrace();
			}
		}

		try {
			prop.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static String captureScreenshot(String testName) {
		Log.info("*** Test: "+ testName + " Failed, Attaching Screenshot ***");
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date());

		// Define the path for the screenshots folder
		String screenshotsDirPath = System.getProperty("user.dir") + "/screenshots";

		// Create the screenshots folder if it doesn't exist
		File screenshotsDir = new File(screenshotsDirPath);
		if (!screenshotsDir.exists()) {
			if (screenshotsDir.mkdirs()) {
				System.out.println("Folder 'screenshots' created successfully at: " + screenshotsDirPath);
			} else {
				System.out.println("Failed to create the folder 'screenshots' at: " + screenshotsDirPath);
			}
		}

		String targetFilePath = screenshotsDirPath + "/" + testName + "_" + timestamp + ".png";

		TakesScreenshot ts = (TakesScreenshot) getDriver();
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		File targetFile = new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		return targetFilePath;
	}
}
