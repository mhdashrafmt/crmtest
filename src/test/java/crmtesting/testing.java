package crmtesting;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testing {

    public static void main(String[] args) {
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Set Chrome for Testing binary path
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\HP\\Desktop\\selinium\\chrome for testing\\chrome-win64\\chrome.exe");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://www.ad.house-academy.scaleup-business-builder.xyz/");

        // Setup explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.findElement(By.name("username")).sendKeys("super-admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//label[contains(text(),'Remember my preference')]/preceding-sibling::input")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("splash-screen")));

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(),'Sign Me In')]")
        ));
        loginButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Page Title: " + driver.getTitle());

        driver.quit();
    }
}