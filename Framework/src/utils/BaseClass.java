package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BaseClass {

	static WebDriver driver;

	public static void openBrowser(String browserType) {
		switch (browserType) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\repository\\ECFramework\\lib\\geckodriver.exe");

			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver",
					"C:\\Selenium\\repository\\ECFramework\\lib\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
		case "IE":
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\repository\\ECFramework\\lib\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			break;
		case "chromeMac":
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
		case "firefoxMac":
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;
		default:
			System.setProperty("webdriver.chrome.driver",
					"C:\\Selenium\\repository\\ECFramework\\lib\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}

	}

	public static void closeBrowser() {
		driver.quit();

	}

}
