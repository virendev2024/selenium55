package testcomponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
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

public class BaseTest
{
    public WebDriver driver;
    public LandingPage landingPage;
    public WebDriver InitializeDriver() throws IOException
    {
        Properties prop = new Properties();  // using Properties class , we can parse into properties file and extract any information required
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+
                "\\src\\main\\java\\seleniumFrameworkDesign\\resources\\GlobalData.properties");  //System.getProperty("user.dir") : gives project path of your project
        prop.load(fis);
        String browserName = prop.getProperty("browser");
        if(browserName.equalsIgnoreCase("chrome"))
        {
            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--incognito");
//            scrollDown();
//            zoomIn();
            driver = new ChromeDriver(options);
        }
        else if(browserName.equalsIgnoreCase("edge"))
        {
            System.setProperty("webdriver.edge.driver","edge.exe");
            driver = new EdgeDriver();
        }
        else if (browserName.equalsIgnoreCase("firefox"))
        {
            //firefox
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;      // it is preparing driver for us
    }

    // its a generic and can be used for any test
    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        // will scan the entire content of json and convert into string
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        // convert String(jsonContent) to hashmap with jackson databind dependency
        ObjectMapper mapper = new ObjectMapper();
        //ObjectMapper has a method called readvalue which reads the string and convert to hashmap
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        //data variable is a list with 2 arguments(hashmap1,hashmap2)
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
        driver.close();
    }
}

