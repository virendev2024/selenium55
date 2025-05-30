package seleniumFrameworkDesign.pageobjects;
//page object should not hold any data , it should focus on elements and actions

import seleniumFrameworkDesign.abstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponents
{
    WebDriver driver;

    //constructor is the 1st method to get executed when you run the class
    //this.driver refers to the current class instance variable(driver) only
    //so make the object of another class and assign driver to it
    public LandingPage(WebDriver driver)  // driver has the scope to only this method
    {
        super(driver);   //from child class to parent class we can send the variables (driver)
        this.driver= driver;
        PageFactory.initElements(driver,this); // this refers to the current class driver
    }
//    WebElement userEmail = driver.findElement(By.id("userEmail"));

    //pageFactory design
    // initElements construct this.
    @FindBy(id="userEmail")
    WebElement userEmail;
//    WebElement userPassword = driver.findElement(By.id("userPassword"));

    @FindBy(id="userPassword")
    WebElement userPassword;

//    WebElement userLogin = driver.findElement(By.name("login"));
    @FindBy(name="login")
    WebElement userLogin;

//    ng-tns-c4-11 ng-star-inserted ng-trigger ng-trigger-flyInOut ngx-toastr toast-error
//div[@class="ng-tns-c4-12 toast-message ng-star-inserted"]
    @FindBy(css="[class*='flyInOut']")
    WebElement errorMessage;
    // Action method , explain what that method does

    public void goTo()
    {
        driver.get("https://rahulshettyacademy.com/client");
    }
    public ProductCatalouge loginApplication(String email , String password )
    {
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        userLogin.click();

        ProductCatalouge productCatalouge = new ProductCatalouge(driver);
        return productCatalouge;
    }

    public String getErrorMessage()
    {
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }
}
