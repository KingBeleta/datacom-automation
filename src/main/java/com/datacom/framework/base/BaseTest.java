package com.datacom.framework.base;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.datacom.framework.reports.ExtentManager;
import com.microsoft.playwright.*;

public class BaseTest {

    // Thread-local objects for parallel execution
    protected static ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    protected static ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    protected static ThreadLocal<BrowserContext> contextThread = new ThreadLocal<>();
    protected static ThreadLocal<Page> pageThread = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    protected int stepCounter;

    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chromium") String browserName, Method method) {
        Playwright playwright = Playwright.create();
        playwrightThread.set(playwright);

        Browser browser;
        BrowserContext context;
        Page page;

        switch (browserName.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
                context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
                page = context.newPage();
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
                context = browser.newContext();
                page = context.newPage();
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
                context = browser.newContext();
                page = context.newPage();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        browserThread.set(browser);
        contextThread.set(context);
        pageThread.set(page);

        stepCounter = 1;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            if (pageThread.get() != null) pageThread.get().close();
            if (contextThread.get() != null) contextThread.get().close();
            if (browserThread.get() != null) browserThread.get().close();
            if (playwrightThread.get() != null) playwrightThread.get().close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pageThread.remove();
            contextThread.remove();
            browserThread.remove();
            playwrightThread.remove();
            test.remove();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ExtentManager.getInstance().flush();
    }

    // ================= Helpers ================= //

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public Page getPage() {
        return pageThread.get();
    }

    public Browser getBrowser() {
        return browserThread.get();
    }

    public void logStep(String message) {
        ExtentTest extentTest = getTest();
        if (extentTest != null) {
            extentTest.info(stepCounter++ + ". " + message);
        } else {
            System.out.println("[LOG] " + message); // fallback
        }
    }

    public void logStep(String message, String screenshotPath) {
        String htmlMessage = stepCounter++ + ". " + message;
        if (screenshotPath != null) {
            htmlMessage += "<br><a href='" + screenshotPath + "' target='_blank'>" +
                    "<img src='" + screenshotPath + "' style='width:100px; height:100px; object-fit:cover;'/>" +
                    "</a>";
        }
        getTest().log(Status.INFO, htmlMessage);
    }

    public void waitForPageLoad() {
        try {
            getPage().waitForLoadState();
        } catch (Exception e) {
            logStep("Page wait failed: " + e.getMessage());
        }
    }
}
