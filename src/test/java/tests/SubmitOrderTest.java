package tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import seleniumFrameworkDesign.pageobjects.*;
import testComponents.BaseTest;

import java.io.IOException;
import java.util.List;
public class SubmitOrderTest extends BaseTest {
        String productName ="ZARA COAT 3";
//        String productName = null;
    @Test(dataProvider = "getData" ,groups = {"Purchase"})
//        public void SubmitOrder(HashMap<String , String> input) throws InterruptedException,IOException
//        {
    public void SubmitOrder(String email, String password , String productName) throws InterruptedException,IOException
    {
//        ProductCatalouge productCatalouge = landingPage.loginApplication(input.get("email"),input.get("password"));
        ProductCatalouge productCatalouge = landingPage.loginApplication(email , password);
            Thread.sleep(2000);
        List<WebElement> products=productCatalouge.getProductList();
//        productCatalouge.addProductToCart(input.get("product"));
        productCatalouge.addProductToCart(productName);
        CartPage cartPage = productCatalouge.goToCartPage();  // takes u to the cart page
            Thread.sleep(5000);
//        Boolean match =cartPage.VerifyProductDisplay(input.get("product"));
        Boolean match =cartPage.VerifyProductDisplay(productName);
        //validations can not go in page object files , page object files should only have the code to perform action
        Assert.assertTrue(match);  //The assert statement is used with a Boolean expression
        CheckoutPage checkoutPage = cartPage.goToCheckout();
//        Thread.sleep(5000);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("document.body.style.zoom = '0.75'");
        checkoutPage.selectCountry("india");
        Thread.sleep(5000);
        ConfirmationPage confirmationpage =checkoutPage.submitOrder();
        // validation
        String confirmMessage = confirmationpage.getConfirmMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        System.out.println("worked");
//        Thread.sleep(5000);
    }
    @Test(dependsOnMethods = {"SubmitOrder"})   // this test depends upon the execution of SubmitOrderTest.
    public void OrderHistoryTest()
    {
//            String productName = null;
            ProductCatalouge productCatalouge = landingPage.loginApplication("virensingh2022@gmail.com","Rahul@1234");
            OrderPage orderPage = productCatalouge.goToOrdersPage();
            Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
    }

    // video - 172
    @DataProvider
    public Object[][] getData() throws IOException {
//        Object is a parent data type for all types of data , so it can access any data weather String , integer , float etc.
        return new Object[][] {{"virensingh2022@gmail.com","Rahul@1234","ZARA COAT 3"},{"singhvirender168.4@gmail.com","Rahul@1234","ADIDAS ORIGINAL"}};
    }

    // video - 173
//    @DataProvider
//    public Object[][] getData()
//    {
//        HashMap<String,String> map = new HashMap<String, String>();
//        map.put("email","virensingh2022@gmail.com");
//        map.put("password","Rahul@1234" );
//        map.put("product","ZARA COAT 3");
//
//        HashMap<String,String> map1 = new HashMap<String, String>();
//        map.put("email","singhvirender168.4@gmail.com");
//        map.put("password","Rahul@1234" );
//        map.put("product","ADIDAS ORIGINAL");
//
//        return new Object[][] {{map},{map1}};
//    }


    //run purchase order.xml // video - 174
//    @DataProvider
//    public Object[][] getData() throws IOException
//    {
////        Object is a parent data type for all types of data , so it can access any data weather String , integer , float etc.
////        return new Object[][] {{"virensingh2022@gmail.com","Rahul@1234","ADIDAS ORIGINAL"},{"singhvirender168.4@gmail.com","Rahul@1234","ADIDAS ORIGINAL"}};
////
//        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//data//PurchaseOrder.json");
//                                                            //sending entire String path as an argument and catching it in filePath in (BaseTest class)
//        return new Object[][] {{data.get(0)},{data.get(1)}};
//

}
