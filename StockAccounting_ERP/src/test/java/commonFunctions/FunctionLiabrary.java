package commonFunctions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.PropertyFileUtil;

public class FunctionLiabrary {
public static WebDriver driver;
public static String Expected="";
public static String Actual="";

//method for launching Browser
public static WebDriver startBrowser() throws Throwable {
			if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "E://Software Testing//Project_By_Ranga_Reddy//Projects//StockAccounting_ERP//chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
			}else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) {
				driver =new FirefoxDriver();
			}else {
				System.out.println("Browser Value is not Matching");	
			}
			return driver;
		}
//method for launching url
public static void openApplication(WebDriver driver) throws Throwable {
	driver.get(PropertyFileUtil.getValueForKey("Url"));
}
//method for wait  for element 
public static void waitForElement(WebDriver driver, String Locator_Type,String LocatorValue,String Testdata) {
	WebDriverWait mywait= new WebDriverWait(driver, Integer.parseInt(Testdata));
	if(Locator_Type.equalsIgnoreCase("name")) {
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	}
	else if(Locator_Type.equalsIgnoreCase("id")) {
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
	else if(Locator_Type.equalsIgnoreCase("xpath")) {
	mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	}
}
//method for type action
	public static void typeAction(WebDriver driver, String Locator_Type,String LocatorValue,String Testdata) {
		if(Locator_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(Testdata);
			
		}
		else if(Locator_Type.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(Testdata);
			
		}
		else if(Locator_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(Testdata);
			
		}
	}
//method for button click
	public static void clickAction(WebDriver driver, String Locator_Type,String LocatorValue,String Testdata) {
		if(Locator_Type.equalsIgnoreCase("name")) {
		driver.findElement(By.name(LocatorValue)).click();
	}
		else if(Locator_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
		else if(Locator_Type.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).click();
		}

	}
//method for validate title
	public static void validateTitle(WebDriver driver, String Expected_Title) {
		String Actual_Title=driver.getTitle();
		try {
		Assert.assertEquals(Actual_Title, Expected_Title,"This is Not Matching");
		
		}catch(Throwable t) {
			System.out.println(t.getMessage());
		}
	}
//method for close browser
	public static void closeBrowser(WebDriver driver) {
		driver.close();
		
	}
	
	//method for mouse click action
	public static void mouseClick(WebDriver driver) throws Throwable {
		Actions ac=new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[text()='Stock Items ']"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[.='Stock Categories'])[2]"))).click().perform();
	}

	//method for stock table validation	
	public static void categoryTable(WebDriver driver, String ExpectedData) throws Throwable {
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed()) {
			Thread.sleep(3000);
			//click search panel
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(ExpectedData);
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
			//capture category name from table
			String ActualData=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
			System.out.println(ActualData+"       "+ExpectedData);
			Assert.assertEquals(ExpectedData, ActualData, "Category Name Not Found In The Table");
		}
		
	}
	//method for capture data
	public static void captureData(WebDriver driver,String LocatorValue ) {
		Expected=driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	//method for supplier table
	public static void supplierTable(WebDriver driver) throws Throwable {
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).isDisplayed()) {
			Thread.sleep(3000);
			//click search panel
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-textbox"))).sendKeys(Expected);
			Thread.sleep(3000);
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
			Actual = driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
			System.out.println(Expected+"       "+Actual);
			Assert.assertEquals(Actual, Expected,"Supplier Number Not Found in Table");
		}
	}
	//method for java time stamp
	public static String captureDate() {
		Date date = new Date();
		DateFormat df=new SimpleDateFormat("YYYY_MM_DD");
		return df.format(date);
	}
	
}
