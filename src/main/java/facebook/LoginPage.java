package facebook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public class LoginPage extends Page {
    private static final String URL = "https://facebook.com/";

    public LoginPage(WebDriver driver) {
        super(URL, driver);
        this.driver = driver;
    }

    private By error = By.id("reg_error_inner");

    //--- Existing user ---
    private By existingEmailInput = By.id("email");
    private By existingPasswordInput = By.id("pass");
    private By loginButton = By.id("u_0_2");

    //--- New user ---
    private By firstNameInput = By.name("firstname");
    private By lastNameInput = By.name("lastname");
    private By emailInput = By.name("reg_email__");
    private By reEnterEmailInput = By.name("reg_email_confirmation__");
    private By passwordInput = By.name("reg_passwd__");
    private By birthday = By.id("u_0_s");
    private By birthdayMonthSelect = By.id("month");
    private By birthdayDaySelect = By.id("day");
    private By birthdayYearSelect = By.id("year");
    private By gender = By.id("u_0_u");
    private By femaleGenderRadio = By.xpath("//label[text()='Female']");
    private By maleGenderRadio = By.xpath("//label[text()='Male']");
    private By createButton = By.name("websubmit");


    //--- Login ---
    public void fillExistingEmailToLogin(String email) {
        getElement(existingEmailInput).sendKeys(email);
    }

    public void fillExistingPasswordToLogin(String password) {
        getElement(existingPasswordInput).sendKeys(password);
    }

    public void pressLoginButton() {
        getElement(loginButton).click();
    }

    public void pressCreateAccount() {
        getElement(createButton).click();
    }


    //---  Personal data ---
    public void fillNewFirstName(String firstName) {
        getElement(firstNameInput).sendKeys(firstName);
    }

    public void fillNewLastName(String lastName) {
        getElement(lastNameInput).sendKeys(lastName);

    }

    public void fillNewEmail(String email) {
        getElement(emailInput).sendKeys(email);
    }

    public void fillReEnterEmail(String email) {
        waitForElementToDisplay(reEnterEmailInput);
        getElement(reEnterEmailInput).sendKeys(email);
    }

    public void fillNewPassword(String password) {
        getElement(passwordInput).sendKeys(password);
    }

    public void chooseBirthdayMonth(int month) {
        Select dropdown = new Select(getElement(birthdayMonthSelect));
        dropdown.selectByValue(String.valueOf(month));
    }

    public void chooseRandomBirthdayMonth() {
        int n = new Random().nextInt(12) + 1;
        Select dropdown = new Select(getElement(birthdayMonthSelect));
        dropdown.selectByValue(String.valueOf(n));
    }

    public void chooseEmptyBirthdayMonth() {
        Select dropdown = new Select(getElement(birthdayMonthSelect));
        dropdown.selectByValue(String.valueOf(0));
    }

    public void chooseBirthdayDay(int dayNumber) {
        Select dropdown = new Select(getElement(birthdayDaySelect));
        dropdown.selectByValue(String.valueOf(dayNumber));
    }

    public void chooseRandomBirthdayDay() {
        //always existing only
        int n = new Random().nextInt(27) + 1;
        Select dropdown = new Select(getElement(birthdayDaySelect));
        dropdown.selectByValue(String.valueOf(n));
    }

    public void chooseEmptyBirthdayDay() {
        Select dropdown = new Select(getElement(birthdayDaySelect));
        dropdown.selectByValue(String.valueOf(0));
    }

    public void chooseBirthdayYear(int year) {
        Select dropdown = new Select(getElement(birthdayYearSelect));
        dropdown.selectByValue(String.valueOf(year));
    }

    public void chooseRandomBirthdayYear() {
        //adults only
        int n = new Random().nextInt(100) + 1905;
        Select dropdown = new Select(getElement(birthdayYearSelect));
        dropdown.selectByValue(String.valueOf(n));
    }

    public void chooseEmptyBirthdayYear() {
        Select dropdown = new Select(getElement(birthdayYearSelect));
        dropdown.selectByValue(String.valueOf(0));
    }

    public void chooseFemale() {
        WebElement element = getElement(femaleGenderRadio);
        waitForElementToDisplay(femaleGenderRadio);
        waitForElementToEnable(femaleGenderRadio);
        element.click();
    }

    public void chooseMaleGender() {
        WebElement element = getElement(maleGenderRadio);
        waitForElementToDisplay(maleGenderRadio);
        waitForElementToEnable(maleGenderRadio);
        element.click();
    }


    //--- error checks ---
    public boolean isErrorWithTextPresent(String errorText) {
        waitForElementToDisplay(error);
        System.out.println("Error: " + getElement(error).getText());
        return getElement(error).getText().equals(errorText);
    }

    public boolean isRegistrationForbidden() {
        return isElementPresent(By.xpath("//div[text()='Sorry, we are not able to process your registration.']"));
    }


    //--- highlighted fields ---
    private boolean isFieldHighlighted(By selector) {
        WebElement element = getElement(selector);
        waitForAttribute(selector, "aria-invalid");
        return element.getAttribute("aria-invalid").equals("true");

    }

    public boolean isFirstNameHighlighted() {
        return isFieldHighlighted(firstNameInput);
    }

    public boolean isLastNameHighlighted() {
        return isFieldHighlighted(lastNameInput);
    }

    public boolean isEmailHighlighted() {
        return isFieldHighlighted(emailInput);
    }

    public boolean isReEnterEmailHighlighted() {
        waitForElementToDisplay(reEnterEmailInput);
        return isFieldHighlighted(reEnterEmailInput);
    }

    public boolean isPasswordHighlighted() {
        return isFieldHighlighted(passwordInput);
    }

    public boolean isBirthdayHighlighted() {
        System.out.println("is birthday highlighted: " + isFieldHighlighted(birthday));
        return isFieldHighlighted(birthday);
    }

    public boolean isGenderHighlighted() {
        System.out.println("is gender highlighted: " + isFieldHighlighted(gender));
        return isFieldHighlighted(gender);
    }


}
