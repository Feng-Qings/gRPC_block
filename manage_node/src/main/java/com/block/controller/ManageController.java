package com.block.controller;

import com.block.service.ManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManageController {
    private final ManageService manageService;

    @GetMapping("/startTrain")
    public void startTrain(){
        manageService.startTrain();
    }
}
