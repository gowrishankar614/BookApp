package assessment;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class for common wait operations
 * Note: Prefer using SeleniumUtils class instead for more comprehensive functionality
 */
@Deprecated(forRemoval = true, since = "2.0")
public class WaitUtils {

    /**
     * @deprecated Use SeleniumUtils.waitForVisibility() instead
     */
    @Deprecated(forRemoval = true, since = "2.0")
    public static WebElement waitForElementVisible(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * @deprecated Use SeleniumUtils.waitForClickable() instead
     */
    @Deprecated(forRemoval = true, since = "2.0")
    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}