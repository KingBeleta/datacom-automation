package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.datacom.framework.base.BasePage;
import com.datacom.framework.base.BaseTest;
import com.datacom.framework.locators.DatacomLocators;
import com.datacom.framework.models.UserData;
import com.datacom.framework.pages.DatacomPage;
import com.datacom.framework.utils.AssertUtil;
import com.datacom.framework.utils.DataProviders;
import com.datacom.framework.utils.ScreenshotUtil;

@Listeners(com.datacom.framework.reports.TestListener.class)
public class DatacomBugsFormTestPhoneNumber extends BaseTest {

    private BasePage basePage;
    private DatacomPage form;
    private SoftAssert softAssert;

    @BeforeMethod(alwaysRun = true)
    public void setupTestObjects() {
        basePage = new BasePage(getPage());
        form = new DatacomPage(getPage());
        softAssert = new SoftAssert();
    }

    @Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
    public void testValidInputPhone(UserData user) {
        runPhoneTest(user, "Successfully registered");
    }

    @Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
    public void testInvalidInputPhone(UserData user) {
        runPhoneTest(user, "Unsuccessful - Phone Number field should only accept numeric values ");
    }

    @Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
    public void testLessMinimumInputPhone(UserData user) {
        runPhoneTest(user, "The phone number should contain at least 10 characters!");
    }

    @Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
    public void testEmptyInputPhone(UserData user) {
        runPhoneTest(user, "The phone number should contain at least 10 characters!");
    }

    private void runPhoneTest(UserData user, String expectedMsg) {

        basePage.navigateToDatacomWebApp();
        logStep("Navigating to Datacom Bugs-Form");

        form.fillOutTheForm(user);
        logStep("Filling-up the fields", ScreenshotUtil.capture(getPage(),  getTest(), user.getTestName()));

        form.click(DatacomLocators.REGISTER_BUTTON);
        logStep("Click register");

        String msg = form.getRegistrationMessage();
        logStep("Verify registration message", ScreenshotUtil.capture(getPage(), getTest(), "RegistrationMsg_" + user.getTestName()));
        AssertUtil.softAssertContains("Registration Message: ", getPage(), softAssert, msg, expectedMsg);

        form.verifyField("Registered Last Name: ", softAssert, form.getResultsLastName(), user.getLastName());
		form.verifyField("Registered Phone Number: ", softAssert, form.getResultsPhoneNumber(), user.getPhone());
		form.verifyField("Registered Email: ", softAssert, form.getResultsEmail(), user.getEmail());

        softAssert.assertAll();
    }
}
