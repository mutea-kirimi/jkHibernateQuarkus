package domain.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ExcelExporterService<T> {
    ByteArrayOutputStream export(List<T> content) throws IOException;
    ByteArrayOutputStream export() throws IOException;
}
