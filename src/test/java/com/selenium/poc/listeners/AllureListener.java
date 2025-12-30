//package com.selenium.poc.listeners;
//
//import com.selenium.poc.base.BaseTest;
//import com.selenium.poc.utils.ScreenshotUtil;
//import org.openqa.selenium.WebDriver;
//import org.testng.*;
//
//public class AllureListener implements ITestListener {
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//
//        WebDriver driver = BaseTest.getDriver();
//
//        if (driver != null) {
//            String testName = result.getMethod().getMethodName();
//
//            ScreenshotUtil.save(driver, testName);
//            ScreenshotUtil.attach(driver);
//        }
//    }
//}

package com.selenium.poc.listeners;

import com.selenium.poc.base.BaseTest;
import com.selenium.poc.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.*;

public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        WebDriver driver = BaseTest.getDriver();

        if (driver != null) {
            ScreenshotUtil.captureAndAttach(
                    driver,
                    result.getMethod().getMethodName()
            );
        }
    }
}

