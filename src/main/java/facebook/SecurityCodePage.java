package facebook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SecurityCodePage extends Page {

    public SecurityCodePage(WebDriver driver) {
        super(driver);
    }

    private By codeInput = By.name("code");
    private By continueButton = By.xpath("//button[text()='Continue']");
    private By cancelButton = By.xpath("//a[text()='Cancel']");
    private By chooseFileButton = By.name("upload_meta");
    private By submitChoosePictureButton = By.name("submit[Continue]");
    private By okButton = By.name("submit[OK]");
    private By photoUploadedText = By.xpath("//div[text()='Photo Uploaded']");
    private By loginButton = By.name("login");

    public void fillCodeInput(String code) {
        getElement(codeInput).sendKeys(code);
    }

    public void pressContinueButton() {
        getElement(continueButton).click();
    }

    public void pressContinueButton2() {
        getElement(submitChoosePictureButton).click();
    }

    public void pressLogin() {
        getElement(loginButton).click();
    }

    public void pressOK() {
        getElement(okButton).click();
    }

    public void uploadPicture(String filePath) {
        getElement(chooseFileButton).sendKeys(filePath);
    }

    public boolean isPictureAcceptedForReview() {
        return isElementPresent(photoUploadedText);
    }

    public boolean isLoginButtonPresent() {
        return isElementPresent(loginButton);
    }

    public void pressCancelButton() {
        getElement(cancelButton).click();
    }

}

