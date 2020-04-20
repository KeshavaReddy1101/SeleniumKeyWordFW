package operation;

import java.io.File;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class UIOperation {
	WebDriver webdriver;
	Actions actions;
	WebDriverWait wait;
	By by;
	ExtentReports extent;
	ExtentTest log;

	public void perform(Properties p, String testCase, String operation, String objectName, String objectType,
			String value) throws Exception {

		switch (operation.toUpperCase()) {

		case "OPENBROWSER":
			if (objectName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "/Users/kreddy/Documents/Softwares/chromedriver");
				webdriver = new ChromeDriver();
			}
			actions = new Actions(webdriver);
			wait = new WebDriverWait(webdriver, 20);
			webdriver.manage().window().maximize();
			ExtentHtmlReporter reporter = new ExtentHtmlReporter(new File("./test-output/extentReport.html"));
			extent = new ExtentReports();
			extent.attachReporter(reporter);
			break;

		case "CLICK":
			by = getObject(p, objectName, objectType);
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			webdriver.findElement(by).click();
			break;

		case "SETTEXT":
			by = getObject(p, objectName, objectType);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			webdriver.findElement(by).sendKeys(value);
			break;

		case "GOTOURL":
			webdriver.get(value);
			break;

		case "GETTEXT":
			by = getObject(p, objectName, objectType);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			webdriver.findElement(by).getText();
			break;

		case "ASSERTELEMENTPRESENT":
			by = getObject(p, objectName, objectType);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			webdriver.findElement(by).isDisplayed();
			break;

		case "MOUSEHOVER":
			by = getObject(p, objectName, objectType);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			actions.moveToElement(webdriver.findElement(by)).perform();
			break;
			
		case "VERIFELEMENT":
			by = getObject(p, objectName, objectType);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			boolean elementPresent = webdriver.findElement(by).isDisplayed();
			log = extent.createTest(testCase);
			if(elementPresent)
				log.pass("Element found "+by);
			else 
				log.fail("Element found");
			extent.flush();
			break;	

		default:
			break;
		}
	}

	private By getObject(Properties p, String objectName, String objectType) throws Exception {

		if (objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(p.getProperty(objectName));
		}

		else if (objectType.equalsIgnoreCase("CLASSNAME")) {
			return By.className(p.getProperty(objectName));
		}

		else if (objectType.equalsIgnoreCase("NAME")) {
			return By.name(p.getProperty(objectName));
		}

		else if (objectType.equalsIgnoreCase("CSS")) {
			return By.cssSelector(p.getProperty(objectName));
		}

		else if (objectType.equalsIgnoreCase("LINK")) {
			return By.linkText(p.getProperty(objectName));
		}

		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {
			return By.partialLinkText(p.getProperty(objectName));
		}

		else {
			throw new Exception("Wrong object type");
		}
	}
}
