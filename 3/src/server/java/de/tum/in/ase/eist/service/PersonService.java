package de.tum.in.ase.eist.service;

import de.tum.in.ase.eist.model.Person;
import de.tum.in.ase.eist.util.PersonSortingOptions;
import de.tum.in.ase.eist.util.PersonSortingOptions.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Collections;
@Service
public class PersonService {
  	// do not change this
    private final List<Person> persons;

    public PersonService() {
        this.persons = new ArrayList<>();
    }

    public Person savePerson(Person person) {
        var optionalPerson = persons.stream().filter(existingPerson -> existingPerson.getId().equals(person.getId())).findFirst();
        if (optionalPerson.isEmpty()) {
            person.setId(UUID.randomUUID());
            persons.add(person);
            return person;
        } else {
            var existingPerson = optionalPerson.get();
            existingPerson.setFirstName(person.getFirstName());
            existingPerson.setLastName(person.getLastName());
            existingPerson.setBirthday(person.getBirthday());
            return existingPerson;
        }
    }

    public void deletePerson(UUID personId) {
        this.persons.removeIf(person -> person.getId().equals(personId));
    }

    public List<Person> getAllPersons(PersonSortingOptions sortingOptions) {
        // TODO Part 3: Add sorting here
        SortField sf=sortingOptions.getSortField();
        SortingOrder SO=sortingOptions.getSortingOrder();
        List<Person> PP=new ArrayList<>(this.persons);
        if(sf==SortField.ID){
            Collections.sort(PP,new PC_ID_AC());
        }
        else if(sf==SortField.FIRST_NAME){
            Collections.sort(PP,new PC_FN_AC());
        }
        else if(sf==SortField.LAST_NAME){
            Collections.sort(PP,new PC_LN_AC());
        }else{
            Collections.sort(PP,new PC_BD_AC());
        }
        if(SO==SortingOrder.DESCENDING){
            Collections.reverse(PP);
        }
        return PP;
    }
}
class PC_ID_AC implements java.util.Comparator<Person> {
    @Override
    public int compare(Person a, Person b) {
        return a.getId().compareTo(b.getId());
    }
}
class PC_FN_AC implements java.util.Comparator<Person> {
    @Override
    public int compare(Person a, Person b) {
        return a.getFirstName().compareTo(b.getFirstName());
    }
}
class PC_LN_AC implements java.util.Comparator<Person> {
    @Override
    public int compare(Person a, Person b) {
        return a.getLastName().compareTo(b.getLastName());
    }
}
class PC_BD_AC implements java.util.Comparator<Person> {
    @Override
    public int compare(Person a, Person b) {
        return a.getBirthday().compareTo(b.getBirthday());
    }
}