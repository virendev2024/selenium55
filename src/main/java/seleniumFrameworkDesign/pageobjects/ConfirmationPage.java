package seleniumFrameworkDesign.pageobjects;

import seleniumFrameworkDesign.abstractcomponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage extends AbstractComponents {
    WebDriver driver;
    public ConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    //String confirmMessage = driver.findElement(By.xpath("//h1[text()=' Thankyou for the order. ']")).getText();
    @FindBy(xpath ="//h1[text()=' Thankyou for the order. ']")
    WebElement confirmMessage;

public String getConfirmMessage()
{
    return confirmMessage.getText();
}
}
