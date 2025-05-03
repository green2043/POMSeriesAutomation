package com.qa.appName.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.qa.appName.factory.DriverFactory;

//DON'T TOUCH THIS, DON'T CHANGE ANYHTING HERE

// DECLARING A CLASS NAMED 'ExtentReportListener' WHICH IMPLEMENTS 'ITestListener' INTERFACE FROM TESTNG.
// THIS CLASS IS USED TO CUSTOMIZE TEST EXECUTION EVENTS (START, FINISH, PASS, FAIL ETC.) FOR EXTENT REPORTING.
public class ExtentReportListener implements ITestListener  {

	// 'private static final' → ACCESS MODIFIER, CLASS-LEVEL, FINAL (CONSTANT)
	// 'String' → CLASS FROM JAVA LANG PACKAGE
	// 'OUTPUT_FOLDER' → CONSTANT VARIABLE TO STORE THE DIRECTORY WHERE REPORT FILES WILL BE SAVED.
	private static final String OUTPUT_FOLDER = "./reports/";

	// FILE_NAME → CONSTANT VARIABLE TO STORE THE NAME OF THE REPORT FILE.
	private static final String FILE_NAME = "TestExecutionReport.html";

	// 'ExtentReports' → CLASS FROM EXTENT REPORT LIBRARY TO CREATE REPORT OBJECT
	// 'extent' → STATIC VARIABLE INITIALIZED VIA init() METHOD
	private static ExtentReports extent = init();

	// 'ThreadLocal<ExtentTest>' → JAVA CLASS THAT PROVIDES THREAD-SPECIFIC INSTANCES OF 'ExtentTest'
	// 'test' → THREADLOCAL VARIABLE TO STORE CURRENT THREAD’S TEST INSTANCE.
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	// 'extentReports' → CLASS-LEVEL OBJECT TO BE RETURNED FROM init()
	private static ExtentReports extentReports;

	// PRIVATE STATIC METHOD NAMED 'init' RETURNING ExtentReports INSTANCE
	private static ExtentReports init() {

		// 'Paths.get()' → STATIC METHOD TO GET PATH OBJECT
		Path path = Paths.get(OUTPUT_FOLDER);

		// 'if (!Files.exists(path))' → CHECK IF REPORT FOLDER DOES NOT EXIST
		if (!Files.exists(path)) {
			try {
				// IF FOLDER DOESN’T EXIST, CREATE IT USING 'Files.createDirectories()'
				Files.createDirectories(path);
			} catch (IOException e) {
				// HANDLE IO EXCEPTION IF DIRECTORY CREATION FAILS
				e.printStackTrace();
			}
		}

		// INITIALIZING EXTENT REPORT OBJECT
		extentReports = new ExtentReports();

		// CREATING SPARK REPORTER OBJECT THAT WILL GENERATE HTML REPORT FILE
		ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);

		// SETTING THE NAME OF THE REPORT FILE
		reporter.config().setReportName("Open Cart Automation Test Results");

		// ATTACHING THE REPORTER TO EXTENTREPORTS
		extentReports.attachReporter(reporter);

		// SETTING SYSTEM INFO IN REPORT FOR EXTRA DETAILS
		extentReports.setSystemInfo("System", "WINDOWS");
		extentReports.setSystemInfo("Author", "Organisation or Project Name");
		extentReports.setSystemInfo("Build#", "1.1");
		extentReports.setSystemInfo("Team", "ProjrctName QA Team");
		extentReports.setSystemInfo("Customer Name", "NAL");

		// RETURN THE INITIALIZED ExtentReports OBJECT
		return extentReports;
	}

	// OVERRIDDEN METHOD FROM ITestListener - CALLED WHEN TEST SUITE STARTS
	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Test Suite started!");
	}

	// OVERRIDDEN METHOD FROM ITestListener - CALLED WHEN TEST SUITE ENDS
	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println(("Test Suite is ending!"));
		extent.flush(); // WRITES ALL INFORMATION TO REPORT FILE
		test.remove();  // REMOVES THREADLOCAL VARIABLE TO AVOID MEMORY LEAK
	}

	// OVERRIDDEN METHOD CALLED WHEN INDIVIDUAL TEST STARTS
	@Override
	public synchronized void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName(); // GET TEST METHOD NAME
		String qualifiedName = result.getMethod().getQualifiedName(); // FULLY QUALIFIED NAME (package.class.method)

		int last = qualifiedName.lastIndexOf(".");
		int mid = qualifiedName.substring(0, last).lastIndexOf(".");
		String className = qualifiedName.substring(mid + 1, last); // EXTRACT CLASS NAME

		System.out.println(methodName + " started!");

		// CREATING TEST ENTRY IN EXTENT REPORT
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());

		// SETTING CATEGORY BASED ON SUITE NAME AND CLASS NAME
		extentTest.assignCategory(result.getTestContext().getSuite().getName());
		extentTest.assignCategory(className);

		// SETTING START TIME OF THE TEST
		test.set(extentTest); // SET THREADLOCAL INSTANCE
		test.get().getModel().setStartTime(getTime(result.getStartMillis()));
	}

	// CALLED WHEN TEST PASSES
	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test passed"); // MARK TEST AS PASS
		test.get().getModel().setEndTime(getTime(result.getEndMillis())); // SET END TIME
	}

	// CALLED WHEN TEST FAILS
	public synchronized void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		String methodName = result.getMethod().getMethodName();

		// MARK TEST AS FAIL AND ATTACH SCREENSHOT
		test.get().fail(result.getThrowable(),
				MediaEntityBuilder.createScreenCaptureFromPath(DriverFactory.getScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	// CALLED WHEN TEST IS SKIPPED
	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		String methodName = result.getMethod().getMethodName();
		test.get().skip(result.getThrowable(),
				MediaEntityBuilder.createScreenCaptureFromPath(DriverFactory.getScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	// CALLED WHEN TEST FAILS BUT IS WITHIN SUCCESS THRESHOLD
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	// UTILITY METHOD TO CONVERT TIME IN MILLISECONDS TO DATE
	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
}


/* 🔍 KEY CONCEPTS TO TELL IN INTERVIEW:

- `ITestListener` is a **TestNG interface** that lets you hook into test lifecycle events (start, pass, fail, etc.).
- `ExtentReports` and `ExtentTest` are **classes from AventStack ExtentReports library** used to generate rich HTML reports.
- `ThreadLocal<ExtentTest>` is used to make **each test thread** store its own test log, to avoid report corruption in parallel execution.
- `@Override` marks **methods from interface ITestListener** being overridden.
- `synchronized` ensures **thread safety** when multiple tests run in parallel.


*/