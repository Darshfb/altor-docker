package pages;

import actions.CustomDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static pages.PageBase.shortWait;

public class P02_LoginPage
{
    WebDriver driver;
    public P02_LoginPage(WebDriver driver)
    {
        this.driver = driver;
    }

    private final By userNameTextField = By.xpath("//input[@id='uid']");
    private final By passwordTextField = By.xpath("//input[@id='passw']");
    private final By loginButton = By.xpath("//input[@name='btnSubmit']");

    public P02_LoginPage enterUserName(String userName)
    {
        shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(userNameTextField));
        new CustomDecorator(driver, userNameTextField).sendKeys(userName);
        return this;
    }

    public P02_LoginPage enterPassword(String password)
    {
        shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(passwordTextField));
        new CustomDecorator(driver, passwordTextField).sendKeys(password);
        return this;
    }

    public void login()
    {
        shortWait(driver).until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        new CustomDecorator(driver, loginButton).click();
    }

    public Boolean checkAdminLoggedInSuccessfully(){
        return driver.findElement(By.xpath("//h1[normalize-space()='Hello Admin User']")).getText().equals("Hello Admin User");
    }

    public String getLoginMessage()
    {
        return driver.findElement(By.xpath("//h1[normalize-space()='Hello Admin User']")).getText();
    }




}
