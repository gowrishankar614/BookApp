package assessment;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class ExtentReport {

	protected ExtentReports extent;
	protected ExtentTest test;

	@BeforeClass
	public void setup() {
		// Initialize ExtentReports
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
	}

	@AfterClass
	public void teardown() {
		// Flush the report
		extent.flush();
	}

	protected void logPass(String message) {
		test.log(Status.PASS, message);
	}

	protected void logFail(String message) {
		test.log(Status.FAIL, message);
	}

	protected void logInfo(String message) {
		test.log(Status.INFO, message);
	}

}
