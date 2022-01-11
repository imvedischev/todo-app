package org.example.mytodolist.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(Long id) {
        super("Could not fine note " + id);
    }
}
