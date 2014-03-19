package com.onlinecourse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Get {
	
	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver","J:\\Steven\\Java\\chromedriver_win32\\chromedriver.exe");

		WebDriver dr = new ChromeDriver();
        Thread.sleep(2000);

        String url = "http://www.baidu.com";
        System.out.printf("now accesss %s \n", url);
        dr.get(url);
        Thread.sleep(2000);

        System.out.println("browser will be close");
        dr.quit();  
        }

    }
