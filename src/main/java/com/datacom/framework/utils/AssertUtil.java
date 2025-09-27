package com.datacom.framework.utils;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.datacom.framework.base.BaseTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class AssertUtil {

    public static void softAssertEqual(String elementToCheck, Page page, SoftAssert softAssert, Object actual, Object expected) {
        page.waitForLoadState(LoadState.LOAD);

        ExtentTest test = BaseTest.getTest(); 
        boolean result = actual.equals(expected);

        if (result) {
            test.pass(elementToCheck + " <span style='color:green;'>PASSED! Expected: " + expected + " | Actual: " + actual + "</span>");
        } else {
            test.fail(elementToCheck + " <span style='color:red;'>FAILED! Expected: " + expected + " | Actual: " + actual + "</span>");
        }

        softAssert.assertEquals(actual, expected);
    }

    public static void softAssertContains(String elementToCheck, Page page, SoftAssert softAssert, String actual, String expected) {
        page.waitForLoadState(LoadState.LOAD);

        ExtentTest test = BaseTest.getTest();
        boolean condition = actual != null && actual.contains(expected);

        if (condition) {
            test.pass(elementToCheck + " <span style='color:green;'>PASSED! Expected to contain: '" + expected +
                    "' | Actual: '" + actual + "'</span>");
        } else {
            test.fail(elementToCheck + " <span style='color:red;'>FAILED! Expected to contain: '" + expected +
                    "' | Actual: '" + actual + "'</span>");
        }

        softAssert.assertTrue(condition, "Expected actual to contain: " + expected + " but was: " + actual);
    }
}
