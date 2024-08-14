package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserMapper userMapper;

    public int createNote(Note note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        Integer userId = userMapper.findUserById(userName);
        
        note.setUserId(userId);

        return noteMapper.insertNote(note);
    }

    public int editNote(Note note) {
        return noteMapper.editNote(note);
    }

    public List<Note> getNotes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();
        Integer userId = userMapper.findUserById(userName);

        return noteMapper.getNotes(userId);
    }

    public void deleteNoteById(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
