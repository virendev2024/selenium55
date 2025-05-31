package testComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver InitializeDriver() throws IOException
    {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(getProperty("user.dir")+
                "\\src\\main\\java\\seleniumFrameworkDesign\\resources\\GlobalData.properties");
        prop.load(fis);

        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");

        // Use a variable to track if headless mode is requested
        boolean isHeadless = browserName.contains("headless");

        if(browserName.contains("chrome")) // Check for "chrome" or "headless-chrome"
        {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--incognito");
            if(isHeadless) // Apply headless argument if the browserName contained "headless"
            {
                options.addArguments("--headless"); // Correct argument for headless mode
                options.addArguments("--disable-gpu"); // Recommended for headless on some systems
                options.addArguments("--window-size=1920,1080"); // Set a fixed window size for headless
            }
            driver = new ChromeDriver(options);
            // Only set window size if not headless or if you want to override the headless window size
            if(!isHeadless) {
                driver.manage().window().setSize(new Dimension(1440,900)); // full screen mode for non-headless
            }
        }
        else if(browserName.contains("edge"))
        {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--inprivate");

            if(isHeadless) // Apply headless argument if the browserName contained "headless"
            {
                options.addArguments("--headless"); // Correct argument for headless mode
                options.addArguments("--disable-gpu"); // Recommended for headless on some systems
                options.addArguments("--window-size=1920,1080"); // Set a fixed window size for headless
            }
            driver = new EdgeDriver(options);
            if(!isHeadless) {
                driver.manage().window().setSize(new Dimension(1440,900)); // full screen mode for non-headless
            }
        }
        else if (browserName.equalsIgnoreCase("firefox"))
        {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private");
            driver = new FirefoxDriver(options);
        }
        else {
            throw new IllegalArgumentException("Browser '" + browserName + "' is not supported. Please choose 'chrome', 'headless-chrome', 'edge', or 'firefox'.");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Maximize only if not headless, as headless usually has a fixed size
        if (!isHeadless) {
            driver.manage().window().maximize();
        }

        tlDriver.set(driver);
        return driver;
    }

    // ... rest of your BaseTest class remains the same
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
        WebDriver currentDriver = tlDriver.get();
        if (currentDriver != null) {
            currentDriver.quit();
            tlDriver.remove();
        }
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        WebDriver currentDriver = tlDriver.get();
        if (currentDriver == null) {
            System.out.println("Driver is null when trying to take screenshot for: " + testCaseName);
            return null;
        }
        File ts = ((TakesScreenshot) currentDriver).getScreenshotAs(OutputType.FILE);
        String filePath = getProperty("user.dir") + File.separator + "reports" + File.separator + testCaseName + ".png";
        FileUtils.copyFile(ts, new File(filePath));
        System.out.println("Screenshot saved at: " + filePath);
        return filePath;
    }
}