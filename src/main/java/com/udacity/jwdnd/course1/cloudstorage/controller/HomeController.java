package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private FileService fileService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialService;


    @GetMapping()
    public String getHomePage(Model model) {
        model.addAttribute("files", fileService.getFiles());
        model.addAttribute("note", new Note());
        model.addAttribute("notes", noteService.getNotes());
        model.addAttribute("credential", new Credential());
        model.addAttribute("credentials", credentialService.getCredentials());
        return "home";
    }

}
