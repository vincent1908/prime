/*
 * Creation : 15 déc. 2016
 */
package extra;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Test5 {

    public static void main(String[] args) throws InterruptedException, Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new FirefoxDriver();

        driver.manage().window().maximize();

        Thread.sleep(5000);

        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");

        Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Animation commerciale")).click();
        driver.findElement(By.linkText("Suivi des ventes Filiale")).click();
        driver.findElement(By.linkText("Ventes par axe client")).click();
        //
        // WebElement country = driver.findElement(By.name("country"));
        // List<WebElement> countries = country.findElements(By.tagName("option"));
        // for(int i=0; i<countries.size(); i++) {
        // String value = countries.get(I).getText();
        // System.out.println(value);
        // }

        WebElement pays = driver.findElement(By.name("cdDr"));
        // List<WebElement> paysList = pays.findElements(By.tagName("option"));
        List<WebElement> paysList = pays.findElements(By.tagName("option"));
        Select dropdown2 = new Select(pays);
        for (int j = 0; j < paysList.size(); j++) {
            String paysListValue = paysList.get(j).getText();
            System.out.println(paysListValue);
            // Thread.sleep(10000);

            Thread.sleep(5000);
            dropdown2.selectByIndex(j);
            // dropdown2.selectByValue(paysListValue);
            // Thread.sleep(10000);
            System.out.println(paysListValue);
        }
    }
}