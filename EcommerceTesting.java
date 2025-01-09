import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class FlipkartAutomation {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.flipkart.com");
            driver.manage().window().maximize();

            // Close login pop-up
            WebElement closeLoginPopup = driver.findElement(By.xpath("//button[text()='✕']"));
            closeLoginPopup.click();

            // 1. Product Search
            WebElement searchBar = driver.findElement(By.name("q"));
            searchBar.sendKeys("Laptop");
            driver.findElement(By.className("L0Z3Pu")).click();
            Thread.sleep(2000);  // Wait for results to load

            List<WebElement> results = driver.findElements(By.className("_1AtVbE"));
            if (results.size() > 0) {
                System.out.println("Search Test Passed. Found " + results.size() + " results.");
            } else {
                System.out.println("Search Test Failed.");
            }

            // 2. Add to Cart
            WebElement firstProduct = results.get(0).findElement(By.className("_2kHMtA"));
            firstProduct.click();
            Thread.sleep(2000);  // Wait for product page to load

            WebElement addToCartButton = driver.findElement(By.xpath("//button[text()='ADD TO CART']"));
            addToCartButton.click();
            Thread.sleep(2000);  // Wait for cart confirmation

            WebElement cartConfirmation = driver.findElement(By.xpath("//span[text()='My Cart']"));
            cartConfirmation.click();
            Thread.sleep(2000);

            WebElement cartItemCount = driver.findElement(By.xpath("//span[@class='_2d4LTz']"));
            if (cartItemCount.getText().equals("1")) {
                System.out.println("Add to Cart Test Passed");
            } else {
                System.out.println("Add to Cart Test Failed");
            }

            // 3. Checkout Process (Login required for this step)
            WebElement checkoutButton = driver.findElement(By.xpath("//span[text()='Place Order']"));
            checkoutButton.click();
            Thread.sleep(2000);

            WebElement phoneNumberField = driver.findElement(By.xpath("//input[@class='_2IX_2- VJZDxU']"));
            phoneNumberField.sendKeys("1234567890");
            driver.findElement(By.xpath("//button[@class='_2KpZ6l _2HKlqd _3AWRs2']")).click();
            Thread.sleep(2000);

            WebElement loginError = driver.findElement(By.xpath("//span[@class='_2YxqC7']"));
            if (loginError.isDisplayed()) {
                System.out.println("Checkout Test Failed - Login Error");
            } else {
                System.out.println("Checkout Test Passed");
            }

            // 4. Error Handling
            driver.findElement(By.xpath("//button[text()='✕']")).click(); // Close any pop-up
            driver.findElement(By.xpath("//input[@class='_2IX_2- VJZDxU']")).sendKeys(""); // Empty field
            driver.findElement(By.xpath("//button[@class='_2KpZ6l _2HKlqd _3AWRs2']")).click();
            Thread.sleep(2000);

            WebElement errorMessage = driver.findElement(By.xpath("//span[@class='_2YxqC7']"));
            if (errorMessage.isDisplayed()) {
                System.out.println("Error Handling Test Passed");
            } else {
                System.out.println("Error Handling Test Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
