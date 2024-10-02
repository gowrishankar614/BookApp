package assessment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        driver.set(webDriver); // Store the driver in ThreadLocal
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        driver.get().quit();
        driver.remove(); // Clean up ThreadLocal
    }
}
