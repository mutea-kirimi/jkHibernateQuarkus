package application.exports;

import domain.service.ExcelExporterService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/*This is an Abstract class to export any type of object list to excel ! */

public abstract class ExcelExporter<T> implements ExcelExporterService<T> {
    private List<ExcelField> excelFields;

    @SafeVarargs
    public ExcelExporter(ExcelField<T>... excelFields) {
        this.excelFields = Arrays.asList(excelFields);
    }

    @Override
    public ByteArrayOutputStream export(List<T> content) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try(Workbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data");

            createAndPopulateHeaderRow(sheet);
            createAndPopulateContentRows(sheet, content);

            workbook.write(outputStream);
        }
        outputStream.flush();
        return outputStream;
    }

    private void writeHeader(Row row, int cellIndex, ExcelField<T> field) {
        row.createCell(cellIndex, CellType.STRING).setCellValue(field.header());
    }

    private void createAndPopulateHeaderRow(Sheet sheet){
        Row headerRow = sheet.createRow(0);
        IntStream.range(0, excelFields.size()).forEach(cell -> writeHeader(headerRow, cell, excelFields.get(cell)));
    }

    private void createAndPopulateContentRows(Sheet sheet, List<T> content){
        IntStream.range(0, content.size()).forEach(row -> {
            Row contentRow = sheet.createRow(row + 1);
            IntStream.range(0, excelFields.size()).forEach(cell -> excelFields.get(cell).writeContent(contentRow, cell, content.get(row)));
        });
    }
}
