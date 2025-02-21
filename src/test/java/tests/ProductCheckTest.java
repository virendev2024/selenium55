package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ProductCheckTest
{
    public static void main(String[] args) throws InterruptedException {

        String productName="IPHONE 13 PRO";
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--incognito");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  // it resolves any sink in issues
        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().window().maximize();
        driver.findElement(By.id("userEmail")).sendKeys("singhvirender168.4@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Rahul@1234");
        driver.findElement(By.name("login")).click();

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));  // waits till elements gets loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products= driver.findElements(By.cssSelector(".mb-3")); // "mb-3" is a css locator for locating all the elements on the page
//        System.out.println(products);

        WebElement prod =products.stream()
                .filter(product -> product.findElement(By.cssSelector("h5 b")).getText().equals(productName))
                .findFirst().orElse(null);
        Thread.sleep(2000);
        System.out.println(prod);
        prod.findElement(By.xpath("//button[text()=' Add To Cart']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
        List<WebElement> cartProducts= driver.findElements(By.xpath("//div[@class='cartSection']//h3"));
        Boolean match = cartProducts.stream().anyMatch(cartproduct ->cartproduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);  //The assert statement is used with a Boolean expression

        Thread.sleep(2000);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,250)");
        driver.findElement(By.xpath("//button[text()='Checkout']")).click();
        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")),"india").build().perform(); //compulsory -> .build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.xpath("//span[text()=' India']")).click();  //(//button[@class='ta-item list-group-item ng-star-inserted'])[2]
        Thread.sleep(5000);
        jse.executeScript("window.scrollBy(0,250)");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[text()='Place Order ']")).click();
        String confirmMessage = driver.findElement(By.xpath("//h1[text()=' Thankyou for the order. ']")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        System.out.println("worked");
        Thread.sleep(5000);
    }
}
