package assessment;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Common utility methods for selenium operations like scrolling, waiting, 
 * JavaScript execution, and drag-and-drop.
 */
public class SeleniumUtils {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    public SeleniumUtils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    /**
     * Scroll element into view using JavaScript
     */
    public void scrollIntoView(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scroll page by specified pixels
     */
    public void scrollByPixels(int pixels) {
        jsExecutor.executeScript("window.scrollBy(0," + pixels + ")", "");
    }

    /**
     * Click element using JavaScript (useful for elements covered by overlays)
     */
    public void jsClick(WebElement element) {
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    /**
     * Wait for element to be visible and return it
     */
    public WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable and return it
     */
    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for all elements to be visible
     */
    public java.util.List<WebElement> waitForAllVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Perform drag and drop operation
     */
    public void dragAndDrop(WebElement source, WebElement target) {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
    }

    /**
     * Wait and click an element
     */
    public void waitAndClick(By locator) {
        WebElement element = waitForClickable(locator);
        element.click();
    }

    /**
     * Wait and fill text in input field
     */
    public void waitAndSendKeys(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Take screenshot using JavaScript execution
     */
    public void waitWithDelay(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }
}
