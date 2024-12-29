package com.accenture.br;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class E_SortableAutomation {
    public static void main(String[] args) {
        // Configuração do WebDriver
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
            // 1. Acessar o site
            driver.get("https://demoqa.com/");

            // Esperar carregamento do site
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("app")));

            // 2. Fechar banner de anúncio se presente
            try {
                WebElement fixedBanner = driver.findElement(By.id("fixedban"));
                if (fixedBanner.isDisplayed()) {
                    System.out.println("Banner detectado. Tentando fechá-lo.");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", fixedBanner);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Nenhum banner encontrado.");
            }

            // 3. Escolher a opção Interactions
            WebElement interactionsButton = driver.findElement(By.xpath("//h5[text()='Interactions']"));
            scrollToElement(driver, interactionsButton);
            try {
                interactionsButton.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Clique interceptado. Tentando com JavaScript.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", interactionsButton);
            }

            // 4. Clicar no submenu Sortable
            WebElement sortableMenu = driver.findElement(By.xpath("//span[text()='Sortable']"));
            scrollToElement(driver, sortableMenu);
            wait.until(ExpectedConditions.elementToBeClickable(sortableMenu)).click();

            // 5. Utilizar drag and drop para reordenar os elementos
            List<WebElement> sortableItems = driver.findElements(By.cssSelector("#demo-tabpane-list .list-group-item"));
            Actions actions = new Actions(driver);

            for (int i = 0; i < sortableItems.size(); i++) {
                for (int j = sortableItems.size() - 1; j > i; j--) {
                    WebElement source = sortableItems.get(j);
                    WebElement target = sortableItems.get(j - 1);
                    actions.dragAndDrop(source, target).perform();
                    sortableItems = driver.findElements(By.cssSelector("#demo-tabpane-list .list-group-item")); // Atualiza lista
                }
            }

            System.out.println("Elementos ordenados com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit(); // Finalizar driver
        }
    }

    // Metodo auxiliar para rolar até um elemento
    private static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
