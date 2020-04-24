package com.hilti.ta.steps;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.hilti.ta.pages.CartPage;
import com.hilti.ta.pages.Homepage;
import com.hilti.ta.pages.ProductPage;
import com.hilti.ta.pages.components.ConsentsComponent;
import com.hilti.ta.services.BannersService;
import com.hilti.ta.services.BrowserService;
import com.hilti.ta.services.CookieService;
import com.hilti.ta.utils.Country;
import com.hilti.ta.utils.WebDriverFactory;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Cucumber steps definition class for general purpose steps.
 */
public class GeneralSteps {

	private Homepage homepage = new Homepage();
	private ConsentsComponent consentsComponent = new ConsentsComponent();
	private BannersService bannersService = new BannersService();
	private CookieService cookieService = new CookieService();
	private BrowserService browserService = new BrowserService();
	private ProductPage productpage = new ProductPage();
	private CartPage cartPage = new CartPage();

	@Given("User opens Hilti website for country (.+)")
	public void userOpensHiltiWebsite(final Country country) {
		homepage.navigateTo(country);

		consentsComponent.closeConsents();
		final List<String> bannerIds = bannersService.getBannerIdsForCountry(country);
		cookieService.createCookie("bannedBannersPermanently", String.join("*", bannerIds));

		browserService.refreshPage();
	}
	
	

// @When("User navigates to product page for <product_code>")
//public void user_navigates_to_product_page_for_product_code(final String productCode) {
//   
//	 productpage.navigateTo(productCode);
//}
 
 @When("User navigates to product page for {string}")
 public void user_navigates_to_product_page_for(String productCode) {
     // Write code here that turns the phrase above into concrete actions
	 productpage.navigateTo(productCode);
	 WebDriverFactory.getWebDriverWait(15);
 }
 

@When("User adds to cart products with given properties for (.+) with following data")
public void user_adds_to_cart_products_with_given_properties(final Country country, DataTable dataTable) {
	
	System.out.println("******************" + country.toString());
	for(Map<String, String> data : dataTable.asMaps()) {
		
		if (
		! data.get("dataForCountry").equalsIgnoreCase(country.toString())) 
			continue ;
				
	
		productpage.selectCartridgeColor( data.get("cartridgeColor"));
		WebDriverFactory.getWebDriverWait(10);

		productpage.selectPackSize(data.get("packSize"));
		WebDriverFactory.getWebDriverWait(10);

		productpage.selectquantity(data.get("quantity"));
		WebDriverFactory.getWebDriverWait(10);

		productpage.clickAddBtn();
		WebDriverFactory.getWebDriverWait(10);
		
		WebDriverFactory.scrollWindow(-200);
	}
}


@When("User navigates to cart page.")
public void user_navigates_to_cart_page() {
	productpage.GotoShoppingCart();

}

@Then("User can see the products are added to cart page for (.+).")
public void user_can_see_the_products_are_added_to_cart_with_proper_quantities_as_above(final Country country ,DataTable dataTable) {
  
   int productNo = 0;
   WebDriverFactory.scrollWindow(500);
   for(Map<String, String> data : dataTable.asMaps()) {
		
		if (! data.get("dataForCountry").equalsIgnoreCase(country.toString())) 
			continue ;
				
		productNo++;
		String desiredColorValue = "";
		String desiredProdSize = "";
		String desiredProdQuant="";
		
		desiredColorValue = data.get("cartridgeColor");
		desiredProdSize = data.get("packSize");
		desiredProdQuant = data.get("quantity");
		
		
		String actualColorValue = "";
		actualColorValue=cartPage.getProductColorByIndex(productNo);
		System.out.println(actualColorValue +"==="+desiredColorValue);
		Assert.assertTrue(actualColorValue.contains(desiredColorValue), "Color value of cart item not matching");
		WebDriverFactory.getWebDriverWait(10);
		
		String actualProdSize="";
		actualProdSize=cartPage.getProductSizeByIndex(productNo);
		System.out.println(actualProdSize +"==="+desiredProdSize);
		Assert.assertTrue(actualProdSize.equalsIgnoreCase(desiredProdSize), "Size value of cart item not matching");
		WebDriverFactory.getWebDriverWait(10);
		
		String actualProdQuant="";
		actualProdQuant=cartPage.getProductQuantityByIndex(productNo);
		System.out.println(actualProdQuant +"==="+desiredProdQuant);
		Assert.assertTrue(actualProdQuant.equalsIgnoreCase(desiredProdQuant), "Quantity value of cart item not matching ");
		WebDriverFactory.getWebDriverWait(10);
		
		
	}
}


 
}
