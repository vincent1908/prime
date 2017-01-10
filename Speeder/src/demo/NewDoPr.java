/*
 * Creation : 3 janv. 2017
 */
package demo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
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

        Thread.sleep(2000);

        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");

        Thread.sleep(2000);
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
            System.out.println("dropdown1 count : " + paysList.size());
            Thread.sleep(2000);
            // for (int j = 0; j < paysList.size(); j++) {
            // for (int j = 1; j < paysList.size(); j++) {
            for (int j = 1; j < 2; j++) {
                WebElement pays2 = driver.findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[2]/select"));

                List<WebElement> paysList2 = pays2.findElements(By.tagName("option"));
                Thread.sleep(2000);
                String paysListValue = paysList2.get(j).getText();
                System.out.println("dropdown1 value : " + paysListValue);

                Select dropdown2 = new Select(pays2);
                Thread.sleep(2000);
                dropdown2.selectByIndex(j);

                System.out.println("---------------------------------------------------------------------------------------------------");

                // Step 11 Select Pays "Bélux "
                WebElement pays1 = driver.findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[3]/select"));
                List<WebElement> paysList1 = pays1.findElements(By.tagName("option"));
                System.out.println("dropdown2 count : " + paysList1.size());
                // for (int k = 0; k < paysList1.size(); k++) {
                // for (int k = 1; k < paysList1.size(); k++) {
                for (int k = 2; k < 3; k++) {
                    WebElement pays3 = driver
                            .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[3]/select"));

                    List<WebElement> paysList3 = pays3.findElements(By.tagName("option"));
                    Thread.sleep(2000);
                    String paysListValue2 = paysList3.get(k).getText();
                    System.out.println("dropdown2 value : " + paysListValue2);

                    Select dropdown3 = new Select(pays3);
                    Thread.sleep(2000);
                    dropdown3.selectByIndex(k);

                    System.out.println("---------------------------------------------------------------------------------------------------");

                    // Step 12 Select DR "Zone P&S Suisse "

                    WebElement dr = driver
                            .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[4]/select"));
                    List<WebElement> drList1 = dr.findElements(By.tagName("option"));
                    System.out.println("dropdown3 count : " + drList1.size());

                    // for (int l = 0; l < drList1.size(); l++) {
                    for (int l = 3; l < 4; l++) {
                        WebElement dr1 = driver
                                .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[4]/select"));

                        List<WebElement> drList2 = dr1.findElements(By.tagName("option"));
                        Thread.sleep(2000);
                        String drList3 = drList2.get(l).getText();
                        System.out.println("dropdown3 value : " + drList3);

                        Select dropdown4 = new Select(dr1);
                        Thread.sleep(2000);
                        dropdown4.selectByIndex(l);

                        System.out.println("---------------------------------------------------------------------------------------------------");

                        // Step 13 Select Secteur "DSP 03" (there will be multiple selections availble for sectur)
                        WebElement secteur = driver
                                .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[5]/select"));
                        List<WebElement> secteurList1 = secteur.findElements(By.tagName("option"));
                        System.out.println("dropdown4 count : " + secteurList1.size());
                        // for (int m = 0; m < secteurList1.size(); m++) {
                        for (int m = 2; m < secteurList1.size(); m++) {
                            // for (int m = 2; m < 8; m++) {
                            WebElement secteur1 = driver
                                    .findElement(By.xpath(".//*[@id='bodyFiltre']/fieldset/form[3]/fieldset[1]/table/tbody/tr[3]/td[5]/select"));

                            List<WebElement> secteurList2 = secteur1.findElements(By.tagName("option"));
                            Thread.sleep(2000);
                            String secteurList3 = secteurList2.get(m).getText();
                            System.out.println("dropdown4 value : " + secteurList3);

                            Select dropdown5 = new Select(secteur1);
                            Thread.sleep(2000);
                            dropdown5.selectByIndex(m);

                            System.out.println("---------------------------------------------------------------------------------------------------");

                            // Step 14 Select Code DoPR from the drop down (there will be multiple selections availble for DoPR)

                            WebElement cDoPr = driver.findElement(By.xpath(".//*[@id='cdClientSelect']"));
                            Thread.sleep(2000);
                            List<WebElement> cDoPrList1 = cDoPr.findElements(By.tagName("option"));
                            Thread.sleep(2000);
                            System.out.println("dropdown5 count : " + cDoPrList1.size());
                            for (int n = 1; n < cDoPrList1.size(); n++) {
                                Thread.sleep(2000);
                                WebElement cDoPr1 = driver.findElement(By.xpath(".//*[@id='cdClientSelect']"));
                                Thread.sleep(2000);
                                List<WebElement> cDoPrList2 = cDoPr1.findElements(By.tagName("option"));
                                // System.out.println("cDoPrList2" + cDoPrList2.size());
                                Thread.sleep(2000);
                                String cDoPrList3 = cDoPrList2.get(n).getText();
                                Thread.sleep(2000);
                                System.out.println("dropdown5 value : " + cDoPrList3);
                                Thread.sleep(2000);
                                Select dropdown6 = new Select(cDoPr1);
                                Thread.sleep(2000);
                                dropdown6.selectByIndex(n);
                                Thread.sleep(2000);

                                System.out.println(
                                        "---------------------------------------------------------------------------------------------------");

                                // // Step 15 Select Raison sociale du DoPR from drop down (there will be multiple selections availble for Raison
                                // social)

                                WebElement raison = driver.findElement(By.xpath(".//*[@id='lbClientSelect']"));

                                List<WebElement> raisonList = raison.findElements(By.tagName("option"));

                                System.out.println("dropdown6 count : " + raisonList.size());

                                for (int p = 1; p < raisonList.size(); p++) {
                                    WebElement raison1 = driver.findElement(By.xpath(".//*[@id='lbClientSelect']"));

                                    List<WebElement> raisonList1 = raison1.findElements(By.tagName("option"));
                                    Thread.sleep(2000);
                                    String raisonList2 = raisonList1.get(p).getText();
                                    System.out.println("dropdown6 value : " + raisonList2);

                                    Select dropdown7 = new Select(raison1);
                                    Thread.sleep(2000);
                                    dropdown7.selectByIndex(p);

                                    // Step 5 From "Période d'analyse" select P - Du & Au
                                    driver.findElement(By.id("data1")).clear();
                                    driver.findElement(By.id("data1")).sendKeys("01/11/2016");
                                    driver.findElement(By.id("data2")).clear();
                                    driver.findElement(By.id("data2")).sendKeys("31/12/2016");

                                    // Step 6 select PVP

                                    // Step 7 Click on Rechercher
                                    driver.findElement(By.cssSelector("#trbody > td")).click();
                                    driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
                                    Thread.sleep(5000);
                                    try {
                                        Alert alert = driver.switchTo().alert();
                                        alert.accept();
                                        System.out.println("Alert command executed");
                                    } catch (Exception e1) {
                                        // TODO Auto-generated catch block
                                        // e1.printStackTrace();
                                        // System.out.println("Alert not required");
                                    }

                                    String resultsOne;
                                    try {
                                        resultsOne = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();
                                        System.out.println(resultsOne);

                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        // e.printStackTrace();
                                        System.out.println("element not found");
                                    }

                                }
                                System.out.println(
                                        "---------------------------------------------------------------------------------------------------");
                                System.out.println("new result");
                            }
                        }

                    }
                }
            }
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
        System.out.println("Test Complete");
        driver.close();
    }

}
