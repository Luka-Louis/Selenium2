package com.onlinecourse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class StartBrowser {
	
	public static void main(String[] args) {
            
		System.setProperty("webdriver.chrome.driver","J:\\Steven\\Java\\chromedriver_win32\\chromedriver.exe");

		WebDriver dr = new ChromeDriver();
        dr.get("http://www.baidu.com");
	}
	
}
