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
public class DatacomBugsFormTestName extends BaseTest {

	private BasePage basePage;
	private DatacomPage form;
	private SoftAssert softAssert;

	@BeforeMethod(alwaysRun = true)
	public void setupPages() {
	    basePage = new BasePage(getPage());
	    form = new DatacomPage(getPage());
	    softAssert = new SoftAssert();
	}
	
	@Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
	public void testValidInputFirstName(UserData user) {
		runFirstNameTest(user, "Successfully registered");
	}

	@Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
	public void testInvalidInputFirstName(UserData user) {
		runFirstNameTest(user, "Successfully registered");
	}

	@Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
	public void testEmptyInputFirstName(UserData user) {
		runFirstNameTest(user, "Successfully registered");
	}

	@Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
	public void testValidInputLastName(UserData user) {
		runLastNameTest(user, "Successfully registered");
	}

	@Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
	public void testInvalidInputLastName(UserData user) {
		runLastNameTest(user, "Unsuccessful");
	}

	@Test(groups = { "smoke" }, dataProvider = "userDataProvider", dataProviderClass = DataProviders.class)
	public void testEmptyInputLastName(UserData user) {
		runLastNameTest(user, "Unsuccessful");
	}

	private void runFirstNameTest(UserData user, String expectedMsg) {

		basePage.navigateToDatacomWebApp();
		logStep("Navigating to Datacom Bugs-Form");

		form.fillOutTheForm(user);
		logStep("Filling-up the fields", ScreenshotUtil.capture(getPage(), getTest(), user.getTestName()));

		form.click(DatacomLocators.REGISTER_BUTTON);
		logStep("Click register");

		String msg = form.getRegistrationMessage();
		logStep("Verify registration message",
				ScreenshotUtil.capture(getPage(), getTest(), "Registration Message: " + user.getTestName()));
		AssertUtil.softAssertContains("Registration Message: ", getPage(), softAssert, msg, expectedMsg);

		form.verifyField("Registered First Name: ", softAssert, form.getResultsFirstName(), user.getFirstName());
		form.verifyField("Registered Last Name: ", softAssert, form.getResultsLastName(), user.getLastName());
		form.verifyField("Registered Phone Number: ", softAssert, form.getResultsPhoneNumber(), user.getPhone());
		form.verifyField("Registered Email: ", softAssert, form.getResultsEmail(), user.getEmail());

		softAssert.assertAll();
	}
	
	private void runLastNameTest(UserData user, String expectedMsg) {

		basePage.navigateToDatacomWebApp();
		logStep("Navigating to Datacom Bugs-Form");

		form.fillOutTheForm(user);
		logStep("Filling-up the fields", ScreenshotUtil.capture(getPage(), getTest(), user.getTestName()));

		form.click(DatacomLocators.REGISTER_BUTTON);
		logStep("Click register");

		String msg = form.getRegistrationMessage();
		logStep("Verify registration message",
				ScreenshotUtil.capture(getPage(), getTest(), "Registration Message: " + user.getTestName()));
		AssertUtil.softAssertContains("Registration Message: ", getPage(), softAssert, msg, expectedMsg);

		form.verifyField("Registered Last Name: ", softAssert, form.getResultsLastName(), user.getLastName());
		form.verifyField("Registered Phone Number: ", softAssert, form.getResultsPhoneNumber(), user.getPhone());
		form.verifyField("Registered Email: ", softAssert, form.getResultsEmail(), user.getEmail());

		softAssert.assertAll();
	}

}
