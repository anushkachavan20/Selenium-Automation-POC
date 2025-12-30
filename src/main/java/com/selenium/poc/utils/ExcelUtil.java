package com.selenium.poc.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    public static Object[][] getExcelData(String filePath, String sheetName) {

        List<Object[]> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int lastRow = sheet.getLastRowNum();

            for (int i = 1; i <= lastRow; i++) { // skip header
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String username = getCellValue(row.getCell(0));
                String password = getCellValue(row.getCell(1));
                String expected = getCellValue(row.getCell(2));

                // 🚨 Skip blank rows
                if (username.isEmpty() && password.isEmpty()) {
                    continue;
                }

                data.add(new Object[]{username, password, expected});
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel file", e);
        }

        return data.toArray(new Object[0][0]);
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }
}
