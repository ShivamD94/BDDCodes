package com.hilti.ta.pages;

import com.hilti.ta.utils.WebDriverFactory;

/**
 * TODO
 *
 * @author jakub.ptak@externals.hilti.com
 */
public class Homepage {

	private static final String HILTI_URL = "https://www.hilti";

	public void navigateTo(final String domain) {
		WebDriverFactory.getDriver().get(HILTI_URL + domain);
	}
}
