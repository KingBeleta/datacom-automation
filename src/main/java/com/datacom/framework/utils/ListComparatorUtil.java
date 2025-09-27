package com.datacom.framework.utils;

import com.aventstack.extentreports.ExtentTest;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Set;

public class ListComparatorUtil {

	public static void compareLists(List<String> actual, Set<String> expected, SoftAssert softAssert, ExtentTest test,
			String listName) {

		// Check for invalid entries
		for (String item : actual) {
			if (expected.contains(item)) {
				test.pass(" <span style='color:green;'> PASSED " + listName + " contains valid entry: " + item);
			} else {
				test.fail(" <span style='color:green;'> FAILED " + listName + " contains invalid entry: " + item);
				softAssert.fail(listName + " invalid entry: " + item);
			}
		}

		// Check for missing items
		for (String item : expected) {
			if (!actual.contains(item)) {
				test.fail("FAILED! MISSING! " + listName + " is missing expected entry: " + item);
				softAssert.fail(listName + " missing entry: " + item);
			}
		}
	}
}
