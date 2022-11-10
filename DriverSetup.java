package com.xinfinit.automation.browserFactory;

import com.xinfinit.automation.pages.SignUp;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverSetup extends AppConfiguraion{
    public static Properties prop;
    public static WebDriver driver;

    private static long PAGE_LOAD_TIMEOUT = 60;
    private static  long IMPLICIT_WAIT= 60;

    static {
        initializeProperties();
        initializeDriver();
    }

    private static void initializeProperties(){
        try {
            prop = new Properties();
            FileInputStream f = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/Itrade/config.properties");
            FileInputStream fe = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/Itrade/widgets.properties");
            prop.load(f);
            prop.load(fe);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void initializeDriver(){
        WebDriverManager.chromedriver().browserVersion("104.0.5112.101").setup();
        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("Chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("Safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(prop.getProperty("url"));
    }

}
