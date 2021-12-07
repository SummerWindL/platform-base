package com.platform.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoiUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(PoiUtils.class);

    public static List<List<String>> readXlsx(String path) {
        Workbook wb = null;
        List<List<String>> list = new ArrayList<>();
        try {
            wb = new XSSFWorkbook(new FileInputStream(path));
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                System.out.println(wb.getSheetName(i));
                for (Row row : sheet) {
                    List<String> rowCellList = new ArrayList<>();
                    // System.out.println("rownum: " + row.getRowNum());
                    for (Cell cell : row) {
                        // System.out.println(cell.toString());
                        rowCellList.add(cell.toString());
                    }
                    list.add(rowCellList);
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static void writeXLSX(List<List<String>> list, String path) {
        try {
            if (list != null) {
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("resultSheet");
                Row row;
                Cell cell;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    row = sheet.createRow(i);
                    List<String> cellValueList = list.get(i);
                    if (cellValueList != null) {
                        for (int j = 0; j < cellValueList.size(); j++) {
                            cell = row.createCell(j);
                            cell.setCellValue(cellValueList.get(j));
                        }
                    }
                }
                PoiUtils.writeExcel(wb, path);
                System.out.println("================= write excel ok =====================");
            } else {
                return;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public static Object getValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        if (CellType.STRING.equals(cellType)) {
            return cell.getStringCellValue();
        } else if (CellType.NUMERIC.equals(cellType)) {
            return cell.getNumericCellValue();
        } else if (CellType.BOOLEAN.equals(cellType)) {
            return cell.getBooleanCellValue();
        } else if (CellType.ERROR.equals(cellType)) {
            return cell.getErrorCellValue();
        } else {
            return cell.toString();
        }
    }

    public static String getStringValue(Cell cell) {
        String value = null;
        if (cell != null) {
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            value = cell.toString();
            value = value.trim();
        }
        return value;
    }

    public static void writeExcel(Workbook wb, String path) {
        try {
            File file = new File(path);
            writeExcel(wb, file);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public static void writeExcel(Workbook wb, File file) {
        FileOutputStream fileOut = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fileOut = new FileOutputStream(file);
            wb.write(fileOut);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static String writeMapAsXLSX(List<Map<String, String>> list, String path) {
        try {
            if (list != null) {
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("resultSheet");
                Row row;
                Cell cell;
                int size = list.size();
                int cellMaxLen = 32000;
                CellStyle backgroundRed = wb.createCellStyle();

                backgroundRed.setFillBackgroundColor(IndexedColors.CORAL.getIndex());
                backgroundRed.setFillForegroundColor(IndexedColors.CORAL.getIndex());
                backgroundRed.setFillPattern(XSSFCellStyle.FINE_DOTS);
                for (int i = 0; i < size; i++) {
                    Map<String, String> cellRow = list.get(i);
                    if (cellRow != null) {
                        row = sheet.createRow(i);
                        int index = 0;
                        for (String key : cellRow.keySet()) {
                            String value = cellRow.get(key);
                            cell = row.createCell(index++);
                            if (StringUtils.isNotBlank(value) && value.length() > 30000) {
                                value = value.substring(0, 30000);
                            }
                            cell.setCellValue(value);
                        }
                    }
                }
                PoiUtils.writeExcel(wb, path);
                LOGGER.info("================= write excel ok =====================");
                LOGGER.info("================= write excel path | " + path);
                return path;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String fileName = "F:/writefile/check_version2.xlsx";
        List<List<String>> list = new ArrayList<>();
        List<String> cellValueList = new ArrayList<>();
        cellValueList.add("asdfasd");
        cellValueList.add("sadfwe");
        list.add(cellValueList);
        cellValueList = new ArrayList<>();
        cellValueList.add("rtywerty");
        cellValueList.add("rytjry");
        list.add(cellValueList);
        PoiUtils.writeXLSX(list, fileName);
        ;
    }

}
