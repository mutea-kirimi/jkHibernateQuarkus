package application;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
public class LogService {

    private static final Logger LOG = LoggerFactory.getLogger(LogService.class);

    @ConfigProperty(name = "quarkus.log.file.path")
    String logFilePath;

    public byte[] getLogFileContent() {
        try {
            return Files.readAllBytes(Path.of(logFilePath));
        } catch (IOException e) {
            LOG.error("exception reading contents from log file {}", logFilePath, e);
            return new byte[0];
        }
    }
}
