package com.knoldus.dgraph;

import com.knoldus.dgraph.doa.DAOOperation;
import com.knoldus.dgraph.module.Address;
import com.knoldus.dgraph.module.deletePojo.DeleteAddress;
import com.knoldus.dgraph.module.deletePojo.DeleteAddressPojo;
import com.knoldus.dgraph.module.People;
import com.knoldus.dgraph.module.Person;
import com.knoldus.dgraph.module.deletePojo.DeleteListValue;

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
        Person newPerson = Person.builder().name(name).age(23).hobbies(hobbies)
                .address(Collections.singletonList(Address.builder().city("Delhi").state("Delhi").houseNumber("25")
                        .street("Gandhi Nagar").build()))
                .build();
        if (daoOperation.addNode(newPerson)) {
            System.out.println("Transaction complete");
        }

        // Get Person
        People people = daoOperation.getPeople(name);
        people.getPersons().forEach(person -> System.out.println(person.getName()));

        String uid = people.getPersons().stream().findFirst().get().getUid();


        //Updating the Person.
        Person updatePerson = Person.builder().uid(uid).age(24)
                .hobbies(Collections.singletonList("Music")).build();

        if (daoOperation.addNode(updatePerson)) {
            System.out.println("Update Transaction completed");
        }

       //Delete the value in list.
        DeleteListValue deleteList = DeleteListValue.builder().uid(uid)
                .hobbies("Drawning").build();

        if (daoOperation.deleteNode(deleteList)) {
            System.out.println("Delete Transaction for value in the list is completed.");
        }

        //Adding new person.
        daoOperation.addNode(newPerson.toBuilder().name("Priyanka").build());

        System.out.println("Person after updation.");
        People updatedPeople = daoOperation.getPeople(name);
        updatedPeople.getPersons().forEach(person -> System.out.println(person.getName()
                + person.getHobbies() + person.getAge())
        );

        //delete the address node
        String addressDeleteUid = daoOperation.getPerson(uid).getPersons().get(0).getAddress().get(0).getUid();

        if (daoOperation.deleteNode(Address.builder().uid(addressDeleteUid).build())) {
            System.out.println("Delete Transaction for address completed");
        }

        System.out.println("To delete the address node");
        String  addressUid = daoOperation.getPerson(uid).getPersons().get(0).getAddress().get(0).getUid();

        //delete the person node
        DeleteAddressPojo deleteAddressPojo = DeleteAddressPojo.builder()
                .address(DeleteAddress.builder().uid(addressUid).build()).uid(uid).build();

        if (daoOperation.deleteNode(deleteAddressPojo)) {
            System.out.println("Delete Transaction completed");
        }


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

