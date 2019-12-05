package com.hilti.ta.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.hilti.ta.utils.WebDriverFactory;

/**
 * TODO
 *
 * @author jakub.ptak@externals.hilti.com
 */
public class HeaderComponent {


	private final By productNavigationMenuItem = By.xpath("//div[@class='m-nav-primary-wrapper']//a[contains(@class, 'js-navigation-link-product')]");
	private final By productNavigation = By.xpath("//li[@class='m-nav-secondary-stage-item is-active']");

	private final String categoryNavigationItemPattern = "//li[@class='m-nav-secondary-stage-item is-active']//a[@class='js-navigation-link']/span[contains(text(), '%s')]";

	public void expandProductNavigation() {
		WebDriverFactory.getDriver().findElement(productNavigationMenuItem).click();
	}

	public void verifyCategoryIsPresentInProductNavigation(final String categoryName) {
		final By categoryNavigationItem = By.xpath(String.format(categoryNavigationItemPattern, categoryName));

		WebDriverFactory.getDriver().findElement(categoryNavigationItem);
	}

	public void waitForProductNavigationToAppear() {
		WebDriverFactory.getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNavigation));
	}
}
