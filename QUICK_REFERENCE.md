# Quick Reference Guide

## 🎯 Common Operations

### Search for a Book
```java
booksPage.navigateToBooks();
booksPage.searchBook("JavaScript");
WebElement book = booksPage.getBookNameElement("Programming JavaScript Applications");
booksPage.clickBook("Programming JavaScript Applications");
```

### Fill and Submit Form
```java
booksPage.navigateToForm();
booksPage.fillStudentForm("John", "Doe", "male", "9876543210");
booksPage.submitForm();
booksPage.closeFormModal();
```

### Read from Excel
```java
XLUtility excel = new XLUtility("src/resources/data.xlsx");
for (int i = 1; i < excel.getRowCount(); i++) {
    String name = excel.getCellData(i, 0);
}
excel.close();
```

### Write to Excel
```java
XLUtility excel = new XLUtility("src/resources/data.xlsx");
String[][] data = {
    {"Name", "Email", "Phone"},
    {"John", "john@test.com", "1234567890"}
};
excel.writeDataToNewSheet("Sheet1", data);
excel.close();
```

### Wait Operations
```java
// Wait for visibility
WebElement elem = utils.waitForVisibility(Locators.SEARCH_BOX);

// Wait and click
utils.waitAndClick(Locators.SUBMIT_BUTTON);

// Wait and send keys
utils.waitAndSendKeys(Locators.FIRST_NAME, "John");

// Custom delay
utils.waitWithDelay(TestData.MEDIUM_WAIT);
```

### Scroll Operations
```java
// Scroll to element
utils.scrollIntoView(element);

// Scroll by pixels
utils.scrollByPixels(500);
utils.scrollByPixels(TestData.SCROLL_300);
```

### Drag and Drop
```java
List<WebElement> items = booksPage.getDraggableItems();
utils.dragAndDrop(items.get(0), items.get(1));
```

---

## 📊 TestData Constants

```java
// URLs
TestData.BASE_URL                    // https://demoqa.com/books

// Test Data
TestData.SEARCH_TERM                 // "JavaScript"
TestData.EXPECTED_BOOK_NAME          // "Programming JavaScript Applications"
TestData.EXPECTED_AUTHOR             // "Eric Elliott"

// Wait Times (in milliseconds)
TestData.SHORT_WAIT                  // 2000ms
TestData.MEDIUM_WAIT                 // 3000ms
TestData.LONG_WAIT                   // 5000ms

// Scroll Distances (in pixels)
TestData.SCROLL_250                  // 250px
TestData.SCROLL_300                  // 300px
TestData.SCROLL_500                  // 500px

// Paths
TestData.EXCEL_DATA_PATH             // "src/resources/data.xlsx"
TestData.WEBTABLE_SHEET_NAME         // "WebTable"

// Options
TestData.GENDER_MALE                 // "male"
TestData.GENDER_FEMALE               // "female"
TestData.GENDER_OTHER                // "other"
```

---

## 🎨 Locators

```java
// Search
Locators.SEARCH_BOX                  // Search input field

// Form
Locators.FIRST_NAME                  // First name input
Locators.LAST_NAME                   // Last name input
Locators.USER_NUMBER                 // Phone number input
Locators.GENDER_RADIO_MALE           // Male gender radio
Locators.GENDER_RADIO_FEMALE         // Female gender radio
Locators.GENDER_RADIO_OTHER          // Other gender radio
Locators.SUBMIT_BUTTON               // Form submit button
Locators.CLOSE_MODAL                 // Modal close button

// Navigation
Locators.HEADER_WRAPPER_FIRST        // First header
Locators.HEADER_WRAPPER_SECOND       // Second header
Locators.HEADER_WRAPPER_FIFTH        // Fifth header
Locators.MENU_ITEM_FIRST_THREE       // Menu item 3
Locators.MENU_ITEM_SECOND_ZERO       // Menu item 0 (second header)
Locators.MENU_ITEM_FIFTH_ZERO        // Menu item 0 (fifth header)

// Table
Locators.WEB_TABLE                   // Web table container
Locators.TABLE_ROW                   // Table row
Locators.TABLE_CELL                  // Table cell

// Drag & Drop
Locators.VERTICAL_LIST               // Draggable items list
```

---

