package com.datacom.framework.utils;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtil {

    // Base folder where ExtentReports will be generated
    private static final String REPORT_FOLDER = "test-output";
    private static final String SCREENSHOT_FOLDER = "screenshots";

    /**
     * Captures a full-page screenshot and returns the relative path for ExtentReports.
     *
     * @param page Playwright Page object
     * @param test ExtentTest object (optional, not used here but can log)
     * @param name Desired screenshot file name (without extension)
     * @return Relative path to screenshot for ExtentReports
     */
    public static String capture(Page page, ExtentTest test, String name) {
        try {
            // 🔹 sanitize filename to remove illegal Windows characters
            String safeName = name.replaceAll("[\\\\/:*?\"<>|]", "_");

            // Ensure screenshot folder exists under test-output
            Path folder = Paths.get(REPORT_FOLDER, SCREENSHOT_FOLDER);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }

            // Build full file path
            String fileName = safeName + ".png";
            Path filePath = folder.resolve(fileName);

            // Capture full-page screenshot
            page.screenshot(new Page.ScreenshotOptions().setPath(filePath).setFullPage(true));

            // Return relative path for ExtentReports HTML
            return SCREENSHOT_FOLDER + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
