//package com.selenium.poc.utils;
//
//import io.qameta.allure.Attachment;
//import org.openqa.selenium.*;
//
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//
//public class ScreenshotUtil {
//
//    private static final String DIR =
//            System.getProperty("user.dir") + "/test-output/screenshots/";
//
//    // ✅ Save to disk
//    public static void save(WebDriver driver, String testName) {
//        try {
//            Files.createDirectories(Paths.get(DIR));
//
//            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            Path dest = Paths.get(DIR + testName + ".png");
//
//            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // ✅ Attach to Allure
//    @Attachment(value = "Failure Screenshot", type = "image/png")
//    public static byte[] attach(WebDriver driver) {
//        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//    }
//}
package com.selenium.poc.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;

import java.io.*;
import java.nio.file.*;

public class ScreenshotUtil {

    private static final String DIR =
            System.getProperty("user.dir") + "/test-output/screenshots/";

    public static void captureAndAttach(WebDriver driver, String testName) {
        try {
            Files.createDirectories(Paths.get(DIR));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path dest = Paths.get(DIR + testName + ".png");
            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

            // ✅ THIS is what actually attaches to Allure
            try (InputStream is = Files.newInputStream(dest)) {
                Allure.addAttachment(
                        "Failure Screenshot - " + testName,
                        "image/png",
                        is,
                        ".png"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
