package com.qunar.www;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qunar.www.FlightSearch.CityPair;

import java.util.List;

public class FlightSearch
{
	static WebDriver driver;
	
	@Before
	public void setUp() throws Exception
	{
		driver = new InternetExplorerDriver();
	}

	@After
	public void tearDown() throws Exception
	{
		//driver.quit();
	}

	@Test
	public void testFightSearch() throws InterruptedException
	{
		By onewayBtn = By.id("js_searchtype_oneway");
		By fromCity = By.name("fromCity");
		By toCity = By.name("toCity");
		By fromDate = By.name("fromDate");
		By searchBtn = By.cssSelector("form#js_flight_domestic_searchbox button.btn_search");
		final By searchComplete = By.cssSelector("div#progTip span");
		By flightResult = By.id("hdivResultPanel");
		By flightItem = By.cssSelector("div[id^='itemBar']");
		final By searchMsg = By.className("msg2");
		
		By bookBtn = By.className("btn_book");
		By transferFlight = By.cssSelector("div[id^='transfer']");
		By vendorListPanel = By.cssSelector("div[id^='wrlist']");
		
		
		driver.get("http://www.qunar.com/");
		
		if(!driver.findElement(onewayBtn).isSelected())
			driver.findElement(By.id("js_searchtype_oneway")).click();
		
		//��ȡ100�����ظ�����������
		HashSet<CityPair> cityPairList = getRandomCityPairs(getCityList());
		
		int testCount = 0;
		
		for(Iterator<CityPair> iter = cityPairList.iterator();iter.hasNext();)
		{
			testCount++;
			System.out.println("This is the test " + testCount);
			driver.get("http://www.qunar.com/");
	
			if(!driver.findElement(onewayBtn).isSelected())
				driver.findElement(By.id("js_searchtype_oneway")).click();
			
			CityPair cityPair = (CityPair)iter.next();
					
			driver.findElement(fromCity).clear();
			driver.findElement(toCity).clear();
			driver.findElement(fromDate).clear();
			
			driver.findElement(fromCity).sendKeys(cityPair.getFrom().getName());
			Thread.sleep(2000);
						
			driver.findElement(toCity).sendKeys(cityPair.getTo().getName());
			Thread.sleep(2000);
					
			/*
			driver.findElement(fromCity).click();
			
			driver.findElement(cityPair.getFrom().getIndex()).click();
			Thread.sleep(2000);
			
			System.out.println(cityPair.getFrom().getName());
			driver.findElement(cityPair.getFrom().getPanelID()).findElement(By.linkText(cityPair.getFrom().getName())).click();
			
		

			driver.findElement(toCity).sendKeys(Keys.SPACE);
			driver.findElement(toCity).click();
			
			Actions actions = new Actions(driver);
			
			actions.moveToElement(driver.findElement(cityPair.getTo().getIndex())).click().perform();		
			//driver.findElement(cityPair.getTo().getIndex()).click();
			Thread.sleep(2000);
			
			driver.findElement(cityPair.getTo().getPanelID()).findElement(By.linkText(cityPair.getTo().getName())).click();
			*/
			
			//ѡ��today+7�պ� 3�����ڵ�����
			setRandomDate();
			
			System.out.println("From: "+ cityPair.getFrom().getName()+" To: "+cityPair.getTo().getName());
	
			driver.findElement(searchBtn).click();
			
			System.out.println("loading...");
			
			//�ȴ�ҳ���������
			(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
                                    return d.findElement(By.tagName("body")).getText().contains("��������");
                            }
			});
			
			Thread.sleep(2000);
//********************************************************************************************************************************		
	
			System.out.println("Checking flight list");
			List<WebElement> flightList = driver.findElement(flightResult).findElements(flightItem);
			
