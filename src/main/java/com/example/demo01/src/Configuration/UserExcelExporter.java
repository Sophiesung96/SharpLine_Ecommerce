package com.example.springboot_ecommerce.Configuration;

import com.example.springboot_ecommerce.Pojo.Users;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserExcelExporter extends AbstractExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public void export(List<Users> usersList, HttpServletResponse response) throws IOException {
        super.setResponseHeader("application/octet-stream", ".xlsx", response, "users_");
        workbook = new XSSFWorkbook();
        ServletOutputStream outputStream = response.getOutputStream();
        writeHeaderLine();
        writeDataLines(usersList);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    // insert data into a table, which stored in an Excel file
    private void writeDataLines(List<Users> usersList) {
        int rowindex = 1;
        for (Users user : usersList) {
            XSSFRow row = sheet.createRow(rowindex++);
            int columnindex = 0;
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(14);
            style.setFont(font);
            createRowCell(row, columnindex++, user.getId(), style);
            createRowCell(row, columnindex++, user.getEmail(), style);
            createRowCell(row, columnindex++, user.getFirst_name(), style);
            createRowCell(row, columnindex++, user.getLast_name(), style);
            createRowCell(row, columnindex++, user.getUsersRole(), style);
            createRowCell(row, columnindex++, user.getEnabledStatus(), style);

        }

    }

    //create the table header
    public void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFRow row = sheet.createRow(0);
        //font configuration
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createRowCell(row, 0, "User ID", style);
        createRowCell(row, 1, "E-mail", style);
        createRowCell(row, 2, "First Name", style);
        createRowCell(row, 3, "Last Name", style);
        createRowCell(row, 4, "Role", style);
        createRowCell(row, 5, "EabledStatus", style);


    }

    //create following row in the table
    public void createRowCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);

    }
}
