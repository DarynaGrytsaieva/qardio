package facebook;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Page {
    private String page;
    WebDriver driver;
    private Wait<WebDriver> wait;
    private String originalWindowHandle;

    Page(String page, WebDriver driver) {
        this.page = page;
        this.driver = driver;
        wait = new FluentWait<>(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(1, SECONDS)
                .ignoring(NoSuchElementException.class);
        originalWindowHandle = driver.getWindowHandle();
    }

    Page(WebDriver driver) {
        this.driver = driver;
        wait = new FluentWait<>(driver)
                .withTimeout(7, SECONDS)
                .pollingEvery(1, SECONDS)
                .ignoring(NoSuchElementException.class);
        originalWindowHandle = driver.getWindowHandle();
    }

    public void openPage() {
        driver.get(page);
    }

    public void toHomePage() {
        driver.get("https://www.facebook.com/");
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public boolean isOnHomePage() {
        System.out.println(driver.getCurrentUrl());
        return driver.getCurrentUrl().equals("https://www.facebook.com/");
    }

    public boolean isLoggedIn() {
        return true;
    }

    public boolean isLoggedOut() {
        return true;
    }

    WebElement getElement(By selector) {
        return wait.until(driver -> driver.findElement(selector));
    }

    boolean isElementPresent(By selector) {
        try {
            return wait.until(driver -> driver.findElement(selector).isDisplayed());
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitForElementToDisplay(By selector) {
        wait.until(driver -> driver.findElement(selector).isDisplayed());
    }

    public void waitForElementToEnable(By selector) {
        wait.until(driver -> driver.findElement(selector).isEnabled());
    }

    public void waitForAttribute(By selector, String text) {
        wait.until(driver -> driver.findElement(selector).getAttribute(text) != null);
    }

    public void waitForUrlEnd(String text) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(10, SECONDS)
                .pollingEvery(1, SECONDS);
        wait.until(webDriver -> webDriver.getCurrentUrl().endsWith(text));

    }

    public String getCurrentPageURL() {
        return driver.getCurrentUrl();
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    public void openNewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank', '_blank');");

        Set<String> tabs = driver.getWindowHandles();
        int count = tabs.size();
        int newTabIndex = count - 1;
        driver.switchTo().window(tabs.toArray()[newTabIndex].toString());

    }

    public void returnToOriginalTab() {
        driver.switchTo().window(originalWindowHandle);
        driver.switchTo().defaultContent();
    }

    public boolean isAlertPresents() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
