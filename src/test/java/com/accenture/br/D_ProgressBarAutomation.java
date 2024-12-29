package com.accenture.br;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class D_ProgressBarAutomation {
    public static void main(String[] args) {
        // Configurar o WebDriver
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/drivers/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Timeout global para carregamento de elementos
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        try {
            // 1. Acessar o site
            driver.get("https://demoqa.com/");

            // Esperar o carregamento do site
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("app")));

            // 2. Fechar o banner fixo se presente
            try {
                WebElement fixedBanner = driver.findElement(By.id("fixedban"));
                if (fixedBanner.isDisplayed()) {
                    System.out.println("Banner detectado. Removendo...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", fixedBanner);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Nenhum banner encontrado.");
            }

            // 3. Escolher a opção Widgets
            WebElement widgetsButton = driver.findElement(By.xpath("//h5[text()='Widgets']"));
            wait.until(ExpectedConditions.elementToBeClickable(widgetsButton));

            // Tentar clicar diretamente
            try {
                widgetsButton.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Clique interceptado, tentando via JavaScript.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", widgetsButton);
            }

            // 4. Clicar no submenu Progress Bar
            WebElement progressBarMenu = driver.findElement(By.xpath("//span[text()='Progress Bar']"));
            wait.until(ExpectedConditions.elementToBeClickable(progressBarMenu));

            try {
                progressBarMenu.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Clique interceptado no Progress Bar, tentando via JavaScript.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", progressBarMenu);
            }

            // 5. Clicar no botão Start
            WebElement startButton = driver.findElement(By.id("startStopButton"));
            startButton.click();

            // 6. Parar antes dos 25%
            boolean stoppedBefore25 = false;
            while (!stoppedBefore25) {
                WebElement progressBar = driver.findElement(By.className("progress-bar"));
                String progressValue = progressBar.getAttribute("aria-valuenow");
                int progress = Integer.parseInt(progressValue);

                if (progress > 20 && progress < 25) { // Aproximar-se de 25%
                    startButton.click(); // Parar o progresso
                    stoppedBefore25 = true;
                    System.out.println("Progresso parado em: " + progress + "%");

                    // Pausa de 2 segundos
                    Thread.sleep(2000);
                }
            }

            // 7. Validar que o valor da Progress Bar é menor ou igual a 25%
            WebElement progressBar = driver.findElement(By.className("progress-bar"));
            int finalProgress = Integer.parseInt(progressBar.getAttribute("aria-valuenow"));
            if (finalProgress <= 25) {
                System.out.println("Validação bem-sucedida: Progresso é " + finalProgress + "% (≤ 25%)");
            } else {
                System.out.println("Validação falhou: Progresso é " + finalProgress + "% (> 25%)");
            }

            // 8. Apertar Start novamente e ao chegar aos 100%, resetar a Progress Bar
            startButton.click();
            wait.until(ExpectedConditions.textToBePresentInElement(progressBar, "100%"));
            System.out.println("Progresso completado: 100%");

            WebElement resetButton = driver.findElement(By.id("resetButton"));
            resetButton.click();
            System.out.println("Progress Bar foi resetada.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Finalizar o teste
            driver.quit();
        }
    }
}
