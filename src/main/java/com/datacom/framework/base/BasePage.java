package com.datacom.framework.base;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BasePage {
	protected final Page page;
	protected final ExtentTest test;

	// Main constructor - both page + extent test
	public BasePage(Page page, ExtentTest test) {
		this.page = page;
		this.test = test;
	}

	// Convenience constructor
	public BasePage(Page page) {
		this(page, null);
	}

	// Getters so subclasses can call them
	protected Page getPage() {
		return page;
	}

	protected ExtentTest getTest() {
		return test;
	}

	// Common logging helper
	protected void logStep(String message) {
		if (test != null) {
			test.log(Status.INFO, message);
		}
	}

	public void navigateToDatacomWebApp() {
		page.navigate("https://qa-practice.netlify.app/bugs-form");
	}

	public void click(String locator) {
		page.locator(locator).click();
	}

	public void type(String locator, String text) {
		page.locator(locator).clear();
		page.locator(locator).fill(text);
	}

	public void selectByLabel(String locator, String label) {
		if (label == null || label.isEmpty()) {
			return; // skip if empty
		}

		Locator selectElement = page.locator(locator);
		selectElement.click();
		Locator option = selectElement.locator("option", new Locator.LocatorOptions().setHasText(label)).first();
		option.click();
	}

}
