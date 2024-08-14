package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HashService hashService;

    @Autowired
    EncryptionService encryptionService;


    public int createCredential(Credential credential) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        Integer userId = userMapper.findUserById(username);
        credential.setUserId(userId);

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setEncryptionKey(encodedKey);
        credential.setPassword(encryptedPassword);

        return credentialMapper.insertCredential(credential);
    }

    public int editCredential(Credential credential) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        Integer userId = userMapper.findUserById(username);
        credential.setUserId(userId);

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setEncryptionKey(encodedKey);
        credential.setPassword(encryptedPassword);

        return credentialMapper.editCredential(credential);
    }

    public List<Credential> getCredentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        Integer userId = userMapper.findUserById(userName);


        return credentialMapper.getCredentials(userId);
    }

    public Credential getCredentialById(Integer credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    public void deleteCredentialById(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

}
