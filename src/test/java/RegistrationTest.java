import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class RegistrationTest extends Fixture {
    private int todayDay;
    private int todayMonth;
    private int todayYear;

    @BeforeClass
    public void setUp() {
        LocalDate now = LocalDate.now();
        todayDay = now.getDayOfMonth();
        todayMonth = now.getMonthValue();
        todayYear = now.getYear();
        loginPage.openPage();
    }

    @Test(dataProvider = "getValidData")
    public void shouldCreateNewAccountWithValidData(String firstName, String lastName, String email, String password) throws InterruptedException {
        //when
        loginPage.fillNewFirstName(firstName);
        actLikeHuman(1000);
        loginPage.fillNewLastName(lastName);
        actLikeHuman(1000);
        loginPage.fillNewEmail(generatedEmail);
        actLikeHuman(1000);
        loginPage.fillReEnterEmail(generatedEmail);
        actLikeHuman(1000);
        loginPage.fillNewPassword(password);
        actLikeHuman(1000);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();
        actLikeHuman(2000);
        loginPage.pressCreateAccount();
        assertFalse(loginPage.isRegistrationBlocked());
        securityCodePage.waitForElementToDisplay(By.name("code"));

        //and
        securityCodePage.openNewTab();
        inboxPage.openMailBox();
        assertTrue(inboxPage.isEmaileliveredInThreeMinutes());
        String code = inboxPage.getConfirmationCode();

        //and
        securityCodePage.returnToOriginalTab();
        securityCodePage.fillCodeInput(code);
        securityCodePage.pressContinueButton();


        //then
        if (securityCodePage.getCurrentPageURL().contains("checkpoint/block/")) {
            securityCodePage.uploadPicture(getClass().getResource("." + File.separator + "files" + File.separator + "avatar.jpg").getFile());
            assertTrue(securityCodePage.isPictureUploaded());
            securityCodePage.pressContinueButton2();

            assertTrue(securityCodePage.isPictureAcceptedForReview());
            securityCodePage.pressOK();
        }
        securityCodePage.isLoginButtonPresent();
        securityCodePage.pressLogin();


    }


    //--- invalid data ---
    @Test(dataProvider = "getInvalidNameData")
    public void shouldNotCreateAccountWithInvalidFirstName(String lastName, String firstName, String email, String password, String errorMessage) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();

        //and
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isErrorWithTextPresent(errorMessage));
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getInvalidNameData")
    public void shouldNotCreateAccountWithInvalidLastName(String firstName, String lastName, String email, String password, String errorMessage) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();

        //and
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isErrorWithTextPresent(errorMessage));
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getInvalidEmailData")
    public void shouldNotCreateAccountWithInvalidEmail(String firstName, String lastName, String email, String
            password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();

        //and
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isEmailHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithNotMatchingEmails(String firstName, String lastName, String email, String
            password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail("A" + email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();

        //and
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isReEnterEmailHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getInvalidPasswordData")
    public void shouldNotCreateAccountWithInvalidPassword(String firstName, String lastName, String email, String
            password, int month, int day, int year, String errorMessage) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseBirthdayMonth(month);
        loginPage.chooseBirthdayDay(day);
        loginPage.chooseBirthdayYear(year);
        loginPage.chooseMaleGender();

        //and
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isErrorWithTextPresent(errorMessage));
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getInvalidBirthdayData")
    public void shouldNotCreateAccountWithInvalidBirthday(String firstName, String lastName, String email, String
            password, int month, int day, int year, String errorMessage) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseBirthdayMonth(month);
        loginPage.chooseBirthdayDay(day);
        loginPage.chooseBirthdayYear(year);
        loginPage.chooseMaleGender();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isErrorWithTextPresent(errorMessage));
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getMinorBirthdayData")
    public void shouldNotCreateAccountOfMinor(String firstName, String lastName, String email, String password,
                                              int month, int day, int year) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseBirthdayMonth(month);
        loginPage.chooseBirthdayDay(day);
        loginPage.chooseBirthdayYear(year);
        loginPage.chooseMaleGender();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isRegistrationForbidden());
        assertTrue(loginPage.isOnHomePage());
    }


    //--- missing data ---

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithMissingFirstName(String firstName, String lastName, String email, String password) {
        //when
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isFirstNameHighlighted());
        assertTrue(loginPage.isOnHomePage());

    }

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithMissingLastName(String firstName, String lastName, String email, String password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseMaleGender();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isLastNameHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithMissingEmail(String firstName, String lastName, String email, String password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isEmailHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithMissingReEnterEmail(String firstName, String lastName, String email, String password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseMaleGender();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isReEnterEmailHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithMissingPassword(String firstName, String lastName, String email, String password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isPasswordHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithMissingBirthdayMonth(String firstName, String lastName, String email, String password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseEmptyBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isBirthdayHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithMissingBirthdayDay(String firstName, String lastName, String email, String password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseEmptyBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseFemale();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isBirthdayHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithMissingBirthdayYear(String firstName, String lastName, String email, String password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayYear();
        loginPage.chooseEmptyBirthdayYear();
        loginPage.chooseFemale();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isBirthdayHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }

    @Test(dataProvider = "getValidData")
    public void shouldNotCreateAccountWithMissingGender(String firstName, String lastName, String email, String password) {
        //when
        loginPage.fillNewFirstName(firstName);
        loginPage.fillNewLastName(lastName);
        loginPage.fillNewEmail(email);
        loginPage.fillReEnterEmail(email);
        loginPage.fillNewPassword(password);
        loginPage.chooseRandomBirthdayMonth();
        loginPage.chooseRandomBirthdayDay();
        loginPage.chooseRandomBirthdayYear();
        loginPage.pressCreateAccount();

        //then
        assertTrue(loginPage.isGenderHighlighted());
        assertTrue(loginPage.isOnHomePage());
    }


    @AfterMethod
    public void tearDown() {
        loginPage.deleteAllCookies();
        loginPage.toHomePage();
    }


    @DataProvider
    public Object[][] getValidData() {
        return new Object[][]{{"John", "Smith", "email2@gmail.com", "A3xK67MnG90"}};
    }

    @DataProvider
    public Object[][] getInvalidNameData() {
        return new Object[][]{
                {"John", "Smith@", "email@gmail.com", "A3xK67MnG90", "It looks like you entered a mobile number or email. Please enter your name."},
                {"John", "12345", "email@gmail.com", "A3xK67MnG90", "It looks like you entered a mobile number or email. Please enter your name."},
                {"John", ",?Brook", "email@gmail.com", "A3xK67MnG90", "This name has certain characters that aren't allowed. Learn more about our name policies."},
                {"John", "aaa", "email@gmail.com", "A3xK67MnG90", "Names on Facebook can't have a large number of repeating characters. Learn more about our name policies."},
                {"John", "bb", "email@gmail.com", "A3xK67MnG90", "We require everyone to use the name they use in everyday life, what their friends call them, on Facebook. Learn more about our name policies."}};
    }

    @DataProvider
    public Object[][] getInvalidEmailData() {
        return new Object[][]{
                {"John", "Smith", "mailbox", "A3xK67MnG90"},
                {"John", "Smith", "email@gmail", "A3xK67MnG90"},
                {"John", "Smith", "@gmail.com", "A3xK67MnG90"}};
    }

    @DataProvider
    public Object[][] getInvalidPasswordData() {
        return new Object[][]{
                {"John", "Smith", "email@gmail.com", "abc12", 10, 10, 1988, "Your password must be at least 6 characters long. Please try another."},
                {"John", "Smith", "email@gmail.com", "1234567", 10, 10, 1988, "Please choose a more secure password. It should be longer than 6 characters, unique to you, and difficult for others to guess."},
                {"John", "Smith", "email@gmail.com", "!!!!!!!", 10, 10, 1988, "Please choose a more secure password. It should be longer than 6 characters, unique to you, and difficult for others to guess."}};
    }

    @DataProvider
    public Object[][] getMinorBirthdayData() {
        int ageLimit = 13;
        return new Object[][]{
                {"John", "Smith", "email@gmail.com", "A3xK67MnG90", todayMonth, todayDay + 1, todayYear - ageLimit}};
    }

    @DataProvider
    public Object[][] getInvalidBirthdayData() {
        return new Object[][]{
                {"John", "Smith", "email@gmail.com", "A3xK67MnG90", todayMonth, todayDay, todayYear, "It looks like you entered the wrong info. Please be sure to use your real birthday."},
                {"John", "Smith", "email@gmail.com", "A3xK67MnG90", todayMonth + 1, todayDay, todayYear, "It looks like you entered the wrong info. Please be sure to use your real birthday."},
                {"John", "Smith", "email@gmail.com", "A3xK67MnG90", todayMonth, todayDay, todayYear - 1, "It looks like you entered the wrong info. Please be sure to use your real birthday."},
                {"John", "Smith", "email@gmail.com", "A3xK67MnG90", 2, 30, todayYear - 20, "The selected date is not valid."}};


    }

    private static void actLikeHuman(int wait) {
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
