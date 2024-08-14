package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public String getNotes(Model model) {

        List<Note> notes = noteService.getNotes();
        model.addAttribute("note", new Note());
        model.addAttribute("notes", notes);

        return "/notes";
    }


    @PostMapping("/add")
    public String uploadNote(@ModelAttribute("note") Note note) {

        int rowUpdated = 0;

        if (note.getNoteId() == null) {
            rowUpdated = noteService.createNote(note);
        } else {
            rowUpdated = noteService.editNote(note);
        }
        if (rowUpdated > 0)
            return "redirect:/result?success";
        else {
            return "redirect:/result?error";
        }

    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId) {
        noteService.deleteNoteById(noteId);
        return "redirect:/home";
    }
}
