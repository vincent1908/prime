/*
 * Creation : 27 sept. 2016
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

public class Test {

    public static void main(String[] args) throws InterruptedException, Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        Thread.sleep(5000);

        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");

        Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Animation Commerciale

        driver.findElement(By.linkText("Animation commerciale")).click();

        // Step 1 Click on the Suivi des ventes Filiale

        driver.findElement(By.id("2#1#0")).click();

        // Step 2 Select Ventes par axe client

        driver.findElement(By.linkText("Ventes par axe client")).click();

        // Step 3 Select Marque " CITROEN"

        try {
            WebElement pays = driver.findElement(By.name("cdMarque"));
            List<WebElement> paysList = pays.findElements(By.tagName("option"));
            Thread.sleep(5000);
            // for (int i = 0; i < paysList.size(); i++) {
            for (int i = 0; i < 1; i++) {
                WebElement pays1 = driver.findElement(By.name("cdMarque"));

                List<WebElement> paysList1 = pays1.findElements(By.tagName("option"));
                Thread.sleep(5000);
                String paysListValue = paysList1.get(i).getText();
                System.out.println(paysListValue);

                Select dropdown1 = new Select(pays1);
                Thread.sleep(5000);
                dropdown1.selectByIndex(i);
                // Step 4 Select Pays "Bélux " (there will be multiple selections availble for Pays)
                WebElement pays2 = driver.findElement(By.name("cdPays"));
                List<WebElement> paysList2 = pays2.findElements(By.tagName("option"));
                Thread.sleep(5000);
                // for (int j = 0; j < paysList2.size(); j++) {
                for (int j = 0; j < 1; j++) {
                    WebElement pays3 = driver.findElement(By.name("cdPays"));

                    List<WebElement> paysList3 = pays3.findElements(By.tagName("option"));
                    Thread.sleep(5000);
                    String paysListValue2 = paysList3.get(j).getText();
                    System.out.println(paysListValue2);

                    Select dropdown2 = new Select(pays3);
                    Thread.sleep(5000);
                    dropdown2.selectByIndex(j);

                    // Step 5 From "Période d'analyse" select P - Du & Au
                    driver.findElement(By.id("data1")).sendKeys("01/11/2016");
                    driver.findElement(By.id("data2")).sendKeys("31/12/2016");
                    // Step 6 select PVP
                    // Step 7 Click on Rechercher
                    driver.findElement(By.cssSelector("#trbody > td")).click();
                    driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
                    Thread.sleep(5000);

                    // ***************
                    WebElement mytable = driver.findElement(By.xpath("//*[@id='tableTotal']"));
                    // To locate rows of table.
                    List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
                    // To calculate no of rows In table.
                    int emp_count = rows_table.size();
                    System.out.println("Total number of rows = " + emp_count);
                    // Loop will execute till the last row of table.
                    for (int row = 0; row < emp_count; row++) {
                        // To locate columns(cells) of that specific row.
                        List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));

                        // To calculate no of columns(cells) In that specific row.
                        int columns_count = Columns_row.size();

                        // String strCellValu1e[columns_count];

                        // Loop will execute till the last cell of that specific row.
                        for (int column = 0; column < columns_count; column++) {
                            // To retrieve text from that specific cell.
                            String celtext = Columns_row.get(column).getText();
                            System.out.println("Cell value is :" + celtext);
                            String strCellValue = celtext.replace(",", " ");

                            // // Step 7 results
                            // driver.findElement(By.xpath("//tr[@id='trbody']/td/div[2]")).click();
                            // String a = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[3]")).getText();
                            // System.out.println(a);
                            // driver.findElement(By.xpath("//tr[@id='trbody']/td/div[4]")).click();
                            // String b = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[4]")).getText();
                            // System.out.println(b);
                            //
                            // driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[5]")).click();
                            // String c = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[5]")).getText();
                            // System.out.println(c);
                            // driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[6]")).click();
                            // String d = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[6]")).getText();
                            // System.out.println(d);
                            // driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[7]")).click();
                            // String e = driver.findElement(By.xpath("//table[@id='tableTotal']/tbody/tr/td[7]")).getText();
                            // System.out.println(e);
                            // String results = driver.findElement(By.xpath(".//*[@id='trbody']/td/div[3]/table/tbody/tr/td/table")).getText();
                            // System.out.println(results);
                            // String resultsTotal = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();
                            // System.out.println(resultsTotal);

                            // Step 6 select Px Fact
                            // driver.findElement(By.xpath(".//*[@id='pxFact']")).click();
                            // // Step 7 Click on Rechercher
                            // driver.findElement(By.cssSelector("#trbody > td")).click();
                            // driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
                            // Thread.sleep(5000);
                            // String resultsTotal1 = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();
                            // System.out.println(resultsTotal1);

                            // to catch all web elements into list
                            // List<WebElement> myList = driver.findElements(By.xpath(".//*[@id='tableTotal']"));
                            //
                            // // myList contains all the web elements
                            // // if you want to get all elements text into array list
                            // List<String> all_elements_text = new ArrayList<>();
                            //
                            // for (int i1 = 0; i1 < myList.size(); i1++) {
                            //
                            // // loading text of each element in to array all_elements_text
                            // all_elements_text.add(myList.get(i1).getText());
                            //
                            // // to print directly
                            // System.out.println(myList.get(i1).getText());
                            //
                            // }
                            // Store product Price in a list
                            // List<WebElement> price = driver.findElements(By.xpath(".//*[@id='tableTotal']"));
                            // // System.out.println(price.size());
                            // for (WebElement p : price) {
                            // String pricewithText = p.getText();
                            //
                            // try {
                            // // Retrieving only numeric value from Rs.145 (example). ReplaceAll replaces any non-digits with blank using "\\D
                            // // (file://d/)"
                            // // for (int k = 0; k < pricewithText.length(); k++) {
                            // int onlyNumeric = Integer.parseInt(pricewithText.replaceAll("\\D", ""));
                            // System.out.println(onlyNumeric);
                            // // }
                            // // if(onlyNumeric>=100 && onlyNumeric<=500)
                            // // {
                            // // int a= onlyNumeric;
                            // // System.out.println(a);
                            // // }

                            // Converting int to Integer object to get size of books between 100 and 500 inclusive
                            // Integer intObj = new Integer(onlyNumeric);
                            // System.out.println(intObj.SIZE);
                            // } catch (NumberFormatException ex) {
                            // System.out.println(ex);
                            // }
                            // }
                        }
                    }
                }
            }
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
            driver.quit();
        }

    }

}
