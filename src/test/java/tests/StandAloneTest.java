package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {
    public static void main(String[] args) throws InterruptedException {
//        WebDriverManager.chromedriver().setup();  // we don't need to set the path of chrome driver explicitely
//        WebDriver driver = new ChromeDriver();    // object for chrome driver
//        String productName = "ZARA COAT 3";
        String productName="IPHONE 13 PRO";
//        WebDriverManager.edgedriver().setup();
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--incognito");

        WebDriver driver = new ChromeDriver(options);

//        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // it resolves any sink in issues
        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().window().maximize();

//        https://rahulshettyacademy.com/client
        driver.findElement(By.id("userEmail")).sendKeys("virensingh2022@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Rahul@1234");
        driver.findElement(By.name("login")).click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));  // waits till elements gets loaded
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(".mb-3")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products= driver.findElements(By.cssSelector(".mb-3")); // "mb-3" is a css locator for locating all the elements on the page
        // all the products(3) are stored in a list of WebElement called "products"
//        for(int i=0;i<products.size();i++) {
//            System.out.println(products.get(i).getText());
//        }


        WebElement prod =products.stream()
                .filter(product -> product.findElement(By.cssSelector("h5 b")).getText().equals(productName)).findAny().orElse(null);
//                .findFirst().orElse(null);


        //for adding the element in cart
        prod.findElement(By.xpath(String.format("//h5[normalize-space()='%s']//following-sibling::button[@class='btn w-10 rounded']",productName))).click();
        //*[@id="products"]/div[1]/div[2]/div[1]/div/div/button[2]
//toast-container
         // it will wait untill the toast container is visible on the screen

        System.out.println("..............................................................................");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#toast-container"))));
//        //ng-animating
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
//        ////i[@class='fa fa-shopping-cart']
        driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

        //validating a product if it is present in the cart or not
        //so we have to get all the items into the cart
        //div[@class='cartSection']//h3
        List<WebElement> cartProducts= driver.findElements(By.xpath("//div[@class='cartSection']//h3"));
        Boolean match = cartProducts.stream().anyMatch(cartproduct ->cartproduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);  //The assert statement is used with a Boolean expression

        Thread.sleep(2000);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,250)");
//        driver.findElement(By.cssSelector(".totalRow button")).click();
        driver.findElement(By.xpath("//button[text()='Checkout']")).click();

        //for advanced selenium interactions
        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")),"india").build().perform(); //compulsory -> .build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.xpath("//span[text()=' India']")).click();  //(//button[@class='ta-item list-group-item ng-star-inserted'])[2]
        //span[text()=' India']

        Thread.sleep(5000);
//        JavascriptExecutor jse1 = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,250)");
//        JavascriptExecutor executor = (JavascriptExecutor)driver;
//        executor.executeScript("document.body.style.zoom = '0.75'");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[text()='Place Order ']")).click();
        //a[text()='Place Order ']
        // validation
        String confirmMessage = driver.findElement(By.xpath("//h1[text()=' Thankyou for the order. ']")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

//
        System.out.println("worked");
        Thread.sleep(5000);
//        driver.close();
//        driver.quit();

    }
}
