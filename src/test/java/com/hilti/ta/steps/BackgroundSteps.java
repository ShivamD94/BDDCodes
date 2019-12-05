package com.hilti.ta.steps;

import com.hilti.ta.utils.WebDriverFactory;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

/**
 * TODO
 *
 * @author jakub.ptak@externals.hilti.com
 */
public class BackgroundSteps {

	@Before
	public void beforeUITests() {
		WebDriverFactory.initialize();
	}

	@After
	public void afterUITests(final Scenario scenario) {
		if (!scenario.isFailed()) {
			WebDriverFactory.quitCurrentDriver();
		}
	}
}
