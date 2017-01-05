/*
 * Creation : 5 janv. 2017
 */
package demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestCase1 {

    public static void main(String[] args) throws Exception {

        // pull values to string array from appln .....
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");
        driver.findElement(By.cssSelector("form[name=\"RechercherMessageForm\"] > table > tbody > tr > td")).click();
        driver.findElement(By.linkText("Animation commerciale")).click();
        driver.findElement(By.linkText("Suivi des ventes Filiale")).click();
        driver.findElement(By.linkText("Ventes par axe client")).click();
        driver.findElement(By.name("cdMarque")).click();
        driver.findElement(By.cssSelector("option[value=\"C\"]")).click();
        driver.findElement(By.name("cdPays")).click();
        new Select(driver.findElement(By.name("cdPays"))).selectByVisibleText("Bélux");
        // Step 5 From "Période d'analyse" select P - Du & Au
        driver.findElement(By.id("data1")).clear();
        driver.findElement(By.id("data1")).sendKeys("01/11/2016");
        driver.findElement(By.id("data2")).clear();
        driver.findElement(By.id("data2")).sendKeys("31/12/2016");
        // Step 7 Click on Rechercher
        driver.findElement(By.cssSelector("#trbody > td")).click();
        driver.findElement(By.cssSelector("#bodyFiltre > table > tbody > tr > td > a > img")).click();
        System.out.println("Result fetching");
        // values pulled to string
        // int a[]=null;
        // int number =0;
        String resultsOne = driver.findElement(By.xpath(".//*[@id='tableTotal']")).getText();

        resultsOne = resultsOne.substring(4, resultsOne.length());
        String[] items = resultsOne.replace(",", "").split(" ");
        // System.out.println("newChar:" + newChar);

        System.out.println("items:" + items);
        String[] results = new String[items.length];
        for (int i = 1; i < items.length; i++) {
            System.out.println("items:" + items[i]);
            results[i] = items[i];

            // results.
        }
        double[] numbers = new double[results.length];
        for (int i = 1; i < results.length; i++) {
            // Note that this is assuming valid input
            // If you want to check then add a try/catch
            // and another index for the numbers if to continue adding the others
            numbers[i] = Double.parseDouble(results[i]);
        }
        System.out.println("trsult:" + numbers);

        // for (int i = 0; i < items.length; i++) {
        // try {
        // results[i] = Integer.parseInt(items[i]);
        // System.out.println("no:" + results[i]);
        // } catch (NumberFormatException nfe) {
        // // NOTE: write something here if you need to recover from formatting errors
        // }
        //
        // }
        // String[] no = resultsOne.split("\\s+");
        // System.out.println("no:" + no);
        // int[] arrayCrit = new int[no.length];
        // System.out.println(resultsOne);
        // // String new ="";
        // // int resultsOneSize = driver.findElements(By.className("entetezone")).size();
        // // System.out.println(resultsOneSize);
        // for (int i = 0; i < no.length; i++) {
        // // String resultsStr = driver.findElement(By.xpath(".//*[@id='tableTotal']/tbody/tr/td[3+i]")).getText();
        // System.out.println("number:" + no[i]);
        // // reseult.re"",
        // // conert resut oneinteger = greg
        // // int a [i] = greg;
        // // new = resultsOne[i];
        // arrayCrit[i] = Integer.parseInt(no[i]);
        // System.out.println("number:" + arrayCrit[i]);
        //
        // //
        // }

        // System.out.println("String Array");
        // // values converted to string array
        // String stringOne[] = resultsOne.split(" ");
        // double[] results = new double[stringOne.length];
        // for (int i = 0; i < stringOne.length; i++) {
        // results[i] = Double.parseDouble(stringOne[i].replaceAll(",", ""));
        // System.out.println(results);
        //
        // }

        Thread.sleep(3000);
        driver.quit();

    }

}
