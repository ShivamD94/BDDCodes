
package com.hilti.ta.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.hilti.ta.pages.PageObject;
import com.hilti.ta.utils.Country;
import com.hilti.ta.utils.WebDriverFactory;

/**
 * Page Object Model representing Product PAge.
 */
public class ProductPage extends PageObject {

	private static final String HILTI_URL = "https://www.hilti";

	private By cartColor = By.xpath("//section[2]//m-property-group[1]//div[1]//div[1]//form[1]//span");
	private By packSize = By.xpath("//section[3]//m-property-group[1]//div[1]//div[1]//form[1]//span");
	private By quantity = By.xpath("//input[@id='quantity-1']");
	private By addBtn = By.xpath("//span[@class='a-icon-cartadd ng-binding']");
	private By shoppingCart = By.xpath("//span[contains(text(),'Shopping Cart')]");
	
	/**
	 * Opens ProductPage  for given country and a given productCode.
	 * 
	 * 
	 *            
	 *  @param productCode          
	 */
	public void navigateTo( final String productCode) {
		String productUrl=WebDriverFactory.getDriver().getCurrentUrl()+productCode;
		WebDriverFactory.getDriver().get(productUrl);
		}
	
	public void selectCartridgeColor(String color) {
		
		WebDriverFactory.getWebDriverWait(10);
	   List <WebElement> cColor=WebDriverFactory.getDriver().findElements(cartColor);
		for(WebElement ele : cColor)
		{
			String cartridgecolor = ele.getText();
			if(cartridgecolor.equalsIgnoreCase(color))
			{ 
				ele.click();
				break;
			}
		}
	}
	
	public void selectPackSize(String size) {
		
		List<WebElement> pSize=WebDriverFactory.getDriver().findElements(packSize);
		for(WebElement ele : pSize)
		{ String packsize = ele.getText();
		 System.out.println(packsize);
		 if(packsize.contains(size))
		 {
			 ele.click();
			 break;
		 }
		}
	}
	
	
	public void selectquantity(String quant) {
		WebDriverFactory.getWebDriverWait(10);
		WebDriverFactory.getDriver().findElement(quantity).sendKeys(Keys.BACK_SPACE);
		WebDriverFactory.getDriver().findElement(quantity).sendKeys(quant);

		
	}
	
	public void clickAddBtn() {
		WebDriverFactory.getDriver().findElement(addBtn).click();
	}
	
	public void GotoShoppingCart() {
		WebDriverFactory.getDriver().findElement(shoppingCart).click();
	}
	
}
