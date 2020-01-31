package com.hilti.ta.steps;

import com.hilti.ta.pages.Homepage;
import com.hilti.ta.pages.components.ConsentsComponent;
import com.hilti.ta.services.BannersService;
import com.hilti.ta.services.BrowserService;
import com.hilti.ta.services.CookieService;
import com.hilti.ta.utils.Country;

import io.cucumber.java.en.Given;

import java.util.List;

/**
 * Cucumber steps definition class for general purpose steps.
 */
public class GeneralSteps {

	private Homepage homepage = new Homepage();
	private ConsentsComponent consentsComponent = new ConsentsComponent();
	private BannersService bannersService = new BannersService();
	private CookieService cookieService = new CookieService();
	private BrowserService browserService = new BrowserService();

	@Given("User opens Hilti website for country (.+)")
	public void userOpensHiltiWebsite(final Country country) {
		homepage.navigateTo(country);

		consentsComponent.closeConsents();
		final List<String> bannerIds = bannersService.getBannerIdsForCountry(country);
		cookieService.createCookie("bannedBannersPermanently", String.join("*", bannerIds));

		browserService.refreshPage();
	}
}
