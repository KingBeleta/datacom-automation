package tests;

import com.datacom.framework.base.BasePage;
import com.datacom.framework.base.BaseTest;
import com.datacom.framework.locators.DatacomLocators;
import com.datacom.framework.pages.DatacomPage;
import com.datacom.framework.utils.ExcelUtils;
import com.datacom.framework.utils.ScreenshotUtil;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Listeners(com.datacom.framework.reports.TestListener.class)
public class DatacomBugsFormTestCountry extends BaseTest {

    private BasePage basePage;
    private static final String TESTDATA = "TestData.xlsx";

    @BeforeMethod(alwaysRun = true)
    public void setupPages() {
        basePage = new BasePage(getPage());
    }

    @Test(groups = {"smoke"})
    public void testCountryDropdownOptions() {
        SoftAssert softAssert = new SoftAssert();

        basePage.navigateToDatacomWebApp();
        logStep("Navigated to Datacom Bugs-Form");


        List<String> actualCountries = DatacomPage.getDropdownOptions(getPage(), DatacomLocators.COUNTRY_FIELD);
        actualCountries.removeIf(s -> s.equalsIgnoreCase("Select a country..."));

        logStep("Extracted " + actualCountries.size() + " country options",
                ScreenshotUtil.capture(getPage(), getTest(), "DropdownOptions"));

        // Load expected list from Excel
        List<String> expectedCountries = ExcelUtils.getStrictColumnValues(TESTDATA, "Sheet2", 0);
        logStep("Loaded " + expectedCountries.size() + " expected countries from Excel.");

        Set<String> expectedSet = new HashSet<>(expectedCountries);
        Set<String> actualSet = new HashSet<>(actualCountries);

        // Find invalid ones in dropdown
        for (String country : actualCountries) {
            if (!expectedSet.contains(country)) {
                logStep("Invalid country in dropdown: " + country);
                softAssert.fail("<span style='color:red;'> Invalid country: " + country);
            }
        }

        // Find missing ones (Excel but not in dropdown)
        for (String country : expectedCountries) {
            if (!actualSet.contains(country)) {
                logStep("<span style='color:yellow;'> Missing expected country: " + country);
                softAssert.fail("Missing country: " + country);
            }
        }

        softAssert.assertAll();
    }
}
