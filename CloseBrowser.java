package com.onlinecourse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class CloseBrowser {
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver","J:\\Steven\\Java\\chromedriver_win32\\chromedriver.exe");

		WebDriver dr = new ChromeDriver();
		System.out.println("browser will be closed");
		
		dr.get("http://www.baidu.com");
		 
        dr.quit();  
        System.out.println("browser is closed");
        
	}

}
