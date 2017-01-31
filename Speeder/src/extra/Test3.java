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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test3 {

    public static void main(String[] args) throws InterruptedException, Exception {
        // System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        // WebDriver driver = new ChromeDriver();
        WebDriver driver = new FirefoxDriver();
        // Runtime.getRuntime().exec("C:\\Users\\E495371\\Desktop\\speeder\\AutNew1.exe");
        driver.manage().window().maximize();

        Thread.sleep(5000);
        // String appUrl = "http://optics.citroen.preprod.inetpsa.com/login.do";
        // driver.get(appUrl);
        // driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");
        driver.get("http://optics.citroen.preprod.inetpsa.com/login.do");
        // Thread.sleep(5000);
        // Runtime.getRuntime().exec("C:\\Users\\E495371\\Desktop\\speeder\\AutNew2.exe");
        Thread.sleep(5000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Animation commerciale")).click();
        driver.findElement(By.linkText("Suivi des ventes Filiale")).click();
        driver.findElement(By.linkText("Ventes par axe client")).click();

        WebElement country = driver.findElement(By.name("cdMarque"));
        List<WebElement> countries = country.findElements(By.tagName("option"));

        for (int i = 0; i < countries.size(); i++) {
            String value = countries.get(i).getText();

            Select dropdown = new Select(country);
            Thread.sleep(5000);
            dropdown.selectByIndex(i);
            Thread.sleep(5000);
            System.out.println(value);

            WebElement pays = driver.findElement(By.name("cdPays"));
            List<WebElement> paysList = pays.findElements(By.tagName("option"));

            for (int j = 0; j < paysList.size(); j++) {
                System.out.println(paysList.size());

                WebDriverWait wait = new WebDriverWait(driver, 30);
                WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("cdPays")));
                title.getSize();

                String paysListValue = paysList.get(j).getText();
                System.out.println(paysListValue);
                Select dropdown2 = new Select(pays);
                Thread.sleep(10000);
                dropdown2.selectByIndex(j);
                Thread.sleep(10000);
                // System.out.println(paysListValue);
            }

        }

        // List<WebElement> menu1 = driver.findElements(By.xpath("//*[@id='bodyFiltre']/fieldset/form/fieldset[1]/table/tbody/tr[3]/td[2]/select"));
        // for (WebElement webElement : menu1) {
        // String abc = webElement.getText();
        // System.out.println(abc);
        //
        // File file = new File("C:\\SpeedTest.xls");
        // // if file doesnt exists, then create it
        // if (!file.exists()) {
        // file.createNewFile();
        // }
        // Write Excel file using jxl api
        // WritableWorkbook wworkbook;
        // wworkbook = Workbook.createWorkbook(file);
        // WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
        //
        // for (int rows = 0; rows < abc.length(); rows++) {
        // wsheet.addCell(new Label(0, rows, abc));
        // wsheet.addCell(new Number(1, rows, 3.1459 + rows));
        // wsheet.addCell(new Label(2, rows, "third Coloum " + rows));
        // wsheet.addCell(new Number(3, rows, 7895 + rows));
        // System.out.println("success");
        // }
        // wworkbook.write();
        // wworkbook.close();

        // {
        // Select dropdown = new Select(webElement);
        // Thread.sleep(10000);
        // dropdown.selectByValue(abc);
        // // dropdown.selectByIndex(1);
        // System.out.println("values taken");
        // }
        // }
        // for (WebElement webElement : menu1) {
        // WebElement value = menu1.get(webElement).getText().toString();
        // System.out.println(webElement);
        // }
        // for (int i = 1; i < menu1.size(); i++) {
        // String value = menu1.get(i).getText().toString();
        // System.out.println("Value of first cBox : " + i + "is : " + value);
        // }
        // By.xpath("//*[@id='bodyFiltre']/fieldset/form/fieldset[1]/table/tbody/tr[3]/td[2]/select")
        // Select dropdown = new Select(By.xpath("//*[@id='bodyFiltre']/fieldset/form/fieldset[1]/table/tbody/tr[3]/td[2]/select"));
        // dropdown.selectByValue(menu1);

        // List<WebElement> menu2 =
        // driver.findElements(By.xpath("//*[@id='bodyFiltre']/fieldset/form/fieldset[1]/table/tbody/tr[3]/td[3]/select"));
        //
        // for (int j = 0; j < menu2.size(); j++) {
        // String value2 = menu2.get(j).getText().toString();
        // System.out.println("Value of scnd cBox : " + j + "is : " + value2);
        //
        // }

        //
        // dropdown.selectByValue("prog");
        // driver.close();

    }

}
