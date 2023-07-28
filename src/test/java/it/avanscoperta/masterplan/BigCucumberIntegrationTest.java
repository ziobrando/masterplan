package it.avanscoperta.masterplan;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        plugin = {"pretty", "html:target/cucumber/masterplan"},
        extraGlue = "it.avanscoperta.masterplan"
)
@DisplayName("Cucumber Features")
public class BigCucumberIntegrationTest {


}
