package com.accenture.br;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class B_BrowserWindowsAutomation {
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

            // 2. Escolher a opção Alerts, Frame & Windows
            WebElement alertsFrameWindows = driver.findElement(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
            wait.until(ExpectedConditions.elementToBeClickable(alertsFrameWindows));

            // Tentar clicar diretamente
            try {
                alertsFrameWindows.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Clique interceptado, tentando via JavaScript.");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", alertsFrameWindows);
            }

            // 3. Clicar no submenu Browser Windows
            WebElement browserWindows = driver.findElement(By.xpath("//span[text()='Browser Windows']"));
            wait.until(ExpectedConditions.elementToBeClickable(browserWindows));
            browserWindows.click();

            // 4. Clicar no botão New Window
            WebElement newWindowButton = driver.findElement(By.id("windowButton"));
            wait.until(ExpectedConditions.elementToBeClickable(newWindowButton));
            newWindowButton.click();

            // 5. Validar que uma nova janela foi aberta
            String mainWindow = driver.getWindowHandle(); // Armazena o handle da janela principal
            Set<String> allWindows = driver.getWindowHandles();

            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(mainWindow)) {
                    driver.switchTo().window(windowHandle); // Trocar para a nova janela

                    // Verificar a mensagem "This is a sample page"
                    WebElement sampleText = driver.findElement(By.id("sampleHeading"));
                    if (sampleText.getText().equals("This is a sample page")) {
                        System.out.println("Validação bem-sucedida: mensagem exibida corretamente.");
                    } else {
                        System.out.println("Falha na validação: mensagem incorreta.");
                    }

                    // 6. Fechar a nova janela
                    driver.close();
                    System.out.println("Nova janela fechada com sucesso.");
                }
            }

            // Voltar para a janela principal
            driver.switchTo().window(mainWindow);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Finalizar o teste
            driver.quit();
        }
    }
}
