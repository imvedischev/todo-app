package org.example.mytodolist.entity;

import lombok.Data;
import org.example.mytodolist.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "note_list")
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;
}