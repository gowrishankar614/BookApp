package assessment;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class for reading and writing Excel files using Apache POI
 */
public class XLUtility {

	private Workbook workbook;
	private Sheet sheet;
	private String filePath;

	/**
	 * Initialize XLUtility with Excel file path
	 * @param filePath Path to the Excel file
	 * @throws IOException If file cannot be read
	 */
	public XLUtility(String filePath) throws IOException {
		this.filePath = filePath;
		FileInputStream fis = new FileInputStream(filePath);
		this.workbook = new XSSFWorkbook(fis);
		int sheetIndex = 0;
		this.sheet = workbook.getSheetAt(sheetIndex);
	}

	/**
	 * Get cell data as String
	 * @param row Row index
	 * @param col Column index
	 * @return Cell value as String
	 */
	public String getCellData(int row, int col) {
		Row excelRow = sheet.getRow(row);
		if (excelRow == null) {
			return null;
		}
		Cell cell = excelRow.getCell(col);

		if (cell == null) {
			return null;
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf((int) cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}
	}

	/**
	 * Get total number of rows in the sheet
	 * @return Number of physical rows
	 */
	public int getRowCount() {
		return sheet.getPhysicalNumberOfRows();
	}

	/**
	 * Get total number of columns in a row
	 * @param rowIndex Row index
	 * @return Number of columns
	 */
	public int getColumnCount(int rowIndex) {
		Row row = sheet.getRow(rowIndex);
		return row != null ? row.getLastCellNum() : 0;
	}

	/**
	 * Create a new sheet if it doesn't already exist
	 * @param sheetName Name of the sheet to create
	 */
	public void createSheet(String sheetName) {
		if (workbook.getSheet(sheetName) == null) {
			workbook.createSheet(sheetName);
		}
	}

	/**
	 * Write data to a new sheet (removes existing sheet with same name)
	 * @param sheetName Name of the sheet
	 * @param data Data array to write (2D array)
	 * @throws IOException If writing to file fails
	 */
	public void writeDataToNewSheet(String sheetName, String[][] data) throws IOException {
		// Remove sheet if it already exists
		int existingSheetIndex = workbook.getSheetIndex(sheetName);
		if (existingSheetIndex != -1) {
			workbook.removeSheetAt(existingSheetIndex);
		}

		Sheet sheet = workbook.createSheet(sheetName);

		for (int i = 0; i < data.length; i++) {
			Row row = sheet.createRow(i);
			for (int j = 0; j < data[i].length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(data[i][j]);
			}
		}

		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			workbook.write(fos);
		}
	}

	/**
	 * Write value to a specific cell
	 * @param sheetIndex Sheet index
	 * @param rowIndex Row index
	 * @param colIndex Column index
	 * @param value Value to write
	 * @throws IOException If writing fails
	 */
	public void setCellData(int sheetIndex, int rowIndex, int colIndex, String value) throws IOException {
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		Row row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		Cell cell = row.createCell(colIndex);
		cell.setCellValue(value);

		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			workbook.write(fos);
		}
	}

	/**
	 * Save the workbook to file (deprecated, use close() instead)
	 */
	@Deprecated(forRemoval = true, since = "2.0")
	public void save() {
		try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Close the workbook and release resources
	 * @throws IOException If closing fails
	 */
	public void close() throws IOException {
		if (workbook != null) {
			workbook.close();
		}
	}
}
