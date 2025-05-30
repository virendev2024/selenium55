package seleniumFrameworkDesign.abstractComponents;

import lombok.extern.java.Log;
import org.openqa.selenium.JavascriptExecutor;
import seleniumFrameworkDesign.pageobjects.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumFrameworkDesign.pageobjects.OrderPage;

import java.time.Duration;



//class is made to utilize reusable code/methods
//with driver we use page factory
//for every new page we create one class
@Log
public class AbstractComponents
{
    WebDriver driver;
    public AbstractComponents(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver , this);
    }

//    driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
    @FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
    WebElement cartHeader;

    @FindBy(xpath="//button[@routerlink=\'/dashboard/myorders\']")
    WebElement orderHeader;

    public void waitForElementToAppear(By findBy) // when driver not available we use findBy
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));  // waits till elements gets loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy)); // value of findBy will come from page object
    }

    public void waitForWebElementToAppear(WebElement findBy) // when driver not available we use findBy
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));  // waits till elements gets loaded
        wait.until(ExpectedConditions.visibilityOf(findBy)); // value of findBy will come from page object
    }


//wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
    public void waitForElementToDissapear(WebElement ele) throws InterruptedException   // when driver available we use WebElement
    {
        Thread.sleep(1000);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));  // waits till elements gets loaded
//        wait.until(ExpectedConditions.invisibilityOf(ele));

    }

    public CartPage goToCartPage()
    {
        cartHeader.click();

        CartPage cartPage = new CartPage(driver);
        return cartPage;
    }

    public OrderPage goToOrdersPage()
    {
        orderHeader.click();
        OrderPage orderPage = new OrderPage(driver);
        return orderPage;

    }


    public void masterPause(long time)
    {
        try {
            Thread.sleep(time);
        }
        catch(InterruptedException e)
        {
            log.warning("Interruption Exception"+e.getMessage());
        }

    }

    public void zoomIn()
    {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.body.style.zoom = '0.75'");
    }

    public void scrollDown()
    {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
//        jse.executeScript("window.scrollBy(0,250)");
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

}
