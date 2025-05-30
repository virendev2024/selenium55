package seleniumFrameworkDesign.pageobjects;

import seleniumFrameworkDesign.abstractComponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalouge extends AbstractComponents {
    WebDriver driver ;
//    String productName = null;


    public ProductCatalouge(WebDriver driver)
    {
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);  // PageFactory is only for Driver.FindElement(By) construction not just for By element locator
    }

    //    List<WebElement> products= driver.findElements(By.cssSelector(".mb-3"));
    @FindBy(css=".mb-3")             // page factory used
    List<WebElement> products;
    @FindBy(css=".ng-animating")
    WebElement spinner;

    By productsBy =By.cssSelector(".mb-3");   //By element locator
//    By addToCart = By.xpath(String.format("//h5[normalize-space()='%s']//following-sibling::button[@class='btn w-10 rounded']",productName));   //By element locator
    //By.xpath(String.format("//h5[normalize-space()='%s']//following-sibling::button[@class='btn w-10 rounded']",productName))
    //*[@id="products"]/div[1]/div[2]/div[1]/div/div/button[2]
    //prod.findElement(By.xpath(String.format("//h5[normalize-space()='%s']//following-sibling::button[@class='btn w-10 rounded']",productName))).click();
    By toastMessage = By.cssSelector("#toast-container");

    //Action method
    public List<WebElement> getProductList()  // for listing down all the product list
    {
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductByName(String productName) // its returning webElement
    {
        WebElement prod =getProductList().stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
        return prod;
    }

//    public void addProductToCart(String productName) throws InterruptedException {
//        WebElement prod = getProductByName(productName);
//        prod.findElement(addToCart).click();
//        waitForElementToAppear(toastMessage);
//        waitForElementToDissapear(spinner);
//        //wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
//
//    }
public void addProductToCart(String productName) throws InterruptedException {
    WebElement prod = getProductByName(productName);
    By addToCart = By.xpath(String.format("//h5[normalize-space()='%s']//following-sibling::button[@class='btn w-10 rounded']", productName));
    prod.findElement(addToCart).click();
    waitForElementToAppear(toastMessage);
    waitForElementToDissapear(spinner);
}

}
