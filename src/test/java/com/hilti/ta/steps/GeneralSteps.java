package com.hilti.ta.steps;

import com.hilti.ta.pages.Homepage;
import com.hilti.ta.utils.Country;

import io.cucumber.java.en.Given;

/**
 * Cucumber steps definition class for general purpose steps.
 */
public class GeneralSteps {

	private Homepage homepage = new Homepage();

	@Given("User opens Hilti website for country (.+)")
	public void userOpensHiltiWebsite(final Country country) {
		homepage.navigateTo(country);
	}
}
