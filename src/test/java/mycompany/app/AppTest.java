import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Run in headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get("http://172.24.0.1/index.php");
    }

    @Test
    public void testHomePage() {
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.tagName("button"));
        
        assertTrue(passwordField.isDisplayed());
        assertTrue(loginButton.isDisplayed());
    }

    @Test
    public void testInvalidPassword() {
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.tagName("button"));

        passwordField.sendKeys("simple");
        loginButton.click();

        WebElement errorMsg = driver.findElement(By.className("error-msg"));
        assertTrue(errorMsg.isDisplayed());
    }

    @Test
    public void testValidPassword() {
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.tagName("button"));

        passwordField.sendKeys("Complex@123");
        loginButton.click();

        assertEquals("http://172.24.0.1/dashboard.php", driver.getCurrentUrl());

        WebElement welcomeMessage = driver.findElement(By.tagName("p"));
        assertTrue(welcomeMessage.getText().contains("Your password: Complex@123"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}