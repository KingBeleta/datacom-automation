package com.datacom.framework.reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.datacom.framework.base.BaseTest;

public class TestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        BaseTest.setTest(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        BaseTest.getTest().pass(" <span style='color:green;'> ✅ PASSED ");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseTest.getTest().fail(" <span style='color:green;'>❌ FAILED ").fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        BaseTest.getTest().skip("⚠️ Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
