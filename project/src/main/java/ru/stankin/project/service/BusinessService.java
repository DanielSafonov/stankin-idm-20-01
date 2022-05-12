package ru.stankin.project.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stankin.project.persistence.entity.Entity;
import ru.stankin.project.persistence.repository.Repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class BusinessService {
    @Autowired
    private Repository repository;

    public List<Entity> getTablePage(int pageNum) {
        return repository.findAll(pageNum);
    }

    public ByteArrayInputStream exportTable() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Cars");
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 9000);
        sheet.setColumnWidth(3, 3000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontName("Tahoma");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("model");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("details");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("launch year");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        int i = 1;
        for (Entity e : repository.findAll()) {
            Row row = sheet.createRow(i++);

            Cell cell1 = row.createCell(0);
            cell1.setCellStyle(style);
            cell1.setCellValue(e.getId());

            Cell cell2 = row.createCell(1);
            cell2.setCellStyle(style);
            cell2.setCellValue(e.getModel());

            Cell cell3 = row.createCell(2);
            cell3.setCellStyle(style);
            cell3.setCellValue(e.getDetails());

            Cell cell4 = row.createCell(3);
            cell4.setCellStyle(style);
            cell4.setCellValue(e.getYear());
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
