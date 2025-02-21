package seleniumFrameworkDesign.pageobjects;

import org.openqa.selenium.interactions.Actions;
import seleniumFrameworkDesign.abstractcomponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends AbstractComponents
{
    WebDriver driver;
    public CheckoutPage(WebDriver driver)
    {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver , this);
    }
    @FindBy(xpath ="//input[@placeholder='Select Country']")
    WebElement country;

//    driver.findElement(By.xpath("(//button[@class='ta-item list-group-item ng-star-inserted'])[2]")).click();
    @FindBy(xpath ="//span[text()=' India']")
    WebElement selectCountry;

//    driver.findElement(By.cssSelector(".action__submit")).click();
    @FindBy(xpath ="//a[text()='Place Order ']")
    WebElement submit;

    By results = By.cssSelector(".ta-results");

    public void selectCountry(String countryName)
    {
        Actions a = new Actions(driver);
        a.sendKeys(country, countryName).build().perform(); //compulsory -> .build().perform();
        waitForElementToAppear(results);
        selectCountry.click();
    }

    public ConfirmationPage submitOrder()
    {
        submit.click();
        return new ConfirmationPage(driver);
    }
}
