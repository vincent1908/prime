/*
 * Creation : 23 déc. 2016
 */
package extra;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestC {
    public static void main(String[] args) throws InterruptedException, Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        Thread.sleep(5000);

        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");

        Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Animation commerciale")).click();
        driver.findElement(By.linkText("Suivi des ventes Filiale")).click();
        driver.findElement(By.linkText("Ventes par axe client")).click();

        try {
            WebElement pays = driver.findElement(By.name("cdDr"));
            List<WebElement> paysList = pays.findElements(By.tagName("option"));
            Thread.sleep(5000);
            for (int j = 0; j < paysList.size(); j++) {
                WebElement pays2 = driver.findElement(By.name("cdDr"));

                List<WebElement> paysList2 = pays2.findElements(By.tagName("option"));
                Thread.sleep(5000);
                String paysListValue = paysList2.get(j).getText();
                System.out.println(paysListValue);

                Select dropdown2 = new Select(pays2);
                Thread.sleep(5000);
                dropdown2.selectByIndex(j);
            }
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
            driver.quit();
        }
    }

}
