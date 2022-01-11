package org.example.mytodolist.service;

import org.example.mytodolist.entity.Note;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface NoteService {

    CollectionModel<EntityModel<Note>> all();
    ResponseEntity<EntityModel<Note>> newNote(Note note);
    EntityModel<Note> one(Long id);
    ResponseEntity<?> cancel(Long id);
    ResponseEntity<?> complete(Long id);
    void deleteNote(Long id);
}
