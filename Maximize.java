package com.onlinecourse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Maximize {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver","J:\\Steven\\Java\\chromedriver_win32\\chromedriver.exe");

		WebDriver dr = new ChromeDriver();
        dr.get("http://www.baidu.com");

        Thread.sleep(2000);

        System.out.println("maximize browser");
        dr.manage().window().maximize();
        Thread.sleep(2000);

        System.out.println("browser will be close");
        dr.quit();  
        
        }

    }
