package crmtesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

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
            Thread.sleep(1000);
            System.out.println("📝 Entered username");

            driver.findElement(By.name("password")).sendKeys("00000");
            Thread.sleep(1000);
            System.out.println("🔑 Entered password");

            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//label[contains(text(),'Remember my preference')]/preceding-sibling::input")));
            js.executeScript("arguments[0].click();", checkbox);
            Thread.sleep(1000);
            System.out.println("☑️ Clicked Remember my preference");

            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(normalize-space(),'Sign Me In')]")));
            js.executeScript("arguments[0].click();", loginButton);
            Thread.sleep(1000);
            System.out.println("✅ Clicked Sign Me In");

            // ---- Change Password Popup (if appears) ----
            try {
                WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(),'OK')]")));
                js.executeScript("arguments[0].click();", okButton);
                Thread.sleep(1000);
                System.out.println("ℹ️ Change Password popup dismissed");
            } catch (Exception e) {
                System.out.println("ℹ️ No Change Password popup appeared");
            }

            // ---- Navigate Sidebar → Admin → Lead Settings → Lead Stage ----
            WebElement menuIcon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"main-wrapper\"]/div[1]/div[1]/div/div/span[2]")));
            js.executeScript("arguments[0].click();", menuIcon);
            Thread.sleep(1000);
            System.out.println("📂 Sidebar clicked");

            WebElement adminMenu = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"menu\"]/li[4]/a")));
            js.executeScript("arguments[0].click();", adminMenu);
            Thread.sleep(1000);
            System.out.println("🛠️ Admin clicked");

            WebElement leadSettings = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"menu\"]/li[4]/ul/li[2]/a")));
            js.executeScript("arguments[0].click();", leadSettings);
            Thread.sleep(1000);
            System.out.println("⚙️ Lead Settings clicked");

            WebElement leadStage = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(.,'Lead Stage')]")));
            js.executeScript("arguments[0].click();", leadStage);
            Thread.sleep(1000);
            System.out.println("📊 Lead Stage tab clicked");

            // ---- CREATE NEW LEAD STAGE ----
            WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Create')]")));
            js.executeScript("arguments[0].click();", createBtn);
            Thread.sleep(1000);
            System.out.println("➕ Clicked Create button");

            WebElement stageName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'modal-dialog')]//input")));
            stageName.clear();
            stageName.sendKeys("Test Stage");
            Thread.sleep(1000);
            System.out.println("📝 Entered Lead Stage name: Test Stage");

            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'modal-dialog')]//button[contains(.,'Save')]")));
            js.executeScript("arguments[0].click();", saveBtn);
            Thread.sleep(1000);
            System.out.println("✅ Saved Lead Stage");

            wait.until(ExpectedConditions.invisibilityOf(saveBtn));

            // ---- EDIT LAST LEAD STAGE ----
            List<WebElement> editBtns = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//*[@id='lead_status']//span[@title='Edit']")));
            WebElement lastEdit = editBtns.get(editBtns.size() - 1);
            js.executeScript("arguments[0].click();", lastEdit);
            Thread.sleep(1000);
            System.out.println("✏️ Clicked Edit on last Lead Stage");

            WebElement editName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'modal-dialog')]//input")));
            editName.clear();
            editName.sendKeys("Updated Stage");
            Thread.sleep(1000);
            System.out.println("✏️ Updated Lead Stage name to: Updated Stage");

            WebElement saveBtn1 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'modal-dialog')]//button[contains(.,'Save')]")));
            js.executeScript("arguments[0].click();", saveBtn1);
            Thread.sleep(1000);
            System.out.println("✅ Saved updated Lead Stage");

            wait.until(ExpectedConditions.invisibilityOf(saveBtn1));

            // ---- DELETE LAST LEAD STAGE ----
            List<WebElement> deleteBtns = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//*[@id='lead_status']//span[@title='Delete']")));
            WebElement lastDelete = deleteBtns.get(deleteBtns.size() - 1);
            js.executeScript("arguments[0].click();", lastDelete);
            Thread.sleep(1000);
            System.out.println("🗑️ Clicked Delete on last Lead Stage");

            WebElement confirmDelete = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'swal2-popup')]//button[contains(.,'Yes')]")));
            js.executeScript("arguments[0].click();", confirmDelete);
            Thread.sleep(1000);
            System.out.println("✅ Confirmed Delete");

            wait.until(ExpectedConditions.invisibilityOf(confirmDelete));

            System.out.println("🎉 Create → Edit → Delete cycle completed successfully");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}