			if(flightList.size() > 0)
			{			
				System.out.println("Flight Count: " + flightList.size());
				
				ArrayList<WebElement> directFlightList = new ArrayList<WebElement>();
				ArrayList<WebElement> indirectFlightList = new ArrayList<WebElement>();
				
				for(WebElement flight: flightList)
				{
					if(flight.getText().contains("ÿ�κ���������˰��"))
						indirectFlightList.add(flight);
					else
						directFlightList.add(flight);
				}
				
				//���з�ֱ�ﺽ�����
				System.out.println("Randomly selecting an indirect flight");
				Random r = new Random();
				
				if(indirectFlightList.size() > 0)
				{
					System.out.println("We are going to randomly select one indirect flight from " + indirectFlightList.size() + " indirect flights");
					
					WebElement indirectFlight = indirectFlightList.get(r.nextInt(indirectFlightList.size()));
					System.out.println(indirectFlight.getText());

					Actions actions = new Actions(driver);
					
					actions.moveToElement(indirectFlight.findElement(bookBtn)).click().perform();	
					
					//indirectFlight.findElement(bookBtn).click();
					
					List<WebElement> transferFlightList = indirectFlight.findElements(transferFlight);
					
					System.out.println("This flight has " + transferFlightList.size() + " indirect flights");
			
					
					for(int i=0; i<transferFlightList.size();i++)
					{
						WebElement transferFlightElem = transferFlightList.get(i);
						By route = By.className("e_qvt_route");
						switch(i)
						{
						
							case 0: 
								{
									//System.out.println(transferFlightElem.getAttribute("id"));
									System.out.println(transferFlightElem.findElement(route).getText());											
									assertTrue(transferFlightElem.findElement(route).getText().contains("��һ��"));
									break;
								}
							case 1: 
								{
									//System.out.println(transferFlightElem.getAttribute("id"));
									System.out.println(transferFlightElem.findElement(route).getText());		
									assertTrue(transferFlightElem.findElement(route).getText().contains("�ڶ���"));
									break;
								}
							case 2: 
								{
									assertTrue(transferFlightElem.findElement(route).getText().contains("������")); 
									break;
								}
							case 3: 
								{
									assertTrue(transferFlightElem.findElement(route).getText().contains("���ĳ�"));
									break;
								}
						}			
					}
					
			
				}
				else System.out.println("There's no indirect flight available for this query");
				
						
				//����ֱ�ﺽ�����
				System.out.println("Randomly selecting an direct flight");
				
				if(directFlightList.size() > 0)
				{
					System.out.println("We are going to randomly select one direct flight from " + directFlightList.size() + " direct flights");
					
					WebElement directFlight = directFlightList.get(r.nextInt(directFlightList.size()));
					System.out.println(directFlight.getText());
					//Actions actions = new Actions(driver);
					
					//actions.moveToElement(directFlight.findElement(bookBtn)).click().perform();	
					
					directFlight.findElement(bookBtn).click();
					List<WebElement> vendorList =  directFlight.findElement(vendorListPanel).findElements(By.tagName("div"));
					
					//��֤���۷�Χ�ڵײ�
					assertTrue(vendorList.get(vendorList.size()-1).getText().contains("���۷�Χ"));
					System.out.println(vendorList.get(vendorList.size()-1).getText());
								
				}
				else  System.out.println("There's no direct flight available for this query");
				
				
			}
			else{

				assertTrue(driver.findElement(flightResult).getText().contains("�ú��ߵ�ǰ�޿��ۺ���"));
				System.out.println(driver.findElement(flightResult).getText());
			}
			System.out.println("--------------------------------------------------------------------------------------");

		}
		
		
	}
	
	public ArrayList<City> getCityList()
	{
		ArrayList<City> cityList = new ArrayList<City>();
		
		driver.findElement(By.name("fromCity")).click();
		
		//ò�Ƴ���Ҫ�ȵ���tab�����б���ʵʱ���ɵģ�ÿ��tab�����£��ó����б���dom�����
		
		ArrayList<String> ABCDE_City = getSubCityList(By.cssSelector("span[data-tab-id='dfh-ABCDE']"), By.cssSelector("div[data-panel-id='dfh-ABCDE'] li"));		
		for(int i=0; i < ABCDE_City.size();i++)	
			cityList.add(new City(By.cssSelector("span[data-tab-id='dfh-ABCDE']"), By.cssSelector("div[data-panel-id='dfh-ABCDE']"),ABCDE_City.get(i)));
		
		
		ArrayList<String> FGHJ_City = getSubCityList(By.cssSelector("span[data-tab-id='dfh-FGHJ']"), By.cssSelector("div[data-panel-id='dfh-FGHJ'] li"));
		for(int i=0; i < FGHJ_City.size();i++)	
			cityList.add(new City(By.cssSelector("span[data-tab-id='dfh-FGHJ']"), By.cssSelector("div[data-panel-id='dfh-FGHJ']"), FGHJ_City.get(i)));
		
		ArrayList<String> KLMNP_City = getSubCityList(By.cssSelector("span[data-tab-id='dfh-KLMNP']"), By.cssSelector("div[data-panel-id='dfh-KLMNP'] li"));
		for(int i=0; i < KLMNP_City.size();i++)	
			cityList.add(new City(By.cssSelector("span[data-tab-id='dfh-KLMNP']"), By.cssSelector("div[data-panel-id='dfh-KLMNP']"),KLMNP_City.get(i)));
		
		ArrayList<String> QRSTW_City = getSubCityList(By.cssSelector("span[data-tab-id='dfh-QRSTW']"), By.cssSelector("div[data-panel-id='dfh-QRSTW'] li"));
		for(int i=0; i < QRSTW_City.size();i++)	
			cityList.add(new City(By.cssSelector("span[data-tab-id='dfh-QRSTW']"), By.cssSelector("div[data-panel-id='dfh-QRSTW']"), QRSTW_City.get(i)));
		
		ArrayList<String> XYZ_City = getSubCityList(By.cssSelector("span[data-tab-id='dfh-XYZ']"), By.cssSelector("div[data-panel-id='dfh-XYZ'] li"));
		for(int i=0; i < XYZ_City.size();i++)	
			cityList.add(new City(By.cssSelector("span[data-tab-id='dfh-XYZ']"), By.cssSelector("div[data-panel-id='dfh-XYZ']"), XYZ_City.get(i)));
		
	/*	cityList = (ArrayList<String>)ABCDE_City.clone();
		cityList.addAll(FGHJ_City);
		cityList.addAll(KLMNP_City);
		cityList.addAll(QRSTW_City);
		cityList.addAll(XYZ_City);
	*/	
		System.out.println(cityList.size());
		return cityList;
		
	}
	
	
	
	public ArrayList<String> getSubCityList(By cityTab, By cityItems)
	{
		ArrayList<String> subCityList = new ArrayList<String>();
		
		driver.findElement(cityTab).click();
		List<WebElement> fromCityList_ABCDE = driver.findElements(cityItems);
	
		
		for(WebElement city: fromCityList_ABCDE)
		{
			System.out.println(city.getText());
			subCityList.add(city.getText());
		}
		
		return subCityList;
		
	}
	
	//��ȡ100�����ظ��ĳ��ж�
	public HashSet<CityPair> getRandomCityPairs(ArrayList<City> cityList)
	{	
		HashSet<CityPair> cityPairList= new HashSet<CityPair>();
		
		do
		{
			Random r = new Random();
			City from  = cityList.get(r.nextInt(cityList.size()));
			City to;
			
			do
			{
				to = cityList.get(r.nextInt(cityList.size()));
				
			}while(from.equals(to));
			
			cityPairList.add(new CityPair(from, to));

		}while(cityPairList.size()!=100);
		
		return cityPairList;
	}
	
	//ѡ��today+7�պ� 3�����ڵ�����
	public void setRandomDate()
	{
	
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.MONTH,3);
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_MONTH,7);
		
		long daySpan = (calendar1.getTime().getTime()-calendar2.getTime().getTime())/24/60/60/1000;
		
		//����7���3�����ڿ�ѡ��������
		
		System.out.println("Time Span (7 days later ~ within 3 months): " + daySpan);
		
		Random r = new Random();
		int randomDayFactor = r.nextInt((int)daySpan)+1;
		
		System.out.println("Generated RandomDayFactor: "+randomDayFactor);
		
		//JS����
		// ((JavascriptExecutor)driver).executeScript("var date = new Date();date.setDate(date.getDate() + 7 + " + randomDayFactor + ");var random_days_later = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate();$('input[name=fromDate]').val(random_days_later);");
		
		Calendar departureDate = Calendar.getInstance();
		departureDate.add(Calendar.DAY_OF_MONTH, 7 + randomDayFactor);
		
		String date = new SimpleDateFormat("YYYY-MM-dd").format(departureDate.getTime());
		System.out.println("Flight Date: " + date);
		
		driver.findElement(By.name("fromDate")).sendKeys(date);
		
	}
	
	class City
	{
		private By index;
		private By panelID;
		private String name;
		
		public City(By index, By panelID, String name)
		{
			this.index = index;
			this.panelID = panelID;
			this.name = name;
		}
		
		public By getIndex()
		{
			return this.index;
		}
		
		public By getPanelID()
		{
			return this.panelID;
		}
		
		public String getName()
		{
			return this.name;
		}
		
		public boolean equals(Object city)
		{
			if(!(city instanceof City))
				return false;
			else if(this==city)
				return true;
			else
				return this.name.equals(((City)city).getName());		
		}
		public int hashcode()
		{
			return name.hashCode();
		}
	}
	
	class CityPair
	{
		private City from;
		private City to;
		
		public CityPair(City from, City to)
		{
			this.from = from;
			this.to = to;		
		}
			
		public City getFrom()
		{
			return this.from;
		}
		
		public City getTo()
		{
			return this.to;
		}

		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			return result;
		}

		public boolean equals(Object p)
		{
			if(!(p instanceof CityPair))
				return false;
			else if(this==p)
				return true;
			else
				return this.from.equals(((CityPair)p).getFrom()) && this.to.equals(((CityPair)p).getTo());		
		}

		
	}
	
	
}
