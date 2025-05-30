package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import seleniumFrameworkDesign.pageobjects.CartPage;
import seleniumFrameworkDesign.pageobjects.ProductCatalouge;
import testComponents.BaseTest;
import testComponents.Retry;

import java.io.IOException;
import java.util.List;

public class ErrorValidationsTest extends BaseTest {

        @Test(groups = {"ErrorHandling"},retryAnalyzer=Retry.class) // retryAnalyzer=Retry.class: for rerunning the test
        public void LoginErrorValidation() throws IOException,InterruptedException {

//                String productName = "ZARA COAT 3";
                landingPage.loginApplication("virensingh2022@gmail.com", "ahul@1234"); // have put the wrong password intentionally
                Assert.assertEquals("Incorrect email  password.", landingPage.getErrorMessage());
                //use "retryAnalyzer=Retry.class" in the test that is expected to fail, it will re-run the test
        }

        @Test
        public void ProductErrorValidation() throws IOException,InterruptedException {
                String productName = "ZARA COAT 3";
                ProductCatalouge productCatalouge = landingPage.loginApplication("singhvirender168.4@gmail.com", "Rahul@1234");
                List<WebElement> products = productCatalouge.getProductList();
                productCatalouge.addProductToCart(productName);
                CartPage cartPage = productCatalouge.goToCartPage();  // takes u to the cart page
                Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 44");  //Trying to validate "ZARA COAT 44" not present in cart section
                Assert.assertFalse(match);
        }
}