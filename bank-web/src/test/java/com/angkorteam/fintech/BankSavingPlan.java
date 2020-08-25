package com.angkorteam.fintech;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class BankSavingPlan {

    private static Map<Integer, Double> USD_RATE = new HashMap<>();
    private static Map<Integer, Double> KHR_RATE = new HashMap<>();

    public static void main(String[] args) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 1220325.32725 USD
        // 4881301309 KHR
        double monthlySaving = 2000d;
        int months = (12 * 20) + 1;

        List<Integer> terms = null;
        terms = Arrays.asList(48, 36, 30, 24, 18, 12, 9, 6, 3, 1);
//        terms = Arrays.asList(24, 12, 9, 6, 3, 1);

        for (int term : terms) {
            m(workbook, terms, term, months, monthlySaving, KHR_RATE);
        }

        try (FileOutputStream outputStream = new FileOutputStream("armet-term-deposit.xlsx")) {
            workbook.write(outputStream);
        }
    }

    static {

        USD_RATE.put(1, 3.50d);
        USD_RATE.put(3, 4.50d);
        USD_RATE.put(6, 5.50d);
        USD_RATE.put(9, 6.00d);
        USD_RATE.put(12, 7.00d);
        USD_RATE.put(18, 7.00d);
        USD_RATE.put(24, 7.25d);
        USD_RATE.put(30, 7.25d);
        USD_RATE.put(36, 7.25d);
        USD_RATE.put(48, 7.25d);

        KHR_RATE.put(1, 3.50d);
        KHR_RATE.put(3, 4.00d);
        KHR_RATE.put(6, 5.00d);
        KHR_RATE.put(9, 5.50d);
        KHR_RATE.put(12, 7.25d);
        KHR_RATE.put(18, 7.20d);
        KHR_RATE.put(24, 8.25d);
        KHR_RATE.put(30, 8.25d);
        KHR_RATE.put(36, 8.25d);
        KHR_RATE.put(48, 10.00d);
    }

    protected static void m(XSSFWorkbook workbook, List<Integer> terms, int term, int months, double monthlySaving, Map<Integer, Double> rates) {
        Map<String, Cell> cellCache = new HashMap<>();
        Map<Integer, Row> rowCache = new HashMap<>();
        DecimalFormat format = new DecimalFormat("000");
        XSSFSheet sheet = workbook.createSheet(term + " Month");
        lookup(2, 1, cellCache, rowCache, sheet).setCellValue("Rate");
        lookup(2, 2, cellCache, rowCache, sheet).setCellValue("Principal");
        lookup(2, 3, cellCache, rowCache, sheet).setCellValue("Term");
        lookup(2, 4, cellCache, rowCache, sheet).setCellValue("Month");
        List<String> monthPrinciples = new ArrayList<>();
        for (int column = 1 + 2; column <= months + 2; column++) {
            Cell monthPrinciple = lookup(column, 2, cellCache, rowCache, sheet);
            monthPrinciple.setCellValue(monthlySaving);
            monthPrinciples.add(monthPrinciple.getAddress().formatAsString());
            lookup(column, 4, cellCache, rowCache, sheet).setCellValue(column - 2);
        }
        int skip = 4;
        for (int row = 1; row <= months; row++) {
            Cell cell = lookup(2, row + skip, cellCache, rowCache, sheet);
            cell.setCellValue(row);
        }
        int columnTerm = term;
        Map<String, Cell> maturityCells = new HashMap<>();
        Cell lastMaturity = null;
        for (int month = 1; month <= months; month++) {
            Cell rateCellObject = lookup(month + 2, 1, cellCache, rowCache, sheet);
            Cell principalCellObject = lookup(month + 2, 2, cellCache, rowCache, sheet);
            Cell termCellObject = lookup(month + 2, 3, cellCache, rowCache, sheet);
            Cell investCellObject = lookup(month + 2, month + skip, cellCache, rowCache, sheet);
            if (month + columnTerm > months) {
                for (int temp : terms) {
                    if (temp >= columnTerm) {
                        continue;
                    } else {
                        columnTerm = temp;
                        break;
                    }
                }
            }
            lookup(month + 2, 1, cellCache, rowCache, sheet).setCellValue(rates.get(columnTerm));
            lookup(month + 2, 3, cellCache, rowCache, sheet).setCellValue(columnTerm);

            for (int k = 1; k < columnTerm; k++) {
                Cell cellObject = lookup(month + 2, month + skip + k, cellCache, rowCache, sheet);
                cellObject.setCellValue(0d);
            }
            Cell maturityCellObject = lookup(month + 2, month + skip + columnTerm, cellCache, rowCache, sheet);
            if (month <= columnTerm) {
                investCellObject.setCellFormula(principalCellObject.getAddress().formatAsString());
                maturityCellObject.setCellFormula(String.format("(((%s / 100 / 12) * %s) * %s) + %s", rateCellObject.getAddress().formatAsString(), termCellObject.getAddress().formatAsString(), investCellObject.getAddress().formatAsString(), investCellObject.getAddress().formatAsString()));
            } else {
                List<String> sums = new ArrayList<>();
                sums.add(principalCellObject.getAddress().formatAsString());
                for (Map.Entry<String, Cell> cell : maturityCells.entrySet()) {
                    if (cell.getKey().contains("." + format.format(month + skip))) {
                        sums.add(cell.getValue().getAddress().formatAsString());
                    }
                }
                investCellObject.setCellFormula(StringUtils.join(sums, " + "));
                maturityCellObject.setCellFormula(String.format("(((%s / 100 / 12) * %s) * %s) + %s", rateCellObject.getAddress().formatAsString(), termCellObject.getAddress().formatAsString(), investCellObject.getAddress().formatAsString(), investCellObject.getAddress().formatAsString()));
            }
            maturityCells.put(format.format(month + 2) + "." + format.format(month + skip + columnTerm), maturityCellObject);
            lastMaturity = maturityCellObject;
        }
        lookup(1, 1, cellCache, rowCache, sheet).setCellValue("Total Principal");
        lookup(1, 2, cellCache, rowCache, sheet).setCellFormula("SUM(" + StringUtils.join(monthPrinciples, ",") + ")");
        lookup(1, 3, cellCache, rowCache, sheet).setCellValue("Total Interest");
        lookup(1, 4, cellCache, rowCache, sheet).setCellFormula(lastMaturity.getAddress().formatAsString() + " - " + lookup(1, 2, cellCache, rowCache, sheet).getAddress().formatAsString());
        lookup(1, 5, cellCache, rowCache, sheet).setCellValue("Total");
        lookup(1, 6, cellCache, rowCache, sheet).setCellFormula(lastMaturity.getAddress().formatAsString());
    }

    protected static Cell lookup(int column, int row, Map<String, Cell> cellCache, Map<Integer, Row> rowCache, Sheet sheet) {
        Row rowObject = null;
        if (rowCache.containsKey(row)) {
            rowObject = rowCache.get(row);
        } else {
            rowObject = sheet.createRow(row - 1);
            rowCache.put(row, rowObject);
        }
        Cell cellObject = null;
        if (cellCache.containsKey((column - 1) + "." + row)) {
            cellObject = cellCache.get((column - 1) + "." + row);
        } else {
            cellObject = rowObject.createCell(column - 1);
            cellCache.put((column - 1) + "." + row, cellObject);
        }
        return cellObject;
    }

    protected static XSSFSheet summaryYear(int yearOfSaving, double interestPerYear, Map<Integer, String> months, XSSFWorkbook workbook, String name) {
        XSSFSheet sheet = workbook.createSheet(name);
        for (int j = 1; j <= 60; j++) {
            Row row = sheet.createRow(j);
            for (int i = 1; i <= 12; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(months.get(i));
            }
        }
        return sheet;
    }

    protected static XSSFSheet totalSheet1(Map<Integer, String> months, XSSFWorkbook workbook) {
        LocalDate date = LocalDate.now();
        XSSFSheet sheet = workbook.createSheet(months.get(date.getMonth().getValue()) + "Total1");

        Object[][] bookData = {
                {"Head First Java", "Kathy Serria", 79},
                {"Effective Java", "Joshua Bloch", 36},
                {"Clean Code", "Robert martin", 42},
                {"Thinking in Java", "Bruce Eckel", 35},
        };

        int rowCount = 0;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }
        return sheet;
    }

    protected static XSSFSheet totalSheet(Map<Integer, String> months, XSSFWorkbook workbook) {
        LocalDate date = LocalDate.now();
        String refName = months.get(date.getMonth().getValue()) + "Total1";

        XSSFSheet sheet = workbook.createSheet("Total");

        Object[][] bookData = {
                {"Head First Java", "Kathy Serria", 79},
                {"Effective Java", "Joshua Bloch", 36},
                {"Clean Code", "Robert martin", 42},
                {"Thinking in Java", "Bruce Eckel", 35},
        };

        int rowCount = 0;

        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);

            int columnCount = 0;

            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                    cell.setCellFormula(refName + "!C5");
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }
        return sheet;
    }

}
