package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    public WebDriver driver = null;


    @BeforeMethod()
    public void initialize(){
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver(chromeOptions);

        //Navigate to site
        driver.get("http://sdetchallenge.fetch.com/");
    }

//    @AfterMethod()
//    public void shutDown(){
//        driver.quit();
//    }
}
