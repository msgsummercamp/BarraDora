package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HelloBean {

    private final MessageService messageService;

    @Autowired
    public HelloBean(@Qualifier("romanianMessageService") MessageService messageService) {
        this.messageService = messageService;
    }

    public void sayHello() {
        System.out.println(messageService.getMessage());
    }
}
