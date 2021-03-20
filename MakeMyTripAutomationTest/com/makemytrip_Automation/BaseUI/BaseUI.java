package com.makemytrip_Automation.BaseUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.makemytrip_Automation.utils.DateUtils;
import com.makemytrip_Automation.utils.ExtentReportManager;

public class BaseUI 
	{
	    public static WebDriver driver;
	    public Properties prop;
		public 	ExtentReports report=ExtentReportManager.getReportInstance();
		public static ExtentTest logger;

	    //*****************start the browser*****************
	    
	    public void invokeBrowser(String browserName) //throws IOException 
	    {	
	     try 
	     {	
	       if(browserName.equalsIgnoreCase("chrome")) 
		    {
			  System.setProperty("webdriver.chrome.driver","D:\\New folder\\makemytrip_Automation\\src\\test\\resources\\drivers\\chromedriver.exe");
			  driver=new ChromeDriver();
			  reportPass("Staring chrome browser");
		    }	
		   else if(browserName.equalsIgnoreCase("mozila firefox")) 
		    {
			  System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\drivers\\geckodriver.exe");
			  driver=new FirefoxDriver();
			  reportPass("Staring Mozila Firefox browser");
		    }
		   else 
		    {
			  System.out.println("Browser driver does not exist");
		    }
	    }
	    catch(Exception e) 
	    {
	    	
	    	reportFail(e.getMessage());
	    }
		  
	    
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			
		if(prop==null) 
		  {
				prop=new Properties();
				FileInputStream file;
				try 
				{
					file = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\ObjectRepository\\projectConfig.properties");
					prop.load(file);
				} 
				catch (Exception e) 
				{
					reportFail(e.getMessage());
					e.printStackTrace();
				}
								
		  }
			
		}
	    
		//**************** OPENING WEBSITE URL **************************
		public void openURL(String websiteURLKey) 
		{
			try 
			{
			  driver.get(websiteURLKey);
			  reportPass(websiteURLKey+" Identified");
			}
			catch(Exception e) 
			{
			  reportFail(e.getMessage());
			}
		}
	
		//**********************CLOSING BROWSER***************************
		public void tearDown() 
		{
			driver.close();
		}
		
		
		//T****************QUITING BROWSER************************
		
		public void quitBrowser()
		{
			driver.quit();
		}
		
		
		
		//*******************ENTERING TEXT IN TEXTBOX*************************
		
		public void enterText(String xpathKey,String data) 
		{
			 
			try 
			{
			  getElement(xpathKey).sendKeys(data);
			  reportPass(data+" :Entered Successfully In Locator Element: "+xpathKey);
			}
			catch(Exception e) 
			{
			  reportFail(e.getMessage());
			}
			
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		}
		
		
		//**********************CLICKING ELEMENT*********************************
		
		public void elementClick(String xpathKey) 
		{
			
			try 
			{
			getElement(xpathKey).click();
			reportPass("Successfully Clicked Locator Element: "+xpathKey);
			}
			catch(Exception e) 
			{
				reportFail(e.getMessage());
			}
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		
		//************************MOUSE CLICK****************************************
		public void mouseClick(String xpathKey) 
		{
			Actions actions = new Actions(driver);    
			WebElement element=getElement(xpathKey);
	        actions.click(element).build().perform();
	    	
		}
		
		//************************mouseClickException***************************
		public void selectDay(int day)
		{
			
			//pre="//p[text()= '";
			String pre=prop.getProperty("preDate_xpath");
			String post=prop.getProperty("postDate_xpath");
			//post= "']";
			String res=pre+day+post;
			
			driver.findElement(By.xpath(res)).click();
		}
		
		
		//*************************DOUBLE CLICKING ELEMENT****************************
		
		public void elementDoubleClick(String xpathKey) throws InterruptedException 
		{
			try 
			{
			Actions actions = new Actions(driver);    
			WebElement element=getElement(xpathKey);
	        actions.doubleClick(element).perform();
	        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	        //driver.manage().timeouts().wait(3);
			reportPass("Clicked Successfully Locator "+xpathKey);
			}
			catch(Exception e) 
			{
				reportFail(e.getMessage());
			}
		  }
		
		public String getDisplayDate() 
		{
			String displayDate=getElement("displayMonthLabel_className").getText();
			return displayDate;
		}
		
		//*****************GET WEB-ELEMENTS******************************
		
		public WebElement getElement(String locatorKey) 
		{
			WebElement element=null;
			
		  try 
		  {
			if(locatorKey.endsWith("_xpath")) 
			{
				//System.out.println(locatorKey);
				element=driver.findElement(By.xpath(prop.getProperty(locatorKey)));
				reportInfo("Element Located Successfully "+locatorKey);
			}
			else if(locatorKey.endsWith("_id")) 
			{
				element=driver.findElement(By.id(prop.getProperty(locatorKey)));
				reportInfo("Element Located Successfully "+locatorKey);

			}
			else if(locatorKey.endsWith("_className")) 
			{
				element=driver.findElement(By.className(prop.getProperty(locatorKey)));
			}
			/*
			else if(locatorKey.endsWith("_linkText")) 
			{
				element=driver.findElement(By.linkText(prop.getProperty(locatorKey)));
			}
			else if(locatorKey.endsWith("_name")) 
			{
				element=driver.findElement(By.name(prop.getProperty(locatorKey)));
			}
			else 
			{
				reportFail();
				//Assert.fail("failing the Testcase, invalid locator"+locatorKey);
			}*/
		  }
		  catch(Exception e) 
		  {
			  reportFail(e.getMessage());
		  }
			return element;
		}
		
		//**************REPORTING FUNCTIONS****************************
		
		public void reportFail(String reportString) 
		{
		  logger.log(Status.FAIL,reportString);
		  takeScreenShotOnFailure();
		  Assert.fail(reportString);
		}
		
		public void reportPass(String reportString) 
		{
			logger.log(Status.PASS,reportString);
		}
		
		public void reportInfo(String reportString) 
		{
			logger.log(Status.INFO,reportString);
		}
		
		//*****************TAKING SCRENNSHOTS******************************
		
		public void takeScreenShotOnFailure() 
		{
			TakesScreenshot takeScreenShot=(TakesScreenshot) driver;
			File sourceFile=takeScreenShot.getScreenshotAs(OutputType.FILE);
			File destFile=new File(System.getProperty("user.dir")+"\\Screenshots\\"+DateUtils.getTimeStamp()+".png");
			try
			{
				FileUtils.copyFile(sourceFile, destFile);
				logger.addScreenCaptureFromPath(System.getProperty("user.dir")+"\\Screenshots\\"+DateUtils.getTimeStamp()+".png");
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		public void takeScreenShot() 
		{
			TakesScreenshot takeScreenShot=(TakesScreenshot) driver;
			File sourceFile=takeScreenShot.getScreenshotAs(OutputType.FILE);
			File destFile=new File(System.getProperty("user.dir")+"\\Screenshots\\"+DateUtils.getTimeStamp()+".png");
			try
			{
				FileUtils.copyFile(sourceFile, destFile);
				logger.addScreenCaptureFromPath(System.getProperty("user.dir")+"\\Screenshots\\"+DateUtils.getTimeStamp()+".png");
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//*****************VERIFYING ELEMENTS******************************
		
		public boolean isElementPresent(String locatorKey) 
		{
			try 
			{
				getElement(locatorKey).isDisplayed();
				reportPass(locatorKey+":Element is Displayed ");
				return true;
			}
			catch(Exception e) 
			{
				reportFail(e.getMessage());
			}
			return false;
		}
		
		
		public boolean isElementSelected(String locatorKey) 
		{
			try 
			{
				getElement(locatorKey).isDisplayed();
				reportPass(locatorKey+":Element is Selected ");
				return true;
			}
			catch(Exception e) 
			{
				reportFail(e.getMessage());
			}
			return false;
		}
		
		public boolean isElementEnabled(String locatorKey) 
		{
			try 
			{
				getElement(locatorKey).isDisplayed();
				reportPass(locatorKey+":Element is Enabled ");
				return true;
			}
			catch(Exception e) 
			{
				reportFail(e.getMessage());
			}
			return false;
		}

		
		
		
 }
