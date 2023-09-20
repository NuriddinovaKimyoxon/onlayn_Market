package logger;

import java.time.LocalDateTime;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        int second = LocalDateTime.now().getSecond();
        int minute = LocalDateTime.now().getMinute();
        int hour = LocalDateTime.now().getHour();

        String time = hour + ":" + minute + ":" + second;
        return record.getLevel().getName() + ": " + record.getSourceClassName()
                + " -> " + record.getSourceMethodName() + " {" + record.getMessage() + "} -: " + time;
    }
}
