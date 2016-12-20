package autoIt;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class demoAuto {

    public static void main(String[] args) throws IOException, Exception {

        WebDriver driver = new FirefoxDriver();
        Thread.sleep(3000);
        driver.manage().window().maximize();
        Thread.sleep(3000);
        // Runtime.getRuntime().exec("D:\\shr\\vin\\Auto_IT\\AutoNew2.exe");

        driver.get("https://www.engprod-charter.net/");
        Thread.sleep(3000);
        Runtime.getRuntime().exec("D:\\shr\\vin\\Auto_IT\\AutoNew2.exe");
        Thread.sleep(3000);

    }

}
