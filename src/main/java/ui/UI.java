package ui;

import jakarta.mail.MessagingException;

import java.io.IOException;

@FunctionalInterface
public interface UI {
    void start() throws MessagingException, IOException;
}
