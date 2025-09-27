package com.datacom.framework.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.testng.asserts.SoftAssert;

import com.datacom.framework.base.BasePage;
import com.datacom.framework.locators.DatacomLocators;
import com.datacom.framework.models.UserData;
import com.datacom.framework.utils.AssertUtil;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DatacomPage extends BasePage {

	public DatacomPage(Page page) {
		super(page);
	}

	public void fillOutTheForm(UserData user) {
		type(DatacomLocators.FIRSTNAME_FIELD, user.getFirstName());
		type(DatacomLocators.LASTNAME_FIELD, user.getLastName());
		type(DatacomLocators.PHONE_NUMBER_FIELD, user.getPhone());
//		page.locator("select#countries_dropdown_menu").highlight();
		selectByLabel(DatacomLocators.COUNTRY_FIELD, user.getCountry());
		type(DatacomLocators.EMAIL_ADDRESS_FIELD, user.getEmail());
		type(DatacomLocators.PASSWORD_FIELD, user.getPassword());
	}

	public void register() {
		click(DatacomLocators.REGISTER_BUTTON);
	}

	public String getRegistrationMessage() {
		Locator resultsMsg = page.locator(DatacomLocators.RESULTS_MESSAGE);
		String msg = resultsMsg.innerText();
		return msg;
	}

	public String getResultsFirstName() {
		Locator resultFn = page.locator(DatacomLocators.RESULTS_FIRSTNAME);
		String fn = resultFn.innerText();
		return fn;
	}

	public String getResultsLastName() {
		Locator resultLn = page.locator(DatacomLocators.RESULTS_LASTNAME);
		String ln = resultLn.innerText();
		return ln;
	}

	public String getResultsPhoneNumber() {
		Locator resultPn = page.locator(DatacomLocators.RESULTS_PHONE_NUMBER);
		String pn = resultPn.innerText();
		return pn;
	}

	public String getResultsCountry() {
		Locator resultCntry = page.locator(DatacomLocators.RESULTS_COUNTRY);
		String cntry = resultCntry.innerText();
		return cntry;
	}

	public String getResultsEmail() {
		Locator resultEmail = page.locator(DatacomLocators.RESULTS_EMAIL);
		String eml = resultEmail.innerText();
		return eml;
	}

	public void verifyField(String elementToCheck, SoftAssert softAssert, String actualValueRaw, String expectedValue) {
		String actualValue = actualValueRaw.contains(":") ? actualValueRaw.split(":", 2)[1].trim()
				: actualValueRaw.trim();

		AssertUtil.softAssertEqual(elementToCheck, getPage(), softAssert, actualValue, expectedValue);
	}

	public static List<String> getDropdownOptions(Page page, String locatorSelector) {
		Locator dropdown = page.locator(locatorSelector).locator("option");
		return dropdown.allTextContents().stream().map(String::trim).collect(Collectors.toList());
	}


}
