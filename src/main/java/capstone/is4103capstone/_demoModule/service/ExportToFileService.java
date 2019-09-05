package capstone.is4103capstone._demoModule.service;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
@Service
public class ExportToFileService {
    public ByteArrayInputStream exportToExcel(String filename){
        String[] COLUMNs = {"Id", "Name", "Password", "Age"};
        try(
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Customers");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // CellStyle for Age
            CellStyle ageCellStyle = workbook.createCellStyle();
            ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

            int rowIdx = 1;

            for (int i=0;i<4;i++) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue("test"+i);
                row.createCell(1).setCellValue("name"+i);
                row.createCell(2).setCellValue("password"+i);

                Cell ageCell = row.createCell(3);
                ageCell.setCellValue("age"+i);
                ageCell.setCellStyle(ageCellStyle);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

    }
}
