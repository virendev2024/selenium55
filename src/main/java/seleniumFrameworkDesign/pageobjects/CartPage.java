package seleniumFrameworkDesign.pageobjects;

import seleniumFrameworkDesign.abstractcomponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponents
{
    WebDriver driver;
    public CartPage(WebDriver driver) {
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

//    List<WebElement> cartProducts= driver.findElements(By.xpath("//div[@class='cartSection']//h3"));
//    Boolean match = cartProducts.stream().anyMatch(cartproduct ->cartproduct.getText().equalsIgnoreCase(productName));
//        Assert.assertTrue(match);  //The assert statement is used with a Boolean expression
//        driver.findElement(By.cssSelector(".totalRow button")).click();

    @FindBy(xpath ="//div[@class='cartSection']//h3")
    List<WebElement> cartProducts;

    @FindBy(xpath = "//button[text()='Checkout']")
    WebElement checkoutElement;

    public Boolean VerifyProductDisplay(String productName)
    {
        Boolean match = cartProducts.stream().anyMatch(product-> product.getText().equalsIgnoreCase(productName));
        return match;     // it will return true or false
    }

    public CheckoutPage goToCheckout()
    {
        masterPause(2000);
        scrollDown();
        masterPause(2000);
        checkoutElement.click();
        return new CheckoutPage(driver);
    }



}
