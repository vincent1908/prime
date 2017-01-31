package extra;
/// *
// * Creation : 21 déc. 2016
// */
// package demo;
//
// import java.util.concurrent.TimeUnit;
//
// import org.openqa.selenium.By;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.support.ui.Select;
//
// public class SumitTest {
//
// public static void main(String[] args) throws Exception {
// System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
// WebDriver driver = new FirefoxDriver();
// driver.manage().window().maximize();
// Thread.sleep(5000);
// driver.get("http://e491188:9ijn3adm@optics.citroen.preprod.inetpsa.com/login.do");
// Thread.sleep(5000);
// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
// driver.findElement(By.linkText("Animation commerciale")).click();
// driver.findElement(By.linkText("Suivi des ventes Filiale")).click();
// driver.findElement(By.linkText("Ventes par axe client")).click();
//
// System.out.println("Execution starts ..");
//
// // public static void DropdwnSelect(WebDriver driver,String Dropdwnlocator,String Dropdwnn_val,String L_value)
// for (int i = 0; i <= 1; i++) {
// WebElement Main_dropdown = driver.findElement(By.name("cdMarque"));
// Select dropdown = new Select(Main_dropdown);
//
// dropdown.selectByIndex(i);
// // selectByVisibleText("PEUGEOT");
// WebElement optionc = dropdown.getFirstSelectedOption();
// System.out.println(optionc.getText()); // output "India"
// System.out.println("Clicking Dropdown by xpath " + i);
//
// for (i
// ) {
// WebElement Main_dropdown2 = driver.findElement(By.name("cdPays"));
// Select dropdown2 = new Select(Main_dropdown2);
//
// dropdown2.selectByIndex(j);
// // selectByVisibleText("PEUGEOT");
// WebElement optionc2 = dropdown2.getFirstSelectedOption();
// System.out.println(optionc2.getText()); // output "India"
// System.out.println("Clicking Dropdown by xpath sub menu " + j);
// Thread.sleep(2000);
// }
//
// Thread.sleep(5000);
//
// }
//
// // driver.close();
// }
// }