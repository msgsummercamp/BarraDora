package org.example;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("romanianMessageService")
public class RomanianMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "Salut veric din RomanianMessageService!";
    }
}
