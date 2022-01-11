package org.example.mytodolist.controller;

import lombok.AllArgsConstructor;
import org.example.mytodolist.entity.Note;
import org.example.mytodolist.service.NoteService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/notes")
    public CollectionModel<EntityModel<Note>> all() {
        return noteService.all();
    }

    @GetMapping("/notes/{id}")
    public EntityModel<Note> one(@PathVariable Long id) {
        return noteService.one(id);
    }

    @PostMapping("/notes")
    public ResponseEntity<EntityModel<Note>> newNote(@RequestBody Note note) {
        return noteService.newNote(note);
    }

    @DeleteMapping("/notes/{id}")
    public CollectionModel<EntityModel<Note>> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return this.all();
    }

    @DeleteMapping("/notes/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        return noteService.cancel(id);
    }

    @PutMapping("/notes/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
        return noteService.complete(id);
    }
}
