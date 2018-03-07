package email;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.util.concurrent.TimeUnit.SECONDS;

public class PutsPage {
    private WebDriver driver;
    private static final String URL = "https://putsbox.com/";

    public PutsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By createButton = By.xpath("//button[text()='Create a PutsBox']");

    public String createNewEmail() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        driver.get(URL);
        driver.findElement(createButton).click();
        return driver.findElement(By.id("putsbox-token-input")).getAttribute("value");
    }


}
