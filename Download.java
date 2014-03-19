package com.onlinecourse;
import java.io.File;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;


    public class Download {

        public static void main(String[] args) throws InterruptedException {
        	
    
            FirefoxProfile firefoxProfile = new FirefoxProfile();

            firefoxProfile.setPreference("browser.download.folderList",2);
            firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
            firefoxProfile.setPreference("browser.download.dir","c:\\Downloads");
            firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv/rar");

            WebDriver driver = new FirefoxDriver(firefoxProfile);
            //new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);

            driver.navigate().to("http://file.txtbook.com.cn/20110730/web/down20090411//2013-11/Œ‰”°¥Û¬Ω.rar");

            
            driver.quit();  
        }

    }
