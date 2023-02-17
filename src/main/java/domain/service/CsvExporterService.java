package domain.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface CsvExporterService<T> {
    String export(List<T> content);
    String export();
}
