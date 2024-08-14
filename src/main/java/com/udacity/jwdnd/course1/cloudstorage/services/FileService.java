package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private UserMapper userMapper;


    public boolean isFileExist(String fileName, Integer userId) {
        return fileMapper.checkDuplicateFile(fileName, userId) != null;
    }

    public void uploadFile(MultipartFile file) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        Integer userId = userMapper.findUserById(userName);


        File newFile = new File();
        newFile.setFileName(file.getOriginalFilename());
        newFile.setContentType(file.getContentType());
        newFile.setFileSize(String.valueOf(file.getSize()));
        newFile.setFileData(file.getBytes());
        newFile.setUserId(userId);

        fileMapper.insertFile(newFile);
    }

    public List<File> getFiles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        Integer userId = userMapper.findUserById(userName);

        return fileMapper.getFiles(userId);
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public void deleteFileById(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }
}
