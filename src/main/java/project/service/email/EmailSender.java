package project.service.email;

import java.util.Map;

public interface EmailSender {
    void send(String from, String to, String template, Map<String, Object> variables, String subject);
}
