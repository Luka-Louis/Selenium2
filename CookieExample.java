package com.onlinecourse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Cookie;


  public class CookieExample{

    public static void main(String[] args) throws InterruptedException {
    	System.setProperty("webdriver.chrome.driver","J:\\Steven\\Java\\chromedriver_win32\\chromedriver.exe");

        WebDriver dr = new ChromeDriver();

        String url = "http://www.baidu.com";
        System.out.printf("now accesss %s \n", url);

        dr.get(url);
        Thread.sleep(2000);

        System.out.println(dr.manage().getCookies());

        dr.manage().deleteAllCookies();

        Cookie c1 = new Cookie("BAIDUID", "");
        Cookie c2 = new Cookie("BDUSS", "");
        dr.manage().addCookie(c1);
        dr.manage().addCookie(c2);

        System.out.println(dr.manage().getCookies());
        
        System.out.println("browser will be close");

      //  dr.quit();
    
    }

  }

