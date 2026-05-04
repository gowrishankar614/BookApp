package assessment;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object for Book Application functionality
 * Encapsulates all interactions with the Books page
 */
public class BooksPage {

    private WebDriver driver;
    private SeleniumUtils utils;

    public BooksPage(WebDriver driver, SeleniumUtils utils) {
        this.driver = driver;
        this.utils = utils;
    }

    /**
     * Navigate to the books page
     */
    public void navigateToBooks() {
        driver.get(TestData.BASE_URL);
    }

    /**
     * Search for a book by keyword
     */
    public void searchBook(String searchTerm) throws InterruptedException {
        WebElement searchBox = utils.waitForVisibility(Locators.SEARCH_BOX);
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
        utils.waitWithDelay(TestData.MEDIUM_WAIT);
    }

    /**
     * Get book name element after search
     */
    public WebElement getBookNameElement(String bookName) throws Exception {
        try {
            return utils.waitForVisibility(By.id("see-book-" + bookName));
        } catch (Exception e) {
            return utils.waitForVisibility(By.linkText(bookName));
        }
    }

    /**
     * Get author element
     */
    public WebElement getAuthorElement(String authorName) {
        try {
            return utils.waitForVisibility(By.xpath("//div[contains(text(), '" + authorName + "')]"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Click on a book to view details
     */
    public void clickBook(String bookName) throws InterruptedException {
        utils.scrollByPixels(TestData.SCROLL_250);
        WebElement bookLink = utils.waitForClickable(By.xpath("//span[@id='see-book-" + bookName + "']"));
        bookLink.click();
    }

    /**
     * Navigate to Form page
     */
    public void navigateToForm() throws InterruptedException {
        utils.waitForClickable(Locators.HEADER_WRAPPER_SECOND).click();
        WebElement menuItem = utils.waitForClickable(Locators.MENU_ITEM_SECOND_ZERO);
        utils.scrollIntoView(menuItem);
        menuItem.click();
    }

    /**
     * Fill form with student data
     */
    public void fillStudentForm(String firstName, String lastName, String gender, String phone) 
            throws InterruptedException {
        utils.waitAndSendKeys(Locators.FIRST_NAME, firstName);
        utils.waitAndSendKeys(Locators.LAST_NAME, lastName);
        utils.waitAndSendKeys(Locators.USER_NUMBER, phone);

        selectGender(gender);
    }

    /**
     * Select gender radio button
     */
    public void selectGender(String gender) throws InterruptedException {
        By genderLocator;
        if (TestData.GENDER_MALE.equalsIgnoreCase(gender)) {
            genderLocator = Locators.GENDER_RADIO_MALE;
        } else if (TestData.GENDER_FEMALE.equalsIgnoreCase(gender)) {
            genderLocator = Locators.GENDER_RADIO_FEMALE;
        } else if (TestData.GENDER_OTHER.equalsIgnoreCase(gender)) {
            genderLocator = Locators.GENDER_RADIO_OTHER;
        } else {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }
        utils.waitAndClick(genderLocator);
    }

    /**
     * Scroll to submit button and submit form
     */
    public void submitForm() throws InterruptedException {
        WebElement submitBtn = utils.waitForClickable(Locators.SUBMIT_BUTTON);
        utils.scrollIntoView(submitBtn);
        utils.jsClick(submitBtn);
        utils.waitWithDelay(TestData.SHORT_WAIT);
    }

    /**
     * Close the form confirmation modal
     */
    public void closeFormModal() {
        WebElement closeBtn = driver.findElement(Locators.CLOSE_MODAL);
        utils.jsClick(closeBtn);
    }

    /**
     * Navigate to Web Table section
     */
    public void navigateToWebTable() throws InterruptedException {
        utils.waitForClickable(Locators.HEADER_WRAPPER_FIRST).click();
        utils.waitWithDelay(TestData.LONG_WAIT);
        utils.waitForClickable(Locators.MENU_ITEM_FIRST_THREE).click();
        utils.waitWithDelay(TestData.SHORT_WAIT);
        utils.scrollByPixels(TestData.SCROLL_300);
        utils.waitWithDelay(TestData.SHORT_WAIT);
    }

    /**
     * Extract web table data
     */
    public String[][] extractTableData() throws InterruptedException {
        WebElement table = utils.waitForVisibility(Locators.WEB_TABLE);
        
        List<WebElement> rows = table.findElements(Locators.TABLE_ROW);
        String[][] data = new String[rows.size()][];

        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(Locators.TABLE_CELL);
            String[] rowData = new String[cells.size()];
            for (int j = 0; j < cells.size(); j++) {
                rowData[j] = cells.get(j).getText();
            }
            data[i] = rowData;
        }
        return data;
    }

    /**
     * Navigate to Drag and Drop section
     */
    public void navigateToDragAndDrop() throws InterruptedException {
        utils.waitForClickable(Locators.HEADER_WRAPPER_FIFTH).click();
        utils.waitWithDelay(TestData.LONG_WAIT);
        utils.scrollByPixels(TestData.SCROLL_300);
        
        WebElement item = utils.waitForClickable(Locators.MENU_ITEM_FIFTH_ZERO);
        utils.scrollIntoView(item);
        utils.jsClick(item);
        
        utils.scrollByPixels(TestData.SCROLL_300);
        utils.waitWithDelay(TestData.MEDIUM_WAIT);
    }

    /**
     * Get draggable list items
     */
    public List<WebElement> getDraggableItems() {
        return utils.waitForAllVisible(Locators.VERTICAL_LIST);
    }

    /**
     * Perform drag and drop for list sorting
     */
    public void sortListByDragDrop(List<WebElement> numbers) {
        utils.dragAndDrop(numbers.get(5), numbers.get(0)); // Six to One
        utils.dragAndDrop(numbers.get(5), numbers.get(1)); // Five to Two
        utils.dragAndDrop(numbers.get(5), numbers.get(2)); // Four to Three
        utils.dragAndDrop(numbers.get(5), numbers.get(3)); // Three to Two
        utils.dragAndDrop(numbers.get(5), numbers.get(4)); // Two to One
    }
}
