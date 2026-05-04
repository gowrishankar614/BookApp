package assessment;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for DemoQA Books application
 * Tests include: Book Search, Form Submission, Web Table Extraction, and Drag & Drop
 */
public class bookLog extends BaseTest {

	private BooksPage booksPage;
	private SeleniumUtils utils;

	@Override
	public void setUp() {
		super.setUp();
		utils = new SeleniumUtils(driver, wait);
		booksPage = new BooksPage(driver, utils);
	}

	@Test(priority = 1)
	public void bookSearch() {
		test = extent.createTest("Test One", "Book Search using keyword");

		try {
			booksPage.navigateToBooks();
			utils.waitWithDelay(TestData.MEDIUM_WAIT);
			
			// Search for book
			booksPage.searchBook(TestData.SEARCH_TERM);

			// Get book name and validate
			WebElement bookNameElement = booksPage.getBookNameElement(TestData.EXPECTED_BOOK_NAME);
			String bookName = bookNameElement.getText();
			
			// Scroll to find author element
			utils.scrollIntoView(bookNameElement);
			utils.waitWithDelay(TestData.SHORT_WAIT);
			
			// Try to find author
			WebElement authorElement = booksPage.getAuthorElement(TestData.EXPECTED_AUTHOR);
			String author = (authorElement != null) ? authorElement.getText() : TestData.EXPECTED_AUTHOR;

			// Assertions
			Assert.assertEquals(TestData.EXPECTED_BOOK_NAME, bookName);
			if (authorElement != null) {
				Assert.assertEquals(TestData.EXPECTED_AUTHOR, author);
			}

			// Click on book
			booksPage.clickBook(TestData.EXPECTED_BOOK_NAME);
			test.pass("Test One passed");
			
		} catch (Exception e) {
			test.fail("Test failed: " + e.getMessage());
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

	@Test(priority = 2)
	public void studentForm() throws IOException {
		test = extent.createTest("Test Two", "Fill up the form for 5 students");

		try {
			driver.get(TestData.BASE_URL);
			booksPage.navigateToForm();

			// Initialize Excel utility
			XLUtility excelUtils = new XLUtility(TestData.EXCEL_DATA_PATH);

			// Iterate through each row in the Excel sheet
			for (int i = 1; i < excelUtils.getRowCount(); i++) {
				String firstName = excelUtils.getCellData(i, 0);
				String lastName = excelUtils.getCellData(i, 1);
				String gender = excelUtils.getCellData(i, 2);
				String phone = excelUtils.getCellData(i, 3);

				// Fill out the form fields
				booksPage.fillStudentForm(firstName, lastName, gender, phone);

				utils.scrollByPixels(TestData.SCROLL_500);

				// Submit the form
				booksPage.submitForm();

				// Close the confirmation window
				booksPage.closeFormModal();
			}

			// Close the Excel utility
			excelUtils.close();

			test.pass("Test Two passed");
		} catch (IOException e) {
			test.fail("IO Exception occurred: " + e.getMessage());
			Assert.fail("Test Two failed due to IO Exception");
		} catch (Exception e) {
			test.fail("Test failed: " + e.getMessage());
			Assert.fail("Test failed: " + e.getMessage());
		}
	}

	@Test(priority = 3)
	public void WebTableToExcel() throws InterruptedException, IOException {
		test = extent.createTest("Test Three", "Write the webtable data to excel");

		try {
			// Navigate to the web page
			driver.get(TestData.BASE_URL);
			booksPage.navigateToWebTable();

			// Extract table data
			String[][] data = booksPage.extractTableData();

			// Use ExcelUtil to write data
			XLUtility excelUtil = new XLUtility(TestData.EXCEL_DATA_PATH);
			excelUtil.writeDataToNewSheet(TestData.WEBTABLE_SHEET_NAME, data);
			excelUtil.close();

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

		try {
			// Navigate to the web page
			driver.get(TestData.BASE_URL);
			booksPage.navigateToDragAndDrop();

			// Get draggable items
			List<WebElement> numbers = booksPage.getDraggableItems();

			// Perform drag and drop operations
			booksPage.sortListByDragDrop(numbers);

			test.pass("Test Four passed");
		} catch (Exception e) {
			e.printStackTrace();
			test.fail("Test failed: " + e.getMessage());
			Assert.fail("Test failed: " + e.getMessage());
		}
	}
}
