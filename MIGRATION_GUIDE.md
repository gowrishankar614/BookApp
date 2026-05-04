# Migration Guide - Refactored SeleniumFramework

## Overview

This document helps team members transition to the refactored SeleniumFramework with improved architecture and best practices.

---

## What Changed?

### ❌ OLD CODE (Before Refactoring)
```java
@Test(priority = 1)
public void bookSearch() {
    try {
        driver.get("https://demoqa.com/books");
        Thread.sleep(3000);
        
        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@id='searchBox']")
            )
        );
        searchBox.clear();
        searchBox.sendKeys("JavaScript");
        Thread.sleep(4000);
        
        WebElement bookNameElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("see-book-Programming JavaScript Applications")
            )
        );
        String bookName = bookNameElement.getText();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", bookNameElement);
        Thread.sleep(2000);
        
        // More code...
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

### ✅ NEW CODE (After Refactoring)
```java
@Test(priority = 1)
public void bookSearch() {
    test = extent.createTest("Test One", "Book Search using keyword");
    
    try {
        booksPage.navigateToBooks();
        utils.waitWithDelay(TestData.MEDIUM_WAIT);
        
        booksPage.searchBook(TestData.SEARCH_TERM);
        
        WebElement bookNameElement = booksPage.getBookNameElement(TestData.EXPECTED_BOOK_NAME);
        String bookName = bookNameElement.getText();
        
        utils.scrollIntoView(bookNameElement);
        utils.waitWithDelay(TestData.SHORT_WAIT);
        
        // More code...
    } catch (Exception e) {
        test.fail("Test failed: " + e.getMessage());
        Assert.fail("Test failed: " + e.getMessage());
    }
}
```

---

## Key Changes Summary

| Aspect | Before | After |
|--------|--------|-------|
| **Locators** | Hardcoded in test | `Locators.java` class |
| **Test Data** | Hardcoded strings | `TestData.java` constants |
| **Page Interactions** | Direct WebDriver calls | `BooksPage.java` methods |
| **Utilities** | Duplicated in tests | `SeleniumUtils.java` class |
| **Wait Logic** | Repeated code | Centralized utilities |
| **Test Size** | 400+ lines | 100+ lines |
| **Report Logging** | Missing | Comprehensive logging |
| **Code Reusability** | Low | High |

---

## Step-by-Step Migration

### Step 1: Update Imports
Replace many individual imports with:
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
```

### Step 2: Use Constants Instead of Hardcoded Values
```java
// OLD
String url = "https://demoqa.com/books";
String searchTerm = "JavaScript";

// NEW
String url = TestData.BASE_URL;
String searchTerm = TestData.SEARCH_TERM;
```

### Step 3: Use Locators Class
```java
// OLD
By searchBox = By.xpath("//input[@id='searchBox']");

// NEW
By searchBox = Locators.SEARCH_BOX;
```

### Step 4: Replace Direct Selenium Calls with BooksPage
```java
// OLD
driver.findElement(By.xpath("//input[@id='searchBox']")).sendKeys("JavaScript");

// NEW
booksPage.searchBook(TestData.SEARCH_TERM);
```

### Step 5: Use SeleniumUtils for Common Operations
```java
// OLD
WebElement element = wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.id("submit"))
);
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].scrollIntoView(true);", element);
Thread.sleep(2000);
element.click();

// NEW
utils.scrollIntoView(element);
utils.waitWithDelay(TestData.SHORT_WAIT);
utils.waitAndClick(Locators.SUBMIT_BUTTON);
```

### Step 6: Add Proper Test Reporting
```java
// OLD
try {
    // test code
} catch (Exception e) {
    e.printStackTrace();
}

// NEW
try {
    // test code
    test.pass("Test passed");
} catch (Exception e) {
    test.fail("Test failed: " + e.getMessage());
    Assert.fail("Test failed: " + e.getMessage());
}
```

---

## Common Migration Scenarios

### Scenario 1: Navigating to Different Pages

**Before:**
```java
driver.findElement(By.xpath("(//div[contains(@class,'header-wrapper')])[2]")).click();
Thread.sleep(5000);
driver.findElement(By.xpath("(//li[@id='item-0'])[2]")).click();
```

**After:**
```java
booksPage.navigateToForm();
```

---

### Scenario 2: Filling Forms

**Before:**
```java
driver.findElement(By.cssSelector("#firstName")).sendKeys("John");
driver.findElement(By.cssSelector("#lastName")).sendKeys("Doe");
driver.findElement(By.cssSelector("#userNumber")).sendKeys("1234567890");

if ("male".equalsIgnoreCase(gender)) {
    driver.findElement(By.cssSelector("label[for='gender-radio-1']")).click();
}
```

**After:**
```java
booksPage.fillStudentForm("John", "Doe", "male", "1234567890");
```

---

### Scenario 3: Working with Excel

**Before:**
```java
XLUtility excelUtils = new XLUtility("src/resources/data.xlsx");

for (int i = 1; i < excelUtils.getRowCount(); i++) {
    String FName = excelUtils.getCellData(i, 0);
    String LName = excelUtils.getCellData(i, 1);
    String Gender = excelUtils.getCellData(i, 2);
    String phone = excelUtils.getCellData(i, 3);
    
    // Use data
    excelUtils.close();
}
```

