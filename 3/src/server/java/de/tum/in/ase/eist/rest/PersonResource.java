package de.tum.in.ase.eist.rest;

import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.service.PersonService;
import de.tum.in.ase.eist.util.PersonSortingOptions;
import de.tum.in.ase.eist.util.PersonSortingOptions.*;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
public class PersonResource {

    private final PersonService personService;

    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    // TODO Part 1: Implement the specified endpoints here
    @GetMapping("persons")
    public ResponseEntity<List<Person>> getAllPersons(
            @RequestParam(name = "sortField", defaultValue = "ID") SortField SF,
            @RequestParam(name = "sortingOrder", defaultValue = "ASCENDING") SortingOrder SO) {
        PersonSortingOptions PC = new PersonSortingOptions();
        PC.setSortField(SF);
        PC.setSortingOrder(SO);
        return ResponseEntity.ok(personService.getAllPersons(PC));
    }

    @PostMapping("persons")
    public ResponseEntity<Person> createPerson(@RequestBody Person note) {
        if (note.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personService.savePerson(note));
    }

    @PutMapping("persons/{personId}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person updatedNote,
            @PathVariable("personId") UUID personId) {
        if (!updatedNote.getId().equals(personId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personService.savePerson(updatedNote));
    }

    @DeleteMapping("persons/{personId}")
    public ResponseEntity<Void> deletePerson(@PathVariable("personId") UUID personId) {
        personService.deletePerson(personId);
        return ResponseEntity.noContent().build();
    }
}
