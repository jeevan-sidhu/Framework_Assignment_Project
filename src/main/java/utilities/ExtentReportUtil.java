package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import factory.DriverFactory;

public class ExtentReportUtil implements ITestListener {
	private static ExtentSparkReporter sparkReporter;
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	public static String repName;
	
	public synchronized void onStart(ITestContext context) {
		String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timestamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);
		
		sparkReporter.config().setDocumentTitle("Assignment Framework Automation Report");
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.STANDARD);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Demo Test Application");
		extent.setSystemInfo("Module", "Index Page");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
//		String browser = context.getCurrentXmlTest().getParameter("browser");
//		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}
	
	public synchronized void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
		extentTest.assignCategory(result.getMethod().getGroups());
		test.set(extentTest);
	}
	
	public synchronized void onTestSuccess(ITestResult result) {
		test.get().log(Status.PASS, result.getName()+" got successfully executed");
	}
	
	public synchronized void onTestFailure(ITestResult result) {
		test.get().log(Status.FAIL, result.getName()+" got failed");
		test.get().log(Status.INFO, result.getThrowable().getMessage());
		
		String imgPath = DriverFactory.captureScreenshot(result.getName());
		test.get().addScreenCaptureFromPath(imgPath);
	}
	
	public synchronized void onTestSkipped(ITestResult result) {
		test.get().log(Status.SKIP, result.getName()+" got skipped");
		test.get().log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public synchronized void onFinish(ITestContext context) {
		extent.flush();
		test.remove();
	}
	
}
