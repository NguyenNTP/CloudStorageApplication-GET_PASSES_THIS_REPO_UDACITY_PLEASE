package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/result")
public class ResultController {

    @GetMapping()
    String resultView(@RequestParam(required = false) String success, @RequestParam(required = false) String error, Model model) {
        if (success != null) {
            model.addAttribute("success", true);
        }
        if (error != null) {
            model.addAttribute("error", true);
        }
        return "result";
    }
}
