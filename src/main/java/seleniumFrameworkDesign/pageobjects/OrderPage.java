package seleniumFrameworkDesign.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import seleniumFrameworkDesign.abstractcomponents.AbstractComponents;

import java.util.List;

public class OrderPage extends AbstractComponents
{
    WebDriver driver;
    public OrderPage(WebDriver driver) {
        super(driver);
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css ="tr td:nth-child(3)")
    List<WebElement> productsName;

    @FindBy(xpath = "//button[text()='Checkout']")
    WebElement checkoutElement;

    public Boolean VerifyOrderDisplay(String productName)
    {
        Boolean match = productsName.stream().anyMatch(product-> product.getText().equalsIgnoreCase(productName));
        return match;     // it will return true or false
    }
}

