package utilities;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementsUtil {
	
	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	
	public ElementsUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	public WebElement getElement(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			return element;
		}
		catch(NoSuchElementException e) {
			System.out.println("Element is not present on the page..." + locator);
			e.printStackTrace();
			return null;
		}
	}
	
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	public void doSendKeys(By locator, String txt) {
		clearTextbox(locator);
		getElement(locator).sendKeys(txt);
	}
	
	public void clearTextbox(By locator) {
		getElement(locator).sendKeys(Keys.CONTROL, "a", Keys.DELETE);
	}
	
	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doClick(By locator, int index) {
		getElements(locator).get(index-1).click();
	}
	
	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}
	
	public boolean doIsEnabled(By locator) {
		return getElement(locator).isEnabled();
	}
	
	public boolean doIsSelected(By locator) {
		return getElement(locator).isSelected();
	}
	
	public int doGetSize(By locator) {
		return getElements(locator).size();
	}
	
	public String doGetText(By locator) {
		return getElement(locator).getText();
	}
	
	public String doGetText(By locator, int index) {
		return getElements(locator).get(index-1).getText();
	}
	
	public String doGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}
	
	public String doGetAttribute(By locator, String attrName, int index) {
		return getElements(locator).get(index-1).getAttribute(attrName);
	}
	
	public void scrollToElement(By locator) {
		jsUtil.scrollIntoView(getElement(locator));
	}
	
	public void scrollPageDown() {
		jsUtil.scrollPageDown();;
	}
	
	public void scrollPageUp() {
		jsUtil.scrollPageUp();
	}
	
	public WebElement waitForElement(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void doWaitAndClick(By locator, int timeOut) {
		waitForElement(locator, timeOut).click();
	}
	
	

}
