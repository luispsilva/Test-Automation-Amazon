package Steps;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AmazonSteps {
	
	private WebDriver driver;
	String compareName;
	
	@Given("^I navigate to \"([^\"]*)\"\\.$")
	public void i_navigate_to(String arg1) throws Throwable {
		
		System.setProperty("webdriver.chrome.driver", "\\Users\\lsilva26\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://www.amazon.com/");
	}

	@When("^I select the options \"([^\"]*)\" in the dropdown next to the search text input criteria\\.$")
	public void i_select_the_options_in_the_dropdown_next_to_the_search_text_input_criteria(String arg1) throws Throwable {
	    
	    WebElement bookElement = driver.findElement(By.id("searchDropdownBox"));
	    Select books = new Select(bookElement);
	    books.selectByVisibleText("Books");
	}

	@Then("^I search for \"([^\"]*)\"\\.$")
	public void i_search_for(String arg1) throws Throwable {
	    
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Test automation");
		driver.findElement(By.xpath("//div/input[@value='Go']")).click();
	}

	@Then("^I select the cheapset book of the page without using any sorting method available\\.$")
	public void i_select_the_cheapset_book_of_the_page_without_using_any_sorting_method_available() throws Throwable {
		
		List<WebElement> listBooks = driver.findElements(By.cssSelector("div[data-cel-widget^='search_result_']"));
		Double minorPrice = null;
		WebElement clickElement = null;
		
		for (int i=0; i<listBooks.size(); i++) {
			
			WebDriverWait wait=new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a-price .a-price-whole")));
			
			WebElement element = listBooks.get(i);
			String whole = element.findElement(By.cssSelector(".a-price .a-price-whole")).getText();
			String fraction = element.findElement(By.cssSelector(".a-price .a-price-fraction")).getText();
			String allPrice = whole+"."+fraction;
			Double price = Double.parseDouble(allPrice);
			
			if (minorPrice == null) {
				minorPrice = price;
				clickElement = element;
				compareName = clickElement.findElement(By.cssSelector("h2 span")).getText();
				
			} else if (price<minorPrice) {
				clickElement = element;
				minorPrice = price;
				compareName = clickElement.findElement(By.cssSelector("h2 span")).getText();
			}
		}
		clickElement.findElement(By.cssSelector(".a-price .a-price-whole")).click();
	}

	@When("^I reach the detailed book page, I check if the name in the header is the same of the book that I select previously$")
	public void i_reach_the_detailed_book_page_I_check_if_the_name_in_the_header_is_the_same_of_the_book_that_I_select_previously() throws Throwable {
	   
		Assert.assertEquals(compareName, driver.findElement(By.id("ebooksProductTitle")).getText());
		driver.quit();
	}
	
}

