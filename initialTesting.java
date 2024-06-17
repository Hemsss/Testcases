package testing;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class initialTesting {

    WebDriver driver;

    @BeforeTest
    public void setup() {
       
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void searchProduct() {
        driver.get("https://www.amazon.com/");

        String searchText = "iPhone 13";
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchText);
        driver.findElement(By.id("nav-search-submit-button")).click();

        // Assertion to verify search results contain the search term
        String title = driver.getTitle();
        assert title.contains(searchText);
    }

    @Test(dependsOnMethods = {"searchProduct"})  // This test depends on searchProduct passing first
    public void addToCart() throws InterruptedException {
        // Select the first search result and add to cart
        driver.findElement(By.xpath("//div[@data-component-type='s-search-result']//h2/a")).click();
        Thread.sleep(2000); // Wait for page to load (not ideal, use explicit waits)
        driver.findElement(By.id("add-to-cart-button")).click();

        // Assertion to verify item added to cart (check cart contents or success message)
        String confirmationText = driver.findElement(By.id("huc-v2-confirmation-quantity")).getText();
        assert confirmationText.contains("1");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
