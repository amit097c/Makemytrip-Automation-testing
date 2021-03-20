package com.makemytrip_Automation.utils;

public class tempReadExcel
{
	public static void main(String[] args) 
	{
		ReadExcelDataFile file=new ReadExcelDataFile(System.getProperty("user.dir")+"\\src\\main\\java\\testData\\loginData.xlsx");
		System.out.println(file.getRowCount("travelInfo"));
		//System.out.println(file.addColumn("sheet1", "comment")); 
		 int colNum=file.getColumnCount("loginInfo");
		 String username=file.getCellData("loginInfo", 0, 2);
		 System.out.println(username);
		 //System.out.println(file.removeColumn("sheet1", colNum));
	}

}
