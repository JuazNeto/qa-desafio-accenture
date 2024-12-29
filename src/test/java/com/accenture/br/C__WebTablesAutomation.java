package com.accenture.br;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class C__WebTablesAutomation {
    public static void main(String[] args) {
        // Configurar caminho relativo para o ChromeDriver
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/drivers/chromedriver.exe");

        // Configurar opções para o ChromeDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        // Instanciar o WebDriver com as opções configuradas
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Timeout global

        try {
            // 1. Acessar o site
            driver.get("https://demoqa.com/");

            // Esperar o carregamento completo da página inicial
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("app")));

            // 2. Fechar ou ocultar o banner que interfere nos cliques
            try {
                WebElement fixedBan = driver.findElement(By.id("fixedban"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", fixedBan);
            } catch (NoSuchElementException e) {
                System.out.println("Banner não encontrado. Continuando...");
            }

            // 3. Escolher a opção Elements na página inicial
            WebElement elementsButton = driver.findElement(By.xpath("//h5[text()='Elements']"));

            // Garantir que o elemento está visível e clicável
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementsButton);
            elementsButton.click();

            // 4. Clicar no submenu Web Tables
            WebElement webTablesButton = driver.findElement(By.xpath("//span[text()='Web Tables']"));
            wait.until(ExpectedConditions.elementToBeClickable(webTablesButton));
            webTablesButton.click();

            // 5. Criar um novo registro
            WebElement addButton = driver.findElement(By.id("addNewRecordButton"));
            addButton.click();

            driver.findElement(By.id("firstName")).sendKeys("Juarez");
            driver.findElement(By.id("lastName")).sendKeys("Neto");
            driver.findElement(By.id("userEmail")).sendKeys("myemail@email.com");
            driver.findElement(By.id("age")).sendKeys("30");
            driver.findElement(By.id("salary")).sendKeys("5000");
            driver.findElement(By.id("department")).sendKeys("IT");
            driver.findElement(By.id("submit")).click();

            // 6. Editar o novo registro criado
            WebElement editButton = driver.findElement(By.xpath("//span[text()='Juarez Neto']/ancestor::div[@class='rt-tr-group']//span[@title='Edit']"));
            editButton.click();

            driver.findElement(By.id("firstName")).clear();
            driver.findElement(By.id("firstName")).sendKeys("Juaz");
            driver.findElement(By.id("submit")).click();

            // 7. Deletar o novo registro criado
            WebElement deleteButton = driver.findElement(By.xpath("//span[text()='Juaz Neto']/ancestor::div[@class='rt-tr-group']//span[@title='Delete']"));
            deleteButton.click();

            // Confirmar deleção
            driver.switchTo().alert().accept();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8. Finalizar o teste
            driver.quit();
        }
    }
}
