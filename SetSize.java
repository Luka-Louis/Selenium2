package com.onlinecourse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;

public class SetSize {
	
	public static void main(String[] args) throws InterruptedException {
		
		Dimension dim = new Dimension(400,800);
		
		System.setProperty("webdriver.chrome.driver","J:\\Steven\\Java\\chromedriver_win32\\chromedriver.exe");

		
		WebDriver dr = new ChromeDriver();
        dr.get("http://www.baidu.com");

        Thread.sleep(2000);
        
        System.out.println("Set browser to 400 x 800");
        dr.manage().window().setSize(dim);
        Thread.sleep(2000);

        System.out.println("browser will be close");
        dr.quit();  
        
        }

    }
