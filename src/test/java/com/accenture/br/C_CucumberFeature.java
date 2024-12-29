package com.accenture.br;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Caminho para o arquivo .feature
        glue = "com.accenture.br", // Pacote onde estão as definições de passos
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Relatórios
        monochrome = true // Saída mais legível no console
)
public class C_CucumberFeature {
    // Classe apenas para executar os testes Cucumber
}
