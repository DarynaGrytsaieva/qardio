import email.InboxPage;
import email.PutsPage;
import facebook.LoginPage;
import facebook.SecurityCodePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class Fixture {
    private static WebDriver driver;
    public static String generatedEmail;
    public static InboxPage inboxPage;
    public static LoginPage loginPage;
    public static SecurityCodePage securityCodePage;


    @BeforeSuite
    public void setEnv() {
        System.setProperty(
                "webdriver.chrome.driver",
                "webdriver/chromedriver");

        System.setProperty(
                "webdriver.gecko.driver",
                "webdriver/geckodriver");


        ChromeOptions options = new ChromeOptions();
        options.addArguments("-incognito");
        options.addArguments("--disable-popup-blocking");

        //DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        //capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        generatedEmail = new PutsPage(driver).createNewEmail();
        System.out.println("Generated email: " + generatedEmail);

        inboxPage = new InboxPage(driver, generatedEmail);
        loginPage = new LoginPage(driver);
        securityCodePage = new SecurityCodePage(driver);

    }

    @AfterSuite
    public void resetEnv() {
        driver.quit();
    }


}
