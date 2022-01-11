package org.example.mytodolist.repository;

import org.example.mytodolist.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
