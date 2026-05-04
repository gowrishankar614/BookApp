package assessment;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Base class for managing Extent Reports
 * Provides common report initialization and logging methods
 */
public class ExtentReport {

	protected ExtentReports extent;
	protected ExtentTest test;

	@BeforeClass
	public void setup() {
		// Initialize ExtentReports with HTML reporter
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extentReport.html");
		sparkReporter.config().setDocumentTitle("Test Execution Report");
		sparkReporter.config().setReportName("DemoQA Test Report");
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "DemoQA Books");
		extent.setSystemInfo("User", System.getProperty("user.name"));
		extent.setSystemInfo("OS", System.getProperty("os.name"));
	}

	@AfterClass
	public void teardown() {
		// Flush the report to generate HTML file
		if (extent != null) {
			extent.flush();
		}
	}

	/**
	 * Log a pass message in the test report
	 */
	protected void logPass(String message) {
		if (test != null) {
			test.log(Status.PASS, message);
		}
	}

	/**
	 * Log a fail message in the test report
	 */
	protected void logFail(String message) {
		if (test != null) {
			test.log(Status.FAIL, message);
		}
	}

	/**
	 * Log an info message in the test report
	 */
	protected void logInfo(String message) {
		if (test != null) {
			test.log(Status.INFO, message);
		}
	}

	/**
	 * Log a warning message in the test report
	 */
	protected void logWarning(String message) {
		if (test != null) {
			test.log(Status.WARNING, message);
		}
	}
}
