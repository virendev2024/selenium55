package extentReports;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ExtentReportDemo
{
    ExtentReports extent;
    @BeforeTest
    public void config()
    {
        // ExtentSparkReporter  : it will create a report
        String path = System.getProperty("user.dir")+"\\reports\\index.html";   // path where the report is created
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");


        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester","Virender singh");

    }

    @Test
    public void initialDemo()
    {
        ExtentTest test =extent.createTest("Initial Demo");  // It will mark the test whether pass or fail
        System.setProperty("webdriver.chrome.driver","C:\\Users\\vsingh36\\AutomationProjects\\selenium55\\src\\main\\java\\seleniumFrameworkDesign\\resources\\driver\\chromedriver.exe");
        WebDriver driver= new ChromeDriver();
        driver.get("https://rahulshettyacademy.com");
        System.out.println(driver.getTitle());
        driver.close();
        test.fail("Result do not match");
        extent.flush();
    }

//    C:\Users\vsingh36\Downloads\chrome-win64\chrome-win64
}
