package com.accenture.br;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class C_Cucumber {

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("I am on the Web Tables page")
    public void i_am_on_the_web_tables_page() {
        // Configuração do ChromeDriver
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        // Instanciar o WebDriver
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Acessar a página de Web Tables
        driver.get("https://demoqa.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Clicar na seção "Elements"
        WebElement elementsButton = driver.findElement(By.xpath("//h5[text()='Elements']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsButton);
        elementsButton.click();

        // Clicar no submenu "Web Tables"
        WebElement webTablesButton = driver.findElement(By.xpath("//span[text()='Web Tables']"));
        wait.until(ExpectedConditions.elementToBeClickable(webTablesButton)).click();
    }

    @When("I create {int} new records")
    public void i_create_new_records(int recordCount) {
        for (int i = 1; i <= recordCount; i++) {
            WebElement addButton = driver.findElement(By.id("addNewRecordButton"));
            addButton.click();

            // Preencher o formulário com dados dinâmicos
            driver.findElement(By.id("firstName")).sendKeys("User" + i);
            driver.findElement(By.id("lastName")).sendKeys("Test" + i);
            driver.findElement(By.id("userEmail")).sendKeys("user" + i + "@example.com");
            driver.findElement(By.id("age")).sendKeys(String.valueOf(20 + i)); // Idade dinâmica
            driver.findElement(By.id("salary")).sendKeys(String.valueOf(40000 + (i * 1000))); // Salário dinâmico
            driver.findElement(By.id("department")).sendKeys("Department" + i);
            driver.findElement(By.id("submit")).click();
        }
    }

    @Then("I delete all new records")
    public void i_delete_all_new_records() {
        // Verificar e ocultar o iframe de anúncio, se presente
        try {
            WebElement iframeAd = driver.findElement(By.xpath("//iframe[contains(@id,'google_ads_iframe')]"));
            driver.switchTo().frame(iframeAd);
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", iframeAd);
            driver.switchTo().defaultContent();
        } catch (NoSuchElementException e) {
            System.out.println("No iframe found, proceeding with deletion...");
        }

        // Deletar os registros
        List<WebElement> deleteButtons = driver.findElements(By.xpath("//span[@title='Delete']"));
        for (WebElement deleteButton : deleteButtons) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteButton); // Rolagem
            wait.until(ExpectedConditions.elementToBeClickable(deleteButton)); // Espera até clicável
            deleteButton.click();

            // Lidar com alertas, se necessário
            try {
                Alert alert = driver.switchTo().alert();
                alert.accept();
            } catch (NoAlertPresentException ignored) {
            }
        }
    }

    @Then("I close the browser")
    public void i_close_the_browser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
