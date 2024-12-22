package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.P02_LoginPage;
import retry_test.RetryAnalyzer;
import utility.Utility;

import static drivers.DriverHolder.getDriver;

public class TC02_Login extends TestBase {
    String userName = Utility.getLoginData("userName");
    String password = Utility.getLoginData("password");
    String loginMSG_FR = Utility.getExcelData(1, 0, "ar");

    @Test(priority = 1, description = "Check login with valid data", retryAnalyzer = RetryAnalyzer.class)
    public void validateLoginWithValidData() {
        new P02_LoginPage(getDriver())
                .enterUserName(userName)
                .enterPassword(password)
                .login();
        Assert.assertTrue(new P02_LoginPage(getDriver()).checkAdminLoggedInSuccessfully());
    }

}
