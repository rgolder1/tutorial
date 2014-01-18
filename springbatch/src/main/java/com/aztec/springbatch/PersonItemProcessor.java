package com.aztec.springbatch;

import org.springframework.batch.item.ItemProcessor;

/**
 * Create an intermediate processor.
 * 
 * A common paradigm in batch processing is to ingest data, transform it, and then pipe it out somewhere else. 
 * Here you write a simple transformer that converts the names to uppercase.
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(firstName, lastName);

        System.out.println("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}
