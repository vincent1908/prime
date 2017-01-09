/*
 * Creation : 27 sept. 2016
 */
package demo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Filiale {

    public static void main(String[] args) throws InterruptedException, Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        Thread.sleep(1000);

        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");

        Thread.sleep(1000);

        // Animation Commerciale

        driver.findElement(By.linkText("Animation commerciale")).click();

        // Step 1 Click on the Suivi des ventes Filiale

        driver.findElement(By.id("2#1#0")).click();

        // Step 2 Select Ventes par axe client

        driver.findElement(By.linkText("Ventes par axe client")).click();

        // Step 3 Select Marque " CITROEN"

        try {

            // This statement makes dropdown 1 dyamnic selection * Selction in dropdown 'CITROEN

            // WebElement pays = driver.findElement(By.name("cdMarque")); // List<WebElement> paysList = pays.findElements(By.tagName("option"));
            // Thread.sleep(1000); // for (int i = 0; i < paysList.size(); i++) {

            for (int i = 0; i < 1; i++) {
                WebElement pays1 = driver.findElement(By.name("cdMarque"));
                List<WebElement> paysList1 = pays1.findElements(By.tagName("option"));
                Thread.sleep(1000);
                String paysListValue = paysList1.get(i).getText();
                System.out.println(paysListValue);

                Select dropdown1 = new Select(pays1);
                Thread.sleep(1000);
                dropdown1.selectByIndex(i);

                // This statement makes dropdown 2 dyamnic selection

                // Step 4 Select Pays "suisse " (there will be multiple selections availble for Pays)
                // WebElement pays2 = driver.findElement(By.name("cdPays"));
                // List<WebElement> paysList2 = pays2.findElements(By.tagName("option"));
                // Thread.sleep(1000);
                // for (int j = 0; j < paysList2.size(); j++) {
                for (int j = 1; j < 2; j++) {
                    WebElement pays3 = driver.findElement(By.name("cdPays"));

                    List<WebElement> paysList3 = pays3.findElements(By.tagName("option"));
                    Thread.sleep(1000);
                    String paysListValue2 = paysList3.get(j).getText();
                    System.out.println(paysListValue2);

                    Select dropdown2 = new Select(pays3);
                    Thread.sleep(1000);
                    dropdown2.selectByIndex(j);
                    // Step 5 From "Période d'analyse" select P - Du & Au
                    driver.findElement(By.id("data1")).clear();
                    driver.findElement(By.id("data1")).sendKeys("01/11/2016");
                    driver.findElement(By.id("data2")).clear();
                    driver.findElement(By.id("data2")).sendKeys("31/12/2016");

                    // Step 6 select PVP

                    // Step 7 Click on Rechercher
                    driver.findElement(By.cssSelector("#trbody > td")).click();
                    driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
                    Thread.sleep(1000);

                    String resultsOne = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();
                    System.out.println(resultsOne);

                }
            }
        }

        catch (

        StaleElementReferenceException e) {
            e.printStackTrace();
            driver.close();
        }

        driver.close();
    }

}

// Step 7 results

// 1 ) take list and get count

// List<WebElement> Exprt_product_list = BasicOperation.getElements(objDataMap.get("Product_list"));
// if(Exprt_product_list.size() > 1){

// WebElement pas1 = driver.findElement(By.xpath(".//*[@id='tableTotal']/tbody/tr/td"));
// System.out.println("Values in a list :" + pas1.getSize());

// String pas1array[]

// List<WebElement> List1 = pas1.findElements(By.xpath(".//*[@id='tableTotal']/tbody/tr/td"));
//
// int size = List1.size() - 2;
// System.out.println("size=" + size);
// String pas1array[] = new String[size];
//
// for (i = 3; i <= List1.size(); i++) {
//
// pas1array[i - 2] = driver.findElement(By.xpath("//*[@id='tableTotal']/tbody/tr/td[" + i + "]")).getText();
// System.out.println("Values of the record no" + i + "is :" + pas1array[i - 2]);
//
// }
//
// 2) for(int i=3;i<list.length;i++)
// {
//
// string array s= //*[@id='tableTotal']/tbody/tr/td[3]. jet text
//
// reo;ace ,
// convert to inter i=i+
//
// }
//// for(string length)
//// {
////
//// }
//
// .//*[@id='tableTotal']/tbody/tr/td[3]

// String str = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();
// System.out.println(str);
// String s[] = str.replaceAll(",", "").split(" ");
// System.out.println(s);
//
// long[] array = new long[s.length - 1];
// for (int j1 = 1; j1 < array.length; j1++) {
// array[j1] = Double.valueOf(s[j1]).longValue();
// System.out.println(array[j1]);
// }

// String stringOne[] = resultsOne.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
// String stringOne[] = resultsOne.split(" ");
// int[] results = new int[stringOne.length];
// long[] results = new long[stringOne.length];
// double[] results = new double[stringOne.length];
// BigDecimal[] a = new BigDecimal[stringOne.length];
// String stringOne[] = resultsOne.split(" ");

// for (int k = 0; k < stringOne.length; k++) {
// System.out.println(stringOne[k]);
// try {
// results[k] = Integer.parseInt(stringOne[k].replaceAll(",", ""));
// results[k] = Double.parseDouble(stringOne[k].replaceAll(",", ""));

// // results[k] = Long.valueOf(stringOne[k].replaceAll(",", ""));
// results[k] = Math.floor(Double.parseDouble(stringOne[k].replaceAll(",", "")));
// Long l1 = new Long(stringOne[k].replaceAll(",", ""));
//
// // DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//
// // DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
// // decimalFormat.setParseBigDecimal(true);
//
// // parse the string
// // BigDecimal bigDecimal = (BigDecimal) DecimalFormat
// // System.out.println(bigDecimal);
// //
// // DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(in_ID);
// // nf.setParseBigDecimal(true);
//
// // a[k] = BigDecimal.
// System.out.println(results[k] + " L1:" + l1);
// } catch (NumberFormatException nfe) {
// nfe.printStackTrace();
// System.out.println("Error" + results[k]);
// }
// }
