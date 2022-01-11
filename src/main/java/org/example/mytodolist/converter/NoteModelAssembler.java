package org.example.mytodolist.converter;

import org.example.mytodolist.controller.NoteController;
import org.example.mytodolist.entity.Note;
import org.example.mytodolist.enums.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NoteModelAssembler implements RepresentationModelAssembler<Note, EntityModel<Note>> {

    @Override
    public EntityModel<Note> toModel(Note entity) {

        EntityModel<Note> noteModel = EntityModel.of(entity,
                linkTo(methodOn(NoteController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(NoteController.class).all()).withRel("notes"));

        if (entity.getStatus() == Status.CREATED) {
            noteModel.add(linkTo(methodOn(NoteController.class).cancel(entity.getId())).withRel("cancel"));
            noteModel.add(linkTo(methodOn(NoteController.class).complete(entity.getId())).withRel("complete"));
        }

        return noteModel;
    }
}
