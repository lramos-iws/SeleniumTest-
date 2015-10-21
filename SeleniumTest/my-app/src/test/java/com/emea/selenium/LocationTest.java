package com.emea.selenium;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class LocationTest {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  public enum Roma{
	  LOCATION1("Rome Fiumicino Airport"),
	  LOCATION2("Rome"),
	  LOCATION3("Rome Via Veneto");
	  
	  Roma(String location){
		  this.nome=location;
	  }
	  private String nome;
	  public String getNome(){
		  return this.nome;
	  }
  }
  public enum Amsterda{
	  LOCATION1("Amsterdam Airport"),
	  LOCATION2("Milton - Pace"),
	  LOCATION3("Steubenville");
	  
	  Amsterda(String location){
		  this.nome=location;
	  }
	  private String nome;
	  public String getNome(){
		  return this.nome;
	  }
	  
  }
  
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://isobar:wunderland@stage2-www-ehil.ctmsp.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().window().maximize();
  }

  @Test
  public void testLocation() throws Exception {
    driver.get(baseUrl + "/en_US/car-rental/home.html");
    Actions action = new Actions(driver);
    
    driver.findElement(By.cssSelector("input.abInput")).clear();
    driver.findElement(By.cssSelector("input.abInput")).sendKeys("rome");
    selectLocations(action, Arrays.asList(Roma.LOCATION1.getNome(),Roma.LOCATION2.getNome(),Roma.LOCATION3.getNome()));
    driver.findElement(By.cssSelector("input.abInput")).clear();
    driver.findElement(By.cssSelector("input.abInput")).sendKeys("ams");
    selectLocations(action, Arrays.asList(Amsterda.LOCATION1.getNome(),Amsterda.LOCATION2.getNome(),Amsterda.LOCATION3.getNome()));
    
    try{
    	assertNotEquals("A socket exception ocurred","We’re sorry, a system error occurred. Please try again in a few minutes or call us for assistance.", driver.findElement(By.cssSelector("p.error")).getText());
    }catch (Error e){
    	verificationErrors.append(e.toString());
    }
    
  }

private void selectLocations(Actions action, List<String> locations ) throws InterruptedException {

	WebElement location = driver.findElement(By.xpath("//div/div/ul/li[2][contains(text(),'"+locations.get(0)+"')]"));
    WebElement location2 = driver.findElement(By.xpath("//div/div/ul/li[6][contains(text(),'"+locations.get(1)+"')]"));
    WebElement location3 = driver.findElement(By.xpath("//div/div/ul/li[7][contains(text(),'"+locations.get(2)+"')]"));
   
    action.moveToElement(location).perform();
    Thread.sleep(2000);
    action.moveToElement(location2).perform();
    Thread.sleep(2000);
    action.moveToElement(location3).perform();
    Thread.sleep(2000);
}

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

}
