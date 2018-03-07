package mailbox;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import static java.util.concurrent.TimeUnit.SECONDS;

public class InboxPage {
    private String email;
    private WebDriver driver;
    private Wait<WebDriver> wait;
    private By mailTitle = By.xpath("//td[contains(text(),'is your Facebook confirmation code')]");
    private By message = By.xpath("//table/tbody/tr");

    public InboxPage(WebDriver driver, String email) {
        this.email = email;
        this.driver = driver;
        driver.manage().timeouts().pageLoadTimeout(20, SECONDS);
        wait = new FluentWait<>(driver)
                .withTimeout(7, SECONDS)
                .pollingEvery(1, SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    public void openMailBox() {
        String url = email.split("@")[0];
        driver.get("https://putsbox.com/" + url + "/inspect");
    }

    public String getConfirmationCode() {
        String code = getElement(mailTitle).getText().split(" ")[0];
        System.out.println("Confirmation code: " + code);
        return code;
    }

    public boolean isEmailReceived() {
        return isElementPresent(message);
    }

    private WebElement getElement(By selector) {
        return wait.until(driver -> driver.findElement(selector));
    }

    private boolean isElementPresent(By selector) {
        try {
            return wait.until(driver -> driver.findElement(selector).isDisplayed());
        } catch (TimeoutException e) {
            return false;
        }
    }


}
