package com.hilti.ta.utils;

import java.awt.Toolkit;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * TODO
 *
 * @author jakub.ptak@externals.hilti.com
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

	public static void initialize() {
		System.setProperty("webdriver.chrome.driver", getWebDriverPath());

		final ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		final WebDriver webDriver = new ChromeDriver(options);
		webDrivers.set(webDriver);

		setupBrowser(webDriver);
		setupWaits(webDriver);
	}

	public static WebDriver getDriver() {
		return webDrivers.get();
	}

	public static void quitCurrentDriver() {
		final WebDriver webDriver = webDrivers.get();
		if (webDriver != null) {
			webDriver.quit();
			webDrivers.remove();
		}
	}

	public static WebDriverWait getWebDriverWait() {
		return getWebDriverWait(0);
	}

	public static WebDriverWait getWebDriverWait(final int seconds) {
		final int wait = seconds > 0 ? seconds : DEFAULT_WEBDRIVER_WAIT_TIME;
		return new WebDriverWait(webDrivers.get(), wait, 50);
	}

	private static void setupBrowser(final WebDriver webDriver) {

		final Dimension screenResolution = getScreenResolution();
		final Dimension targetResolution = new Dimension(1920, 1080);
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

	private static String getWebDriverPath() {
		final OSEnum os = OSEnum.getOS();

		final String driverLocation = Paths
				.get(System.getProperty("user.dir"), "target", "test-classes", "drivers", "ChromeDriver").toString();

		switch (os) {
			case MACOS:
				return driverLocation + "Mac";
			case WINDOWS:
				return driverLocation + "Win.exe";
			default:
				throw new RuntimeException("Unsupported operating system: " + os);
		}

	}

	private static Dimension getScreenResolution() {
		final java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) screenSize.getWidth();
		final int height = (int) screenSize.getHeight();
		return new Dimension(width, height);
	}
}
