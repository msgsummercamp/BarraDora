package org.example;

import org.springframework.stereotype.Component;

@Component("englishMessageService")
public class EnglishMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "Hello from EnglishMessageService!";
    }
}
