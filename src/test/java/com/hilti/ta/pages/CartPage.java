package com.hilti.ta.pages;

import org.openqa.selenium.By;

import com.hilti.ta.utils.WebDriverFactory;

public class CartPage extends PageObject{

	
	private By product1Color = By.xpath("//tbody[@id='NORMAL-50351-0']//td[contains(@class,'m-cart--td-info js-row-info')]//strong");
	private By product1quant = By.xpath("//input[@id='CartUpdateForm-50351_shopping-cart-input']");
	private By product1Size = By.xpath("//tbody[@id='NORMAL-50351-0']//div[contains(@class,'m-cart--td-price--extra-data')]//div");
	private By prod2Color = By.xpath("//tbody[@id='NORMAL-50373-2']//td[contains(@class,'m-cart--td-info js-row-info')]//strong");
	private By prod2quant = By.xpath("//input[@id='CartUpdateForm-50373_shopping-cart-input']");
	private By prod2Size = By.xpath("//tbody[@id='NORMAL-50373-2']//div[contains(@class,'m-cart--td-price--extra-data')]//div");
	private By prod3color = By.xpath("//tbody[@id='NORMAL-50352-3']//td[contains(@class,'m-cart--td-info js-row-info')]//strong");
	private By prod3quant = By.xpath("//input[@id='CartUpdateForm-50352_shopping-cart-input']");
	private By prod3size = By.xpath("//tbody[@id='NORMAL-50352-3']//div[contains(@class,'m-cart--td-price--extra-data')]//div");
	
	private By prodColor = By.xpath("//tbody[1]//tr[1]//td[2]//div[1]//strong[1]");
	private By prodQuant = By.xpath("//tbody[1]//tr[1]//td[6]//div[1]//form[1]//div[1]//input[1]");
	private By prodSize = By.xpath("//tbody[1]//tr[1]//td[4]//div[2]//div[1]");
	
	public String getProductColorByIndex(int index) {
		
		  prodColor = By.xpath("//tbody["+index+"]//tr[1]//td[2]//div[1]//strong[1]");
		  return WebDriverFactory.getDriver().findElement(prodColor).getText();
		
		
	}
	
	public String getProductQuantityByIndex(int index)
	{
		prodQuant = By.xpath("//tbody["+index+"]//tr[1]//td[6]//div[1]//form[1]//div[1]//input[1]");
		return WebDriverFactory.getDriver().findElement(prodQuant).getAttribute("value");

	}
	
	public String getProductSizeByIndex(int index)
	{
		prodSize = By.xpath("//tbody["+index+"]//tr[1]//td[4]//div[2]//div[1]");
		return WebDriverFactory.getDriver().findElement(prodSize).getAttribute("innerHTML");
	}
}
