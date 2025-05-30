package testComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import seleniumFrameworkDesign.resources.ExtentReporterNG;

import java.io.File;
import java.io.IOException;
// "result" holds all the information about the test case
public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();  // Thread safe
    @Override
    public void onTestStart(ITestResult result)
    {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test); // creates unique thread id of (ErrorValidationTest)-> Test
    }

    @Override
    public void onTestSuccess(ITestResult result)
    {
        extentTest.get().log(Status.PASS,"Test Passed");
//        test.log(Status.PASS,"Test Passed");
    }
    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable()); // Log the error message
//        test.fail(result.getThrowable());
        // Get the WebDriver instance
        try {
            //here we haven't used the method to get the "driver" because fields are associated in class level not method level
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        String filePath = null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(), driver); // getScreenshot returns the path of the screenshot
            System.out.println("Screenshot saved at: " + filePath); // Debug log
        } catch (IOException e) {
            e.printStackTrace();
        }
//        extentTest.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());
        // Ensure the file path is correct
        if (filePath != null) {
            try {
                // Extract just the filename from the full filePath
                String screenshotFileName = new File(filePath).getName();
                extentTest.get().addScreenCaptureFromPath(screenshotFileName, "Screenshot for " + result.getMethod().getMethodName());
            } catch (Exception e) {
                e.printStackTrace(); // Log any issues with adding the screenshot
            }
        } else {
            System.out.println("Screenshot path is null, cannot attach to report.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

}
