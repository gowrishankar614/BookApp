package assessment;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.wdm.WebDriverManager;

public class bookLog extends BaseTest {

	String BASE_URL = "https://demoqa.com/books";
	String SEARCH_TERM = "JavaScript";
	String EXPECTED_BOOK_NAME = "Programming JavaScript Applications";
	String EXPECTED_AUTHOR = "Eric Elliott";

	@Test(priority = 1)
	public void bookSearch() {
		test = extent.createTest("Test One", "Book Search using keyword");
		// WebDriver driver = DriverManager.getDriver(); // Get the WebDriver instance

		try {

			driver.get(BASE_URL);
			// driver.findElement(By.xpath("//input[@id='searchBox']")).sendKeys(SEARCH_TERM);
			WebElement searchBox = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='searchBox']")));

			searchBox.sendKeys(SEARCH_TERM);
			
			// Wait for search results to load
			Thread.sleep(2000);

			// Validate the product name and author - wait for elements to be visible
			WebElement bookNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("see-book-Programming JavaScript Applications")));
			WebElement authorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[normalize-space()='Eric Elliott']")));

			String bookName = bookNameElement.getText();
			String author = authorElement.getText();

			// Assertions
			Assert.assertEquals(EXPECTED_BOOK_NAME, bookName);
			Assert.assertEquals(EXPECTED_AUTHOR, author);

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,250)", "");

			WebElement bookLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='see-book-Programming JavaScript Applications']")));
			bookLink.click();
			// Log result
			test.pass("Test One passed");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}

	}

	@Test(priority = 2)
	public void studentForm() throws IOException {
		test = extent.createTest("Test Two", "Fill up the form for 5 studends");
		// WebDriver driver = DriverManager.getDriver(); // Get the WebDriver instance

		try {
			driver.get(BASE_URL);
			driver.findElement(By.xpath("(//div[contains(@class,'header-wrapper')])[2]")).click();
			// driver.findElement(By.xpath("(//li[@id='item-0'])[2]")).click();
			WebElement element = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//li[@id='item-0'])[2]")));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

			element.click();

			// Initialize Excel utility
			XLUtility excelUtils = new XLUtility("src/resources/data.xlsx");

			// Iterate through each row in the Excel sheet
			for (int i = 1; i < excelUtils.getRowCount(); i++) { // Starting from 1 to skip header
				String FName = excelUtils.getCellData(i, 0);
				String LName = excelUtils.getCellData(i, 1);
				String Gender = excelUtils.getCellData(i, 2);
				String phone = excelUtils.getCellData(i, 3);

				// Fill out the form fields
				driver.findElement(By.cssSelector("#firstName")).sendKeys(FName);
				driver.findElement(By.cssSelector("#lastName")).sendKeys(LName);
				driver.findElement(By.cssSelector("#userNumber")).sendKeys(phone);

				// Select the radio button based on gender
				if ("male".equalsIgnoreCase(Gender)) {
					driver.findElement(By.cssSelector("label[for='gender-radio-1']")).click();
				} else if ("female".equalsIgnoreCase(Gender)) {
					driver.findElement(By.cssSelector("label[for='gender-radio-2']")).click();
				} else if ("other".equalsIgnoreCase(Gender)) {
					driver.findElement(By.cssSelector("label[for='gender-radio-3']")).click();
				}

				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,500)", "");

			// Submit the form
			// driver.findElement(By.cssSelector("#submit")).click();
			WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submit);
			
			// Use JavaScript click to avoid interception
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);

				// Wait for submission to process
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

				// Close the confirmation window
				// driver.findElement(By.cssSelector("#closeLargeModal")).click();
				WebElement closeButton = driver.findElement(By.id("closeLargeModal"));
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);

			}

			// Close the Excel utility
			excelUtils.close();

			test.pass("Test Two passed");
		} catch (IOException e) {
			test.fail("IO Exception occurred: " + e.getMessage());
			Assert.fail("Test Three failed due to IO Exception");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

	@Test(priority = 3)
	public void WebTableToExcel() throws InterruptedException, IOException {
		test = extent.createTest("Test Three", "Write the webtable data to excel");
		// WebDriver driver = DriverManager.getDriver(); // Get the WebDriver instance

		try {
			// Navigate to the web page
			driver.get(BASE_URL);

			// Locate the table
			driver.findElement(By.xpath("(//div[contains(@class,'header-wrapper')])[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("(//li[@id='item-3'])[1]")).click();
			Thread.sleep(2000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,300)", "");
			Thread.sleep(2000);
			// WebElement table = driver.findElement(By.xpath("//div[@class='ReactTable
			// -striped -highlight']"));
			WebElement table = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'ReactTable')]")));

			// Prepare data to write to Excel
			int rowCount = 0;
			String[][] data = new String[table.findElements(By.className("rt-tr")).size()][];

			for (WebElement row : table.findElements(By.className("rt-tr"))) {
				String[] rowData = new String[row.findElements(By.className("rt-td")).size()];
				int colCount = 0;

				for (WebElement cell : row.findElements(By.className("rt-td"))) {
					rowData[colCount++] = cell.getText();
				}
				data[rowCount++] = rowData;
			}

			// Use ExcelUtil to write data
			String excelPath = "src/resources/data.xlsx";
			XLUtility excelUtil = new XLUtility(excelPath);
			excelUtil.writeDataToNewSheet("WebTable", data);
			excelUtil.close();

			// System.out.println("Data written to Excel successfully");
			test.pass("Test Three passed");
		} catch (IOException e) {
			test.fail("IO Exception occurred: " + e.getMessage());
			Assert.fail("Test Three failed due to IO Exception");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}

	}

	@Test(priority = 4)
	public void DragAndDropExample() throws InterruptedException {
		test = extent.createTest("Test Four", "Sort the List");
		// WebDriver driver = DriverManager.getDriver(); // Get the WebDriver instance

		try {
			// Navigate to the web page
			driver.get(BASE_URL);

			// Locate the table
			driver.findElement(By.xpath("(//div[contains(@class,'header-wrapper')])[5]")).click();
			Thread.sleep(5000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,300)", "");
			
			// Wait for element to be clickable before clicking
			WebElement item = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//li[@id='item-0'])[5]")));
			js.executeScript("arguments[0].scrollIntoView(true);", item);
			js.executeScript("arguments[0].click();", item);
			
			js.executeScript("window.scrollBy(0,300)", "");
			Thread.sleep(3000);

			// Find the list of elements
			// List<WebElement> numbers =
			// driver.findElements(By.cssSelector(".vertical-list-container
			// .list-group-item"));
			List<WebElement> numbers = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.cssSelector(".vertical-list-container .list-group-item")));
			// System.out.println("The length is " + numbers.size());

			// Initialize the Actions class
			Actions actions = new Actions(driver);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// Drag and drop in the reverse order
			actions.dragAndDrop(numbers.get(5), numbers.get(0)).perform(); // Six to One
			actions.dragAndDrop(numbers.get(5), numbers.get(1)).perform(); // Five to Two
			actions.dragAndDrop(numbers.get(5), numbers.get(2)).perform(); // Four to Three
			actions.dragAndDrop(numbers.get(5), numbers.get(3)).perform(); // Three to Two
			actions.dragAndDrop(numbers.get(5), numbers.get(4)).perform(); // Two to One

			test.pass("Test Four passed");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Test failed: " + e.getMessage());
		}

	}

}
