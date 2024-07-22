package com.mycompany.app;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppTest {

    WebDriver driver; 
	WebDriverWait wait; 
	String url = "http://172.24.0.1";

    @Before
    public void setUp() {
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");  // Run in headless mode
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-dev-shm-usage");
        // driver = new ChromeDriver(options);
        driver = new HtmlUnitDriver(); 
		wait = new WebDriverWait(driver, 10); 
        //driver.get("http://172.24.0.1/index.php");
    }

    @After
    public void tearDown() { 
		driver.quit(); 
	}	 
	

    @Test
    public void testHomePage() {
        //get web page
		driver.get(url);

        wait.until(ExpectedConditions.titleContains("Login |"))

        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.tagName("button"));
        
        assertTrue(passwordField.isDisplayed());
        assertTrue(loginButton.isDisplayed());
    }

    @Test
    public void testInvalidPassword() {
        driver.get(url);

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

        assertEquals(url+"/dashboard.php", driver.getCurrentUrl());

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