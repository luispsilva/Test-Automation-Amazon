package Runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class) 
@CucumberOptions(
		features = "src/test/resources/features/book_amazon.feature",
		glue = "Steps",
		plugin = "pretty",
		monochrome = true,
		dryRun = false
		)
public class Runner {
	
}