**After:**
```java
XLUtility excelUtils = new XLUtility(TestData.EXCEL_DATA_PATH);

for (int i = 1; i < excelUtils.getRowCount(); i++) {
    String firstName = excelUtils.getCellData(i, 0);
    String lastName = excelUtils.getCellData(i, 1);
    String gender = excelUtils.getCellData(i, 2);
    String phone = excelUtils.getCellData(i, 3);
    
    booksPage.fillStudentForm(firstName, lastName, gender, phone);
}

excelUtils.close();
```

---

### Scenario 4: Scrolling and Waiting

**Before:**
```java
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].scrollIntoView(true);", element);
Thread.sleep(2000);
js.executeScript("window.scrollBy(0,500)", "");
Thread.sleep(2000);
```

**After:**
```java
utils.scrollIntoView(element);
utils.waitWithDelay(TestData.SHORT_WAIT);
utils.scrollByPixels(TestData.SCROLL_500);
utils.waitWithDelay(TestData.SHORT_WAIT);
```

---

## Breaking Changes

### ⚠️ Important: What Works Differently Now

1. **Test Methods Must Create Extent Test**
   ```java
   test = extent.createTest("Test Name", "Description");
   ```

2. **Always Use BooksPage**
   - Don't use `driver.findElement()` directly
   - Use `booksPage.` methods instead

3. **Always Wrap in Try-Catch**
   - Catch and log failures with `test.fail()`
   - Use `Assert.fail()` to mark test as failed

4. **Use Locators Class**
   - Don't hardcode XPath/CSS selectors
   - Use predefined `Locators.` constants

---

## Backward Compatibility

### ✅ What Still Works

- `WaitUtils` class (marked as deprecated)
- `DriverManager` (improved but compatible)
- `XLUtility.save()` method (deprecated, use close() instead)
- All TestNG annotations

### ❌ What Doesn't Work

- Hardcoded locators (use `Locators.java` instead)
- Hardcoded test data (use `TestData.java` instead)
- Direct WebDriver calls for page interactions (use `BooksPage.java` instead)

---

## Checklist for Migration

- [ ] Updated all hardcoded URLs to use `TestData.BASE_URL`
- [ ] Updated all locators to use `Locators` class
- [ ] Replaced direct WebDriver calls with `BooksPage` methods
- [ ] Added Extent Report logging (`test.pass()`, `test.fail()`)
- [ ] Used `SeleniumUtils` for wait/scroll operations
- [ ] Used `TestData` constants for test values
- [ ] Added proper exception handling with try-catch
- [ ] Removed unused imports
- [ ] Verified test runs successfully
- [ ] Generated and reviewed Extent Report

---

## Common Issues & Solutions

### Issue: "Cannot find symbol: Locators"
**Solution**: Import `Locators` class or use full path `assessment.Locators.SEARCH_BOX`

### Issue: "Cannot find symbol: TestData"
**Solution**: Import `TestData` class or use `assessment.TestData.BASE_URL`

### Issue: "test is null"
**Solution**: Add `test = extent.createTest("name", "desc");` at start of test method

### Issue: "booksPage is null"
**Solution**: Initialize in setUp(): `booksPage = new BooksPage(driver, utils);`

### Issue: "NoSuchElementException"
**Solution**: Use `utils.waitForVisibility()` instead of direct `findElement()`

---

## Performance Tips

1. **Use headless mode** for faster execution
2. **Reduce wait times** when testing on fast machines
3. **Use `scrollByPixels()`** instead of gradual scrolling
4. **Close Excel files** promptly to free memory
5. **Reuse BooksPage instance** instead of creating new ones

---

## Testing the Migration

### Verify New Code Works
```bash
# Run single test
mvn test -Dtest=bookLog#bookSearch

# Run all tests
mvn clean test

# Run with TestNG XML
mvn test -Dsuite=TestSuites/testng.xml
```

### Check Reports
- Open `extentReport.html` to verify test results
- Verify logs show test steps
- Check test status (PASS/FAIL)

---

## FAQ

**Q: Do I need to update all tests at once?**
A: No, you can migrate tests gradually. Old and new code can coexist.

**Q: Can I use old WaitUtils class?**
A: Yes, but it's deprecated. Use `SeleniumUtils` instead.

**Q: How do I add a new page object?**
A: Create a new class extending `BooksPage` pattern and implement page methods.

**Q: What if a locator changes?**
A: Update it once in `Locators.java` and all tests automatically use the new locator.

**Q: How do I debug a failing test?**
A: Check `extentReport.html` for detailed logs and review test.fail() messages.

---

## Resources

- 📖 Full Documentation: See `DOCUMENTATION.md`
- 📋 Quick Reference: See `QUICK_REFERENCE.md`
- 🔧 Refactoring Details: See `REFACTORING_SUMMARY.md`

---

## Support

For questions or issues:
1. Check `DOCUMENTATION.md` for detailed information
2. Review `QUICK_REFERENCE.md` for examples
3. Check existing test methods for patterns
4. Review Extent Report for error details

---

**Version**: 2.0
**Last Updated**: May 5, 2026
