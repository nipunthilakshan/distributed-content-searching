package com.example.formicarymrt.client.controllers;

import com.example.formicarymrt.client.ApplicationState;
import com.example.formicarymrt.client.CommandProcessor;
import com.example.formicarymrt.client.models.SearchForm;
import com.example.formicarymrt.client.models.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private final ApplicationState applicationState;
    private final CommandProcessor commandProcessor;

    public IndexController(ApplicationState applicationState, CommandProcessor commandProcessor) {
        this.applicationState = applicationState;
        this.commandProcessor = commandProcessor;
    }

    @GetMapping("/")
    public String index(Model model) {
        Status status = new Status();
        resetModel(status);
        model.addAttribute("status", status);

        return "index";
    }

    @PostMapping("/")
    public String indexSubmit(@ModelAttribute Status status) {
        LOGGER.info("Searching for {}. . .", status.getSearchForm().getFileName());

        commandProcessor.search(status.getSearchForm().getFileName());

        resetModel(status);
        return "index";
    }

    private void resetModel(Status status) {
        status.setNeighbours(applicationState.getNeighbours());
        status.setOngoingRequests(new ArrayList<>(applicationState.getOngoingRequests().asMap().values()));
        status.setSearchHits(applicationState.getSearchHits());
        status.setSearchForm(new SearchForm());
    }
}
