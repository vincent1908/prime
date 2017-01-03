/*
 * Creation : 3 janv. 2017
 */
package demo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class NewDoPr {

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
        // Step 8 Go to Suivi des ventes DoPR
        driver.findElement(By.linkText("Suivi des ventes DoPR")).click();
        // Step 9 Select Ventes par axe client
        driver.findElement(By.xpath("(//a[contains(text(),'Ventes par axe client')])[2]")).click();

        // Step 10 Select Marque " CITROEN"

        try {
            WebElement pays = driver.findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[2]/select"));
            List<WebElement> paysList = pays.findElements(By.tagName("option"));
            Thread.sleep(5000);
            for (int j = 1; j < paysList.size(); j++) {
                WebElement pays2 = driver.findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[2]/select"));

                List<WebElement> paysList2 = pays2.findElements(By.tagName("option"));
                Thread.sleep(5000);
                String paysListValue = paysList2.get(j).getText();
                System.out.println(paysListValue);

                Select dropdown2 = new Select(pays2);
                Thread.sleep(5000);
                dropdown2.selectByIndex(j);

                // Step 11 Select Pays "Bélux "
                WebElement pays1 = driver.findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[3]/select"));
                List<WebElement> paysList1 = pays1.findElements(By.tagName("option"));
                for (int k = 0; k < paysList1.size(); k++) {
                    WebElement pays3 = driver
                            .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[3]/select"));

                    List<WebElement> paysList3 = pays3.findElements(By.tagName("option"));
                    Thread.sleep(2000);
                    String paysListValue2 = paysList3.get(k).getText();
                    System.out.println(paysListValue2);

                    Select dropdown3 = new Select(pays3);
                    Thread.sleep(2000);
                    dropdown3.selectByIndex(k);

                    // Step 12 Select DR "AC BELUX "

                    WebElement dr = driver
                            .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[4]/select"));
                    List<WebElement> drList1 = dr.findElements(By.tagName("option"));
                    for (int l = 0; l < drList1.size(); l++) {
                        WebElement dr1 = driver
                                .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[4]/select"));

                        List<WebElement> drList2 = dr1.findElements(By.tagName("option"));
                        Thread.sleep(2000);
                        String drList3 = drList2.get(l).getText();
                        System.out.println(drList3);

                        Select dropdown4 = new Select(dr1);
                        Thread.sleep(2000);
                        dropdown4.selectByIndex(l);

                        // Step 13 Select Secteur "DSP 03" (there will be multiple selections availble for sectur)
                        WebElement secteur = driver
                                .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[5]/select"));
                        List<WebElement> secteurList1 = secteur.findElements(By.tagName("option"));
                        for (int m = 0; m < secteurList1.size(); m++) {
                            WebElement secteur1 = driver
                                    .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[5]/select"));

                            List<WebElement> secteurList2 = secteur1.findElements(By.tagName("option"));
                            Thread.sleep(2000);
                            String secteurList3 = secteurList2.get(m).getText();
                            System.out.println(secteurList3);

                            Select dropdown5 = new Select(secteur1);
                            Thread.sleep(2000);
                            dropdown5.selectByIndex(m);
                        }

                    }
                }
            }
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }

    }

}
