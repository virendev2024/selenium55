package seleniumFrameworkDesign.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
    public static ExtentReports getReportObject()
    {
        String path = System.getProperty("user.dir")+"//reports//index.html";
//        String path = System.getProperty("C:\\Users\\vsingh36\\AutomationProjects\\selenium55\\reports");
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester","Virender singh");
//        extent.createTest(path);
        return extent;
    }

}