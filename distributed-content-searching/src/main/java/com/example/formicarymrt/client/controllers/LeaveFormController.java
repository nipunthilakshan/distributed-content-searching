package com.example.formicarymrt.client.controllers;

import com.example.formicarymrt.client.CommandProcessor;
import com.example.formicarymrt.client.models.Status;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LeaveFormController {

    private CommandProcessor commandProcessor;

    public LeaveFormController(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }


    @PostMapping("/leave")
    public String indexSubmit(@ModelAttribute Status status) {

        commandProcessor.unregister();
        return "leave";
    }
}
