package info.itest.www;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

public class CreatePost
{
	
	static WebDriver driver;
	String userName = "admin";
	String password = "admin";
	
	@Before
	public void setUp() throws Exception
	{
		System.out.println("test setup"); 
		driver = new ChromeDriver();
	}

	@After
	public void tearDown() throws Exception
	{
		driver.quit();
		System.out.println("test close"); 
	}

//	@Test
	public void testLogin()
	{
		System.out.println("test start"); 
		

		
		login(userName,password);
		assertTrue(driver.getCurrentUrl().contains("wp-admin"));
		WebElement adminLink = driver.findElement(By.id("wp-admin-bar-my-account")).findElement(By.className("ab-item"));
		System.out.println(adminLink.getText());
		assertTrue(adminLink.getText().contains("admin"));
			
	}

	
//	@Test
	public void testCreatePost()
	{
		System.out.println("Create Post");
		login(userName, password);
		
		String title = "Test " + System.currentTimeMillis();
		this.createPost(title);
		driver.get("http://localhost:8080/wordpress/");
		WebElement first_post = driver.findElement(By.cssSelector(".entry-title a"));
		assertEquals(title.toUpperCase(), first_post.getText());	
	}
	
	
//	@Test
	public void testDeletePost()
	{
		login(userName, password);
		String rowId = createPost();
		driver.get("http://localhost:8080/wordpress/wp-admin/edit.php");
		Actions action = new Actions(driver);
		WebElement row = driver.findElement(By.id("post-" + rowId));
		action.moveToElement(row).perform();
		row.findElement(By.cssSelector(".trash a")).click();
		assertFalse(this.isPostExist(rowId));
	}
	
	//@Test (expected = NoSuchElementException.class)	
	public void testDeletePost2()
	{
		login(userName, password);
		String rowId = createPost();
		driver.get("http://localhost:8080/wordpress/wp-admin/edit.php");
		Actions action = new Actions(driver);
		WebElement elem = driver.findElement(By.id("post-" + rowId));
		action.moveToElement(elem).perform();
		elem.findElement(By.cssSelector(".trash a")).click();
		//assertFalse(this.isPostExist(rowId));
		driver.findElement(By.id("post-" + rowId));
	}
	
	@Test
	public void testUpdatePost()
	{
		login(userName, password);
		String rowId = createPost();
		
		driver.get("http://localhost:8080/wordpress/wp-admin/edit.php");
		WebElement row = driver.findElement(By.id("post-" + rowId));
		WebElement titleElem = row.findElement(By.cssSelector(".post-title a"));
		titleElem.click();
		
		String title = this.getTitle() + " Updated at " + System.currentTimeMillis();
		String content = "Updated at " + System.currentTimeMillis();
		
		this.setTitle(title);
		this.setContent(content);
		this.update();
		
		driver.get("http://localhost:8080/wordpress/wp-admin/edit.php");
		row = driver.findElement(By.id("post-" + rowId));
		titleElem = row.findElement(By.cssSelector(".post-title a"));
		assertEquals(titleElem.getText(), title);
		
		titleElem.click();
		assertEquals(this.getTitle(),title);
		assertEquals(this.getContent(),"<p>"+content+"</p>");
	}
	
	@Test
	public void testSearchPost()
	{
		login(userName, password);
		String title = System.currentTimeMillis()+"";
		String rowId = this.createPost(title);
		
		driver.get("http://localhost:8080/wordpress/wp-admin/edit.php");
		driver.findElement(By.id("post-search-input")).sendKeys(title);
		driver.findElement(By.id("search-submit")).click();
		
		String subTitle = driver.findElement(By.cssSelector(".subtitle")).getText();
		assertEquals(subTitle, "Search results for " + "¡°" + title +"¡±");
		
		String resultCount = driver.findElement(By.cssSelector(".displaying-num")).getText();
		assertEquals(resultCount, "1 item");
		
		List<WebElement> resultSet = driver.findElement(By.id("the-list")).findElements(By.cssSelector("tr[id^='post-']"));
		
		assertEquals(resultSet.size(),1);
		
		WebElement row = driver.findElement(By.id("post-" + rowId));
		WebElement titleElem = row.findElement(By.cssSelector(".post-title a"));
		assertEquals(titleElem.getText(), title);
	}
	
	
	public boolean isPostExist(String rowId)
	{
		try
		{
			driver.findElement(By.id("rowId"));
			return true;
		}catch(NoSuchElementException e)
		{
			return false;
		}
		
	}
	
	public void login(String userName, String password)
	{
		driver.get("http://localhost:8080/wordpress/wp-login.php");
		driver.findElement(By.id("user_login")).sendKeys(userName);
		driver.findElement(By.id("user_pass")).sendKeys(password);
		driver.findElement(By.name("wp-submit")).click();
	}
	
	public void setContent(String content)
	{	
		//			driver.switchTo().frame("content_ifr");
		//	driver.findElement(By.id("tinymce")).sendKeys("Body Content");
		
		String js = "document.getElementById('content_ifr').contentWindow.document.body.innerHTML='"+content+"'";
		((JavascriptExecutor)driver).executeScript(js);
	}
	
	public void setTitle(String title)
	{
		driver.findElement(By.name("post_title")).clear();;
		driver.findElement(By.name("post_title")).sendKeys(title);
	}
	
	public String getTitle()
	{
		return driver.findElement(By.name("post_title")).getAttribute("value");
	}
	
	public String getContent()
	{
		String js = "return document.getElementById('content_ifr').contentWindow.document.body.innerHTML";
		return ((JavascriptExecutor)driver).executeScript(js)+"";
	}
	
	public void publish()
	{
		driver.findElement(By.name("publish")).click();
	}
	
	public void update()
	{
		driver.findElement(By.id("publish")).click();
	}
	
	public String createPost(String title, String content)
	{
		driver.get("http://localhost:8080/wordpress/wp-admin/post-new.php");
		//String title = "Test " + System.currentTimeMillis();
		this.setTitle(title);
		this.setContent(content);
		this.publish();
		return this.getPostId();
	}
	
	public String createPost()
	{
		String title = "Test " + System.currentTimeMillis();
		String content = "test" + System.currentTimeMillis();
		return this.createPost(title,content);
	}
	
	public String createPost(String title)
	{
		String content = "test" + System.currentTimeMillis();
		return this.createPost(title,content);
	}
	
	public String getPostId()
	{	
		String URL = driver.findElement(By.id("sample-permalink")).getText();
		String[] arr = URL.split("=");
		return arr[1];
	}
}
