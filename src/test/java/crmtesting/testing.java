package crmtesting;

import java.time.Duration;
import java.util.List;

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
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\HP\\Desktop\\selinium\\chrome for testing\\chrome-win64\\chrome.exe");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get("https://demo.scaleup-business-builder.xyz/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // ---- LOGIN ----
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("super-admin");
            driver.findElement(By.name("password")).sendKeys("00000");

            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//label[contains(text(),'Remember my preference')]/preceding-sibling::input")));
            js.executeScript("arguments[0].click();", checkbox);

            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(normalize-space(),'Sign Me In')]")));
            js.executeScript("arguments[0].click();", loginButton);

            Thread.sleep(1000);

            // ---- Optional Change Password Popup ----
            try {
                WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'OK')]")));
                js.executeScript("arguments[0].click();", okButton);
            } catch (Exception e) {
                // ignore if popup not found
            }

            // ---- Navigate Sidebar → Admin → Lead Settings → Lead Stage ----
            WebElement menuIcon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"main-wrapper\"]/div[1]/div[1]/div/div/span[2]")));
            js.executeScript("arguments[0].click();", menuIcon);

            WebElement adminMenu = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"menu\"]/li[4]/a")));
            js.executeScript("arguments[0].click();", adminMenu);

            WebElement leadSettings = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"menu\"]/li[4]/ul/li[2]/a")));
            js.executeScript("arguments[0].click();", leadSettings);

            WebElement leadStage = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Lead Stage')]")));
            js.executeScript("arguments[0].click();", leadStage);

            Thread.sleep(1000);

            // ---- CREATE NEW LEAD STAGE ----
            WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Create')]")));
            js.executeScript("arguments[0].click();", createBtn);

            WebElement stageName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'modal-dialog')]//input")));
            stageName.clear();
            String stageText = "Test Stage " + (System.currentTimeMillis() % 100000);
            stageName.sendKeys(stageText);

            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'modal-dialog')]//button[contains(.,'Save')]")));
            js.executeScript("arguments[0].click();", saveBtn);

            Thread.sleep(1000);

            // ---- FIND LAST CREATED STAGE ----
            List<WebElement> stages = driver.findElements(By.xpath("//div[@id='lead_status']//li"));
            WebElement lastStage = stages.get(stages.size() - 1);

            // ---- EDIT THE LAST CREATED LEAD STAGE ----
            WebElement editIcon = lastStage.findElement(By.xpath(".//span[contains(@class,'pointer')][1]"));
            js.executeScript("arguments[0].scrollIntoView(true);", editIcon);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", editIcon);

            WebElement editName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'modal-dialog')]//input")));
            editName.clear();
            String updatedText = stageText + " Updated";
            editName.sendKeys(updatedText);

            WebElement saveBtn1 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'modal-dialog')]//button[contains(.,'Save')]")));
            js.executeScript("arguments[0].click();", saveBtn1);

            Thread.sleep(1000);

            // ---- REFRESH LIST & FIND UPDATED STAGE ----
            stages = driver.findElements(By.xpath("//div[@id='lead_status']//li"));
            WebElement updatedStage = stages.get(stages.size() - 1);
            wait.until(ExpectedConditions.visibilityOf(updatedStage));

            System.out.println("✅ Updated stage found: " + updatedText);

            // ---- DELETE THE UPDATED LEAD STAGE ----
            WebElement deleteBtn = updatedStage.findElement(By.xpath(".//span[contains(@class,'pointer')][2]"));
            js.executeScript("arguments[0].scrollIntoView(true);", deleteBtn);
            Thread.sleep(1000);
            js.executeScript("arguments[0].click();", deleteBtn);

            WebElement confirmDelete = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Yes') or contains(text(),'Confirm') or contains(text(),'Delete')]")));
            js.executeScript("arguments[0].click();", confirmDelete);

            // ---- VERIFY DELETION ----
            wait.until(ExpectedConditions.invisibilityOf(updatedStage));

            System.out.println("🎉 Create → Edit → Delete cycle completed successfully");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}

