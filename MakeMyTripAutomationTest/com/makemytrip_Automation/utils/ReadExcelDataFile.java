package com.makemytrip_Automation.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

public class ReadExcelDataFile 
{
	public String path;
	public FileInputStream fis=null;
	public FileOutputStream fileOut=null;
	private XSSFWorkbook workbook=null;
	private XSSFSheet sheet=null;
	private XSSFCell cell=null;
	private XSSFRow row=null;
	
	
	//*********************CONSTRUCTOR***********************
	public ReadExcelDataFile(String path) 
	{
		this.path=path;
		try 
		{
			fis=new FileInputStream(path);
			workbook=new XSSFWorkbook(fis);
			sheet=workbook.getSheetAt(0);
			fis.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	//****************** RETURN ROW COUNT IN A SHEET *********************
	public int getRowCount(String sheetName) 
	{
		int index=workbook.getSheetIndex(sheetName);
		if(index==-1) 
		{
			return 0;
		}
		else 
		{
			sheet=workbook.getSheetAt(index);
			int number=sheet.getLastRowNum();
			return number;
		}
	}
	
	//*********************RETURN DATA FROM A CELL ***************************
	public String getCellData(String sheetName,String colName,int rowNum) 
	{
		if(rowNum<=0) 
		{
			return "";
		}
		int index=workbook.getSheetIndex(sheetName);
		int colNum=-1;
		if(index==-1) 
		{
			return "";
		}
		sheet=workbook.getSheetAt(index);
		row=sheet.getRow(0);
		for(int i=0;i<row.getLastCellNum();i++) 
		{
			if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())) 
			{
				 colNum=i;
			}
		}
		
		if(colNum==-1) 
		 {
			return "";
		 }
		else
		 {
			row=sheet.getRow(rowNum-1);
			if(row==null) 
			 {
				return "";
			 }
			else 
			 {
				cell=row.getCell(colNum);
				if(cell==null) 
				 {
					return "";
				 }
				else 
				 {
				    if(cell.getCellType()==Cell.CELL_TYPE_STRING) 
				    {
				    	return cell.getStringCellValue();
				    }
				    else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC) 
				    {
				    	return String.valueOf(cell.getNumericCellValue());
				    }
				    else if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN) 
				    {
				    	return String.valueOf(cell.getBooleanCellValue());
				    }
				    else 
				    {
				    	return "";
				    }
				 }
			 }
		 }
		

		
	}
	
	public String getCellData(String sheetName,int colNum,int rowNum) 
	{
		
	  try 
	  {
		if(rowNum<=0) 
		{
			return "";
		}
		int index=workbook.getSheetIndex(sheetName);
		//int colNum=-1;
		if(index==-1) 
		{
			return "";
		}
		sheet=workbook.getSheetAt(index);
		row=sheet.getRow(rowNum-1);
		
		if(row==null)
		{
			return "";
		}
		cell =row.getCell(colNum);
		if(cell==null) 
		{
			return "";
		}
		
		if(cell.getCellType()==cell.CELL_TYPE_STRING) 
		{
			return cell.getStringCellValue();
		}
		else if(cell.getCellType()==cell.CELL_TYPE_NUMERIC||cell.getCellType()==cell.CELL_TYPE_BOOLEAN||cell.getCellType()==cell.CELL_TYPE_BLANK) 
		{
			if(cell.getCellType()==cell.CELL_TYPE_NUMERIC) 
			{
				return String.valueOf(cell.getNumericCellValue());
			}
			else if(cell.getCellType()==cell.CELL_TYPE_BOOLEAN||cell.getCellType()==cell.CELL_TYPE_BLANK) 
			{
				if(cell.getCellType()==cell.CELL_TYPE_BOOLEAN) 
				{
					return String.valueOf(cell.getBooleanCellValue());
				}
				else 
				{
					return "";
				}
			}
			else 
			{
				return "";
			}
		}
		else 
		{
			return "";
		}
		
		
	  }
	  catch(Exception e) 
	  {
		  e.printStackTrace();
		  return "Row :"+rowNum+"Or column:"+colNum+"does not exist";
	  }
	}
	
	
	//************RETURN TRUE IF THE DATA IS SET SUCCESSFULLY**************************
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try 
		{
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0)
				return false;

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) 
			{	
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
				colNum = i;
			}
			
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cell.setCellStyle(cs);
			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();

		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//************************ADD SHEET***********************
	public boolean addSheet(String sheetname) 
	{

		FileOutputStream fileOut;
		try 
		{
			workbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//********************REMOVE SHEET****************************

	
	public boolean removeSheet(String sheetName) 
	{
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return false;

		FileOutputStream fileOut;
		try 
		{
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	/****************** Returns true if column is created successfully ***********************/
	public boolean addColumn(String sheetName, String colName) 
	{
		try
		{
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;

			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);

			sheet = workbook.getSheetAt(index);

			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);

			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public boolean removeColumn(String sheetName, int colNum) 
	{
		try 
		{
			if (!isSheetExist(sheetName))
				return false;
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			XSSFCreationHelper createHelper = workbook.getCreationHelper();
			

			for (int i = 0; i < getRowCount(sheetName); i++) 
			{
				row = sheet.getRow(i);
				if (row != null)
				{
					cell = row.getCell(colNum);
					if (cell != null) 
					{
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	public boolean isSheetExist(String sheetName) 
	{
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) 
		{
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} 
		else
			return true;
	}
	/****************** Returns number of columns in a sheet ***********************/
	public int getColumnCount(String sheetName) 
	{
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum();

	}
	

	
	public int getCellRowNum(String sheetName, String colName, String cellValue) 
	{

		for (int i = 2; i <= getRowCount(sheetName); i++)
		{
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue))
			{
				return i;
			}
		}
		return -1;

	}
	
	
}