## 🧪 Test Structure

### Basic Test Template
```java
@Test(priority = 1)
public void testName() {
    test = extent.createTest("Test Name", "Description");
    
    try {
        // Test steps
        booksPage.navigateToBooks();
        utils.waitWithDelay(TestData.MEDIUM_WAIT);
        
        // Assertions
        Assert.assertEquals(expected, actual);
        
        // Log result
        test.pass("Test passed");
        
    } catch (Exception e) {
        test.fail("Test failed: " + e.getMessage());
        Assert.fail("Test failed: " + e.getMessage());
    }
}
```

---

## 📋 Assertion Examples

```java
// String comparison
Assert.assertEquals(TestData.EXPECTED_BOOK_NAME, bookName);

// Element visibility
Assert.assertTrue(element.isDisplayed());

// List size
Assert.assertEquals(5, items.size());

// Null check
Assert.assertNotNull(element);

// Custom message
Assert.assertEquals("Books not found", expected, actual);
```

---

## 🔧 Method Chaining Example

```java
utils.waitAndClick(Locators.HEADER_WRAPPER_SECOND);
utils.waitAndSendKeys(Locators.SEARCH_BOX, TestData.SEARCH_TERM);
utils.scrollByPixels(TestData.SCROLL_500);
booksPage.submitForm();
```

---

## 🚨 Exception Handling

```java
try {
    // Selenium operation
    WebElement element = utils.waitForVisibility(Locators.SEARCH_BOX);
    
} catch (TimeoutException e) {
    test.fail("Element not found: " + e.getMessage());
    Assert.fail("Element not found");
    
} catch (StaleElementReferenceException e) {
    test.fail("Element is stale: " + e.getMessage());
    // Retry operation
    
} catch (Exception e) {
    test.fail("Unexpected error: " + e.getMessage());
    e.printStackTrace();
    Assert.fail(e.getMessage());
}
```

---

## 📂 File Locations

```
Project Root: /Users/gowrishankar/git/BookApp1/

Key Files:
├── src/test/java/assessment/
│   ├── bookLog.java               (Main test class)
│   ├── BooksPage.java             (Page object)
│   ├── SeleniumUtils.java         (Utilities)
│   ├── DriverManager.java         (Driver management)
│   └── ... (other classes)
│
├── src/resources/
│   └── data.xlsx                  (Test data)
│
├── test-output/
│   └── (TestNG reports)
│
├── extentReport.html              (Extent report)
├── DOCUMENTATION.md               (Full documentation)
├── REFACTORING_SUMMARY.md         (Refactoring details)
└── pom.xml                        (Maven configuration)
```

---

## 🎬 Creating a New Test

1. Add test method in `bookLog.java` with `@Test` annotation
2. Create test instance: `test = extent.createTest("Name", "Description")`
3. Use `booksPage` for page interactions
4. Use `utils` for common operations
5. Add assertions with `Assert`
6. Wrap in try-catch for error handling
7. Log results with `test.pass()` or `test.fail()`

---

## 📊 Running Tests

### Maven Command
```bash
mvn clean test
```

### TestNG XML
```bash
mvn test -Dsuite=TestSuites/testng.xml
```

### Single Test Class
```bash
mvn test -Dtest=bookLog
```

### Single Test Method
```bash
mvn test -Dtest=bookLog#bookSearch
```

---

## 🐛 Debugging Tips

1. **Enable detailed logs**: Add `System.out.println()` statements
2. **Take screenshots**: Use Selenium's screenshot capability
3. **Slow down execution**: Increase `TestData` wait times
4. **Inspect elements**: Use browser DevTools to verify locators
5. **Check Extent Report**: Review `extentReport.html` for details
6. **Use breakpoints**: Debug in IDE with breakpoints
7. **Check Excel data**: Verify `data.xlsx` content

---

## ✅ Checklist Before Running Tests

- [ ] Excel data file exists at `src/resources/data.xlsx`
- [ ] All locators are correctly updated in `Locators.java`
- [ ] TestData values match current application
- [ ] Browser is in supported version
- [ ] No other instances of browser running
- [ ] Network connectivity is stable
- [ ] Write permissions for output files exist
- [ ] Java version is 11 or higher

---

**Last Updated**: May 5, 2026
