package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private EncryptionService encryptionService;

    @GetMapping
    public String getCredentials(Model model) {

        List<Credential> credentials = credentialService.getCredentials();
        model.addAttribute("credential", new Credential());
        model.addAttribute("credentials", credentials);

        return "/credentials";
    }


    @PostMapping("/add")
    public String uploadCredential(@ModelAttribute("credential") Credential credential) {

        int rowUpdated = 0;

        if (credential.getCredentialId() == null) {
            rowUpdated = credentialService.createCredential(credential);
        } else {
            rowUpdated = credentialService.editCredential(credential);
        }
        if (rowUpdated > 0)
            return "redirect:/result?success";
        else {
            return "redirect:/result?error";
        }

    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId) {
        credentialService.deleteCredentialById(credentialId);
        return "redirect:/home";
    }

    @GetMapping("/decrypt/{credentialId}")
    @ResponseBody
    public String decryptPassword(@PathVariable Integer credentialId) {
        Credential credential = credentialService.getCredentialById(credentialId);
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getEncryptionKey());
        return decryptedPassword;
    }

}
