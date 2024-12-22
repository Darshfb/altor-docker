package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P01_HomePage;
import retry_test.RetryAnalyzer;

import static drivers.DriverHolder.getDriver;

public class TC01_Home extends TestBase
{
    @Test(priority = 1, description = "Validate signIn button Navigate to login page", retryAnalyzer = RetryAnalyzer.class)
    public void validateSignInButtonNavigateToLoginPage()
    {
        // Hard assertion -> We use it to stop the whole test execution in failure
        // Soft assertion -> To complete running without stopping test execution in failure
        new P01_HomePage(getDriver()).clickSignInButton();
        Assert.assertEquals(new P01_HomePage(getDriver()).getLoginMessage(), "Online Banking Login");
    }

}
