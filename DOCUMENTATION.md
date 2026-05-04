# SeleniumFramework Project - Complete Documentation

## 📋 Table of Contents
1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [File Structure](#file-structure)
4. [Classes & Their Responsibilities](#classes--their-responsibilities)
5. [Usage Guide](#usage-guide)
6. [Configuration](#configuration)
7. [Troubleshooting](#troubleshooting)

---

## 🎯 Project Overview

**SeleniumFramework** is a robust, maintainable test automation framework built with:
- **Selenium WebDriver 4.20.0** - Browser automation
- **TestNG 7.12.0** - Test execution and reporting
- **Apache POI 5.4.0** - Excel data handling
- **Extent Reports 5.1.2** - Advanced reporting
- **WebDriverManager 6.1.0** - Automatic driver management

### Key Features
✅ Page Object Model (POM) architecture
✅ Centralized locator management
✅ Comprehensive utility methods
✅ Data-driven testing support
✅ Extent HTML reports
✅ Thread-safe driver management
✅ Excel integration for test data

---

## 🏗️ Architecture

### Design Patterns Used

#### 1. Page Object Model (POM)
- Each page is represented by a separate class (e.g., `BooksPage`)
- All page interactions are encapsulated
- Separates test logic from page implementation

#### 2. Singleton Pattern
- `DriverManager` maintains a single WebDriver instance per thread
- Uses ThreadLocal for thread-safe execution

#### 3. Utility Pattern
- `SeleniumUtils` provides reusable methods
- `XLUtility` handles Excel operations

#### 4. Factory Pattern (Implicit)
- `DriverManager.setupDriver()` creates appropriate driver instances

---

## 📁 File Structure

### Core Test Class
```
bookLog.java
├── bookSearch()          - Test for book search functionality
├── studentForm()         - Test for form submission
├── WebTableToExcel()     - Test for web table extraction
└── DragAndDropExample()  - Test for drag and drop operations
```

### Page Objects
```
BooksPage.java
├── navigateToBooks()
├── searchBook()
├── navigateToForm()
├── fillStudentForm()
├── navigateToWebTable()
├── extractTableData()
├── navigateToDragAndDrop()
└── sortListByDragDrop()
```

### Utilities & Managers
```
SeleniumUtils.java      - Common Selenium operations
DriverManager.java      - WebDriver lifecycle management
XLUtility.java         - Excel read/write operations
WaitUtils.java         - Explicit wait methods (deprecated)
```

### Constants & Configuration
```
Locators.java          - All element locators (XPath, CSS, etc.)
TestData.java          - Test data and configuration values
```

### Base Classes
```
BaseTest.java          - Base class for all test classes
ExtentReport.java      - Extent Reports configuration
```

---

## 🔧 Classes & Their Responsibilities

### bookLog.java
**Purpose**: Main test execution class

**Methods**:
- `setUp()` - Initialize page objects and utilities
- `bookSearch()` - Test book search functionality
- `studentForm()` - Test form submission with Excel data
- `WebTableToExcel()` - Extract and export table data
- `DragAndDropExample()` - Test drag-and-drop sorting

**Key Points**:
- Extends `BaseTest` for common setup/teardown
- Uses `BooksPage` for all page interactions
- Uses `SeleniumUtils` for common operations
- References `TestData` for all test values

---

### BooksPage.java
**Purpose**: Page Object encapsulating all book app interactions

**Key Methods**:
```java
public void navigateToBooks()                          // Go to books page
public void searchBook(String searchTerm)              // Search for a book
public WebElement getBookNameElement(String name)     // Get book element
public WebElement getAuthorElement(String author)     // Get author element
public void clickBook(String bookName)                 // Click on book
public void navigateToForm()                          // Navigate to form
public void fillStudentForm(String fn, String ln, 
                           String gender, String phone) // Fill form fields
public void selectGender(String gender)                // Select gender
public void submitForm()                               // Submit form
public void closeFormModal()                           // Close modal
public void navigateToWebTable()                       // Navigate to table
public String[][] extractTableData()                   // Extract table data
public void navigateToDragAndDrop()                    // Navigate to drag-drop
public List<WebElement> getDraggableItems()           // Get draggable items
public void sortListByDragDrop(List<WebElement> items) // Perform drag-drop
```

**Design Principle**: Single Responsibility - All book page interactions

---

### SeleniumUtils.java
**Purpose**: Reusable Selenium utility methods

**Wait Methods**:
```java
public WebElement waitForVisibility(By locator)          // Wait for visibility
public WebElement waitForClickable(By locator)           // Wait for clickable
public List<WebElement> waitForAllVisible(By locator)   // Wait for all visible
public void waitWithDelay(long milliseconds)             // Thread sleep
```

**Scroll Methods**:
```java
public void scrollIntoView(WebElement element)      // Scroll element into view
public void scrollByPixels(int pixels)               // Scroll by pixels
```

**Click Methods**:
```java
public void jsClick(WebElement element)              // Click via JavaScript
public void waitAndClick(By locator)                 // Wait and click
```

**Other Methods**:
```java
public void dragAndDrop(WebElement source, WebElement target) // Drag-drop
public void waitAndSendKeys(By locator, String text)          // Wait and type
```

**Benefits**:
- Centralized wait logic
- Reduced code duplication
- Easy to maintain and update

---

### DriverManager.java
**Purpose**: WebDriver lifecycle management

**Key Features**:
- ThreadLocal storage for thread-safety
- Chrome headless configuration
- Performance optimizations

**Methods**:
```java
public static void setupDriver()   // Initialize WebDriver
public static WebDriver getDriver() // Get current driver
public static void quitDriver()    // Quit and cleanup
```

**Configuration Options**:
```
--headless=new                        // Headless mode
--no-sandbox                          // Disable sandbox
--disable-dev-shm-usage              // Shared memory optimization
--start-maximized                     // Maximize window
--disable-blink-features=AutomationControlled
--excludeSwitches=enable-automation
```

---

### Locators.java
**Purpose**: Centralized element locator repository

**Categories**:
- Book Search Locators
- Navigation Locators
- Menu Locators
- Form Locators
- Table Locators
- Drag & Drop Locators

**Usage**:
```java
By searchBox = Locators.SEARCH_BOX;
utils.waitForVisibility(searchBox);
```

**Benefits**:
- Easy XPath/selector updates
- Single source of truth for locators
- Reduces hardcoding in tests

---

### TestData.java
**Purpose**: Centralized test data and configuration

**Categories**:
- URLs
- Test Data (search terms, expected values)
- Wait Times (SHORT_WAIT, MEDIUM_WAIT, LONG_WAIT)
- Scroll Distances
- Form Data Paths
- Gender Options

**Usage**:
```java
driver.get(TestData.BASE_URL);
booksPage.searchBook(TestData.SEARCH_TERM);
utils.waitWithDelay(TestData.MEDIUM_WAIT);
```

**Benefits**:
- Easy to change test data
- Support for multiple environments
- Centralized configuration

---

### BaseTest.java
**Purpose**: Common setup and teardown for all tests

**Methods**:
```java
@BeforeMethod
public void setUp()    // Initialize driver and utilities

@AfterMethod
public void tearDown() // Close driver and cleanup
```

**Extends**: `ExtentReport` class

**Key Points**:
- Inherited by all test classes
- Sets up WebDriver and WebDriverWait
- Initializes Extent Reports

---

### XLUtility.java
**Purpose**: Excel file operations using Apache POI

**Read Methods**:
```java
public String getCellData(int row, int col)     // Get cell value
public int getRowCount()                        // Get row count
public int getColumnCount(int rowIndex)         // Get column count
```

**Write Methods**:
```java
public void writeDataToNewSheet(String name, String[][] data)
public void setCellData(int sheet, int row, int col, String value)
```

**Lifecycle**:
```java
public void close() throws IOException   // Close workbook
```

**Usage Example**:
```java
XLUtility excel = new XLUtility("src/resources/data.xlsx");
String firstName = excel.getCellData(1, 0);
excel.close();
```

---

### ExtentReport.java
**Purpose**: Extent Reports integration and logging

**Report Features**:
- HTML report generation
- System information logging
- Test logging with different levels

**Logging Methods**:
```java
protected void logPass(String message)    // Log PASS
protected void logFail(String message)    // Log FAIL
protected void logInfo(String message)    // Log INFO
protected void logWarning(String message) // Log WARNING
```

**Setup**:
- Report file: `extentReport.html`
- Report title: "Test Execution Report"
- System info captured: Application, User, OS

---

## 📖 Usage Guide

### 1. Running a Single Test
```java
@Test(priority = 1)
public void bookSearch() {
    test = extent.createTest("Test One", "Book Search");
    try {
        booksPage.navigateToBooks();
        booksPage.searchBook(TestData.SEARCH_TERM);
        // Assertions
        test.pass("Test passed");
    } catch (Exception e) {
        test.fail("Test failed: " + e.getMessage());
    }
}
```

### 2. Reading from Excel
```java
XLUtility excel = new XLUtility(TestData.EXCEL_DATA_PATH);
for (int i = 1; i < excel.getRowCount(); i++) {
    String firstName = excel.getCellData(i, 0);
    String lastName = excel.getCellData(i, 1);
    // Use data
}
excel.close();
```

### 3. Waiting for Elements
```java
// Using SeleniumUtils
WebElement element = utils.waitForVisibility(Locators.SEARCH_BOX);
utils.waitAndClick(Locators.SUBMIT_BUTTON);

// Using explicit waits
WebElement elem = wait.until(
    ExpectedConditions.elementToBeClickable(Locators.SUBMIT_BUTTON)
);
```

### 4. Scrolling
```java
utils.scrollIntoView(element);           // Scroll to element
utils.scrollByPixels(500);                // Scroll 500px down
```

### 5. Page Interactions
```java
booksPage.navigateToBooks();
booksPage.searchBook("JavaScript");
booksPage.fillStudentForm("John", "Doe", "male", "1234567890");
booksPage.submitForm();
```

---

## ⚙️ Configuration

### Modify Wait Times
In `TestData.java`:
```java
public static final long SHORT_WAIT = 2000;   // Change as needed
public static final long MEDIUM_WAIT = 3000;
public static final long LONG_WAIT = 5000;
```

### Modify Browser Options
In `DriverManager.java`:
```java
options.addArguments("--headless=new");        // Remove for non-headless
options.addArguments("--disable-gpu");         // Add GPU disable
```

### Change Base URL
In `TestData.java`:
```java
public static final String BASE_URL = "https://demoqa.com/books";
```

### Update Locators
In `Locators.java`:
```java
public static final By SEARCH_BOX = By.xpath("//input[@id='searchBox']");
```

---

## 🆘 Troubleshooting

### Issue: Element Not Found
**Solution**:
1. Check locator in `Locators.java`
2. Verify element exists on page
3. Increase wait time in `TestData.LONG_WAIT`
4. Use `utils.waitForVisibility()` instead of direct `findElement()`

### Issue: Tests Hanging
**Solution**:
1. Check wait times in `TestData.java`
2. Verify network connectivity
3. Check browser console for JavaScript errors
4. Use headless mode for faster execution

### Issue: Excel File Not Found
**Solution**:
1. Verify path in `TestData.EXCEL_DATA_PATH`
2. Check file exists in project
3. Ensure correct relative path

### Issue: Report Not Generated
**Solution**:
1. Verify `ExtentReport.flush()` is called
2. Check file write permissions
3. Verify `extent.html` output path

---

## 🚀 Best Practices

1. **Always use Locators class** for element identification
2. **Use SeleniumUtils methods** instead of direct Selenium calls
3. **Keep TestData updated** when test data changes
4. **Use BooksPage methods** instead of direct WebDriver calls
5. **Handle exceptions properly** with meaningful error messages
6. **Use meaningful test names** for better reporting
7. **Keep tests independent** and not dependent on execution order
8. **Use try-catch blocks** to capture test failures properly
9. **Log important steps** using `test.info()` or `test.pass()`
10. **Close Excel files** explicitly to prevent memory leaks

---

## 📝 Notes

- Framework uses Chrome WebDriver in headless mode
- Tests are designed for parallel execution (ThreadLocal driver)
- Extent Reports are generated in project root
- All screenshots and logs are stored in `test-output/` directory
- Excel data file location: `src/resources/data.xlsx`

---

## 🔄 Version History

- **v2.0** - Refactored with POM, centralized utilities, and documentation
- **v1.0** - Initial framework implementation

---

**Last Updated**: May 5, 2026
**Framework Version**: 2.0
**Java Version**: 11+
