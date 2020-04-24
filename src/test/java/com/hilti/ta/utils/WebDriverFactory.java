package com.hilti.ta.utils;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Closeables;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Provides methods for managing the {@link WebDriver} instances.
 */
public class WebDriverFactory {

	private static final Integer IMPLICITLY_WAIT_TIME = 0;
	private static final Integer SCRIPT_LOAD_TIME = 15;
	private static final Integer PAGE_LOAD_TIME = 60;
	private static final Integer DEFAULT_WEBDRIVER_WAIT_TIME = 10;

	private static ThreadLocal<WebDriver> webDrivers = new ThreadLocal<>();

	/**
	 * Prevent from initialization of the WebDriverFactory
	 */
	private WebDriverFactory() {
		// EMPTY
	}

	/**
	 * Initializes {@link ChromeDriver} for current thread.
	 */
	public static void initialize() {
		WebDriverManager.chromedriver().setup();

		final ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		final WebDriver webDriver = new ChromeDriver(options);
		webDrivers.set(webDriver);

		setupBrowser(webDriver);
		setupWaits(webDriver);
	}

	/**
	 * Retrieves an instance of {@link WebDriver} for current thread. May return {@code null} in case the
	 * {@link WebDriverFactory#initialize()} method is not called before for the current thread.
	 * 
	 * @return WebDriver instance {@link WebDriver}
	 */
	public static WebDriver getDriver() {
		
		return webDrivers.get();
	}

	/**
	 * Quits driver for current thread, if present.
	 */
	public static void quitCurrentDriver() {
		final WebDriver webDriver = webDrivers.get();
		if (webDriver != null) {
			webDriver.quit();
			webDrivers.remove();
		}
	}

	/**
	 * Gets default {@link WebDriverWait} instance with default wait of 10 seconds and 50 milliseconds retry interval.
	 * 
	 * @return {@link WebDriverWait}
	 */
	public static WebDriverWait getWebDriverWait() {
		return getWebDriverWait(0);
	}

	/**
	 * Gets customized {@link WebDriverWait} instance. The default retry interval is 50 milliseconds
	 * 
	 * @param seconds
	 *            wait time in seconds
	 * @return {@link WebDriverWait}
	 */
	public static WebDriverWait getWebDriverWait(final int seconds) {
		final int wait = seconds > 0 ? seconds : DEFAULT_WEBDRIVER_WAIT_TIME;
		return new WebDriverWait(webDrivers.get(), wait, 50);
	}

	public static void scrollWindow(int pixel) {
        WebDriver driver = getDriver();
		JavascriptExecutor je = (JavascriptExecutor)driver;
		je.executeScript("scroll(0,"+pixel+")");
	}
	
	private static void setupBrowser(final WebDriver webDriver) {

		final Dimension screenResolution = getScreenResolution();
		final Dimension targetResolution = getProvidedDimension();//new Dimension(1920, 1080);
		final OSEnum os = OSEnum.getOS();

		switch (os) {
			case MACOS:
				if (screenResolution.getWidth() > targetResolution.getWidth()) {
					webDriver.manage().window().setSize(targetResolution);
				} else {
					webDriver.manage().window().setSize(screenResolution);
				}
				break;
			case WINDOWS:
				if (screenResolution.getWidth() > targetResolution.getWidth()) {
					webDriver.manage().window().setSize(targetResolution);
				} else {
					webDriver.manage().window().maximize();
				}
				break;
			default:
				throw new RuntimeException("Unsupported operating system: " + os);
		}

		webDriver.manage().deleteAllCookies();
	}

	private static void setupWaits(final WebDriver webDriver) {
		webDriver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIME, TimeUnit.SECONDS);
		webDriver.manage().timeouts().setScriptTimeout(SCRIPT_LOAD_TIME, TimeUnit.SECONDS);
		webDriver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_TIME, TimeUnit.SECONDS);
	}

	private static Dimension getScreenResolution() {
		final java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) screenSize.getWidth();
		final int height = (int) screenSize.getHeight();
		return new Dimension(width, height);
	}
	
	
	private static Dimension getProvidedDimension() {
		

    	System.out.println("Detecting given platform resolution....");
    	/**
    	 * Trying to read Resolution size from properties files
    	 */
    	//ClassPathResource resource = new ClassPathResource( "app.properties" );
    	Properties p = new Properties();
    	InputStream inputStream = null;
    	try {
    		/**
    		 * This is path where app.properties is generated after filtering : target/classes/app.properties
    		 */
    	    inputStream = new FileInputStream("target/classes/app.properties");
    	    p.load( inputStream );
    	} catch ( IOException e ) {
    	    e.printStackTrace();
    	} finally {
    	    Closeables.closeQuietly( inputStream );
    	}
    	
    	int width = Integer.parseInt(p.getProperty("resolution.width")) ;
    	System.out.println("Platform Resolution width entered is : " + width );
    	
    	
    	int height = Integer.parseInt(p.getProperty("resolution.height")) ;
    	System.out.println("Platform Resolution height entered is : " + height );
    	
    	return new Dimension(width, height);
		
	}
	
	
}
