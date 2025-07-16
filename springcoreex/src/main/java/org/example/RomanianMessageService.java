package org.example;

import org.springframework.stereotype.Component;

@Component("romanianMessageService")
public class RomanianMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "Salut veric din RomanianMessageService!";
    }
}
