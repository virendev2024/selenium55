package testComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions; // Import EdgeOptions
import org.openqa.selenium.firefox.FirefoxDriver; // Import FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions; // Import FirefoxOptions
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import seleniumFrameworkDesign.pageobjects.LandingPage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static java.lang.System.getProperty;

public class BaseTest
{
    public WebDriver driver;
    public LandingPage landingPage;
    // Add a ThreadLocal for WebDriver (if not already present from previous merges)
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver InitializeDriver() throws IOException
    {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(getProperty("user.dir")+
                "\\src\\main\\java\\seleniumFrameworkDesign\\resources\\GlobalData.properties");
        prop.load(fis);

        // Get browser name from system properties first, then fallback to GlobalData.properties
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");

        if(browserName.equalsIgnoreCase("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--incognito");
            driver = new ChromeDriver(options);
        }
        else if(browserName.equalsIgnoreCase("edge"))
        {
            WebDriverManager.edgedriver().setup(); // Use WebDriverManager for Edge
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--remote-allow-origins=*"); // Similar to Chrome
            options.addArguments("--inprivate"); // Edge's incognito mode equivalent
            driver = new EdgeDriver(options);
        }
        else if (browserName.equalsIgnoreCase("firefox"))
        {
            WebDriverManager.firefoxdriver().setup(); // Use WebDriverManager for Firefox
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private"); // Firefox's incognito mode equivalent
            // Firefox doesn't typically need --remote-allow-origins for standard use
            driver = new FirefoxDriver(options);
        }
        // Handle a default case or throw an exception if browser name is invalid
        else {
            throw new IllegalArgumentException("Browser '" + browserName + "' is not supported. Please choose 'chrome', 'edge', or 'firefox'.");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Set the driver for the current thread in ThreadLocal (important for parallel execution)
        tlDriver.set(driver);
        return driver;
    }

    // Generic method to read JSON data into a List of HashMaps
    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
        return data;
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException
    {
        driver = InitializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        // Use ThreadLocal to get the driver and quit it
        WebDriver currentDriver = tlDriver.get();
        if (currentDriver != null) {
            currentDriver.quit(); // Use quit() for full browser closure
            tlDriver.remove(); // Remove from ThreadLocal after use
        }
    }

    // Method to take a screenshot, now retrieving driver from ThreadLocal
    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException { // Removed WebDriver driver parameter as it's fetched from ThreadLocal
        WebDriver currentDriver = tlDriver.get(); // Get the driver for the current thread
        if (currentDriver == null) {
            System.out.println("Driver is null when trying to take screenshot for: " + testCaseName);
            return null;
        }
        File ts = ((TakesScreenshot) currentDriver).getScreenshotAs(OutputType.FILE);
        String filePath = getProperty("user.dir") + File.separator + "reports" + File.separator + testCaseName + ".png";
        FileUtils.copyFile(ts, new File(filePath));
        System.out.println("Screenshot saved at: " + filePath); // Debug log
        return filePath;
    }
}