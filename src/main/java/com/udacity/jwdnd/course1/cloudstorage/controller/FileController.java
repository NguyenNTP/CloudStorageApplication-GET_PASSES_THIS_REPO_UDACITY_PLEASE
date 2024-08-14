package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public String getFiles(Model model) {
        List<File> files = fileService.getFiles();

        model.addAttribute("files", files);
        return "/home";
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        Integer userId = userMapper.findUserById(userName);

        if (file == null || file.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "No file selected for upload.");
            return "redirect:/home";
        }

        if (file.getSize() > 10485760) {
            redirectAttributes.addFlashAttribute("errorMessage", " The maximum limit of file size is 10MB.");
            return "redirect:/home";
        }


        if (fileService.isFileExist(file.getOriginalFilename(), userId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "File already exists.");
            return "redirect:/home";
        }

        try {
            fileService.uploadFile(file);
            redirectAttributes.addFlashAttribute("successMessage", "File uploaded successfully.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while uploading the file.");
        }

        return "redirect:/home";
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<byte[]> viewFile(@PathVariable Integer fileId) {
        File file = fileService.getFileById(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(file.getFileData());
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId) {
        fileService.deleteFileById(fileId);
        return "redirect:/home";
    }

}
