package assessment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

public class XLUtility {

	private Workbook workbook;
	private Sheet sheet;
	private String filePath;

	public XLUtility(String filePath) throws IOException {
		this.filePath = filePath;
		FileInputStream fis = new FileInputStream(filePath);
		this.workbook = new XSSFWorkbook(fis);
		int sheetIndex=0;
		this.sheet = workbook.getSheetAt(sheetIndex);
	}

	public String getCellData(int row, int col) {
		Row excelRow = sheet.getRow(row);
		if (excelRow == null)
			return null;
		Cell cell = excelRow.getCell(col);

		if (cell == null)
			return null;

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}
	}

	public int getRowCount() {
		return sheet.getPhysicalNumberOfRows();
	}

	public void createSheet(String sheetName) {
		// Check if the sheet already exists
        if (workbook.getSheet(sheetName) == null) {
            workbook.createSheet(sheetName);
        }
	}
	
	public void writeDataToNewSheet(String sheetName, String[][] data) throws IOException {
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

	public void close() throws IOException {
		workbook.close();
	}
}
