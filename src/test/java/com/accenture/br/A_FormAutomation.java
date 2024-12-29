package com.accenture.br;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class A_FormAutomation {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/drivers/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            driver.get("https://demoqa.com/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Remover iframes e anúncios
            js.executeScript("document.querySelectorAll('iframe').forEach(iframe => iframe.remove());");
            js.executeScript("document.querySelectorAll('div[id*=\"google_ads_iframe\"]').forEach(div => div.remove());");

            WebElement formsButton = driver.findElement(By.xpath("//h5[text()='Forms']"));
            js.executeScript("arguments[0].scrollIntoView(true);", formsButton);
            js.executeScript("arguments[0].click();", formsButton);

            WebElement practiceForm = driver.findElement(By.xpath("//span[text()='Practice Form']"));
            wait.until(ExpectedConditions.elementToBeClickable(practiceForm)).click();

            driver.findElement(By.id("firstName")).sendKeys("Keyser");
            driver.findElement(By.id("lastName")).sendKeys("Soze");
            driver.findElement(By.id("userEmail")).sendKeys("email@email.com");

            // Clique no botão de rádio com JavaScript
            WebElement maleRadioButton = driver.findElement(By.xpath("//label[@for='gender-radio-1']"));
            js.executeScript("arguments[0].scrollIntoView(true);", maleRadioButton);
            js.executeScript("arguments[0].click();", maleRadioButton);

            driver.findElement(By.id("userNumber")).sendKeys("8112345678");

            js.executeScript("document.getElementById('dateOfBirthInput').value='01 Jan 1990'");

            WebElement subjectsInput = driver.findElement(By.id("subjectsInput"));
            subjectsInput.sendKeys("Computer Science");
            subjectsInput.sendKeys(Keys.ENTER);

            driver.findElement(By.xpath("//label[text()='Reading']")).click();

            String filePath = System.getProperty("user.dir") + "/src/test/resources/formdata.txt";
            driver.findElement(By.id("uploadPicture")).sendKeys(filePath);

            driver.findElement(By.id("currentAddress")).sendKeys("Rua 123");

            WebElement stateInput = driver.findElement(By.id("react-select-3-input"));
            stateInput.sendKeys("NCR");
            stateInput.sendKeys(Keys.ENTER);

            WebElement cityInput = driver.findElement(By.id("react-select-4-input"));
            cityInput.sendKeys("Delhi");
            cityInput.sendKeys(Keys.ENTER);

            driver.findElement(By.id("submit")).click();

            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-content")));
            if (modal.isDisplayed()) {
                System.out.println("Popup de confirmação exibido.");
            }

            driver.findElement(By.id("closeLargeModal")).click();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
