package application.exports;

import domain.service.CsvExporterService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class CsvExporter<T> implements CsvExporterService<T> {
    private List<CsvField<T>> csvFields;
    private final StringBuilder stringBuilder;


    @SafeVarargs
    public CsvExporter(CsvField<T>... csvFields) {
        this.csvFields = Arrays.asList(csvFields);
        this.stringBuilder = new StringBuilder();
    }

    public void appendHeaderRow() {
        var csvHeaderRow = csvFields.stream()
                .map(f -> f.header())
                .collect(Collectors.joining(";"));
        stringBuilder.append(csvHeaderRow);
        stringBuilder.append("\n");
    }

    public void appendContentRow(T element){
        IntStream.range(0, csvFields.size()).forEach(i -> {
            stringBuilder.append(csvFields.get(i).valueGetter().apply(element));
            stringBuilder.append(";");
        });
        stringBuilder.append("\n");
    }

    public void appendAllContentRows(List<T> elements){
        elements.forEach(e -> appendContentRow(e));
    }

    public String export(List<T> elements){
        appendHeaderRow();
        appendAllContentRows(elements);
        return stringBuilder.toString();
    }
}
