package com.serviceNow.xls;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Xls_Reader {

	private static Workbook workbook;
	private static Sheet currentSheet;

	public Xls_Reader(String filePath, String sheetName) {
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			try {
				workbook = new XSSFWorkbook(fileInputStream);
			} catch (Exception e) {
				System.out.println("Error while creating XSSFWorkbook: " + e.getMessage());
				e.printStackTrace();
			}
			setSheet(sheetName);
		} catch (IOException e) {
			System.out.println("Something went wrong could not open Excel file: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("@@@ Excel File Class was successfully initialized");
	}

	public Map<String, Map<String, String>> getFullTC_Data(String TC_Name) {

		Map<String, Map<String, String>> table = new LinkedHashMap<>();
		int lastRow = getRowCount();
		int firstRow = -1;
		int colCount;

		// Set rowIndex as the first data row for the TC
		for (int i = 0; i < lastRow; i++) {
			try {
				String cellValue = getCellValue(0, i);
				if (cellValue.equals(TC_Name)) {
					firstRow = i;
					break;
				}
			} catch (Exception e) {
				continue;
			}
		}

		if (firstRow == -1) {
			throw new IllegalArgumentException(TC_Name + "could not be finned");
		}

		// Set rowCount as the last data row for the TC
		for (int i = firstRow + 1; i < lastRow; i++) {
			String cellValue = getCellValue(0, i);
			if (cellValue.equalsIgnoreCase("EndTest") || cellValue.equalsIgnoreCase("End")) {
				lastRow = i;
				break;
			}
		}

		colCount = getColumnCount_inRow(firstRow);

		for (int y = firstRow + 1; y < lastRow; y++) {
			String rowKey = getCellValue(0, y);
			if (rowKey.isEmpty()) {
				continue;
			}

			Map<String, String> rowData = new LinkedHashMap<String, String>();
			for (int x = 1; x < colCount; x++) {

				String cellValue = getCellValue(x, y);
				if (cellValue == null || cellValue.isEmpty() || cellValue.isBlank()) {
					continue;
				}
				String colKey = getCellValue(x, firstRow);
				if (colKey == null || colKey.isEmpty() || colKey.isBlank()) {
					colCount++;
					continue;
				}
				rowData.put(colKey, cellValue);
			}
			if (!rowData.isEmpty()) {
				table.put(rowKey, rowData);
			}
		}
		return table;
	}

	public void setSheet(String sheetName) {
		int sheetIndex = workbook.getSheetIndex(sheetName);
		if (sheetIndex != -1) {
			currentSheet = workbook.getSheetAt(sheetIndex);
		} else {
			throw new IllegalArgumentException("Sheet " + sheetName + " not found");
		}
	}

	public int getCellRow(String cellValue, int cellColumn) {
		Iterator<Row> rowIterator = currentSheet.iterator();

		int rowIndex = -1;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell cell = row.getCell(cellColumn);
			if (cell != null && cell.getCellType() == CellType.STRING) {
				String cellText = cell.getStringCellValue();
				if (cellText.equals(cellValue)) {
					rowIndex = row.getRowNum();
					break;
				}
			}
		}
		return rowIndex;
	}

	public String getCellValue(int col, int row) {
		Row r = currentSheet.getRow(row);
		if (r == null) {
			return null;
		}
		Cell c = r.getCell(col);
		if (c == null) {
			return null;
		}
		return c.toString();
	}

	public String getCellValue(String columnName, String rowName) {
		Iterator<Row> rowIterator = currentSheet.iterator();
		Row headerRow = rowIterator.next();

		int columnIndex = -1;
		int rowIndex = -1;

		// Find column index
		Iterator<Cell> cellIterator = headerRow.iterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			String cellValue = cell.getStringCellValue();
			if (cellValue.equalsIgnoreCase(columnName)) {
				columnIndex = cell.getColumnIndex();
				break;
			}
		}

		// Find row index
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell cell = row.getCell(0);
			String cellValue = cell.getStringCellValue();
			if (cellValue.equalsIgnoreCase(rowName)) {
				rowIndex = row.getRowNum();
				break;
			}
		}

		if (columnIndex == -1 || rowIndex == -1) {
			throw new IllegalArgumentException("Cell not found");
		}

		Cell cell = currentSheet.getRow(rowIndex).getCell(columnIndex);
		return cell.getStringCellValue();
	}

	public int getColumnCount() {
		Row firstRow = currentSheet.getRow(0);
		int lastColumn = firstRow.getLastCellNum();
		int columnCount = 0;
		for (int i = 0; i < lastColumn; i++) {
			Cell cell = firstRow.getCell(i);
			if (cell != null && !cell.getStringCellValue().isEmpty()) {
				columnCount++;
			}
		}
		return columnCount;
	}

	public int getRowCount() {
		return currentSheet.getPhysicalNumberOfRows();
	}

	public int getColumnCount_inRow(int row) {
		Row r = currentSheet.getRow(row);
		return r.getPhysicalNumberOfCells();
	}

	public void close() throws IOException {
		workbook.close();
	}

	// getFirstCellNum
}
