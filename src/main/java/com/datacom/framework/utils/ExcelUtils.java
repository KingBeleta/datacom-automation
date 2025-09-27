package com.datacom.framework.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.datacom.framework.models.UserData;

public class ExcelUtils {

	public static List<UserData> getUserData(String filePath, String sheetName) {
		List<UserData> userDataList = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				System.out.println("Could not find sheet: " + sheetName);
				System.out.println("Available sheets:");
				for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
					System.out.println(" - " + workbook.getSheetName(i));
				}
				throw new RuntimeException("Sheet not found: " + sheetName);
			}

			Iterator<Row> rowIterator = sheet.iterator();

//			// Skip header row
//			Row headerRow = rowIterator.next();

			int rowNum = 1;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				String testName = getCellValue(row, 0);
				String firstName = getCellValue(row, 1);
				String lastName = getCellValue(row, 2);
				String phone = getCellValue(row, 3);
				String country = getCellValue(row, 4);
				String email = getCellValue(row, 5);
				String password = getCellValue(row, 6);

				UserData user = new UserData(rowNum, testName, firstName, lastName, phone, country, email, password);

				userDataList.add(user);
				rowNum++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userDataList;
	}

	private static String getCellValue(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell).trim();
	}

	 public static List<String> getStrictColumnValues(String resourceName, String sheetName, int columnIndex) {
	        List<String> values = new ArrayList<>();
	        try (InputStream is = ExcelUtils.class.getClassLoader().getResourceAsStream(resourceName);
	             Workbook workbook = new XSSFWorkbook(is)) {

	            if (is == null) {
	                throw new RuntimeException("Excel resource not found: " + resourceName);
	            }
	            Sheet sheet = workbook.getSheet(sheetName);
	            for (Row row : sheet) {
	                Cell cell = row.getCell(columnIndex);
	                if (cell != null) {
	                    values.add(cell.getStringCellValue().trim());
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return values;
	    }


	public static Set<String> getColumnValues(String excelPath, String sheetName, int columnIndex) {
		Set<String> values = new HashSet<>();
		try (FileInputStream fis = new FileInputStream(excelPath); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			for (Row row : sheet) {
				Cell cell = row.getCell(columnIndex);
				if (cell != null) {
					values.add(cell.getStringCellValue().trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

}
