package logger;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class FileHandlers extends StreamHandler {
    private final File file = new File("fileHandler.txt");

    @SneakyThrows
    @Override
    public synchronized void publish(LogRecord record) {
        if (!file.exists()) {
            file.createNewFile();
        }
        MyFormatter myFormatter = new MyFormatter();
        String format = myFormatter.format(record) + "\n";
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(format);
        fileWriter.close();
    }
}
