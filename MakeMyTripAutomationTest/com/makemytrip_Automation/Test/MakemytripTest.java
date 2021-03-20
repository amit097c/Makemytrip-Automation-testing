package com.makemytrip_Automation.Test;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;

import org.apache.xmlbeans.impl.regex.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.makemytrip_Automation.BaseUI.BaseUI;
import com.makemytrip_Automation.utils.ReadExcelDataFile;


public class MakemytripTest extends BaseUI
 {
	public static ReadExcelDataFile file;
	@Test
	public void login() throws InterruptedException 
	{
		logger=report.createTest("LoginTest");	//testcase title
		invokeBrowser("chrome");
		openURL(prop.getProperty("websiteURL"));	
	    
		file=new ReadExcelDataFile(System.getProperty("user.dir")+"\\src\\main\\java\\testData\\loginData.xlsx");//setting up excel file
        
	    String username=file.getCellData("loginInfo", 0, 2);	//Retrieving username data from excel file 'loginInfo'
		String password=file.getCellData("loginInfo",1,2);		//Retrieving password data from excel file 'loginInfo'
		elementClick("signinBtn_xpath");						//Clicking signing button	
        enterText("usernameTextbox_xpath",username);			//Entering data into username field 
        elementDoubleClick("continueBtn_xpath");				//clicking continue button
        Thread.sleep(3000);										//waiting for page to get load
        enterText("passwordTextbox_xpath",password);			//entering password into password text field
        elementClick("loginBtn_xpath");							//clicking login button
        elementClick("crossBarBtn_xpath");						//closing pop up login in window modal 
        
        takeScreenShot();  										//taking screenshot
        
	}
	
	@Test(dependsOnMethods = "login")
	public void selectCity() throws InterruptedException 
	{
		logger=report.createTest("selectCityTest");	            //setting testCase name 

		elementClick("fromCityBtn_xpath");						//clicking on departure element 
		String from=file.getCellData("travelInfo", 0,2);		//reading username from excel file data cell
		String to=file.getCellData("travelInfo", 1, 2);			//reading password from excel file data cell
		enterText("fromCityTextbox_xpath",from);				//entering text in departure textbox field	
		Thread.sleep(1000);										//waiting for the page to get load
		elementClick("fromCityFirstOption_xpath");				//selecting departure city from dropdown menu
		elementDoubleClick("toCityBtn_xpath");					//clicking on arrival city button
		enterText("toCityTextbox_xpath",to);					//entering text in arrival city textbox
		Thread.sleep(3000);										//waiting for the page to get load
		elementClick("toCityFirstOption_xpath");				//selecting arrival city from dropdown menu

	}
	
	
	
	@Test(dependsOnMethods = "selectCity")
	public void selectDate() throws java.text.ParseException, InterruptedException 
	{
		logger=report.createTest("selectDateTest");	            //setting testCase name


		String date=file.getCellData("travelInfo", 2, 2);      						 //reading date from excel file data cell
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 	 //specifying the date format
		LocalDate friday_date =LocalDate.parse(date, formatter); 					 //converting string type date to local date type
		//d.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)
		int friday=friday_date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).getDayOfMonth(); //retrieving next/current friday date

		//int friday=friday_date.getDayOfMonth();
		
		Date currentDate = new Date();												//intializing date variable
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try 
		{
			 Date expectedDate = dateFormat.parse(date);
			 String day = new SimpleDateFormat("dd").format(expectedDate);
			 String month = new SimpleDateFormat("MMMM").format(expectedDate);			//parsing date format to string
			 String year = new SimpleDateFormat("yyyy").format(expectedDate);
			 String expectedMonthYear = month + " " + year;
	
			 try 
			   {
				 elementDoubleClick("departureBtn_xpath");								//clicking departure button
			    } 
			 catch (InterruptedException e) 
			 {
				 reportFail(e.getMessage());
				 e.printStackTrace();
			}
			while (true)											//infinite loop for finding the expected date(next/current friday)
			{
				String displayDate=getDisplayDate() ;
				if (expectedMonthYear.equals(displayDate))
				{
					selectDay(friday);
					mouseClick("searchBtn_xpath");
					Thread.sleep(10000);
					break;
				}
				else if (expectedDate.compareTo(currentDate) > 0)
				{
					elementClick("forwardArrowBtn_xpath");				//for navigating the calendar a month forward
				} 
				else
				{
					elementClick("backwardArrowBtn_xpath");				//for navigating the calendar a month backward
				} 
				}

			}
	
	 
		catch (Exception e)
		{
			reportFail(e.getMessage());
			e.printStackTrace();
		}

	}
	@Test(dependsOnMethods = "selectDate")
	public void flightDetails()// throws InterruptedException 
	{
		logger=report.createTest("selectCityTest");	            //setting testCase name 

		try 
		{
					String[] str=new String[10];
					elementClick("sortBtn_xpath");				//clicking sort button to sort according to descending price wise
					Thread.sleep(2000);							//waiting for sort to occur
					for(int i=1;i<6;i++)
					{
					 str[i-1]=driver.findElement(By.xpath("//div[@class='fli-intl-lhs pull-left']/div/div["+i+"]"+"/div/div/div/div/div/div/div[2]")).getText();//finding  the available flights details
					}
					for(int i=0;i<5;i++) 
					{
					 System.out.println(str[i]);				//printing the flights details on console
					}
				
		}
		catch(Exception e) 
		{
			reportFail(e.getMessage());
			e.printStackTrace();
		}
	
		
	}

@AfterTest
public void reportEnd() 
{
	report.flush();									//wrapping each testcase report
}
	
	
	
	

 }

