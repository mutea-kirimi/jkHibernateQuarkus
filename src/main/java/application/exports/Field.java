package application.exports;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public sealed interface Field<T> {
    String header();
    void writeContent(Row row, int cellIndex, T element);

    record StringField<T>(
         String header,
         Function<T, String> valueGetter
    ) implements Field<T> {
        @Override
        public void writeContent(Row row, int cellIndex, T element) {
            row.createCell(cellIndex, CellType.STRING).setCellValue(valueGetter.apply(element));
        }
    }

    record DoubleField<T>(
            String header,
            Function<T, Double> valueGetter
    ) implements Field<T> {
        @Override
        public void writeContent(Row row, int cellIndex, T element) {
            row.createCell(cellIndex, CellType.NUMERIC).setCellValue(valueGetter.apply(element));
        }
    }

    record IntegerField<T>(
            String header,
            Function<T, Integer> valueGetter
    ) implements Field<T> {
        @Override
        public void writeContent(Row row, int cellIndex, T element) {
            row.createCell(cellIndex, CellType.NUMERIC).setCellValue(valueGetter.apply(element));
        }
    }

    record DateTimeField<T>(
            String header,
            Function<T, LocalDateTime> valueGetter
    ) implements Field<T> {
        @Override
        public void writeContent(Row row, int cellIndex, T element) {
            var format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            var datetime = valueGetter.apply(element).format(format);
            row.createCell(cellIndex, CellType.STRING).setCellValue(datetime);
        }
    }
}
