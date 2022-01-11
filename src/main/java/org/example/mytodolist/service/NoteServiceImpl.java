package org.example.mytodolist.service;

import lombok.AllArgsConstructor;
import org.example.mytodolist.controller.NoteController;
import org.example.mytodolist.converter.NoteModelAssembler;
import org.example.mytodolist.entity.Note;
import org.example.mytodolist.enums.Status;
import org.example.mytodolist.exception.NoteNotFoundException;
import org.example.mytodolist.repository.NoteRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteModelAssembler noteModelAssembler;

    @Override
    public CollectionModel<EntityModel<Note>> all() {
        List<EntityModel<Note>> notes = noteRepository.findAll().stream()
                .map(noteModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(notes,
                linkTo(methodOn(NoteController.class).all()).withSelfRel());
    }

    @Override
    public ResponseEntity<EntityModel<Note>> newNote(Note note) {
        note.setStatus(Status.CREATED);
        Note newNote = noteRepository.save(note);

        return ResponseEntity
                .created(linkTo(methodOn(NoteController.class).one(newNote.getId())).toUri())
                .body(noteModelAssembler.toModel(newNote));
    }

    @Override
    public EntityModel<Note> one(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        return noteModelAssembler.toModel(note);
    }

    @Override
    public ResponseEntity<?> cancel(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if (note.getStatus() == Status.CREATED) {
            note.setStatus(Status.CANCELED);
            return ResponseEntity.ok(noteModelAssembler.toModel(noteRepository.save(note)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                .withTitle("Method not allowed")
                .withDetail("You can't cancel an note that is in the " + note.getStatus() + " status"));
    }

    @Override
    public ResponseEntity<?> complete(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        if (note.getStatus() == Status.CREATED) {
            note.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(noteModelAssembler.toModel(noteRepository.save(note)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                .withTitle("Method not allowed")
                .withDetail("You can't complete an note that is in the " + note.getStatus() + " status"));
    }

    @Override
    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
        noteRepository.delete(note);
    }
}
