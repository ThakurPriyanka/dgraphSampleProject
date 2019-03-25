package com.knoldus.dgraph;

import com.knoldus.dgraph.doa.DOAOperation;
import com.knoldus.dgraph.module.People;
import com.knoldus.dgraph.module.Person;


public class DgraphSampleMain {

    private static DOAOperation doaOperation = new DOAOperation();

    public static void main(final String[] args) {

        // Initialize

        if (doaOperation.alterSchema()) {
            System.out.println("Schema altered.");
        }
        // Create data
        Person newPerson = Person.builder().name("sam").age(24).build();
        if (doaOperation.addNode(newPerson)) {
            System.out.println("Transaction complete");
        }

        People people = doaOperation.getPeople();
        people.getPersons().forEach(person -> System.out.println(person.getName()));

    }
}

