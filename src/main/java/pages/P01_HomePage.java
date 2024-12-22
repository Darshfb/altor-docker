package pages;

import actions.CustomDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static pages.PageBase.shortWait;

public class P01_HomePage {
    // Webdriver
    WebDriver driver;

    // initialize constructor
    public P01_HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private final By singIn_Button = By.xpath("//font[normalize-space()='Sign In']");

    public P01_HomePage clickSignInButton() {
        shortWait(driver).until(ExpectedConditions.elementToBeClickable(singIn_Button));
        new CustomDecorator(driver, singIn_Button).click();
        return this;
    }

    public String getLoginMessage()
    {
        // Temporary locator -> Cause this locator doesn't related to this page
        return driver.findElement(By.xpath("//h1[normalize-space()='Online Banking Login']")).getText();
    }


}
