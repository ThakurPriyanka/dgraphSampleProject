package com.knoldus.dgraph;

import com.knoldus.dgraph.doa.DAOOperation;
import com.knoldus.dgraph.module.People;
import com.knoldus.dgraph.module.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DgraphSampleMain {

    private static DAOOperation daoOperation = new DAOOperation();

    public static void main(final String[] args) {

        // Initialize
        final String name = "sam";
        if (daoOperation.alterSchema()) {
            System.out.println("Schema altered.");
        }
        // Create data
        List<String> hobbies = new ArrayList();
        hobbies.add("Cooking");
        hobbies.add("Drawning");
        Person newPerson = Person.builder().name(name).age(23).hobbies(hobbies).build();
        if (daoOperation.addNode(newPerson)) {
            System.out.println("Transaction complete");
        }

        People people = daoOperation.getPeople(name);
        people.getPersons().forEach(person -> System.out.println(person.getName()));

        String uid = people.getPersons().stream().findFirst().get().getUid();

        Person updatePerson = Person.builder().uid(uid).age(24)
                .hobbies(Collections.singletonList("Music")).build();

        if (daoOperation.addNode(updatePerson)) {
            System.out.println("Update Transaction completed");
        }

        System.out.println("Person after updation.");
        People updatedPeople = daoOperation.getPeople(name);
        updatedPeople.getPersons().forEach(person -> System.out.println(person.getName()
                + person.getHobbies() + person.getAge())
        );

        System.out.println("To delete the person node");

        Person deletePerson = Person.builder().uid(uid).build();

        if (daoOperation.deleteNode(deletePerson)) {
            System.out.println("Delete Transaction completed");
        }

        People personAfterDeletion = daoOperation.getPeople(name);
        if (personAfterDeletion.getPersons().isEmpty()) {
            System.out.println("No data found.");
        }
    }
}

