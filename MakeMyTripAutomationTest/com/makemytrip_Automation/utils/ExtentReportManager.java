package com.makemytrip_Automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class ExtentReportManager 
{
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;
	public static ExtentReports getReportInstance() 
	{
		if(htmlReporter==null&&report==null) 
		{
			String fileName=DateUtils.getTimeStamp();
			htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"\\test-output\\"+fileName+".html");
			report=new ExtentReports();
			report.attachReporter(htmlReporter);
			
			report.setSystemInfo("OS", "Windows 10 pro");
			report.setSystemInfo("Environment", "UAT");
			report.setSystemInfo("Build", "10.0.14393");
			report.setSystemInfo("Browser", "Chrome");
			report.setSystemInfo("System Type", "x64-based PC");
			report.setSystemInfo("System Model", "HP Envy Sleekbook 4 PC");
			
			htmlReporter.config().setDocumentTitle("UAT UI AUTOMATION RESULTS");
			htmlReporter.config().setReportName("Login Test Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTimeStampFormat("MMM dd,yyyy HH:mm:ss");
		}
		return report;
	}
	

}
