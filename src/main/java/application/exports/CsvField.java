package application.exports;

import java.util.function.Function;

public record CsvField<T> (
        String header,
        Function<T, String> valueGetter
){}
