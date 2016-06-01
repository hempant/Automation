package com.perficient.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtil {

	public LinkedHashMap<String, String> getInputData(String TCName) {
		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		try {
			FileInputStream fs = new FileInputStream("resources/excel/Input/Input.xls");
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheet("TestData");

			int intRow = findRowNumber(sheet, TCName);

			for (int i = 0; i <= sheet.getRow(intRow-1).getLastCellNum()-1; i++) {
				parameters.put(sheet.getRow(intRow-1).getCell(i,Row.CREATE_NULL_AS_BLANK).getStringCellValue(), sheet.getRow(intRow).getCell(i,Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		return parameters;
	}

	public String setupOutput() {
		String fileName = new SimpleDateFormat("'resources/excel/Output/Output_'yyyyMMdd_hhmmss'.xls'").format(new Date());
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			HSSFWorkbook workBook = new HSSFWorkbook();

			workBook.write(outputStream);
			outputStream.writeTo(fout);
			outputStream.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public void writeOutput(String fileName, LinkedHashMap<String, String> parameters, LinkedList<LinkedHashMap<String, String>> results) {

		try {
			FileInputStream file = new FileInputStream(new File(fileName));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.createSheet(parameters.get("tc_name"));			    
			    
			int rownum = 0;
			Row row = sheet.createRow(rownum++);
			
			//Create the header
			int cellnum = 0;
			row.createCell(cellnum++).setCellValue("TestCase Name");
			row.createCell(cellnum++).setCellValue("Test Class and Method");
			row.createCell(cellnum++).setCellValue("Step Description");
			row.createCell(cellnum++).setCellValue("Status");
			row.createCell(cellnum++).setCellValue("Expected Result");
			row.createCell(cellnum++).setCellValue("Actual Result");
			row.createCell(cellnum++).setCellValue("Browser");
			row.createCell(cellnum++).setCellValue("Exception (if any)");
			
			//Fill the rows
			for(LinkedHashMap<String, String> result : results) {
				row = sheet.createRow(rownum++);
				cellnum = 0;
				row.createCell(cellnum++).setCellValue(parameters.get("tc_name"));
				row.createCell(cellnum++).setCellValue(parameters.get("test_class") + " - " + parameters.get("test_method"));
				row.createCell(cellnum++).setCellValue(result.get("Description"));
				row.createCell(cellnum++).setCellValue(result.get("Status"));
				row.createCell(cellnum++).setCellValue(result.get("ExpectedResult"));
				row.createCell(cellnum++).setCellValue(result.get("ActualResult"));
				row.createCell(cellnum++).setCellValue(result.get("Browser"));
				row.createCell(cellnum++).setCellValue(result.get("Exception"));
		    }
			
			
			//Alternate way to iterate through the map with map entry
//			int rownum = 0;
//		    for(LinkedHashMap<String, String> result : results) {
//				if (rownum ==0) {
//					Row row = sheet.createRow(rownum++);
//					int cellnum = 0;
//					for (Map.Entry<String, String> item : result.entrySet()) {
//						Cell cell = row.createCell(cellnum++);
//						cell.setCellValue(item.getKey());
//					}					
//				}
//				Row row = sheet.createRow(rownum++);
//				int cellnum = 0;
//				for (Map.Entry<String, String> item : result.entrySet()) {
//					Cell cell = row.createCell(cellnum++);
//					cell.setCellValue(item.getValue());
//				}
//		    }
			
			file.close();

			FileOutputStream outFile =new FileOutputStream(new File(fileName));
			workbook.write(outFile);
			outFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static int findRowNumber(HSSFSheet sheet, String cellData) {
		for (Row row : sheet) {
			Cell cell = row.getCell(0,Row.CREATE_NULL_AS_BLANK);
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				if (cell.getRichStringCellValue().getString().trim().equals(cellData)) {
					return row.getRowNum();
				}
			}
		}
		return 0;
	}

}
